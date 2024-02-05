package com.hmzhkj.common.core.utils;

import java.util.Map;

import com.hmzhkj.common.core.constant.SecurityConstants;
import com.hmzhkj.common.core.constant.TokenConstants;
import com.hmzhkj.common.core.text.Convert;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


public class JwtUtils
{
    public static String secret = TokenConstants.SECRET;


    public static String createToken(Map<String, Object> claims)
    {
        String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
        return token;
    }


    public static Claims parseToken(String token)
    {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }


    public static String getUserKey(String token)
    {
        Claims claims = parseToken(token);
        return getValue(claims, SecurityConstants.USER_KEY);
    }


    public static String getUserKey(Claims claims)
    {
        return getValue(claims, SecurityConstants.USER_KEY);
    }


    public static String getUserId(String token)
    {
        Claims claims = parseToken(token);
        return getValue(claims, SecurityConstants.DETAILS_USER_ID);
    }


    public static String getUserId(Claims claims)
    {
        return getValue(claims, SecurityConstants.DETAILS_USER_ID);
    }


    public static String getUserName(String token)
    {
        Claims claims = parseToken(token);
        return getValue(claims, SecurityConstants.DETAILS_USERNAME);
    }


    public static String getUserName(Claims claims)
    {
        return getValue(claims, SecurityConstants.DETAILS_USERNAME);
    }


    public static String getValue(Claims claims, String key)
    {
        return Convert.toStr(claims.get(key), "");
    }
}
