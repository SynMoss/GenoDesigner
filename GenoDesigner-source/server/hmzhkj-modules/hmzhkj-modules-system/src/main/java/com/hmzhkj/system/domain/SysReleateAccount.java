package com.hmzhkj.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmzhkj.common.core.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


@Data
@TableName("sys_releate_account")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "sys_releate_account ", description = "Related accounts")
public class SysReleateAccount implements Serializable {
    private static final long serialVersionUID = 1L;


    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "createBy")
    private String createBy;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "createTime")
    private Date createTime;

    @ApiModelProperty(value = "updateBy")
    private String updateBy;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "updateTime")
    private Date updateTime;

    @Excel(name = "systemName", width = 15)
    @ApiModelProperty(value = "systemName")
    private String systemName;

    @Excel(name = "systemCode", width = 15)
    @ApiModelProperty(value = "systemCode")
    private String systemCode;

    @Excel(name = "bindStatus:0.unbound,1.bound", width = 15)
    @ApiModelProperty(value = "bindStatus:0.unbound,1.bound")
    private Integer bindStatus;

    @TableField(updateStrategy = FieldStrategy.IGNORED )
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "bindTime")
    private Date bindTime;

    @TableField(updateStrategy = FieldStrategy.IGNORED )
    @Excel(name = "Bind account", width = 15)
    @ApiModelProperty(value = "Bind account")
    private String bindAccount;

    @TableField(updateStrategy = FieldStrategy.IGNORED )
    @Excel(name = "Bind password", width = 15)
    @ApiModelProperty(value = "Bind password")
    private String bindPassword;
}
