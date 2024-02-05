package com.hmzhkj.system.service;

import java.util.List;

import com.hmzhkj.system.domain.SysDept;
import com.hmzhkj.system.domain.vo.TreeSelect;


public interface ISysDeptService
{

    public List<SysDept> selectDeptList(SysDept dept);

    List<SysDept> selectUserDeptList(SysDept dept);


    public List<TreeSelect> selectDeptTreeList(SysDept dept);

    List<TreeSelect> selectUserDeptTreeList(SysDept dept);


    public List<SysDept> buildDeptTree(List<SysDept> depts);


    public List<TreeSelect> buildDeptTreeSelect(List<SysDept> depts);


    public List<String> selectDeptListByRoleId(String roleId);


    public SysDept selectDeptById(String deptId);


    public int selectNormalChildrenDeptById(String deptId);


    public boolean hasChildByDeptId(String deptId);


    public boolean checkDeptExistUser(String deptId);


    public String checkDeptNameUnique(SysDept dept);


    public void checkDeptDataScope(String deptId);


    public int insertDept(SysDept dept);


    public int updateDept(SysDept dept);


    public int deleteDeptById(String deptId);
}
