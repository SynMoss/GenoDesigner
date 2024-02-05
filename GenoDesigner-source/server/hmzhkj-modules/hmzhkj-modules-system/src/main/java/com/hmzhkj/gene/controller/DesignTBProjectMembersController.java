package com.hmzhkj.gene.controller;

import com.hmzhkj.common.core.exception.ServiceException;
import com.hmzhkj.common.core.web.controller.BaseController;
import com.hmzhkj.common.core.web.domain.AjaxResult;
import com.hmzhkj.framework.annotation.Log;
import com.hmzhkj.framework.enums.BusinessType;
import com.hmzhkj.gene.domain.dto.AddProjectMembersDto;
import com.hmzhkj.gene.domain.dto.RemoveProjectMemberDto;
import com.hmzhkj.gene.service.IDesignTBProjectMembersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

 
@RestController
@RequestMapping("/project/members")
public class DesignTBProjectMembersController extends BaseController
{
    @Autowired
    private IDesignTBProjectMembersService designTBProjectMembersService;

     
    @GetMapping("/list/{projectId}")
    public AjaxResult listProjectMembers(@PathVariable String projectId)
    {
        List<Map<String, String>> list = designTBProjectMembersService.selectDesignTBProjectMembersList(projectId);
        return AjaxResult.success(list);
    }

     
    @Log(title = "Project members", businessType = BusinessType.INSERT)
    @PostMapping("/save")
    public AjaxResult saveProjectMember(@RequestBody AddProjectMembersDto addMembers){
        designTBProjectMembersService.insertDesignTBProjectMembers(addMembers);
        return AjaxResult.success();
    }

     
    @Log(title = "Project members", businessType = BusinessType.DELETE)
    @DeleteMapping("/remove")
    public AjaxResult removeProjectMember(@RequestBody RemoveProjectMemberDto removeMember){
        designTBProjectMembersService.deleteDesignTBProjectMembers(removeMember);
        return AjaxResult.success();
    }

     
    @GetMapping("/{projectId}")
    public AjaxResult listNonProjectMembers(@PathVariable String projectId)
    {
        List<Map<String, String>> list = designTBProjectMembersService.selectNonDesignTBProjectMembersList(projectId);
        return AjaxResult.success(list);
    }

     
    @GetMapping("/isMember/{projectId}")
    public AjaxResult isProjectMember(@PathVariable String projectId){
        Boolean isMember = designTBProjectMembersService.isProjectMember(projectId);
        return AjaxResult.success(isMember);
    }

     
    @Log(title = "Project members", businessType = BusinessType.UPDATE)
    @PutMapping("/permission")
    public AjaxResult updatePermission(@RequestBody Map<String,String> map){
        designTBProjectMembersService.updatePermission(map);
        return AjaxResult.success();
    }

     
    @GetMapping("/member")
    public AjaxResult selectMember(RemoveProjectMemberDto member) {
        Boolean flag = designTBProjectMembersService.isProjectMember(member.getProjectId());
        if(!flag){
            throw new ServiceException("No permission to view this project");

        }
        return AjaxResult.success(designTBProjectMembersService.selectMember(member));
    }

     
    @PutMapping("/delete")
    public AjaxResult deleteMember(@RequestBody String[] staffIds){
        designTBProjectMembersService.deleteMember(staffIds);
        return AjaxResult.success();
    }
}
