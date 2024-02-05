package com.hmzhkj.gene.controller;

import com.hmzhkj.common.core.web.controller.BaseController;
import com.hmzhkj.common.core.web.domain.AjaxResult;
import com.hmzhkj.common.core.web.page.TableDataInfo;
import com.hmzhkj.framework.annotation.Log;
import com.hmzhkj.framework.enums.BusinessType;
import com.hmzhkj.framework.annotation.RequiresPermissions;
import com.hmzhkj.framework.utils.SecurityUtils;
import com.hmzhkj.gene.domain.Part;
import com.hmzhkj.gene.domain.dto.QueryParamDto;
import com.hmzhkj.gene.service.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

 
@RestController
@RequestMapping("/part")
public class PartController extends BaseController {
    @Autowired
    private PartService partService;


    @Log(title = "component query", businessType = BusinessType.VIEWED)
    @RequiresPermissions("part:part:list")
    @GetMapping("/list")
    public TableDataInfo queryPartListPage(QueryParamDto queryParamDto){
        startPage();
        List<Part> pageList =partService.listPage(queryParamDto);
        return getDataTable(pageList);
    }

    @Log(title = "New Component", businessType = BusinessType.INSERT)
    @RequiresPermissions("part:part:add")
    @PostMapping("/add")
    public AjaxResult addPart(@RequestBody Part part){
        return toAjax(partService.addPart(part));
    }

    @Log(title = "Edit Component", businessType = BusinessType.UPDATE)
    @RequiresPermissions("part:part:edit")
    @PutMapping("/edit")
    public AjaxResult editPart(@RequestBody Part part){
        part.setUpdateTime(new Date());
        part.setUpdateStaffNo(SecurityUtils.getUserId());
        part.setLength(part.getContent()==null?0:part.getContent().length());
        return toAjax(partService.updateById(part));
    }

    @Log(title = "delete component", businessType = BusinessType.DELETE)
    @RequiresPermissions("part:part:delete")
    @DeleteMapping("/delete")
    public AjaxResult deletePart(String id){
        return toAjax(partService.removeById(new Part().setId(id)));
    }

     
    @GetMapping("/checkName")
    public AjaxResult checkName(String name,String id){
        Boolean isExist = partService.checkName(name,id);
        return AjaxResult.success(isExist);
    }
}
