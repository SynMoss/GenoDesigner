package com.hmzhkj.system.domain;

import cn.hutool.core.util.IdUtil;
import com.hmzhkj.common.core.annotation.Excel;
import com.hmzhkj.common.core.annotation.Excels;
import com.hmzhkj.common.core.web.domain.BaseEntity;
import com.hmzhkj.common.core.xss.Xss;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

 
public class SysUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

     
    @Excel(name = "userId", cellType = Excel.ColumnType.NUMERIC, prompt = "userId")
    private String userId;

     
    @Excel(name = "deptId", type = Excel.Type.IMPORT)
    private String deptId;


    @Excel(name = "userName")
    private String userName;

     
    @Excel(name = "nickName")
    private String nickName;

     
    @Excel(name = "email")
    private String email;

     
    @Excel(name = "phonenumber")
    private String phonenumber;

     
    @Excel(name = "sex", readConverterExp = "0=male,1=female,2=unknown")
    private String sex;

     
    private String avatar;

     
    private String password;

     
    @Excel(name = "status", readConverterExp = "0=normal,1=deactivate")
    private String status;

     
    private String delFlag;

     
    @Excel(name = "loginIp", type = Excel.Type.EXPORT)
    private String loginIp;

     
    @Excel(name = "loginDate", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Excel.Type.EXPORT)
    private Date loginDate;

     
    private String isAudit;

     
    @Excels({
        @Excel(name = "deptName", targetAttr = "deptName", type = Excel.Type.EXPORT),
        @Excel(name = "leader", targetAttr = "leader", type = Excel.Type.EXPORT)
    })
    private SysDept dept;

     
    private List<SysRole> roles;

     
    private String[] roleIds;

     
    private String[] postIds;

     
    private String roleId;

     
    private String logonMode;

    public String getLogonMode() {
        return logonMode;
    }

    public void setLogonMode(String logonMode) {
        this.logonMode = logonMode;
    }

    public String getIsAudit() {
        return isAudit;
    }

    public void setIsAudit(String isAudit) {
        this.isAudit = isAudit;
    }

    public SysUser()
    {

    }

    public SysUser(String userId)
    {
        this.userId = userId;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public boolean isAdmin()
    {
        return isAdmin(this.userId);
    }

    public static boolean isAdmin(String userId)
    {
        return "1".equals(userId);
    }

    public String getDeptId()
    {
        return deptId;
    }

    public void setDeptId(String deptId)
    {
        this.deptId = deptId;
    }

    @Xss(message = "User nickname cannot contain script characters")
    @Size(min = 0, max = 30, message = "User nickname length cannot exceed 30 characters")
    public String getNickName()
    {
        return nickName;
    }

    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }

    @Xss(message = "User nickname length cannot exceed 30 characters")
    @NotBlank(message = "User account cannot be empty")
    @Size(min = 0, max = 30, message = "The length of the user account cannot exceed 30 characters")
    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    @Email(message = "Incorrect email format")
    @Size(min = 0, max = 50, message = "The email length cannot exceed 50 characters")
    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    @Size(min = 0, max = 11, message = "The length of the phone number cannot exceed 11 characters")
    public String getPhonenumber()
    {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber)
    {
        this.phonenumber = phonenumber;
    }

    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
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

    public String getLoginIp()
    {
        return loginIp;
    }

    public void setLoginIp(String loginIp)
    {
        this.loginIp = loginIp;
    }

    public Date getLoginDate()
    {
        return loginDate;
    }

    public void setLoginDate(Date loginDate)
    {
        this.loginDate = loginDate;
    }

    public SysDept getDept()
    {
        return dept;
    }

    public void setDept(SysDept dept)
    {
        this.dept = dept;
    }

    public List<SysRole> getRoles()
    {
        return roles;
    }

    public void setRoles(List<SysRole> roles)
    {
        this.roles = roles;
    }

    public String[] getRoleIds()
    {
        return roleIds;
    }

    public void setRoleIds(String[] roleIds)
    {
        this.roleIds = roleIds;
    }

    public String[] getPostIds()
    {
        return postIds;
    }

    public void setPostIds(String[] postIds)
    {
        this.postIds = postIds;
    }

    public String getRoleId()
    {
        return roleId;
    }

    public void setRoleId(String roleId)
    {
        this.roleId = roleId;
    }
    @Override
    public void genId(){
        this.userId = IdUtil.getSnowflakeNextIdStr();
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("deptId", getDeptId())
            .append("userName", getUserName())
            .append("nickName", getNickName())
            .append("email", getEmail())
            .append("phonenumber", getPhonenumber())
            .append("sex", getSex())
            .append("avatar", getAvatar())
            .append("password", getPassword())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("loginIp", getLoginIp())
            .append("loginDate", getLoginDate())
            .append("isAudit", getIsAudit())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("dept", getDept())
            .toString();
    }
}
