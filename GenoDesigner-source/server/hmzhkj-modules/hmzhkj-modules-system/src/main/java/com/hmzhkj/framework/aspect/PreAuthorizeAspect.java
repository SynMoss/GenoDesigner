package com.hmzhkj.framework.aspect;

import java.lang.reflect.Method;

import com.hmzhkj.framework.annotation.RequiresRoles;
import com.hmzhkj.framework.auth.AuthUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import com.hmzhkj.framework.annotation.RequiresLogin;
import com.hmzhkj.framework.annotation.RequiresPermissions;

 
@Aspect
@Component
public class PreAuthorizeAspect
{
     
    public PreAuthorizeAspect()
    {
    }

     
    public static final String POINTCUT_SIGN = " @annotation(com.hmzhkj.framework.annotation.RequiresLogin) || "
            + "@annotation(com.hmzhkj.framework.annotation.RequiresPermissions) || "
            + "@annotation(com.hmzhkj.framework.annotation.RequiresRoles)";

     
    @Pointcut(POINTCUT_SIGN)
    public void pointcut()
    {
    }

     
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable
    {
                 MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        checkMethodAnnotation(signature.getMethod());
        try
        {
                         Object obj = joinPoint.proceed();
            return obj;
        }
        catch (Throwable e)
        {
            throw e;
        }
    }

     
    public void checkMethodAnnotation(Method method)
    {
                 RequiresLogin requiresLogin = method.getAnnotation(RequiresLogin.class);
        if (requiresLogin != null)
        {
            AuthUtil.checkLogin();
        }

                 RequiresRoles requiresRoles = method.getAnnotation(RequiresRoles.class);
        if (requiresRoles != null)
        {
            AuthUtil.checkRole(requiresRoles);
        }

                 RequiresPermissions requiresPermissions = method.getAnnotation(RequiresPermissions.class);
        if (requiresPermissions != null)
        {
            AuthUtil.checkPermi(requiresPermissions);
        }
    }
}
