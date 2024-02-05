package com.hmzhkj.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.hmzhkj.system.domain.SysOperLog;
import com.hmzhkj.system.domain.vo.SysOperlogVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hmzhkj.common.core.utils.poi.ExcelUtil;
import com.hmzhkj.common.core.web.controller.BaseController;
import com.hmzhkj.common.core.web.domain.AjaxResult;
import com.hmzhkj.common.core.web.page.TableDataInfo;
import com.hmzhkj.framework.annotation.Log;
import com.hmzhkj.framework.enums.BusinessType;
import com.hmzhkj.framework.annotation.InnerAuth;
import com.hmzhkj.framework.annotation.RequiresPermissions;
import com.hmzhkj.system.service.ISysOperLogService;

 
@RestController
@RequestMapping("/operlog")
@RequiredArgsConstructor
public class SysOperlogController extends BaseController
{
    private final ISysOperLogService operLogService;

    @RequiresPermissions("system:operlog:list")
    @GetMapping("/list")
    public TableDataInfo list(SysOperLog operLog)
    {
        startPage();
        List<SysOperLog> list = operLogService.selectOperLogList(operLog);
        return getDataTable(list);
    }

    @Log(title = "Operation Log", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:operlog:export")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysOperlogVo ids)
    {
        List<SysOperLog> list= operLogService.selectOperLogListByIds(ids.getIds());
        ExcelUtil<SysOperLog> util = new ExcelUtil<SysOperLog>(SysOperLog.class);
        util.exportExcel(response, list, "user data");
    }

    @Log(title = "Operation Log", businessType = BusinessType.DELETE)
    @RequiresPermissions("system:operlog:remove")
    @DeleteMapping("/{operIds}")
    public AjaxResult remove(@PathVariable String[] operIds)
    {
        return toAjax(operLogService.deleteOperLogByIds(operIds));
    }

    @RequiresPermissions("system:operlog:remove")
    @Log(title = "Operation Log", businessType = BusinessType.CLEAN)
    @DeleteMapping("/clean")
    public AjaxResult clean()
    {
        operLogService.cleanOperLog();
        return AjaxResult.success();
    }

    @InnerAuth
    @PostMapping
    public AjaxResult add(@RequestBody SysOperLog operLog)
    {
        return toAjax(operLogService.insertOperlog(operLog));
    }
}
