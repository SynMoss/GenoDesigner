package com.hmzhkj.gene.controller;

import com.hmzhkj.common.core.exception.ServiceException;
import com.hmzhkj.common.core.web.controller.BaseController;
import com.hmzhkj.common.core.web.domain.AjaxResult;
import com.hmzhkj.common.core.web.page.TableDataInfo;
import com.hmzhkj.framework.annotation.Log;
import com.hmzhkj.framework.enums.BusinessType;
import com.hmzhkj.framework.annotation.RequiresPermissions;
import com.hmzhkj.framework.utils.SecurityUtils;
import com.hmzhkj.gene.domain.DesignTBProjectMain;
import com.hmzhkj.gene.service.IDesignTBProjectMainService;
import com.hmzhkj.gene.service.IDesignTBProjectMembersService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/project/main")
public class DesignTBProjectMainController extends BaseController {
    @Autowired
    private IDesignTBProjectMainService designTBProjectMainService;

    @Autowired
    private IDesignTBProjectMembersService designTBProjectMembersService;


    @Log(title = "Project Query", businessType = BusinessType.VIEWED)
    @ApiOperation(value = "Project Query", notes = "Project Query")
    @GetMapping("/list")
    @RequiresPermissions("gene-project:project:list")
    public TableDataInfo listProjects(DesignTBProjectMain designTBProjectMain)
    {
        startPage();
        List<DesignTBProjectMain> list = designTBProjectMainService.selectDesignTBProjectMainList(designTBProjectMain);
        return getDataTable(list);
    }


    @GetMapping(value = "/{id}")
    public AjaxResult getProject(@PathVariable("id") String id) {

        return AjaxResult.success(designTBProjectMainService.selectDesignTBProjectMainById(id));
    }


    @Log(title = "Project", businessType = BusinessType.INSERT)
    @PostMapping("/save")
    @RequiresPermissions("gene-project:project:add")
    public AjaxResult saveProject(@RequestBody DesignTBProjectMain designTBProjectMain)
    {
        System.err.println("LoginUser:"+ SecurityUtils.getLoginUser());
        return toAjax(designTBProjectMainService.insertDesignTBProjectMain(designTBProjectMain));
    }


    @Log(title = "Project", businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    @RequiresPermissions("gene-project:project:edit")
    public AjaxResult updateProject(@RequestBody DesignTBProjectMain designTBProjectMain)
    {
        Boolean flag = designTBProjectMembersService.isProjectMember(designTBProjectMain.getId());
        if(!flag){
            throw new ServiceException("No permission to view this project");

        }
        return toAjax(designTBProjectMainService.updateDesignTBProjectMain(designTBProjectMain));
    }


    @Log(title = "Project", businessType = BusinessType.DELETE)
    @DeleteMapping("/remove")
    @RequiresPermissions("gene-project:project:remove")
    public AjaxResult removeProject(@RequestBody DesignTBProjectMain designTBProjectMain)
    {
        return toAjax(designTBProjectMainService.deleteDesignTBProjectMainById(designTBProjectMain));
    }


    @GetMapping("/listCreateStaff")
    public AjaxResult getProjectCreateStaff()
    {
        return AjaxResult.success(designTBProjectMainService.selectProjectCreateStaffList());
    }


    @GetMapping("/listAll")
    public AjaxResult listAllProjects()
    {
        return AjaxResult.success(designTBProjectMainService.selectAllProjects());
    }


    @GetMapping("/checkName")
    public AjaxResult checkName(String projectName,String id){
        Boolean isExist = designTBProjectMainService.checkName(projectName,id);
        return AjaxResult.success(isExist);
    }
}
