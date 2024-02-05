package com.hmzhkj.system.mapper;

import java.util.List;

import com.hmzhkj.system.domain.SysRole;


public interface SysRoleMapper
{

    List<SysRole> selectRoleList(SysRole role);





    List<SysRole> selectRolePermissionByUserId(String userId);


    List<SysRole> selectRoleAll();

    List<String> selectRoleListByUserId(String userId);


    SysRole selectRoleById(String roleId);


    List<SysRole> selectRolesByUserName(String userName);


    SysRole checkRoleNameUnique(String roleName);


    SysRole checkRoleKeyUnique(String roleKey);


    int updateRole(SysRole role);


    int insertRole(SysRole role);


    int deleteRoleById(String roleId);


    int deleteRoleByIds(String[] roleIds);
}
