package com.hmzhkj.auth.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;

import com.hmzhkj.framework.utils.SecurityUtils;
import com.hmzhkj.system.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hmzhkj.common.core.constant.CacheConstants;
import com.hmzhkj.common.core.constant.SecurityConstants;
import com.hmzhkj.common.core.utils.JwtUtils;
import com.hmzhkj.common.core.utils.ServletUtils;
import com.hmzhkj.common.core.utils.StringUtils;
import com.hmzhkj.common.core.utils.ip.IpUtils;
import com.hmzhkj.common.core.utils.uuid.IdUtils;
import com.hmzhkj.common.redis.service.RedisService;


@Component
public class TokenService
{
    @Autowired
    private RedisService redisService;

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private final static long expireTime = CacheConstants.EXPIRATION;

    private final static String ACCESS_TOKEN = CacheConstants.LOGIN_TOKEN_KEY;

    private final static Long MILLIS_MINUTE_TEN = CacheConstants.REFRESH_TIME * MILLIS_MINUTE;


    public Map<String, Object> createToken(LoginUser loginUser)
    {
        String userId = loginUser.getSysUser().getUserId();
        String token = userId+"_"+IdUtils.fastSimpleUUID();
        String userName = loginUser.getSysUser().getUserName();
        loginUser.setToken(token);
        loginUser.setUserid(userId);
        loginUser.setUsername(userName);
        loginUser.setIpaddr(IpUtils.getIpAddr(ServletUtils.getRequest()));
        refreshToken(loginUser);


        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put(SecurityConstants.USER_KEY, token);
        claimsMap.put(SecurityConstants.DETAILS_USER_ID, userId);
        claimsMap.put(SecurityConstants.DETAILS_USERNAME, userName);


        Map<String, Object> rspMap = new HashMap<>();
        rspMap.put("access_token", JwtUtils.createToken(claimsMap));
        rspMap.put("expires_in", expireTime);
        return rspMap;
    }


    public LoginUser getLoginUser()
    {
        return getLoginUser(ServletUtils.getRequest());
    }

    public LoginUser getLoginUser(HttpServletRequest request)
    {

        String token = SecurityUtils.getToken(request);
        return getLoginUser(token);
    }

    public LoginUser getLoginUser(String token)
    {
        LoginUser user = null;
        try
        {
            if (StringUtils.isNotEmpty(token))
            {
                String userkey = JwtUtils.getUserKey(token);
                user = redisService.getCacheObject(getTokenKey(userkey));
                return user;
            }
        }
        catch (Exception e)
        {
        }
        return user;
    }


    public void setLoginUser(LoginUser loginUser)
    {
        if (StringUtils.isNotNull(loginUser) && StringUtils.isNotEmpty(loginUser.getToken()))
        {
            refreshToken(loginUser);
        }
    }


    public void delLoginUser(String token)
    {
        if (StringUtils.isNotEmpty(token))
        {
            String userkey = JwtUtils.getUserKey(token);
            LoginUser user = redisService.getCacheObject(getTokenKey(userkey));
            if(user != null){
                redisService.deleteObject("design_token:"+user.getUserid());
            }
            redisService.deleteObject(getTokenKey(userkey));
        }
    }
    public void delLoginUserByUserId(String userId)
    {
        if (StringUtils.isNotEmpty(userId))
        {
            String userkey = ACCESS_TOKEN +userId+"_";
            redisService.deleteAll(userkey);
        }
    }

    public void verifyToken(LoginUser loginUser)
    {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN)
        {
            refreshToken(loginUser);
        }
    }


    public void refreshToken(LoginUser loginUser)
    {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * MILLIS_MINUTE);

        String userKey = getTokenKey(loginUser.getToken());
        redisService.setCacheObject(userKey, loginUser, expireTime, TimeUnit.MINUTES);
    }

    private String getTokenKey(String token)
    {
        return ACCESS_TOKEN + token;
    }
}