package com.hmzhkj.system.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hmzhkj.system.domain.SysRole;
import com.hmzhkj.system.domain.SysUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.hmzhkj.system.service.ISysMenuService;
import com.hmzhkj.system.service.ISysPermissionService;
import com.hmzhkj.system.service.ISysRoleService;

 
@Service
@RequiredArgsConstructor
public class SysPermissionServiceImpl implements ISysPermissionService
{
    private final ISysRoleService roleService;

    private final ISysMenuService menuService;

     
    @Override
    public Set<String> getRolePermission(SysUser user)
    {
        Set<String> roles = new HashSet<>();
                 if (user.isAdmin())
        {
            roles.add("admin");
        }
        else
        {
            roles.addAll(roleService.selectRolePermissionByUserId(user.getUserId()));
        }
        return roles;
    }

     
    @Override
    public Set<String> getMenuPermission(SysUser user)
    {
        Set<String> perms = new HashSet<String>();
                 if (user.isAdmin())
        {
            perms.add("*:*:*");
        }
        else
        {
            List<SysRole> roles = user.getRoles();
            if (!roles.isEmpty() && roles.size() > 1)
            {
                                 for (SysRole role : roles)
                {
                    Set<String> rolePerms = menuService.selectMenuPermsByRoleId(role.getRoleId());
                    role.setPermissions(rolePerms);
                    perms.addAll(rolePerms);
                }
            }
            else
            {
                perms.addAll(menuService.selectMenuPermsByUserId(user.getUserId()));
            }
        }
        return perms;
    }
}
