package com.hmzhkj.system.service;

import com.hmzhkj.system.domain.SysUser;

import java.util.Set;


 
public interface ISysPermissionService
{
     
    public Set<String> getRolePermission(SysUser user);

     
    public Set<String> getMenuPermission(SysUser user);
}
