package com.hmzhkj.system.domain;

import cn.hutool.core.util.IdUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmzhkj.common.core.annotation.Excel;
import com.hmzhkj.common.core.web.domain.BaseEntity;

import java.util.Date;

 
public class SysLogininfor extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    @Override
    public void genId(){
        this.infoId = IdUtil.getSnowflakeNextIdStr();
    }
     
    @Excel(name = "infoId", cellType = Excel.ColumnType.NUMERIC)
    private String infoId;

     
    @Excel(name = "userId")
    private String userId;

     
    @Excel(name = "userName")
    private String userName;

     
    @Excel(name = "status", readConverterExp = "0=success,1=fail")
    private String status;

     
    @Excel(name = "ipaddr")
    private String ipaddr;

     
    @Excel(name = "msg")
    private String msg;

     
    @Excel(name = "consumeTime")
    private long consumeTime;

     
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "accessTime", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date accessTime;

    public String getInfoId()
    {
        return infoId;
    }

    public void setInfoId(String infoId)
    {
        this.infoId = infoId;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getIpaddr()
    {
        return ipaddr;
    }

    public void setIpaddr(String ipaddr)
    {
        this.ipaddr = ipaddr;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public Date getAccessTime()
    {
        return accessTime;
    }

    public void setAccessTime(Date accessTime)
    {
        this.accessTime = accessTime;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getConsumeTime() {
        return consumeTime;
    }

    public void setConsumeTime(long consumeTime) {
        this.consumeTime = consumeTime;
    }
}