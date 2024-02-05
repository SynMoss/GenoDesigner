package com.hmzhkj.framework.utils;

import javax.servlet.http.HttpServletRequest;

import com.hmzhkj.system.model.LoginUser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.hmzhkj.common.core.constant.SecurityConstants;
import com.hmzhkj.common.core.constant.TokenConstants;
import com.hmzhkj.common.core.context.SecurityContextHolder;
import com.hmzhkj.common.core.utils.ServletUtils;
import com.hmzhkj.common.core.utils.StringUtils;


public class SecurityUtils
{

    public static String getUserId()
    {
        return SecurityContextHolder.getUserId();
    }


    public static String getUsername()
    {
        return SecurityContextHolder.getUserName();
    }


    public static String getUserKey()
    {
        return SecurityContextHolder.getUserKey();
    }


    public static LoginUser getLoginUser()
    {
        return SecurityContextHolder.get(SecurityConstants.LOGIN_USER, LoginUser.class);
    }


    public static String getToken()
    {
        return getToken(ServletUtils.getRequest());
    }


    public static String getToken(HttpServletRequest request)
    {

        String token = request.getHeader(TokenConstants.AUTHENTICATION);
        return replaceTokenPrefix(token);
    }


    public static String replaceTokenPrefix(String token)
    {

        if (StringUtils.isNotEmpty(token) && token.startsWith(TokenConstants.PREFIX))
        {
            token = token.replaceFirst(TokenConstants.PREFIX, "");
        }
        return token;
    }


    public static boolean isAdmin(String userId)
    {
        return  "1".equals(userId);
    }


    public static String encryptPassword(String password)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }


    public static boolean matchesPassword(String rawPassword, String encodedPassword)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
