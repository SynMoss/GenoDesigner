package com.hmzhkj.gene.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("model_info")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "model_info", description = "Model information")
public class ModelInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @Excel(name = "createStaffNo", width = 15)
    @ApiModelProperty(value = "createStaffNo")
    private String createStaffNo;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "createTime")
    private Date createTime;

    @Excel(name = "updateStaffNo", width = 15)
    @ApiModelProperty(value = "updateStaffNo")
    private String updateStaffNo;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "updateTime")
    private Date updateTime;

    @Excel(name = "modelName", width = 15)
    @ApiModelProperty(value = "modelName")
    private String modelName;

    @Excel(name = "number", width = 15)
    @ApiModelProperty(value = "number")
    private String number;

    @Excel(name = "Model status: 0 failure; 1 Effective", width = 15)
    @ApiModelProperty(value = "Model status: 0 failure; 1 Effective")
    private Integer modelState;

    @Excel(name = "isDelete", width = 15)
    @ApiModelProperty(value = "isDelete 0 deleted 1 not deleted")
    private Integer isDelete;

    @Excel(name = "usedLarger", width = 15)
    @ApiModelProperty(value = "usedLarger")
    private Integer usedLarger;

    @Excel(name = "Interface identification", width = 15)
    @ApiModelProperty(value = "Interface identification")
    private String interfaceId;


    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @TableField(exist = false)
    private Date startDate;


    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @TableField(exist = false)
    private Date endDate;


    @TableField(exist = false)
    private String[] states;
}
