package com.hmzhkj.system.domain;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import cn.hutool.core.util.IdUtil;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hmzhkj.common.core.web.domain.BaseEntity;


public class SysMenu extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    @Override
    public void genId(){
        this.menuId = IdUtil.getSnowflakeNextIdStr();
    }

    private String menuId;


    private String menuName;


    private String parentName;


    private String parentId;

    private String breadcrumbParentId;

    public SysMenu getBreadcrumbParent() {
        return breadcrumbParent;
    }

    public void setBreadcrumbParent(SysMenu breadcrumbParent) {
        this.breadcrumbParent = breadcrumbParent;
    }

    private SysMenu breadcrumbParent;

    private Integer orderNum;


    private String path;


    private String component;


    private String query;


    private Integer isFrame;


    private Integer isCache;


    private String menuType;


    private String visible;


    private String status;


    private String perms;


    private String icon;


    private List<SysMenu> children = new ArrayList<SysMenu>();

    public String getMenuId()
    {
        return menuId;
    }

    public void setMenuId(String menuId)
    {
        this.menuId = menuId;
    }
    public String getBreadcrumbParentId() {
        return breadcrumbParentId;
    }

    public void setBreadcrumbParentId(String breadcrumbParentId) {
        this.breadcrumbParentId = breadcrumbParentId;
    }
    @NotBlank(message = "The menu name cannot be empty")
    @Size(min = 0, max = 50, message = "The length of the menu name cannot exceed 50 characters")
    public String getMenuName()
    {
        return menuName;
    }

    public void setMenuName(String menuName)
    {
        this.menuName = menuName;
    }

    public String getParentName()
    {
        return parentName;
    }

    public void setParentName(String parentName)
    {
        this.parentName = parentName;
    }

    public String getParentId()
    {
        return parentId;
    }

    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }

    @NotNull(message = "Display order cannot be empty")
    public Integer getOrderNum()
    {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum)
    {
        this.orderNum = orderNum;
    }

    @Size(min = 0, max = 200, message = "The routing address cannot exceed 200 characters")
    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    @Size(min = 0, max = 200, message = "The component path cannot exceed 255 characters")
    public String getComponent()
    {
        return component;
    }

    public void setComponent(String component)
    {
        this.component = component;
    }

    public String getQuery()
    {
        return query;
    }

    public void setQuery(String query)
    {
        this.query = query;
    }

    public Integer getIsFrame()
    {
        return isFrame;
    }

    public void setIsFrame(Integer isFrame)
    {
        this.isFrame = isFrame;
    }

    public Integer getIsCache()
    {
        return isCache;
    }

    public void setIsCache(Integer isCache)
    {
        this.isCache = isCache;
    }

    @NotBlank(message = "The menu type cannot be empty")
    public String getMenuType()
    {
        return menuType;
    }

    public void setMenuType(String menuType)
    {
        this.menuType = menuType;
    }

    public String getVisible()
    {
        return visible;
    }

    public void setVisible(String visible)
    {
        this.visible = visible;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    @Size(min = 0, max = 100, message = "The length of the permission identifier cannot exceed 100 characters")
    public String getPerms()
    {
        return perms;
    }

    public void setPerms(String perms)
    {
        this.perms = perms;
    }

    public String getIcon()
    {
        return icon;
    }

    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    public List<SysMenu> getChildren()
    {
        return children;
    }

    public void setChildren(List<SysMenu> children)
    {
        this.children = children;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("menuId", getMenuId())
                .append("menuName", getMenuName())
                .append("parentId", getParentId())
                .append("orderNum", getOrderNum())
                .append("path", getPath())
                .append("component", getComponent())
                .append("isFrame", getIsFrame())
                .append("IsCache", getIsCache())
                .append("menuType", getMenuType())
                .append("visible", getVisible())
                .append("status ", getStatus())
                .append("perms", getPerms())
                .append("icon", getIcon())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
