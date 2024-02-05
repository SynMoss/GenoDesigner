package com.hmzhkj.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Accessors(chain = true)
public class SysNoticeSend {
    private String noticeId;
    private String userId;
    private Integer isRead;
    private Integer isDisposed;
    @TableField(exist = false)
    private String noticeTitle;
    @TableField(exist = false)
    private String noticeContent;
    @TableField(exist = false)
    private String noticeType;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:SS")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:SS")
    private Date readTime;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    private String createBy;
    private String updateBy;
}
