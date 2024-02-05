package com.hmzhkj.system.domain;

import cn.hutool.core.util.IdUtil;
import com.hmzhkj.common.core.annotation.Excel;
import com.hmzhkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


public class SysDictType extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    @Override
    public void genId(){
        this.dictId = IdUtil.getSnowflakeNextIdStr();
    }

    @Excel(name = "dictId", cellType = Excel.ColumnType.NUMERIC)
    private String dictId;


    @Excel(name = "dictName")
    private String dictName;


    @Excel(name = "dictType")
    private String dictType;


    @Excel(name = "status", readConverterExp = "0=normal,1=deactivate")
    private String status;

    public String getDictId()
    {
        return dictId;
    }

    public void setDictId(String dictId)
    {
        this.dictId = dictId;
    }

    @NotBlank(message = "Dictionary name cannot be empty")
    @Size(min = 0, max = 100, message = "Dictionary type names cannot exceed 100 characters in length")
    public String getDictName()
    {
        return dictName;
    }

    public void setDictName(String dictName)
    {
        this.dictName = dictName;
    }

    @NotBlank(message = "Dictionary type cannot be empty")
    @Size(min = 0, max = 100, message = "The length of the dictionary type cannot exceed 100 characters")
    @Pattern(regexp = "^[a-z][a-z0-9_]*$", message = "The dictionary type must start with a letter and can only be lowercase letters, numbers, and dashes")
    public String getDictType()
    {
        return dictType;
    }

    public void setDictType(String dictType)
    {
        this.dictType = dictType;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("dictId", getDictId())
            .append("dictName", getDictName())
            .append("dictType", getDictType())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
