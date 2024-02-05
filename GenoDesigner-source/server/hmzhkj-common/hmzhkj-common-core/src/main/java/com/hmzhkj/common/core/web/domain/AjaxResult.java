package com.hmzhkj.common.core.web.domain;

import com.hmzhkj.common.core.constant.HttpStatus;
import com.hmzhkj.common.core.utils.StringUtils;

import java.util.HashMap;
import java.util.Objects;


public class AjaxResult extends HashMap<String, Object>
{
    private static final long serialVersionUID = 1L;


    public static final String CODE_TAG = "code";


    public static final String MSG_TAG = "msg";


    public static final String DATA_TAG = "data";


    public AjaxResult()
    {
    }


    public AjaxResult(int code, String msg)
    {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
    }


    public AjaxResult(int code, String msg, Object data)
    {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        if (StringUtils.isNotNull(data))
        {
            super.put(DATA_TAG, data);
        }
    }
    public Object getData(){
        return get(DATA_TAG);
    }

    public static AjaxResult success()
    {
        return AjaxResult.success("Operation successful");
    }


    public static AjaxResult success(Object data)
    {
        return AjaxResult.success("Operation successful", data);
    }


    public static AjaxResult success(String msg)
    {
        return AjaxResult.success(msg, null);
    }


    public static AjaxResult success(String msg, Object data)
    {
        return new AjaxResult(HttpStatus.SUCCESS, msg, data);
    }


    public static AjaxResult error()
    {
        return AjaxResult.error("Operation failed");
    }


    public static AjaxResult error(String msg)
    {
        return AjaxResult.error(msg, null);
    }

    public static AjaxResult error(String msg, Object data)
    {
        return new AjaxResult(HttpStatus.ERROR, msg, data);
    }


    public static AjaxResult error(int code, String msg)
    {
        return new AjaxResult(code, msg, null);
    }


    public boolean isSuccess()
    {
        return !isError();
    }


    public boolean isError()
    {
        int status = (Integer)this.get(CODE_TAG);
        return HttpStatus.SUCCESS!=status;
    }
    public String getMsg(){
        Object o = super.get(MSG_TAG);
        if(o!=null){
            return o.toString();
        }
        return "";
    }

    @Override
    public AjaxResult put(String key, Object value)
    {
        super.put(key, value);
        return this;
    }
}
