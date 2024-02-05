package com.hmzhkj.system.domain;

import cn.hutool.core.util.IdUtil;
import com.hmzhkj.common.core.web.domain.BaseEntity;
import com.hmzhkj.common.core.xss.Xss;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;


public class SysNotice extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    @Override
    public void genId(){
        this.noticeId = IdUtil.getSnowflakeNextIdStr();
    }

    private String noticeId;


    private String noticeTitle;


    private String noticeType;


    private String noticeContent;


    private String status;

    private List<String> userIdList;

    public List<String> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
    }

    public String getNoticeId()
    {
        return noticeId;
    }

    public void setNoticeId(String noticeId)
    {
        this.noticeId = noticeId;
    }

    public void setNoticeTitle(String noticeTitle)
    {
        this.noticeTitle = noticeTitle;
    }

    @Xss(message = "The announcement title cannot contain script characters")
    @NotBlank(message = "The announcement title cannot contain script characters")
    @Size(min = 0, max = 50, message = "The announcement title cannot exceed 50 characters")
    public String getNoticeTitle()
    {
        return noticeTitle;
    }

    public void setNoticeType(String noticeType)
    {
        this.noticeType = noticeType;
    }

    public String getNoticeType()
    {
        return noticeType;
    }

    public void setNoticeContent(String noticeContent)
    {
        this.noticeContent = noticeContent;
    }

    public String getNoticeContent()
    {
        return noticeContent;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("noticeId", getNoticeId())
            .append("noticeTitle", getNoticeTitle())
            .append("noticeType", getNoticeType())
            .append("noticeContent", getNoticeContent())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
