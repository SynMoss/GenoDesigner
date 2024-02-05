package com.hmzhkj.system.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import cn.hutool.core.util.IdUtil;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hmzhkj.common.core.annotation.Excel;
import com.hmzhkj.common.core.annotation.Excel.ColumnType;
import com.hmzhkj.common.core.web.domain.BaseEntity;

 
public class SysConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    @Override
    public void genId(){
        this.configId = IdUtil.getSnowflakeNextIdStr();
    }
     
    @Excel(name = "configId", cellType = ColumnType.NUMERIC)
    private String configId;

     
    @Excel(name = "configName")
    private String configName;

     
    @Excel(name = "configKey")
    private String configKey;

     
    @Excel(name = "configValue")
    private String configValue;

     
    @Excel(name = "configType", readConverterExp = "Y=Yes,N=No")
    private String configType;

    public String getConfigId()
    {
        return configId;
    }

    public void setConfigId(String configId)
    {
        this.configId = configId;
    }

    @NotBlank(message = "The parameter name cannot be empty")
    @Size(min = 0, max = 100, message = "The parameter name cannot exceed 100 characters")
    public String getConfigName()
    {
        return configName;
    }

    public void setConfigName(String configName)
    {
        this.configName = configName;
    }

    @NotBlank(message = "The length of the parameter key cannot be empty")
    @Size(min = 0, max = 100, message = "The length of the parameter key name cannot exceed 100 characters")
    public String getConfigKey()
    {
        return configKey;
    }

    public void setConfigKey(String configKey)
    {
        this.configKey = configKey;
    }

    @NotBlank(message = "The parameter key value cannot be empty")
    @Size(min = 0, max = 500, message = "The length of parameter key values cannot exceed 500 characters")
    public String getConfigValue()
    {
        return configValue;
    }

    public void setConfigValue(String configValue)
    {
        this.configValue = configValue;
    }

    public String getConfigType()
    {
        return configType;
    }

    public void setConfigType(String configType)
    {
        this.configType = configType;
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("configId", getConfigId())
            .append("configName", getConfigName())
            .append("configKey", getConfigKey())
            .append("configValue", getConfigValue())
            .append("configType", getConfigType())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
