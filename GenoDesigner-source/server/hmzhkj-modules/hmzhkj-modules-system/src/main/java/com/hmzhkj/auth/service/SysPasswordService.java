package com.hmzhkj.auth.service;

import com.hmzhkj.common.core.constant.CacheConstants;
import com.hmzhkj.common.core.constant.Constants;
import com.hmzhkj.common.core.exception.ServiceException;
import com.hmzhkj.common.redis.service.RedisService;
import com.hmzhkj.framework.utils.SecurityUtils;
import com.hmzhkj.system.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

 
@Component
public class SysPasswordService
{
    @Autowired
    private RedisService redisService;

    private int maxRetryCount = CacheConstants.PASSWORD_MAX_RETRY_COUNT;

    private Long lockTime = CacheConstants.PASSWORD_LOCK_TIME;

    @Autowired
    private SysRecordLogService recordLogService;

     
    private String getCacheKey(String username)
    {
        return CacheConstants.PWD_ERR_CNT_KEY + username;
    }

    public void validate(SysUser user, String password,long startTime)
    {
        String username = user.getUserName();

        Integer retryCount = redisService.getCacheObject(getCacheKey(username));

        if (retryCount == null)
        {
            retryCount = 0;
        }

        if (retryCount >= Integer.valueOf(maxRetryCount).intValue())
        {
            String errMsg = String.format("Password input error %s times, account locked for %s minutes", maxRetryCount, lockTime);
            recordLogService.recordLogininfor("-",username, Constants.LOGIN_FAIL,errMsg, System.currentTimeMillis()-startTime);
            throw new ServiceException(errMsg);
        }

        if (!matches(user, password))
        {
            retryCount = retryCount + 1;
            recordLogService.recordLogininfor("-",username, Constants.LOGIN_FAIL, String.format("Password input error %s times", retryCount), System.currentTimeMillis()-startTime);
            redisService.setCacheObject(getCacheKey(username), retryCount, lockTime, TimeUnit.MINUTES);
            throw new ServiceException("User not present/password incorrect");
        }
        else
        {
            clearLoginRecordCache(username);
        }
    }

    public boolean matches(SysUser user, String rawPassword)
    {
        return SecurityUtils.matchesPassword(rawPassword, user.getPassword());
    }

    public void clearLoginRecordCache(String loginName)
    {
        if (redisService.hasKey(getCacheKey(loginName)))
        {
            redisService.deleteObject(getCacheKey(loginName));
        }
    }
}
