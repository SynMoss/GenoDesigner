package com.hmzhkj.gene.controller;

import com.hmzhkj.common.core.web.controller.BaseController;
import com.hmzhkj.common.core.web.domain.AjaxResult;
import com.hmzhkj.framework.annotation.Log;
import com.hmzhkj.framework.enums.BusinessType;
import com.hmzhkj.framework.annotation.RequiresPermissions;
import com.hmzhkj.gene.domain.Template;
import com.hmzhkj.gene.service.TemplateService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/template")
public class TemplateController extends BaseController {
    @Autowired
    private TemplateService templateService;

    @RequiresPermissions("programme:programme:saveBlank")
    @Log(title = "Template Save", businessType = BusinessType.INSERT)
    @ApiOperation(value = "Template Save", notes = "Template Save")
    @PostMapping(value = "/saveTemplate")
    public AjaxResult saveTemplate(@RequestBody Template template) {
        templateService.saveTemplate(template);
        return AjaxResult.success("Operation successful");
    }

    @Log(title = "Template deletion", businessType = BusinessType.DELETE)
    @DeleteMapping(value = "/deleteTemplate")
    public AjaxResult deleteTemplate(String id) {
        templateService.deleteTemplate(id);
        return AjaxResult.success("Operation successful");
    }

    @Log(title = "Template Query", businessType = BusinessType.VIEWED)
    @ApiOperation(value = "Template Query", notes = "Template Query")
    @GetMapping(value = "/getTemplateList")
    public List<Template> getTemplateList(String name) {
        System.err.println(name);
        List<Template> templateList= templateService.getTemplateList(name);
        return templateList;
    }

     
    @GetMapping("/checkName")
    public AjaxResult checkName(String name,String id){
        Boolean isExist = templateService.checkName(name,id);
        return AjaxResult.success(isExist);
    }
}
