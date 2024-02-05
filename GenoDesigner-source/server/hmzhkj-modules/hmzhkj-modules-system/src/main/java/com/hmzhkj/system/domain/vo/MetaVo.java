package com.hmzhkj.system.domain.vo;

import com.hmzhkj.common.core.utils.StringUtils;

import java.util.List;
import java.util.Map;

 
public class MetaVo
{
     
    private String title;

     
    private String icon;

     
    private boolean noCache;

     
    private String link;
    private List<BreadcrumbVo> breadcrumbList;

    public List<BreadcrumbVo> getBreadcrumbList() {
        return breadcrumbList;
    }

    public void setBreadcrumbList(List<BreadcrumbVo> breadcrumbList) {
        this.breadcrumbList = breadcrumbList;
    }

    public MetaVo()
    {
    }

    public MetaVo(String title, String icon)
    {
        this.title = title;
        this.icon = icon;
    }

    public MetaVo(String title, String icon, boolean noCache)
    {
        this.title = title;
        this.icon = icon;
        this.noCache = noCache;
    }

    public MetaVo(String title, String icon, String link)
    {
        this.title = title;
        this.icon = icon;
        this.link = link;
    }

    public MetaVo(String title, String icon, boolean noCache, String link)
    {
        this.title = title;
        this.icon = icon;
        this.noCache = noCache;
        if (StringUtils.ishttp(link))
        {
            this.link = link;
        }
    }

    public boolean isNoCache()
    {
        return noCache;
    }

    public void setNoCache(boolean noCache)
    {
        this.noCache = noCache;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getIcon()
    {
        return icon;
    }

    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    public String getLink()
    {
        return link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }
}
