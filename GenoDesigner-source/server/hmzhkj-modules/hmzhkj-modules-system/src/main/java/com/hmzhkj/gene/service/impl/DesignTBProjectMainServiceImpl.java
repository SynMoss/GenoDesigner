package com.hmzhkj.gene.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmzhkj.common.core.exception.ServiceException;
import com.hmzhkj.framework.utils.SecurityUtils;
import com.hmzhkj.gene.domain.DesignTBProjectMain;
import com.hmzhkj.gene.domain.DesignTBProjectMembers;
import com.hmzhkj.gene.domain.Logs;
import com.hmzhkj.gene.domain.vo.ProjectVo;
import com.hmzhkj.gene.mapper.DesignTBProjectMainMapper;
import com.hmzhkj.gene.mapper.DesignTBProjectMembersMapper;
import com.hmzhkj.gene.mapper.ProgrammeMapper;
import com.hmzhkj.gene.service.IDesignTBProjectMainService;
import com.hmzhkj.gene.service.LogsService;
import com.hmzhkj.gene.domain.Programme;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

 
@Service
@RequiredArgsConstructor
public class DesignTBProjectMainServiceImpl extends ServiceImpl<DesignTBProjectMainMapper, DesignTBProjectMain> implements IDesignTBProjectMainService {
    private final DesignTBProjectMainMapper designTBProjectMainMapper;
    private final DesignTBProjectMembersMapper designTBProjectMembersMapper;
    private final LogsService logsService;
    private final ProgrammeMapper programmeMapper;
  
     
    @Transactional
    @Override
    public DesignTBProjectMain selectDesignTBProjectMainById(String id) {
        DesignTBProjectMain designTBProjectMain = designTBProjectMainMapper.selectDesignTBProjectMainById(id);
        if (designTBProjectMain == null){
            throw new ServiceException("Check if the project does not exist...");
        }
        int i = designTBProjectMainMapper.updateViewedById(id);
        if (i <= 0 || i > 1) {
            throw new ServiceException("View time update failed...");
        }
         
        return designTBProjectMainMapper.selectDesignTBProjectMainById(id);
    }

     
    @Override
    public List<DesignTBProjectMain> selectDesignTBProjectMainList(DesignTBProjectMain designTBProjectMain){
        String projectName = designTBProjectMain.getProjectName();
        if(StringUtils.hasLength(projectName)){
            designTBProjectMain.setProjectName(projectName.trim());
        }
        designTBProjectMain.setLoginStaffId(SecurityUtils.getUserId());
        return designTBProjectMainMapper.selectDesignTBProjectMainList(designTBProjectMain);
    }

     
    @Transactional
    @Override
    public int insertDesignTBProjectMain(DesignTBProjectMain designTBProjectMain) {
        Logs logs = new Logs();
        if (designTBProjectMain == null || !StringUtils.hasLength(designTBProjectMain.getProjectName())) {
            throw new ServiceException("Save project or project name cannot be empty");
        }
        designTBProjectMain.setProjectIsDelete(1).setProjectMemberNumber(1).setProjectProgrammeNumber(0).setViewedTime(new Date());
        int i = designTBProjectMainMapper.insert(designTBProjectMain);
        if (i == 0) {
            throw new ServiceException("Project addition failed...");
        }
        String projectMainId = designTBProjectMain.getId();
        DesignTBProjectMembers designTBProjectMembers = new DesignTBProjectMembers().setProjectId(projectMainId).
                setStatus(0).setStaffId(SecurityUtils.getUserId()).setPermission("1");
        i = designTBProjectMembersMapper.insert(designTBProjectMembers);
        if(i == 1){
            logsService.saveLog(projectMainId,"newly added","project management","design_t_b_project_main",
                    "Create Project【" + designTBProjectMain.getProjectName() + "】");
        }
        return i;
    }

     
    @Override
    public int updateDesignTBProjectMain(DesignTBProjectMain designTBProjectMain){
        Logs logs = new Logs();
        if (designTBProjectMain == null || !StringUtils.hasLength(designTBProjectMain.getProjectName())) {
            throw new ServiceException("Project modification or project name cannot be empty");
        }
        String id = designTBProjectMain.getId();
        DesignTBProjectMain oldProject = designTBProjectMainMapper.selectDesignTBProjectMainById(id);
        if(oldProject == null){
            throw new ServiceException("The modification project does not exist");
        }
        String oldName = oldProject.getProjectName();
        int i = designTBProjectMainMapper.updateById(designTBProjectMain);
        if(i == 1 && !oldName.equals(designTBProjectMain.getProjectName())){
            logsService.saveLog(id,"update","project management","design_t_b_project_main",
                    "Change project name【" + oldName + "】 to：" + designTBProjectMain.getProjectName());
        }else if(i == 1){
            logsService.saveLog(id,"update","project management","design_t_b_project_main",
                    "Modify the information of this project");
        }
        return i;
    }

     
    @Transactional
    @Override
    public int deleteDesignTBProjectMainById(DesignTBProjectMain designTBProjectMain){
         
        Logs logs = new Logs();
        designTBProjectMain.setProjectIsDelete(0).setProjectMemberNumber(0).setProjectProgrammeNumber(0);
        int i = designTBProjectMainMapper.updateById(designTBProjectMain);
        if(i == 1){
            logsService.saveLog(designTBProjectMain.getId(),"delete","project management","design_t_b_project_main",
                    "delete project:" + designTBProjectMain.getProjectName());
        }
        QueryWrapper<Programme> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("project_id",designTBProjectMain.getId());
        List<Programme> programmes = programmeMapper.selectList(queryWrapper);
        Date date = new Date();
        date = null;
        for(Programme programme : programmes){
            programme.setProjectId("");
        }
        for (Programme programme : programmes) {
            programmeMapper.updateById(programme);
        }
         return i;
    }

     
    @Override
    public List<Map<String, String>> selectProjectCreateStaffList() {
        return designTBProjectMainMapper.selectProjectCreateStaffList(SecurityUtils.getLoginUser().getUserid());
    }

     
    @Override
    public List<ProjectVo> selectAllProjects() {
        return designTBProjectMainMapper.selectAllProject(SecurityUtils.getUserId());
    }

    @Override
    public Boolean checkName(String projectName,String id) {
        QueryWrapper<DesignTBProjectMain> wrapper = new QueryWrapper<DesignTBProjectMain>()
                .eq("project_name", projectName)
                .eq("create_staff_no", SecurityUtils.getUserId())
                .eq("project_is_delete",1);
                 if(id!=null){
            wrapper.ne("id", id);
        }
        Long count = designTBProjectMainMapper.selectCount(wrapper);
        if(count == null || count == 0L){
            return  false;
        }
        return true;
    }
}
