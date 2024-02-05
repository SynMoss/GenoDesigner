package com.hmzhkj.common.core.web.page;

import com.hmzhkj.common.core.text.Convert;
import com.hmzhkj.common.core.utils.ServletUtils;

 
public class TableSupport
{
     
    public static final String PAGE_NUM = "pageNum";

     
    public static final String PAGE_SIZE = "pageSize";

     
    public static final String ORDER_BY_COLUMN = "orderByColumn";

     
    public static final String IS_ASC = "isAsc";

     
    public static final String REASONABLE = "reasonable";

     
    public static PageDomain getPageDomain()
    {
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPageNum(Convert.toInt(ServletUtils.getParameter(PAGE_NUM), 1));
        pageDomain.setPageSize(Convert.toInt(ServletUtils.getParameter(PAGE_SIZE), 10));
        pageDomain.setOrderByColumn(ServletUtils.getParameter(ORDER_BY_COLUMN));
        pageDomain.setIsAsc(ServletUtils.getParameter(IS_ASC));
        pageDomain.setReasonable(ServletUtils.getParameterToBool(REASONABLE));
        return pageDomain;
    }

    public static PageDomain buildPageRequest()
    {
        return getPageDomain();
    }
}
