package com.hmzhkj.system.domain.vo;

import com.hmzhkj.system.domain.SysMenu;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BreadcrumbVo {
    private String breadcrumbName;
    private String breadcrumbPath;

    public BreadcrumbVo() {
    }
    public BreadcrumbVo(SysMenu sysMenu) {
        breadcrumbName = sysMenu.getMenuName();
        breadcrumbPath = sysMenu.getPath();
    }
}
