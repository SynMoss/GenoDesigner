package com.hmzhkj.common.core.web.controller;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;

import com.hmzhkj.common.core.web.page.TableDataInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import com.github.pagehelper.PageInfo;
import com.hmzhkj.common.core.constant.HttpStatus;
import com.hmzhkj.common.core.utils.DateUtils;
import com.hmzhkj.common.core.utils.PageUtils;
import com.hmzhkj.common.core.web.domain.AjaxResult;


public class BaseController
{
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());


    @InitBinder
    public void initBinder(WebDataBinder binder)
    {

        binder.registerCustomEditor(Date.class, new PropertyEditorSupport()
        {
            @Override
            public void setAsText(String text)
            {
                setValue(DateUtils.parseDate(text));
            }
        });
    }


    protected void startPage()
    {
        PageUtils.startPage();
    }


    protected void clearPage()
    {
        PageUtils.clearPage();
    }


    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected TableDataInfo getDataTable(List<?> list)
    {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setRows(list);
        rspData.setMsg("query was successful");
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }


    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected TableDataInfo getDataTableCustom(List<?> list,Long total)
    {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setRows(list);
        rspData.setMsg("query was successful");
        rspData.setTotal(total);
        return rspData;
    }


    protected AjaxResult toAjax(int rows)
    {
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }


    protected AjaxResult toAjax(boolean result)
    {
        return result ? success() : error();
    }


    public AjaxResult success()
    {
        return AjaxResult.success();
    }


    public AjaxResult error()
    {
        return AjaxResult.error();
    }


    public AjaxResult success(String message)
    {
        return AjaxResult.success(message);
    }


    public AjaxResult error(String message)
    {
        return AjaxResult.error(message);
    }
}
