package com.hmzhkj.system.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hmzhkj.system.domain.SysMenu;


public interface SysMenuMapper
{

    List<SysMenu> selectMenuList(SysMenu menu);


    List<String> selectMenuPerms();


    List<SysMenu> selectMenuListByUserId(SysMenu menu);


    List<String> selectMenuPermsByRoleId(String roleId);


    List<String> selectMenuPermsByUserId(String userId);


    List<SysMenu> selectMenuTreeAll();


    List<SysMenu> selectMenuTreeByUserId(String userId);


    List<String> selectMenuListByRoleId(@Param("roleId") String roleId, @Param("menuCheckStrictly") boolean menuCheckStrictly);


    SysMenu selectMenuById(String menuId);


    int hasChildByMenuId(String menuId);


    int insertMenu(SysMenu menu);


    int updateMenu(SysMenu menu);


    int deleteMenuById(String menuId);


    SysMenu checkMenuNameUnique(@Param("menuName") String menuName, @Param("parentId") String parentId);
}
