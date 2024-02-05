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
import java.util.List;


@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName("design_t_b_project_main")
public class
DesignTBProjectMain implements Serializable{
    private static final long serialVersionUID = 1L;


    @TableId(value = "id",type= IdType.ASSIGN_ID)
    private String id;


    @Excel(name = "projectName")
    private String projectName;


    @Excel(name = "projectDescription")
    private String projectDescription;


    @Excel(name = "projectProgrammeNumber")
    private Integer projectProgrammeNumber;


    @Excel(name = "projectMemberNumber")
    private Integer projectMemberNumber;


    @Excel(name = "projectIsDelete")
    private Integer projectIsDelete;


    @Excel(name = "deleteReason")
    private String deleteReason;


    @Excel(name = "viewedTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    private Date viewedTime;


    @Excel(name = "createStaffNo")
    @TableField(fill = FieldFill.INSERT)
    private String createStaffNo;


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


    @Excel(name = "createStaffName")
    @TableField(fill = FieldFill.INSERT)
    private String createStaffName;


    @TableField(exist = false)
    private List<String> createStaffNoList;


    @TableField(exist = false)
    private String loginStaffId;


    @TableField(exist = false)
    private Integer permission;


    @TableField(exist = false)
    private Integer status;


    @TableField(exist = false)
    private Integer programmes;


    @TableField(exist = false)
    private Integer members;
}
