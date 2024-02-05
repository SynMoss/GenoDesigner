package com.hmzhkj.system.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import cn.hutool.core.util.IdUtil;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hmzhkj.common.core.annotation.Excel;
import com.hmzhkj.common.core.annotation.Excel.ColumnType;
import com.hmzhkj.common.core.web.domain.BaseEntity;


public class SysPost extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    @Override
    public void genId(){
        this.postId = IdUtil.getSnowflakeNextIdStr();
    }

    @Excel(name = "postId", cellType = ColumnType.NUMERIC)
    private String postId;


    @Excel(name = "postCode")
    private String postCode;


    @Excel(name = "postName")
    private String postName;


    @Excel(name = "postSort")
    private Integer postSort;


    @Excel(name = "status", readConverterExp = "0=normal,1=deactivate")
    private String status;


    private boolean flag = false;

    public String getPostId()
    {
        return postId;
    }

    public void setPostId(String postId)
    {
        this.postId = postId;
    }

    @NotBlank(message = "The position code cannot be empty")
    @Size(min = 0, max = 64, message = "The length of the job code cannot exceed 64 characters")
    public String getPostCode()
    {
        return postCode;
    }

    public void setPostCode(String postCode)
    {
        this.postCode = postCode;
    }

    @NotBlank(message = "The position name cannot be empty")
    @Size(min = 0, max = 50, message = "The length of the position name cannot exceed 50 characters")
    public String getPostName()
    {
        return postName;
    }

    public void setPostName(String postName)
    {
        this.postName = postName;
    }

    public Integer getPostSort()
    {
        return postSort;
    }

    public void setPostSort(Integer postSort)
    {
        this.postSort = postSort;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public boolean isFlag()
    {
        return flag;
    }

    public void setFlag(boolean flag)
    {
        this.flag = flag;
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("postId", getPostId())
            .append("postCode", getPostCode())
            .append("postName", getPostName())
            .append("postSort", getPostSort())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
