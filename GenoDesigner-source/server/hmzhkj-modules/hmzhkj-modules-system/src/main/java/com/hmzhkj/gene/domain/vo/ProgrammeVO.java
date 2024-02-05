package com.hmzhkj.gene.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmzhkj.common.core.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "programmeVo", description = "Plan information")
public class ProgrammeVO implements Serializable {
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
 
    @Excel(name = "programmeName", width = 15)
    @ApiModelProperty(value = "programmeName")
    private String programmeName;

    @Excel(name = "programmeNumber", width = 15)
    @ApiModelProperty(value = "programmeNumber")
    private String programmeNumber;
 
    @Excel(name = "programmeState 0 View Only 1 editable", width = 15)
    @ApiModelProperty(value = "programmeState 0 View Only 1 editable")
    private Integer programmeState;
  
    @Excel(name = "stateId", width = 15)
    @ApiModelProperty(value = "stateId")
    private String stateId;
  
    @Excel(name = "localFileId", width = 15)
    @ApiModelProperty(value = "localFileId")
    private String localFileId;
 
    @Excel(name = "packFileId", width = 15)
    @ApiModelProperty(value = "packFileId")
    private String packFileId;

    @Excel(name = "configFileId", width = 15)
    @ApiModelProperty(value = "configFileId")
    private String configFileId;

    @Excel(name = "possessStaff", width = 15)
    @ApiModelProperty(value = "possessStaff")
    private String possessStaff;

    @Excel(name = "packState:0Packaged ；1Unpacked", width = 15)
    @ApiModelProperty(value = "packState:0Packaged ；1Unpacked")
    private String packState;


    @Excel(name = "projectId", width = 15)
    @ApiModelProperty(value = "projectId")
    private String projectId;

 
    @Excel(name = "projectName", width = 15)
    @ApiModelProperty(value = "projectName")
    private String projectName;


    @Excel(name = "createStaffName", width = 15)
    @ApiModelProperty(value = "createStaffName")
    private String createStaffName;
 
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date viewedTime;
    private String sourceFileName;
    private String packFileName;
}
