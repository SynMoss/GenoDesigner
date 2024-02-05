package com.hmzhkj.gene.controller;

import com.hmzhkj.common.core.web.controller.BaseController;
import com.hmzhkj.common.core.web.domain.AjaxResult;
import com.hmzhkj.common.core.web.page.TableDataInfo;
import com.hmzhkj.gene.domain.DesignTBExternalPaccout;
import com.hmzhkj.gene.service.DesignTBExternalPaccoutService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

 
@RestController
@RequestMapping("external/paccout")
@Slf4j
public class DesignTBExternalPaccoutController extends BaseController {
    @Autowired
    private DesignTBExternalPaccoutService designTBExternalPaccoutService;

     
    @GetMapping("/list")
    public TableDataInfo listExternalPaccout(DesignTBExternalPaccout designTBExternalPaccout)
    {
        startPage();
        List<DesignTBExternalPaccout> list = designTBExternalPaccoutService.selectDesignTBExternalPaccoutList(designTBExternalPaccout);
        return getDataTable(list);
    }
     
    @PostMapping("/add")
    public AjaxResult saveExternalPaccout(@RequestBody DesignTBExternalPaccout designTBExternalPaccout){
        return AjaxResult.success(designTBExternalPaccoutService.insertDesignTBExternalPaccout(designTBExternalPaccout));
    }
     
    @PutMapping("/unbind/{id}")
    public AjaxResult unBind(@PathVariable String id){
        return AjaxResult.success(designTBExternalPaccoutService.unBindDesignTBExternalPaccout(id));
    }
     
    @PutMapping("/bind/{id}")
    public AjaxResult bind(@PathVariable String id){
        return AjaxResult.success(designTBExternalPaccoutService.bindDesignTBExternalPaccout(id));
    }
     
    @DeleteMapping("/delete/{id}")
    public AjaxResult deleteExternalPaccout(@PathVariable String id){
        return AjaxResult.success(designTBExternalPaccoutService.removeExternalPaccout(id));
    }
     
    @PutMapping("/edit")
    public AjaxResult editExternalPaccout(@RequestBody DesignTBExternalPaccout designTBExternalPaccout)
    {
        return AjaxResult.success(designTBExternalPaccoutService.updateExternalPaccout(designTBExternalPaccout));
    }
     
    @GetMapping("/hasBindComponent")
    public AjaxResult hasBindComponent()
    {
        return AjaxResult.success(designTBExternalPaccoutService.hasBindComponent());
    }
}
