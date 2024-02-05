package com.hmzhkj.system.mapper;

import java.util.List;

import com.hmzhkj.system.domain.SysDept;
import org.apache.ibatis.annotations.Param;


public interface SysDeptMapper
{

    List<SysDept> selectDeptList(SysDept dept);

    List<SysDept> selectUserDeptList(SysDept dept);


    List<String> selectDeptListByRoleId(@Param("roleId") String roleId, @Param("deptCheckStrictly") boolean deptCheckStrictly);


    SysDept selectDeptById(String deptId);


    List<SysDept> selectChildrenDeptById(String deptId);


    int selectNormalChildrenDeptById(String deptId);


    int hasChildByDeptId(String deptId);

    int checkDeptExistUser(String deptId);


    SysDept checkDeptNameUnique(@Param("deptName") String deptName, @Param("parentId") String parentId);


    int insertDept(SysDept dept);


    int updateDept(SysDept dept);


    void updateDeptStatusNormal(String[] deptIds);


    int updateDeptChildren(@Param("depts") List<SysDept> depts);


    int deleteDeptById(String deptId);
}
