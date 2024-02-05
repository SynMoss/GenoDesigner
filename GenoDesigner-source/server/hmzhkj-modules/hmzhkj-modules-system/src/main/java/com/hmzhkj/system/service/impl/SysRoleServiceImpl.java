package com.hmzhkj.system.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hmzhkj.system.domain.SysRole;
import com.hmzhkj.system.domain.SysUser;
import com.hmzhkj.system.mapper.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hmzhkj.common.core.constant.UserConstants;
import com.hmzhkj.common.core.exception.ServiceException;
import com.hmzhkj.common.core.utils.SpringUtils;
import com.hmzhkj.common.core.utils.StringUtils;
import com.hmzhkj.framework.annotation.DataScope;
import com.hmzhkj.framework.utils.SecurityUtils;
import com.hmzhkj.system.domain.SysRoleDept;
import com.hmzhkj.system.domain.SysRoleMenu;
import com.hmzhkj.system.domain.SysUserRole;
import com.hmzhkj.system.service.ISysRoleService;


@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl implements ISysRoleService
{
    private final SysRoleMapper roleMapper;

    private final SysUserMapper userMapper;

    private final SysRoleMenuMapper roleMenuMapper;

    private final SysUserRoleMapper userRoleMapper;

    private final SysRoleDeptMapper roleDeptMapper;


    @Override
    @DataScope(deptAlias = "d")
    public List<SysRole> selectRoleList(SysRole role){
        String roleName = role.getRoleName();
        String roleKey = role.getRoleKey();
        if(!StringUtils.isEmpty(roleName)){
            role.setRoleName(roleName.trim());
        }
        if(!StringUtils.isEmpty(roleKey)){
            role.setRoleKey(roleKey.trim());
        }
        return roleMapper.selectRoleList(role);
    }

    @Override
    public List selectRoleListByIds(String[] ids) {
        List<SysRole> sysRoles = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            SysRole sysRole = roleMapper.selectRoleById(ids[i]);
            sysRoles.add(sysRole);
        }
        return sysRoles;
    }



    @Override
    public List<SysRole> selectRolesByUserId(String userId)
    {
        List<SysRole> userRoles = roleMapper.selectRolePermissionByUserId(userId);
        List<SysRole> roles = selectRoleAll();
        for (SysRole role : roles)
        {
            for (SysRole userRole : userRoles)
            {
                if (role.getRoleId().equals(userRole.getRoleId()))
                {
                    role.setFlag(true);
                    break;
                }
            }
        }
        return roles;
    }


    @Override
    public Set<String> selectRolePermissionByUserId(String userId)
    {
        List<SysRole> perms = roleMapper.selectRolePermissionByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysRole perm : perms)
        {
            if (StringUtils.isNotNull(perm))
            {
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }


    @Override
    public List<SysRole> selectRoleAll()
    {
        return SpringUtils.getAopProxy(this).selectRoleList(new SysRole());
    }


    @Override
    public List<String> selectRoleListByUserId(String userId)
    {
        return roleMapper.selectRoleListByUserId(userId);
    }


    @Override
    public SysRole selectRoleById(String roleId)
    {
        return roleMapper.selectRoleById(roleId);
    }


    @Override
    public String checkRoleNameUnique(SysRole role)
    {
        SysRole info = roleMapper.checkRoleNameUnique(role.getRoleName());
        if (StringUtils.isNotNull(info) && ! info.getRoleId().equals(role.getRoleId()))
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }


    @Override
    public String checkRoleKeyUnique(SysRole role)
    {
        SysRole info = roleMapper.checkRoleKeyUnique(role.getRoleKey());
        if (StringUtils.isNotNull(info) && !info.getRoleId().equals(role.getRoleId()))
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }


    @Override
    public void checkRoleAllowed(SysRole role)
    {

    }


    @Override
    public void checkRoleDataScope(String roleId)
    {
        if (!SysUser.isAdmin(SecurityUtils.getUserId()))
        {
            SysRole role = new SysRole();
            role.setRoleId(roleId);
            List<SysRole> roles = SpringUtils.getAopProxy(this).selectRoleList(role);
            if (StringUtils.isEmpty(roles))
            {
                throw new ServiceException("No permission to access role data!");
            }
        }
    }


    @Override
    public int countUserRoleByRoleId(String roleId)
    {
        return userRoleMapper.countUserRoleByRoleId(roleId);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertRole(SysRole role)
    {
        role.genId();

        roleMapper.insertRole(role);
        return insertRoleMenu(role);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateRole(SysRole role)
    {

        roleMapper.updateRole(role);

        roleMenuMapper.deleteRoleMenuByRoleId(role.getRoleId());
        return insertRoleMenu(role);
    }


    @Override
    public int updateRoleStatus(SysRole role)
    {
        return roleMapper.updateRole(role);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int authDataScope(SysRole role)
    {

        roleMapper.updateRole(role);

        roleDeptMapper.deleteRoleDeptByRoleId(role.getRoleId());

        return insertRoleDept(role);
    }


    public int insertRoleMenu(SysRole role)
    {
        int rows = 1;

        List<SysRoleMenu> list = new ArrayList<SysRoleMenu>();
        for (String menuId : role.getMenuIds())
        {
            SysRoleMenu rm = new SysRoleMenu();
            rm.setRoleId(role.getRoleId());
            rm.setMenuId(menuId);
            list.add(rm);
        }
        if (list.size() > 0)
        {
            rows = roleMenuMapper.batchRoleMenu(list);
        }
        return rows;
    }


    public int insertRoleDept(SysRole role)
    {
        int rows = 1;

        List<SysRoleDept> list = new ArrayList<SysRoleDept>();
        for (String deptId : role.getDeptIds())
        {
            SysRoleDept rd = new SysRoleDept();
            rd.setRoleId(role.getRoleId());
            rd.setDeptId(deptId);
            list.add(rd);
        }
        if (list.size() > 0)
        {
            rows = roleDeptMapper.batchRoleDept(list);
        }
        return rows;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteRoleById(String roleId)
    {

        roleMenuMapper.deleteRoleMenuByRoleId(roleId);

        roleDeptMapper.deleteRoleDeptByRoleId(roleId);
        return roleMapper.deleteRoleById(roleId);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteRoleByIds(String[] roleIds)
    {
        for (String roleId : roleIds)
        {
            checkRoleAllowed(new SysRole(roleId));
            checkRoleDataScope(roleId);
            SysRole role = selectRoleById(roleId);
            if (countUserRoleByRoleId(roleId) > 0)
            {
                throw new ServiceException(String.format("%1$s Assigned, cannot be deleted", role.getRoleName()));
            }
        }

        roleMenuMapper.deleteRoleMenu(roleIds);

        roleDeptMapper.deleteRoleDept(roleIds);
        return roleMapper.deleteRoleByIds(roleIds);
    }


    @Override
    public int deleteAuthUser(SysUserRole userRole)
    {
        return userRoleMapper.deleteUserRoleInfo(userRole);
    }


    @Override
    public int deleteAuthUsers(String roleId, String[] userIds)
    {
        return userRoleMapper.deleteUserRoleInfos(roleId, userIds);
    }

    @Override
    public int insertAuthUsers(String roleId, String[] userIds)
    {
        SysUser sysUser=new SysUser();

        List<SysUserRole> list = new ArrayList<>();
        for (String userId : userIds)
        {
            sysUser=userMapper.selectUserById(userId);
            if("2".equals(sysUser.getDelFlag())){
                throw new ServiceException(String.format("%1$s User does not exist, please reselect", sysUser.getUserName()));
            }
            SysUserRole ur = new SysUserRole();
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            list.add(ur);
        }
        userRoleMapper.deleteUserRole(userIds);
        return userRoleMapper.batchUserRole(list);
    }
}
