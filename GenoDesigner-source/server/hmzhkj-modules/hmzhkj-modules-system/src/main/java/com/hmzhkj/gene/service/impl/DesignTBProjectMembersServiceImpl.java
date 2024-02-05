package com.hmzhkj.gene.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hmzhkj.common.core.exception.ServiceException;
import com.hmzhkj.common.core.utils.StringUtils;
import com.hmzhkj.framework.utils.SecurityUtils;
import com.hmzhkj.gene.domain.DesignTBProjectMain;
import com.hmzhkj.gene.domain.DesignTBProjectMembers;
import com.hmzhkj.gene.domain.dto.AddProjectMembersDto;
import com.hmzhkj.gene.domain.dto.RemoveProjectMemberDto;
import com.hmzhkj.gene.mapper.DesignTBProjectMainMapper;
import com.hmzhkj.gene.mapper.DesignTBProjectMembersMapper;
import com.hmzhkj.gene.service.IDesignTBProjectMainService;
import com.hmzhkj.gene.service.IDesignTBProjectMembersService;
import com.hmzhkj.gene.service.LogsService;
import com.hmzhkj.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

 
@Service
@RequiredArgsConstructor
public class DesignTBProjectMembersServiceImpl implements IDesignTBProjectMembersService {
    private final DesignTBProjectMembersMapper designTBProjectMembersMapper;
    private final DesignTBProjectMainMapper designTBProjectMainMapper;
    private final LogsService logsService;
    private final IDesignTBProjectMainService projectService;
    private final ISysUserService userService;
     
    @Transactional
    @Override
    public List<Map<String, String>> selectDesignTBProjectMembersList(String projectId){
        String[] staffIds = designTBProjectMembersMapper.selectDesignTBProjectMembersStaffIds(projectId);
        List<Map<String, String>> data  = userService.selectUserListInProject(staffIds);
        QueryWrapper<DesignTBProjectMembers> wrapper = new QueryWrapper<DesignTBProjectMembers>().
                eq("project_id",projectId).ne("status",2);
        List<DesignTBProjectMembers> list = designTBProjectMembersMapper.selectList(wrapper);
        outer:
        for (Map<String,String> map : data) {
            for (DesignTBProjectMembers member : list) {
                if(member.getStaffId().equals(map.get("userId"))){
                    map.put("id",member.getId());
                    map.put("status",member.getStatus().toString());
                    map.put("permission",member.getPermission());
                    continue outer;
                }
            }
        }
        return data;
    }

     
    @Override
    @Transactional
    public void insertDesignTBProjectMembers(AddProjectMembersDto addMembers){
        int i = 0;
        String projectId = addMembers.getProjectId();
        String[] staffIds = addMembers.getStaffIds();
        if(staffIds == null ||staffIds.length == 0){
            throw new ServiceException("The added user does not exist");
        }
        DesignTBProjectMain designTBProjectMain = designTBProjectMainMapper.selectById(projectId);
        if(designTBProjectMain == null || designTBProjectMain.getProjectIsDelete() == 0){
            throw new ServiceException("The project does not exist...");
        }
        for (String staffId : staffIds) {
            DesignTBProjectMembers designTBProjectMembers = new DesignTBProjectMembers().setStaffId(staffId).setProjectId(projectId);
            List<DesignTBProjectMembers> designTBProjectMembersList = designTBProjectMembersMapper.selectDesignTBProjectMembersList(designTBProjectMembers);
            if (designTBProjectMembersList != null && designTBProjectMembersList.size() > 0) {
                throw new ServiceException("The current added member already exists...");
            }
            designTBProjectMembers.setStatus(1).setPermission("0");
            i = designTBProjectMembersMapper.insert(designTBProjectMembers);
            if (i == 0 || i > 1) {
                throw new ServiceException("Failed to add member...");
            }
            designTBProjectMain.setProjectMemberNumber(designTBProjectMain.getProjectMemberNumber()+1);
            i = designTBProjectMainMapper.updateById(designTBProjectMain);
            if(i == 0 || i > 1){
                throw new ServiceException("Failed to modify the number of project members...");
            }
        }
        List<Map<String, String>> r = userService.selectUserListInProject(staffIds);;
        List<String> list = new ArrayList<>();
        for(Map<String,String> map : r){
            list.add(map.get("nickName"));
        }
        logsService.saveLog(projectId,"newly added","project management","design_t_b_project_main",
                "Add project members：" + StringUtils.join(list, ", "));
    }

     
    @Override
    @Transactional
    public void deleteDesignTBProjectMembers(RemoveProjectMemberDto removeMember){
        String projectId = removeMember.getProjectId();
        DesignTBProjectMain designTBProjectMain = designTBProjectMainMapper.selectById(projectId);
        if(designTBProjectMain == null || designTBProjectMain.getProjectIsDelete() == 0){
            throw new ServiceException("The project does not exist...");
        }
        if(SecurityUtils.getUserId().equals(removeMember.getStaffId())){
            throw new ServiceException("Cannot remove myself");
        }
        QueryWrapper<DesignTBProjectMembers> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("project_id",projectId).eq("staff_id",removeMember.getStaffId()).
                eq("status",0);
        if(designTBProjectMembersMapper.selectCount(queryWrapper) > 0){
            throw new ServiceException("The project creator cannot be deleted");
        }
        int i = designTBProjectMembersMapper.removeProjectMember(removeMember);
        if(i == 0 || i > 1){
            throw new ServiceException("Failed to delete project members...");
        }
        designTBProjectMain.setProjectMemberNumber(designTBProjectMain.getProjectMemberNumber()-1);
        i = designTBProjectMainMapper.updateById(designTBProjectMain);
        if(i == 0 || i > 1){
            throw new ServiceException("Failed to modify the number of project members...");
        }
        String nickName = userService.selectUserById(removeMember.getStaffId()).getNickName();
        logsService.saveLog(projectId,"delete","project management","design_t_b_project_main",
                "Delete project members：" + nickName);
    }

     
    @Transactional
    @Override
    public List<Map<String, String>> selectNonDesignTBProjectMembersList(String projectId){
        String[] staffIds = designTBProjectMembersMapper.selectDesignTBProjectMembersStaffIds(projectId);
        return userService.selectUserListInProject(staffIds);
    }

     
    @Override
    public Boolean isProjectMember(String projectId) {
        String[] staffIds = designTBProjectMembersMapper.selectDesignTBProjectMembersStaffIds(projectId);
        String userId = SecurityUtils.getUserId();
        return Arrays.asList(staffIds).contains(userId);
    }

     
    @Override
    public void updatePermission(Map<String,String> map) {
        DesignTBProjectMembers updateMember = designTBProjectMembersMapper.selectById(map.get("id"));
        if(updateMember.getStatus() == 0 && "0".equals(updateMember.getPermission())){
            throw new ServiceException("Prohibit performing this operation on project creators");
        }
        updateMember.setPermission(map.get("permission"));
        int i = designTBProjectMembersMapper.updateById(updateMember);
        if(i < 1){
            throw new ServiceException("Failed to change permissions");
        }
    }

     
    @Override
    public DesignTBProjectMembers selectMember(RemoveProjectMemberDto member) {
        QueryWrapper<DesignTBProjectMembers> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("project_id",member.getProjectId()).notIn("status",2);
        if(member.getStaffId() != null){
            queryWrapper.eq("staff_id",member.getStaffId());
        }else{
            queryWrapper.eq("staff_id",SecurityUtils.getLoginUser().getUserid());
        }
        return designTBProjectMembersMapper.selectOne(queryWrapper);
    }

    @Override
    @Transactional
    public void deleteMember(String[] staffIds) {
        QueryWrapper<DesignTBProjectMembers> wrapper = new QueryWrapper<DesignTBProjectMembers>().
                ne("status",2).in("staff_id", Arrays.asList(staffIds));
        DesignTBProjectMain designTBProjectMain = null;
        List<DesignTBProjectMembers> designTBProjectMembers = designTBProjectMembersMapper.selectList(wrapper);
        for (DesignTBProjectMembers member : designTBProjectMembers) {
            if(member.getStatus() == 0){
                designTBProjectMain = designTBProjectMainMapper.selectDesignTBProjectMainById(member.getProjectId());
                if (designTBProjectMain != null){
                    projectService.deleteDesignTBProjectMainById(designTBProjectMain);
                }
            }
            int i = designTBProjectMembersMapper.updateById(member.setStatus(2));
            if(i < 1){
                throw new ServiceException("operation failed");
            }
        }
    }
}
