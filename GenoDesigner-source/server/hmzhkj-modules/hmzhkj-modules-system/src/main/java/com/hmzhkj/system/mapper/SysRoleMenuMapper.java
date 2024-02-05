package com.hmzhkj.system.mapper;

import java.util.List;
import com.hmzhkj.system.domain.SysRoleMenu;

 
public interface SysRoleMenuMapper
{
     
    int checkMenuExistRole(String menuId);

     
    int deleteRoleMenuByRoleId(String roleId);

     
    int deleteRoleMenu(String[] ids);

     
    int batchRoleMenu(List<SysRoleMenu> roleMenuList);
}
