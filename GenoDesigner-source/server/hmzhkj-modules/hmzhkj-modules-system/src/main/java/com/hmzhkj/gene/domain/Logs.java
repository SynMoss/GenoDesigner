package com.hmzhkj.gene.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmzhkj.common.core.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName("logs")
public class Logs implements Serializable{
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private String id;


    @Excel(name = "relationId")
    private String relationId;


    @Excel(name = "staffId")
    private String staffId;


    @Excel(name = "operationType")
    private String operationType;


    @Excel(name = "operationModule")
    private String operationModule;


    @Excel(name = "objectSource")
    private String objectSource;


    @Excel(name = "objectId")
    private String objectId;


    @Excel(name = "operationState")
    private String operationState;


    @Excel(name = "operationLog")
    private String operationLog;


    @Excel(name = "createStaffNo")
    @TableField(fill = FieldFill.INSERT)
    private String createStaffNo;


    @Excel(name = "createStaffName")
    @TableField(fill = FieldFill.INSERT)
    private String createStaffName;


    @Excel(name = "createTime")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")

    private Date createTime;


    @Excel(name = "updateStaffNo")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateStaffNo;


    @Excel(name = "updateTime")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")

    private Date updateTime;
}
