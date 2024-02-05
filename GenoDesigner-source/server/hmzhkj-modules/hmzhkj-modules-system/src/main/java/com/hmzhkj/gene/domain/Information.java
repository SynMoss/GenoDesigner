package com.hmzhkj.gene.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

 
@Data
public class Information implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

     
    private String programmeId;

     
    private Integer programmeState;

     
    private String possessStaff;

     
    private Integer informationState;

    private Date createTime;

    private String createStaffNo;

    private Date updateTime;

    private String updateStaffNo;

    private String packFileName;

    private String packType;

    @TableField(exist = false)
    private String programmeName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProgrammeId() {
        return programmeId;
    }

    public void setProgrammeId(String programmeId) {
        this.programmeId = programmeId;
    }

    public Integer getProgrammeState() {
        return programmeState;
    }

    public void setProgrammeState(Integer programmeState) {
        this.programmeState = programmeState;
    }

    public String getPossessStaff() {
        return possessStaff;
    }

    public void setPossessStaff(String possessStaff) {
        this.possessStaff = possessStaff;
    }

    public Integer getInformationState() {
        return informationState;
    }

    public void setInformationState(Integer informationState) {
        this.informationState = informationState;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateStaffNo() {
        return createStaffNo;
    }

    public void setCreateStaffNo(String createStaffNo) {
        this.createStaffNo = createStaffNo;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateStaffNo() {
        return updateStaffNo;
    }

    public void setUpdateStaffNo(String updateStaffNo) {
        this.updateStaffNo = updateStaffNo;
    }
}
