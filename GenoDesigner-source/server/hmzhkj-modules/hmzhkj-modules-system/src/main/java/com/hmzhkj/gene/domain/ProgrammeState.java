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
@TableName("programme_state")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "programme_state", description = "programme_state_table")
public class ProgrammeState implements Serializable {
    private static final long serialVersionUID = 1L;


    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "createTime")
    private Date createTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "updateTime")
    private Date updateTime;

    @Excel(name = "programmeName", width = 15)
    @ApiModelProperty(value = "programmeName")
    private String programmeName;

    @Excel(name = "programmeNumber", width = 15)
    @ApiModelProperty(value = "programmeNumber")
    private String programmeNumber;

    @Excel(name = "programmeState:0 invalid; 1 Effective", width = 15)
    @ApiModelProperty(value = "programmeState:0 invalid; 1 Effective")
    private Integer programmeState;

    @Excel(name = "isDelete 0 deleted 1 not deleted", width = 15)
    @ApiModelProperty(value = "isDelete 0 deleted 1 not deleted")
    private Integer isDelete;

    @Excel(name = "createStaffNo", width = 15)
    @ApiModelProperty(value = "createStaffNo")
    private String createStaffNo;

    @Excel(name = "updateStaffNo", width = 15)
    @ApiModelProperty(value = "updateStaffNo")
    private String updateStaffNo;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @TableField(exist = false)
    private Date startDate;


    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @TableField(exist = false)
    private Date endDate;

    @TableField(exist = false)
    private String[] states;
}
