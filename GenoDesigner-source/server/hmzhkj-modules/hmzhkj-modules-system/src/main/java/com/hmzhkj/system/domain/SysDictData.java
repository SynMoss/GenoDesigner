package com.hmzhkj.system.domain;

import cn.hutool.core.util.IdUtil;
import com.hmzhkj.common.core.annotation.Excel;
import com.hmzhkj.common.core.constant.UserConstants;
import com.hmzhkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

 
public class SysDictData extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    @Override
    public void genId(){
        this.dictCode = IdUtil.getSnowflakeNextIdStr();
    }
     
    @Excel(name = "dictCode", cellType = Excel.ColumnType.NUMERIC)
    private String dictCode;

     
    @Excel(name = "dictSort", cellType = Excel.ColumnType.NUMERIC)
    private Long dictSort;

     
    @Excel(name = "dictLabel")
    private String dictLabel;

     
    @Excel(name = "dictValue")
    private String dictValue;

     
    @Excel(name = "dictType")
    private String dictType;

     
    private String cssClass;

     
    private String listClass;

     
    @Excel(name = "isDefault", readConverterExp = "Y=Yes,N=No")
    private String isDefault;

     
    @Excel(name = "status", readConverterExp = "0=normal,1=deactivate")
    private String status;

    public String getDictCode()
    {
        return dictCode;
    }

    public void setDictCode(String dictCode)
    {
        this.dictCode = dictCode;
    }

    public Long getDictSort()
    {
        return dictSort;
    }

    public void setDictSort(Long dictSort)
    {
        this.dictSort = dictSort;
    }

    @NotBlank(message = "Dictionary label cannot be empty")
    @Size(min = 0, max = 100, message = "Dictionary label length cannot exceed 100 characters")
    public String getDictLabel()
    {
        return dictLabel;
    }

    public void setDictLabel(String dictLabel)
    {
        this.dictLabel = dictLabel;
    }

    @NotBlank(message = "Dictionary key value cannot be empty")
    @Size(min = 0, max = 100, message = "The length of dictionary key values cannot exceed 100 characters")
    public String getDictValue()
    {
        return dictValue;
    }

    public void setDictValue(String dictValue)
    {
        this.dictValue = dictValue;
    }

    @NotBlank(message = "Dictionary type cannot be empty")
    @Size(min = 0, max = 100, message = "The length of the dictionary type cannot exceed 100 characters")
    public String getDictType()
    {
        return dictType;
    }

    public void setDictType(String dictType)
    {
        this.dictType = dictType;
    }

    @Size(min = 0, max = 100, message = "The length of the style attribute cannot exceed 100 characters")
    public String getCssClass()
    {
        return cssClass;
    }

    public void setCssClass(String cssClass)
    {
        this.cssClass = cssClass;
    }

    public String getListClass()
    {
        return listClass;
    }

    public void setListClass(String listClass)
    {
        this.listClass = listClass;
    }

    public boolean getDefault()
    {
        return UserConstants.YES.equals(this.isDefault);
    }

    public String getIsDefault()
    {
        return isDefault;
    }

    public void setIsDefault(String isDefault)
    {
        this.isDefault = isDefault;
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
            .append("dictCode", getDictCode())
            .append("dictSort", getDictSort())
            .append("dictLabel", getDictLabel())
            .append("dictValue", getDictValue())
            .append("dictType", getDictType())
            .append("cssClass", getCssClass())
            .append("listClass", getListClass())
            .append("isDefault", getIsDefault())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
