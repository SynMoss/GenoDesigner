package com.hmzhkj.system.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.hmzhkj.system.domain.SysLogininfor;
import com.hmzhkj.system.domain.vo.SysLoginforVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hmzhkj.common.core.constant.CacheConstants;
import com.hmzhkj.common.core.utils.poi.ExcelUtil;
import com.hmzhkj.common.core.web.controller.BaseController;
import com.hmzhkj.common.core.web.domain.AjaxResult;
import com.hmzhkj.common.core.web.page.TableDataInfo;
import com.hmzhkj.framework.annotation.Log;
import com.hmzhkj.framework.enums.BusinessType;
import com.hmzhkj.common.redis.service.RedisService;
import com.hmzhkj.framework.annotation.InnerAuth;
import com.hmzhkj.framework.annotation.RequiresPermissions;
import com.hmzhkj.system.service.ISysLogininforService;

 
@RestController
@RequestMapping("/logininfor")
@RequiredArgsConstructor
public class SysLogininforController extends BaseController
{
    private final ISysLogininforService logininforService;

    private final RedisService redisService;

    @RequiresPermissions("system:logininfor:list")
    @GetMapping("/list")
    public TableDataInfo list(SysLogininfor logininfor)
    {
        startPage();
        List<SysLogininfor> list = logininforService.selectLogininforList(logininfor);
        return getDataTable(list);
    }

    @Log(title = "login Log", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:logininfor:export")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysLoginforVo ids)
    {
        List list = new ArrayList();
        list = logininforService.selectLoginforListByIds(ids.getIds());
        ExcelUtil<SysLogininfor> util = new ExcelUtil<SysLogininfor>(SysLogininfor.class);
        util.exportExcel(response, list, "login Log");
    }


    @RequiresPermissions("system:logininfor:remove")
    @Log(title = "login Log", businessType = BusinessType.DELETE)
    @DeleteMapping("/{infoIds}")
    public AjaxResult remove(@PathVariable String[] infoIds)
    {
        return toAjax(logininforService.deleteLogininforByIds(infoIds));
    }

    @RequiresPermissions("system:logininfor:remove")
    @Log(title = "login Log", businessType = BusinessType.DELETE)
    @DeleteMapping("/clean")
    public AjaxResult clean()
    {
        logininforService.cleanLogininfor();
        return AjaxResult.success();
    }

    @RequiresPermissions("system:logininfor:unlock")
    @Log(title = "Account unlocking", businessType = BusinessType.OTHER)
    @GetMapping("/unlock/{userName}")
    public AjaxResult unlock(@PathVariable("userName") String userName)
    {
        redisService.deleteObject(CacheConstants.PWD_ERR_CNT_KEY + userName);
        return success();
    }
    @RequiresPermissions("system:logininfor:unlock")
    @Log(title = "Account unlocking", businessType = BusinessType.OTHER)
    @GetMapping("/isLock/{userName}")
    public AjaxResult islock(@PathVariable("userName") String userName)
    {
        int maxRetryCount = CacheConstants.PASSWORD_MAX_RETRY_COUNT;
        Integer retryCount = redisService.getCacheObject(getCacheKey(userName));
        if (retryCount == null)
        {
            retryCount = 0;
        }
        if(retryCount >= Integer.valueOf(maxRetryCount).intValue()){
            return success("locked");
        }else {
            return success("unlocked");
        }
    }
    @InnerAuth
    @PostMapping
    public AjaxResult add(@RequestBody SysLogininfor logininfor)
    {
        return toAjax(logininforService.insertLogininfor(logininfor));
    }
    private String getCacheKey(String username)
    {
        return CacheConstants.PWD_ERR_CNT_KEY + username;
    }
}
