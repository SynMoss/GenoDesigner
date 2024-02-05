package com.hmzhkj.system.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hmzhkj.system.domain.SysUserRole;


public interface SysUserRoleMapper
{

    int deleteUserRoleByUserId(String userId);


    int deleteUserRole(String[] ids);


    int countUserRoleByRoleId(String roleId);


    int batchUserRole(List<SysUserRole> userRoleList);


    int deleteUserRoleInfo(SysUserRole userRole);

    int deleteUserRoleInfos(@Param("roleId") String roleId, @Param("userIds") String[] userIds);
}
