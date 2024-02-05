package com.hmzhkj.system.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.hmzhkj.system.domain.SysDictData;
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
import com.hmzhkj.common.core.utils.StringUtils;
import com.hmzhkj.common.core.utils.poi.ExcelUtil;
import com.hmzhkj.common.core.web.controller.BaseController;
import com.hmzhkj.common.core.web.domain.AjaxResult;
import com.hmzhkj.common.core.web.page.TableDataInfo;
import com.hmzhkj.framework.annotation.Log;
import com.hmzhkj.framework.enums.BusinessType;
import com.hmzhkj.framework.annotation.RequiresPermissions;
import com.hmzhkj.framework.utils.SecurityUtils;
import com.hmzhkj.system.service.ISysDictDataService;
import com.hmzhkj.system.service.ISysDictTypeService;

 
@RestController
@RequestMapping("/dict/data")
@RequiredArgsConstructor
public class SysDictDataController extends BaseController
{
    private final ISysDictDataService dictDataService;
    
    private final ISysDictTypeService dictTypeService;

    @RequiresPermissions("system:dict:list")
    @GetMapping("/list")
    public TableDataInfo list(SysDictData dictData)
    {
        startPage();
        List<SysDictData> list = dictDataService.selectDictDataList(dictData);
        return getDataTable(list);
    }

    @Log(title = "dictionary data", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:dict:export")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysDictData dictData)
    {
        List<SysDictData> list = dictDataService.selectDictDataList(dictData);
        ExcelUtil<SysDictData> util = new ExcelUtil<>(SysDictData.class);
        util.exportExcel(response, list, "dictionary data");
    }

     
    @RequiresPermissions("system:dict:query")
    @GetMapping(value = "/{dictCode}")
    public AjaxResult getInfo(@PathVariable String dictCode)
    {
        return AjaxResult.success(dictDataService.selectDictDataById(dictCode));
    }

     
    @GetMapping(value = "/type/{dictType}")
    public AjaxResult dictType(@PathVariable String dictType)
    {
        List<SysDictData> data = dictTypeService.selectDictDataByType(dictType);
        if (StringUtils.isNull(data))
        {
            data = new ArrayList<SysDictData>();
        }
        return AjaxResult.success(data);
    }

     
    @RequiresPermissions("system:dict:add")
    @Log(title = "dictionary data", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysDictData dict)
    {
        dict.setCreateBy(SecurityUtils.getUsername());
        return toAjax(dictDataService.insertDictData(dict));
    }

     
    @RequiresPermissions("system:dict:edit")
    @Log(title = "dictionary data", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysDictData dict)
    {
        dict.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(dictDataService.updateDictData(dict));
    }

     
    @RequiresPermissions("system:dict:remove")
    @Log(title = "dictionary data", businessType = BusinessType.DELETE)
    @DeleteMapping("/{dictCodes}")
    public AjaxResult remove(@PathVariable String[] dictCodes)
    {
        dictDataService.deleteDictDataByIds(dictCodes);
        return success();
    }
}
