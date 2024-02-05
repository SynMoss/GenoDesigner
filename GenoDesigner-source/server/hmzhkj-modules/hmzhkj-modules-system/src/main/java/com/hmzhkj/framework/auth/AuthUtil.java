package com.hmzhkj.framework.auth;

import com.hmzhkj.framework.annotation.RequiresRoles;
import com.hmzhkj.framework.annotation.RequiresPermissions;
import com.hmzhkj.system.model.LoginUser;


public class AuthUtil
{

    public static AuthLogic authLogic = new AuthLogic();


    public static void logout()
    {
        authLogic.logout();
    }


    public static void logoutByToken(String token)
    {
        authLogic.logoutByToken(token);
    }
    public static void logoutByUserIds(String[] userIds){
        for (String userId : userIds) {
            authLogic.logoutByUserId(userId);
        }
    }

    public static void checkLogin()
    {
        authLogic.checkLogin();
    }


    public static LoginUser getLoginUser(String token)
    {
        return authLogic.getLoginUser(token);
    }


    public static void verifyLoginUserExpire(LoginUser loginUser)
    {
        authLogic.verifyLoginUserExpire(loginUser);
    }


    public static boolean hasRole(String role)
    {
        return authLogic.hasRole(role);
    }


    public static void checkRole(String role)
    {
        authLogic.checkRole(role);
    }


    public static void checkRole(RequiresRoles requiresRoles)
    {
        authLogic.checkRole(requiresRoles);
    }


    public static void checkRoleAnd(String... roles)
    {
        authLogic.checkRoleAnd(roles);
    }


    public static void checkRoleOr(String... roles)
    {
        authLogic.checkRoleOr(roles);
    }


    public static boolean hasPermi(String permission)
    {
        return authLogic.hasPermi(permission);
    }


    public static void checkPermi(String permission)
    {
        authLogic.checkPermi(permission);
    }


    public static void checkPermi(RequiresPermissions requiresPermissions)
    {
        authLogic.checkPermi(requiresPermissions);
    }


    public static void checkPermiAnd(String... permissions)
    {
        authLogic.checkPermiAnd(permissions);
    }


    public static void checkPermiOr(String... permissions)
    {
        authLogic.checkPermiOr(permissions);
    }
}
