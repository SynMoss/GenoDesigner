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
@TableName("design_t_b_project_members")
public class DesignTBProjectMembers implements Serializable{
    private static final long serialVersionUID = 1L;


    @TableId(value = "id",type= IdType.ASSIGN_ID)
    private String id;


    @Excel(name = "projectId")
    private String projectId;


    @Excel(name = "staffId")
    private String staffId;


    @Excel(name = "status")
    private Integer status;


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


    @Excel(name = "permission")
    private String permission;
}
