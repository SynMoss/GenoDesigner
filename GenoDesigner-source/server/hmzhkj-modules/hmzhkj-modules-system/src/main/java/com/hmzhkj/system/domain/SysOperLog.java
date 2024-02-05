package com.hmzhkj.system.domain;

import cn.hutool.core.util.IdUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmzhkj.common.core.annotation.Excel;
import com.hmzhkj.common.core.web.domain.BaseEntity;

import java.util.Date;


public class SysOperLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    @Override
    public void genId(){
        this.operId = IdUtil.getSnowflakeNextIdStr();
    }

    @Excel(name = "operId", cellType = Excel.ColumnType.NUMERIC)
    private String operId;


    @Excel(name = "operation module")
    private String title;


    @Excel(name = "businessType", readConverterExp = "0=other,1=add,2=update,3=delete,4=authorize,5=export,6=export,7=Forceful retreat,8=generating,9=wipe data")
    private Integer businessType;


    private Integer[] businessTypes;


    @Excel(name = "method")
    private String method;


    @Excel(name = "requestMethod")
    private String requestMethod;


    @Excel(name = "operatorType", readConverterExp = "0=other,1=Backend users,2=Mobile users")
    private Integer operatorType;


    @Excel(name = "operate Name")
    private String operName;


    @Excel(name = "deptName")
    private String deptName;


    @Excel(name = "Request Url")
    private String operUrl;


    @Excel(name = "Operation Ip")
    private String operIp;


    @Excel(name = "Request parameters")
    private String operParam;


    @Excel(name = "return parameters")
    private String jsonResult;


    @Excel(name = "status", readConverterExp = "0=normal,1=abnormal")
    private Integer status;


    @Excel(name = "errorMsg")
    private String errorMsg;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "operation time", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date operTime;

    public String getOperId()
    {
        return operId;
    }

    public void setOperId(String operId)
    {
        this.operId = operId;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Integer getBusinessType()
    {
        return businessType;
    }

    public void setBusinessType(Integer businessType)
    {
        this.businessType = businessType;
    }

    public Integer[] getBusinessTypes()
    {
        return businessTypes;
    }

    public void setBusinessTypes(Integer[] businessTypes)
    {
        this.businessTypes = businessTypes;
    }

    public String getMethod()
    {
        return method;
    }

    public void setMethod(String method)
    {
        this.method = method;
    }

    public String getRequestMethod()
    {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod)
    {
        this.requestMethod = requestMethod;
    }

    public Integer getOperatorType()
    {
        return operatorType;
    }

    public void setOperatorType(Integer operatorType)
    {
        this.operatorType = operatorType;
    }

    public String getOperName()
    {
        return operName;
    }

    public void setOperName(String operName)
    {
        this.operName = operName;
    }

    public String getDeptName()
    {
        return deptName;
    }

    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }

    public String getOperUrl()
    {
        return operUrl;
    }

    public void setOperUrl(String operUrl)
    {
        this.operUrl = operUrl;
    }

    public String getOperIp()
    {
        return operIp;
    }

    public void setOperIp(String operIp)
    {
        this.operIp = operIp;
    }

    public String getOperParam()
    {
        return operParam;
    }

    public void setOperParam(String operParam)
    {
        this.operParam = operParam;
    }

    public String getJsonResult()
    {
        return jsonResult;
    }

    public void setJsonResult(String jsonResult)
    {
        this.jsonResult = jsonResult;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public String getErrorMsg()
    {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }

    public Date getOperTime()
    {
        return operTime;
    }

    public void setOperTime(Date operTime)
    {
        this.operTime = operTime;
    }
}
