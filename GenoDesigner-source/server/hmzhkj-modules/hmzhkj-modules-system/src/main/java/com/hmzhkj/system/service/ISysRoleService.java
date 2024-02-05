package com.hmzhkj.system.service;

import java.util.List;
import java.util.Set;

import com.hmzhkj.system.domain.SysRole;
import com.hmzhkj.system.domain.SysUserRole;


public interface ISysRoleService
{

    public List<SysRole> selectRoleList(SysRole role);

    public List selectRoleListByIds(String[] ids);


    public List<SysRole> selectRolesByUserId(String userId);


    public Set<String> selectRolePermissionByUserId(String userId);


    public List<SysRole> selectRoleAll();


    public List<String> selectRoleListByUserId(String userId);


    public SysRole selectRoleById(String roleId);


    public String checkRoleNameUnique(SysRole role);


    public String checkRoleKeyUnique(SysRole role);


    public void checkRoleAllowed(SysRole role);


    public void checkRoleDataScope(String roleId);


    public int countUserRoleByRoleId(String roleId);


    public int insertRole(SysRole role);


    public int updateRole(SysRole role);


    public int updateRoleStatus(SysRole role);


    public int authDataScope(SysRole role);


    public int deleteRoleById(String roleId);


    public int deleteRoleByIds(String[] roleIds);


    public int deleteAuthUser(SysUserRole userRole);


    public int deleteAuthUsers(String roleId, String[] userIds);


    public int insertAuthUsers(String roleId, String[] userIds);
}
