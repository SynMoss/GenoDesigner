package com.hmzhkj.system.service;

import java.util.List;
import java.util.Set;
import com.hmzhkj.system.domain.SysMenu;
import com.hmzhkj.system.domain.vo.RouterVo;
import com.hmzhkj.system.domain.vo.TreeSelect;


public interface ISysMenuService
{

    public List<SysMenu> selectMenuList(String userId);


    public List<SysMenu> selectMenuList(SysMenu menu, String userId);


    public Set<String> selectMenuPermsByUserId(String userId);


    public Set<String> selectMenuPermsByRoleId(String roleId);


    public List<SysMenu> selectMenuTreeByUserId(String userId);


    public List<String> selectMenuListByRoleId(String roleId);


    public List<RouterVo> buildMenus(List<SysMenu> menus);


    public List<SysMenu> buildMenuTree(List<SysMenu> menus);


    public List<TreeSelect> buildMenuTreeSelect(List<SysMenu> menus);


    public SysMenu selectMenuById(String menuId);


    public boolean hasChildByMenuId(String menuId);


    public boolean checkMenuExistRole(String menuId);


    public int insertMenu(SysMenu menu);


    public int updateMenu(SysMenu menu);


    public int deleteMenuById(String menuId);


    public String checkMenuNameUnique(SysMenu menu);
}
