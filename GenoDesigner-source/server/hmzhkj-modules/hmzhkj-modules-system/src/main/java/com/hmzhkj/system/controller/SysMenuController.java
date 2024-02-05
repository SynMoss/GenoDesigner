package com.hmzhkj.system.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hmzhkj.common.core.constant.UserConstants;
import com.hmzhkj.common.core.utils.StringUtils;
import com.hmzhkj.common.core.web.controller.BaseController;
import com.hmzhkj.common.core.web.domain.AjaxResult;
import com.hmzhkj.framework.annotation.Log;
import com.hmzhkj.framework.enums.BusinessType;
import com.hmzhkj.framework.annotation.RequiresPermissions;
import com.hmzhkj.framework.utils.SecurityUtils;
import com.hmzhkj.system.domain.SysMenu;
import com.hmzhkj.system.service.ISysMenuService;


@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class SysMenuController extends BaseController
{
    private final ISysMenuService menuService;


    @RequiresPermissions("system:menu:list")
    @GetMapping("/list")
    public AjaxResult list(SysMenu menu)
    {
        String userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuList(menu, userId);
        return AjaxResult.success(menus);
    }


    @RequiresPermissions("system:menu:query")
    @GetMapping(value = "/{menuId}")
    public AjaxResult getInfo(@PathVariable String menuId)
    {
        return AjaxResult.success(menuService.selectMenuById(menuId));
    }


    @GetMapping("/treeselect")
    public AjaxResult treeselect(SysMenu menu)
    {
        String userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuList(menu, userId);
        return AjaxResult.success(menuService.buildMenuTreeSelect(menus));
    }


    @GetMapping(value = "/roleMenuTreeselect/{roleId}")
    public AjaxResult roleMenuTreeselect(@PathVariable("roleId") String roleId)
    {
        String userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuList(userId);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("checkedKeys", menuService.selectMenuListByRoleId(roleId));
        ajax.put("menus", menuService.buildMenuTreeSelect(menus));
        return ajax;
    }

    @RequiresPermissions("system:menu:add")
    @Log(title = "menu management", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysMenu menu)
    {
        if (UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu)))
        {
            return AjaxResult.error("Failed to add menu" + menu.getMenuName() + "menu name already exists");
        }
        else if (menu.getIsFrame().equals(1) && !StringUtils.ishttp(menu.getPath()))
        {
            return AjaxResult.error("Failed to add menu" + menu.getMenuName() + "address must start with http (s)://");
        }
        menu.setCreateBy(SecurityUtils.getUsername());
        return toAjax(menuService.insertMenu(menu));
    }


    @RequiresPermissions("system:menu:edit")
    @Log(title = "menu management", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysMenu menu)
    {
        if (UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu)))
        {
            return AjaxResult.error("Failed to modify menu" + menu.getMenuName() + "menu name already exists");
        }
        else if (menu.getIsFrame().equals(1) && !StringUtils.ishttp(menu.getPath()))
        {
            return AjaxResult.error("Failed to modify menu" + menu.getMenuName() + "address must start with http (s)://");
        }
        else if (menu.getMenuId().equals(menu.getParentId()))
        {
            return AjaxResult.error("Failed to modify menu" + menu.getMenuName() + "The superior menu cannot select oneself");
        }
        menu.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(menuService.updateMenu(menu));
    }


    @RequiresPermissions("system:menu:remove")
    @Log(title = "menu management", businessType = BusinessType.DELETE)
    @DeleteMapping("/{menuId}")
    public AjaxResult remove(@PathVariable("menuId") String menuId)
    {
        if (menuService.hasChildByMenuId(menuId))
        {
            return AjaxResult.error("Submenu exists, deletion not allowed");
        }
        if (menuService.checkMenuExistRole(menuId))
        {
            return AjaxResult.error("Menu assigned, deletion not allowed");
        }
        return toAjax(menuService.deleteMenuById(menuId));
    }


    @GetMapping("getRouters")
    public AjaxResult getRouters()
    {
        String userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(menuService.buildMenus(menus));
    }
}