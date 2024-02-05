package com.hmzhkj.system.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.hmzhkj.system.domain.SysDept;
import com.hmzhkj.system.domain.SysRole;
import com.hmzhkj.system.domain.SysUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.hmzhkj.common.core.constant.UserConstants;
import com.hmzhkj.common.core.exception.ServiceException;
import com.hmzhkj.common.core.utils.SpringUtils;
import com.hmzhkj.common.core.utils.StringUtils;
import com.hmzhkj.framework.annotation.DataScope;
import com.hmzhkj.framework.utils.SecurityUtils;
import com.hmzhkj.system.domain.vo.TreeSelect;
import com.hmzhkj.system.mapper.SysDeptMapper;
import com.hmzhkj.system.mapper.SysRoleMapper;
import com.hmzhkj.system.service.ISysDeptService;


@Service
@RequiredArgsConstructor
public class SysDeptServiceImpl implements ISysDeptService
{
    private final SysDeptMapper deptMapper;

    private final SysRoleMapper roleMapper;


    @Override
    @DataScope(deptAlias = "d")
    public List<SysDept> selectDeptList(SysDept dept){
        String name = dept.getDeptName();
        if(!StringUtils.isEmpty(name)){
            dept.setDeptName(name.trim());
        }
        return deptMapper.selectDeptList(dept);
    }


    @Override
    @DataScope(deptAlias = "d")
    public List<SysDept> selectUserDeptList(SysDept dept){
        String name = dept.getDeptName();
        if(!StringUtils.isEmpty(name)){
            dept.setDeptName(name.trim());
        }
        return deptMapper.selectUserDeptList(dept);
    }


    @Override
    public List<TreeSelect> selectDeptTreeList(SysDept dept)
    {
        List<SysDept> depts = SpringUtils.getAopProxy(this).selectDeptList(dept);
        return buildDeptTreeSelect(depts);
    }


    @Override
    public List<TreeSelect> selectUserDeptTreeList(SysDept dept)
    {
        List<SysDept> depts = SpringUtils.getAopProxy(this).selectUserDeptList(dept);
        return buildDeptTreeSelect(depts);
    }


    @Override
    public List<SysDept> buildDeptTree(List<SysDept> depts)
    {
        List<SysDept> returnList = new ArrayList<>();
        List<String> tempList = new ArrayList<>();
        for (SysDept dept : depts)
        {
            tempList.add(dept.getDeptId());
        }
        for (SysDept dept : depts)
        {

            if (!tempList.contains(dept.getParentId()))
            {
                recursionFn(depts, dept);
                returnList.add(dept);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = depts;
        }
        return returnList;
    }


    @Override
    public List<TreeSelect> buildDeptTreeSelect(List<SysDept> depts)
    {
        List<SysDept> deptTrees = buildDeptTree(depts);
        return deptTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }


    @Override
    public List<String> selectDeptListByRoleId(String roleId)
    {
        SysRole role = roleMapper.selectRoleById(roleId);
        return deptMapper.selectDeptListByRoleId(roleId, role.isDeptCheckStrictly());
    }


    @Override
    public SysDept selectDeptById(String deptId)
    {
        return deptMapper.selectDeptById(deptId);
    }


    @Override
    public int selectNormalChildrenDeptById(String deptId)
    {
        return deptMapper.selectNormalChildrenDeptById(deptId);
    }


    @Override
    public boolean hasChildByDeptId(String deptId)
    {
        int result = deptMapper.hasChildByDeptId(deptId);
        return result > 0;
    }


    @Override
    public boolean checkDeptExistUser(String deptId)
    {
        int result = deptMapper.checkDeptExistUser(deptId);
        return result > 0;
    }


    @Override
    public String checkDeptNameUnique(SysDept dept)
    {
        SysDept info = deptMapper.checkDeptNameUnique(dept.getDeptName(), dept.getParentId());
        if (StringUtils.isNotNull(info) && !info.getDeptId().equals(dept.getDeptId()))
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }


    @Override
    public void checkDeptDataScope(String deptId)
    {
        if (!SysUser.isAdmin(SecurityUtils.getUserId()))
        {
            SysDept dept = new SysDept();
            dept.setDeptId(deptId);
            List<SysDept> depts = SpringUtils.getAopProxy(this).selectDeptList(dept);
            if (StringUtils.isEmpty(depts))
            {
                throw new ServiceException("No permission to access department data！");
            }
        }
    }


    @Override
    public int insertDept(SysDept dept)
    {
        SysDept info = deptMapper.selectDeptById(dept.getParentId());

        if (!UserConstants.DEPT_NORMAL.equals(info.getStatus()))
        {
            throw new ServiceException("Department deactivated, no new additions allowed");
        }
        dept.setAncestors(info.getAncestors() + "," + dept.getParentId());
        dept.genId();
        return deptMapper.insertDept(dept);
    }


    @Override
    public int updateDept(SysDept dept)
    {
        SysDept newParentDept = deptMapper.selectDeptById(dept.getParentId());
        SysDept oldDept = deptMapper.selectDeptById(dept.getDeptId());
        if (StringUtils.isNotNull(newParentDept) && StringUtils.isNotNull(oldDept))
        {
            String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getDeptId();
            String oldAncestors = oldDept.getAncestors();
            dept.setAncestors(newAncestors);
            updateDeptChildren(dept.getDeptId(), newAncestors, oldAncestors);
        }
        int result = deptMapper.updateDept(dept);
        if (UserConstants.DEPT_NORMAL.equals(dept.getStatus()) && StringUtils.isNotEmpty(dept.getAncestors())
                && !StringUtils.equals("0", dept.getAncestors()))
        {

            updateParentDeptStatusNormal(dept);
        }
        return result;
    }


    private void updateParentDeptStatusNormal(SysDept dept)
    {
        String ancestors = dept.getAncestors();
        String[] deptIds = ancestors.split(",");
        deptMapper.updateDeptStatusNormal(deptIds);
    }


    public void updateDeptChildren(String deptId, String newAncestors, String oldAncestors)
    {
        List<SysDept> children = deptMapper.selectChildrenDeptById(deptId);
        for (SysDept child : children)
        {
            child.setAncestors(child.getAncestors().replaceFirst(oldAncestors, newAncestors));
        }
        if (children.size() > 0)
        {
            deptMapper.updateDeptChildren(children);
        }
    }


    @Override
    public int deleteDeptById(String deptId)
    {
        return deptMapper.deleteDeptById(deptId);
    }


    private void recursionFn(List<SysDept> list, SysDept t)
    {

        List<SysDept> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysDept tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                recursionFn(list, tChild);
            }
        }
    }


    private List<SysDept> getChildList(List<SysDept> list, SysDept t)
    {
        List<SysDept> tlist = new ArrayList<SysDept>();
        Iterator<SysDept> it = list.iterator();
        while (it.hasNext())
        {
            SysDept n = it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().equals(t.getDeptId()))
            {
                tlist.add(n);
            }
        }
        return tlist;
    }


    private boolean hasChild(List<SysDept> list, SysDept t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }
}
