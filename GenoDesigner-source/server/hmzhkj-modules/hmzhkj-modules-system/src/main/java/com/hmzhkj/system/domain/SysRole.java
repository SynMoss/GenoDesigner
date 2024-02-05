package com.hmzhkj.system.domain;

import cn.hutool.core.util.IdUtil;
import com.hmzhkj.common.core.annotation.Excel;
import com.hmzhkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

public class SysRole extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    @Override
    public void genId(){
        this.roleId = IdUtil.getSnowflakeNextIdStr();
    }

    @Excel(name = "roleId", cellType = Excel.ColumnType.NUMERIC)
    private String roleId;


    @Excel(name = "roleName")
    private String roleName;


    @Excel(name = "RoleKey")
    private String roleKey;


    @Excel(name = "Role sorting")
    private Integer roleSort;


    @Excel(name = "dataScope", readConverterExp = "1=All data permissions,2=Custom data permissions,3=Data permissions for this department,4=Data permissions for this department and below,5=Only personal data permissions")
    private String dataScope;


    private boolean menuCheckStrictly;


    private boolean deptCheckStrictly;


    @Excel(name = "status", readConverterExp = "0=normal,1=deactivate")
    private String status;


    private String delFlag;


    private boolean flag = false;


    private String[] menuIds;


    private String[] deptIds;


    private Set<String> permissions;

    public SysRole()
    {

    }

    public SysRole(String roleId)
    {
        this.roleId = roleId;
    }

    public String getRoleId()
    {
        return roleId;
    }

    public void setRoleId(String roleId)
    {
        this.roleId = roleId;
    }

    public boolean isAdmin()
    {
        return isAdmin(this.roleId);
    }

    public static boolean isAdmin(String roleId)
    {
        return "1".equals(roleId);
    }

    @NotBlank(message = "Role name cannot be empty")
    @Size(min = 0, max = 30, message = "The length of the character name cannot exceed 30 characters")
    public String getRoleName()
    {
        return roleName;
    }

    public void setRoleName(String roleName)
    {
        this.roleName = roleName;
    }

    @NotBlank(message = "The permission character cannot be empty")
    @Size(min = 0, max = 100, message = "The length of permission characters cannot exceed 100 characters")
    public String getRoleKey()
    {
        return roleKey;
    }

    public void setRoleKey(String roleKey)
    {
        this.roleKey = roleKey;
    }

    public Integer getRoleSort()
    {
        return roleSort;
    }

    public void setRoleSort(Integer roleSort)
    {
        this.roleSort = roleSort;
    }

    public String getDataScope()
    {
        return dataScope;
    }

    public void setDataScope(String dataScope)
    {
        this.dataScope = dataScope;
    }

    public boolean isMenuCheckStrictly()
    {
        return menuCheckStrictly;
    }

    public void setMenuCheckStrictly(boolean menuCheckStrictly)
    {
        this.menuCheckStrictly = menuCheckStrictly;
    }

    public boolean isDeptCheckStrictly()
    {
        return deptCheckStrictly;
    }

    public void setDeptCheckStrictly(boolean deptCheckStrictly)
    {
        this.deptCheckStrictly = deptCheckStrictly;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public boolean isFlag()
    {
        return flag;
    }

    public void setFlag(boolean flag)
    {
        this.flag = flag;
    }

    public String[] getMenuIds()
    {
        return menuIds;
    }

    public void setMenuIds(String[] menuIds)
    {
        this.menuIds = menuIds;
    }

    public String[] getDeptIds()
    {
        return deptIds;
    }

    public void setDeptIds(String[] deptIds)
    {
        this.deptIds = deptIds;
    }

    public Set<String> getPermissions()
    {
        return permissions;
    }

    public void setPermissions(Set<String> permissions)
    {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("roleId", getRoleId())
            .append("roleName", getRoleName())
            .append("roleKey", getRoleKey())
            .append("roleSort", getRoleSort())
            .append("dataScope", getDataScope())
            .append("menuCheckStrictly", isMenuCheckStrictly())
            .append("deptCheckStrictly", isDeptCheckStrictly())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
