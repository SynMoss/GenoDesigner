package com.hmzhkj.common.core.utils;

import com.github.pagehelper.PageHelper;
import com.hmzhkj.common.core.utils.sql.SqlUtil;
import com.hmzhkj.common.core.web.page.PageDomain;
import com.hmzhkj.common.core.web.page.TableSupport;

 
public class PageUtils extends PageHelper
{
     
    public static void startPage()
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
        Boolean reasonable = pageDomain.getReasonable();
        PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
    }

     
    public static void clearPage()
    {
        PageHelper.clearPage();
    }
}
