package com.hmzhkj.framework.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import com.hmzhkj.common.core.constant.SecurityConstants;
import com.hmzhkj.common.core.exception.InnerAuthException;
import com.hmzhkj.common.core.utils.ServletUtils;
import com.hmzhkj.common.core.utils.StringUtils;
import com.hmzhkj.framework.annotation.InnerAuth;


@Aspect
@Component
public class InnerAuthAspect implements Ordered
{
    @Around("@annotation(innerAuth)")
    public Object innerAround(ProceedingJoinPoint point, InnerAuth innerAuth) throws Throwable
    {
        String source = ServletUtils.getRequest().getHeader(SecurityConstants.FROM_SOURCE);

        if (!StringUtils.equals(SecurityConstants.INNER, source))
        {
            throw new InnerAuthException("No internal access permission, access not allowed");
        }

        String userid = ServletUtils.getRequest().getHeader(SecurityConstants.DETAILS_USER_ID);
        String username = ServletUtils.getRequest().getHeader(SecurityConstants.DETAILS_USERNAME);

        if (innerAuth.isUser() && (StringUtils.isEmpty(userid) || StringUtils.isEmpty(username)))
        {
            throw new InnerAuthException("No user information set, access not allowed ");
        }
        return point.proceed();
    }


    @Override
    public int getOrder()
    {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }
}
