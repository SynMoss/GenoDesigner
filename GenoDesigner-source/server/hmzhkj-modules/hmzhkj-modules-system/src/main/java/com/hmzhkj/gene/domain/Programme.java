package com.hmzhkj.gene.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmzhkj.common.core.annotation.Excel;
import com.hmzhkj.common.core.utils.StringUtils;
import com.hmzhkj.gene.model.Annotation;
import com.hmzhkj.gene.model.AnnotationTypeHandle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Data
@TableName(value = "programme",autoResultMap = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "programme", description = "programme information")
public class Programme implements Serializable {
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

    @Excel(name = "programmeState 0 View Only 1 Editable", width = 15)
    @ApiModelProperty(value = "programmeState 0 View Only 1 Editable")
    private Integer programmeState;

    @Excel(name = "stateId", width = 15)
    @ApiModelProperty(value = "stateId")
    private String stateId;

    @Excel(name = "configFileId", width = 15)
    @ApiModelProperty(value = "configFileId")
    private String configFileId;

    @Excel(name = "possessStaff", width = 15)
    @ApiModelProperty(value = "possessStaff")
    private String possessStaff;

    @Excel(name = "packState:0 has been packaged; 1 Unpacked", width = 15)
    @ApiModelProperty(value = "packState:0 has been packaged; 1 Unpacked")
    private Integer packState;


    @Excel(name = "projectId", width = 15)
    @ApiModelProperty(value = "projectId")
    private String projectId;


    @Excel(name = "createStaffName", width = 15)
    @ApiModelProperty(value = "createStaffName")
    private String createStaffName;

    @TableField(exist = false)
    private String orderByColumn;

    @TableField(exist = false)
    private String isAsc;

    @TableField(typeHandler = AnnotationTypeHandle.class)
    private List<Annotation> annotationList;


    @TableField(exist = false)
    private String[] packStates;

    @TableField(exist = false)
    private String templateId;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date viewedTime;

    private String sourceFileName;
    private String packFileName;
    private String sourceFileMergeName;
    @TableField(exist = false)
    private String sourceFileNameShow;
    private Boolean saveFeature;
    public String getSourceFileNameShow(){
        if(StringUtils.isNotEmpty(sourceFileName)){
            String[] strs = sourceFileName.split(",");
            StringBuilder sb = new StringBuilder();
            for (String str : strs) {
                int index = str.indexOf("-");
                if(index>-1){
                    sb.append(str.substring(index+1));
                }else{
                    sb.append(str);
                }
                sb.append(", ");
            }
            sb.delete(sb.length()-2,sb.length()-1);
            return sb.toString();
        }
        return "";
    }

    @Excel(name = "carrier", width = 15)
    @ApiModelProperty(value = "carrier")
    private String carrier;

    @Excel(name = "Annotation Database", width = 15)
    @ApiModelProperty(value = "Annotation Database")
    private String databaseForAnnotation;

    private String packType;
    private Integer chooseFile;
    @TableField(exist = false)
    private List<Sequence> sequenceList;
    private Integer maxFeaturesToShow;
    public int getMaxFeaturesToShowNoNull(){
        return maxFeaturesToShow==null?2000:maxFeaturesToShow;
    }
    private Integer sequenceLengthToShow;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Map<String,String>> molecularPack;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String,String> runningState;


}
