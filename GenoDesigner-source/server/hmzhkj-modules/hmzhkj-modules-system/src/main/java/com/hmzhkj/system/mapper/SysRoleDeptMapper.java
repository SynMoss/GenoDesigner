package com.hmzhkj.system.mapper;

import java.util.List;
import com.hmzhkj.system.domain.SysRoleDept;

 
public interface SysRoleDeptMapper
{
     
    int deleteRoleDeptByRoleId(String roleId);

     
    int deleteRoleDept(String[] ids);

     
    int selectCountRoleDeptByDeptId(String deptId);

     
    int batchRoleDept(List<SysRoleDept> roleDeptList);
}
