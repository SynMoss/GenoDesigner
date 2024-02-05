package com.hmzhkj.gene.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmzhkj.common.core.exception.ServiceException;
import com.hmzhkj.common.core.utils.DateUtils;
import com.hmzhkj.common.core.web.domain.AjaxResult;
import com.hmzhkj.framework.utils.SecurityUtils;
import com.hmzhkj.gene.domain.*;
import com.hmzhkj.gene.domain.dto.ProgrammeQueryParam;
import com.hmzhkj.gene.domain.vo.ProgrammeVO;
import com.hmzhkj.gene.mapper.*;
import com.hmzhkj.gene.service.*;
import com.hmzhkj.gene.util.Assembler;
import com.hmzhkj.system.domain.SysNotice;
import com.hmzhkj.system.service.ISysNoticeService;
import com.hmzhkj.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.*;


@Service
@RequiredArgsConstructor
public class ProgrammeServiceImpl extends ServiceImpl<ProgrammeMapper, Programme> implements IProgrammeService {
    private final IProgrammeStateService iProgrammeStateService;
    private final SequenceService sequenceService;
    private final SequenceOperateService sequenceOperateService;
    private final FeatureService featureService;
    private final IInformationService iInformationService;
    private final ProgrammeMapper programmeMapper;
    private final DesignTBProjectMainMapper projectMapper;
    private final IDesignTBProjectMainService designTBProjectMainService;
    private final ISysNoticeService sysNoticeService;
    private final SequenceMapper sequenceMapper;
    private final SequenceOperateMapper sequenceOperateMapper;
    private final FeatureMapper featureMapper;
    private final ISysUserService userService;
    private final TemplateMapper templateMapper;
    private final LogsService logsService;

    @Value("${path.sequence}")
    private String sequencePath;
    @Value("${path.template}")
    private String templatePath;


    @Override
    public List<ProgrammeVO> queryPageList(ProgrammeQueryParam queryParams){
        String programmeName = queryParams.getProgrammeName();
        String projectId = queryParams.getProjectId();
        if(projectId == null || !StringUtils.hasLength(projectId)){
            queryParams.setPossessStaff(SecurityUtils.getUserId());
        }
        if(StringUtils.hasLength(programmeName)){
            queryParams.setProgrammeName(programmeName.trim());
        }
        return programmeMapper.queryProgrammeList(queryParams);
    }



    @Transactional
    @Override
    public Map<String,String>  createByTemplate(Programme programme) {

        String separator = File.separator;
        Template template = templateMapper.selectById(programme.getTemplateId());
        if(template == null){
            throw new ServiceException("Template does not exist!");
        }
        String templateFileName = template.getTemplateFileName();
        File templateFile = new File(this.templatePath + separator + templateFileName);
        if(!templateFile.exists()){
            throw new ServiceException("The template file does not exist!");
        }

        createBlank(programme);
        String programmeId = programme.getId();
        if(StringUtils.hasLength(programme.getProjectId())){
            logsService.saveLog(programme.getProjectId(),"newly added","project management","design_t_b_project_main",
                    "Plan【" + programme.getProgrammeName() + "】Add to current project");
        }

        long fileSize = templateFile.length();
        File localFile = FileUtil.copy(templateFile,
                new File(sequencePath + separator + programmeId + separator + fileSize + "_" +templateFileName),
                true);
        Map<String,String> map = new HashMap();
        map.put("fileName", localFile.getName());
        map.put("programmeId", programmeId);
        return map;
    }


    @Transactional
    @Override
    public AjaxResult createBlank(Programme programme) {
        programme.setProgrammeNumber(DateUtils.dateTimeNow());
        programme.setCreateTime(new Date());
        programme.setUpdateTime(new Date());
        programme.setCreateStaffNo(SecurityUtils.getUserId());
        programme.setPossessStaff(SecurityUtils.getUserId());
        programme.setCreateStaffName(SecurityUtils.getLoginUser().getSysUser().getNickName());
        programme.setPackState(1);
        programme.setPackState(1);

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("programme_number", "FA_STATUS_001");
        ProgrammeState state = iProgrammeStateService.getOne(queryWrapper,false);
        if(state != null){
            programme.setStateId(state.getId());
        }
        boolean save = save(programme);
        if(!save){
            throw new ServiceException("Plan addition failed...");
        }
        String projectId = programme.getProjectId();
        if(StringUtils.hasLength(projectId)){
            DesignTBProjectMain designTBProjectMain = designTBProjectMainService.selectDesignTBProjectMainById(projectId);
            if(designTBProjectMain == null  || designTBProjectMain.getProjectIsDelete() == 0){
                throw new ServiceException("The selected project does not exist...");
            }
            designTBProjectMain.setProjectProgrammeNumber(designTBProjectMain.getProjectProgrammeNumber() + 1);
            designTBProjectMainService.updateById(designTBProjectMain);
            logsService.saveLog(programme.getProjectId(),"newly added","project management","design_t_b_project_main",
                    "Plan【" + programme.getProgrammeName() + "】Add to current project");
        }
        return AjaxResult.success("Operation successful",programme.getId());
    }


    @Override
    @Transactional
    public void deleteBatch(String ids){
        String projectId = "";
        DesignTBProjectMain designTBProjectMain = null;
        List<String> strings = Arrays.asList(ids.split(","));

        QueryWrapper<Programme> queryWrapperProgramme = new QueryWrapper<>();
        queryWrapperProgramme.in("id",strings);
        List<Programme> programmeList = this.list(queryWrapperProgramme);
        removeByIds(strings);

        for(Programme programme : programmeList){
            projectId = programme.getProjectId();
            if(StringUtils.hasLength(projectId)){
                designTBProjectMain = projectMapper.selectDesignTBProjectMainById(projectId);
                if(designTBProjectMain != null){
                    designTBProjectMain.setProjectProgrammeNumber(designTBProjectMain.getProjectProgrammeNumber() - 1);
                    logsService.saveLog(programme.getProjectId(),"remove","project management","design_t_b_project_main",
                            "Remove the【" + programme.getProgrammeName() + "】from the current project");
                }
            }
        }


        for (String programmeId : strings) {

            String localFilePath = sequencePath + java.io.File.separator + programmeId;
            java.io.File file = new java.io.File(localFilePath);
            FileUtil.del(file);

            List<Sequence> sequenceList = sequenceMapper.list(new Sequence().setProgrammeId(programmeId));
            if(!sequenceList.isEmpty()){
                List<Long> sequenceIds = new ArrayList<>();
                for (Sequence sequence : sequenceList) {
                    sequenceIds.add(sequence.getId());
                }
                sequenceService.removeByIdList(sequenceIds);
                sequenceOperateService.removeBySequenceIdList(sequenceIds);
                featureService.removeBySequenceIdList(sequenceIds,null);
            }
        }


    }


    @Override
    @Transactional
    public void cloneProgramme(Programme programme){
        String oldId = programme.getId();
        Programme oldProgramme = programmeMapper.selectById(oldId);
        if(oldProgramme == null){
            throw new ServiceException("The cloned scheme does not exist...");
        }
        String userId = SecurityUtils.getUserId();
        Date date = new Date();

        String newId = IdUtil.getSnowflakeNextIdStr();
        String sourceFileMergeName = oldProgramme.getSourceFileMergeName();
        oldProgramme.setProgrammeState(1);
        oldProgramme.setId(newId);
        oldProgramme.setProgrammeName(programme.getProgrammeName());

        oldProgramme.setPossessStaff(userId);
        oldProgramme.setCreateStaffNo(userId);
        oldProgramme.setCreateStaffName(SecurityUtils.getLoginUser().getSysUser().getNickName());
        oldProgramme.setCreateTime(date);

        oldProgramme.setPackState(1);
        oldProgramme.setProjectId(null);

        this.save(oldProgramme);
        FileUtil.copyContent(new File(this.getSequenceFolderPath(oldId)),
                new File(this.getSequenceFolderPath(newId)), true);

        this.copy(oldId,newId);
    }


    @Override
    @Transactional
    public void share(Programme programme) {

        String programmeId = programme.getId();
        Date date = new Date();
        Programme one = programmeMapper.selectById(programmeId);
        String newName = (one.getProgrammeName() + "_copy");

        if(one.getProgrammeName().length() > 24){
            newName = one.getProgrammeName().substring(0,24) + "_copy";
        }
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.like("programme_name",newName);
        Long count = programmeMapper.selectCount(wrapper);
        if(one == null){
            throw new ServiceException("The shared plan does not exist...");
        }

        Integer programmeState = programme.getProgrammeState();
        String possessStaff = programme.getPossessStaff();
        if(StringUtils.hasLength(programmeId) && programmeState == null && StringUtils.hasLength(possessStaff)){
            throw new ServiceException("Some parameters are empty");
        }

        String snowflakeNextIdStr = IdUtil.getSnowflakeNextIdStr();
        String sourceFileMergeName = one.getSourceFileMergeName();
        one.setId(snowflakeNextIdStr);
        one.setProgrammeName(newName + count);
        one.setProgrammeState(programmeState);
        one.setPossessStaff(possessStaff);
        one.setCreateTime(date);
        one.setProjectId("");

        if(programmeState != 2){
            one.setPackState(1);
        }else{
            one.setPackFileName(programme.getPackFileName());
        }
        this.save(one);
        FileUtil.copyContent(new File(this.getSequenceFolderPath(programmeId)),
                new File(this.getSequenceFolderPath(snowflakeNextIdStr)), true);

        if(one.getPackType().contains("2")){
            this.copy(programmeId , snowflakeNextIdStr);
        }


        SysNotice notice = new SysNotice();
        notice.genId();
        notice.setNoticeTitle("Sharing plan");
        notice.setNoticeType("1");
        notice.setStatus("1");
        notice.setNoticeContent(SecurityUtils.getLoginUser().getSysUser().getNickName() +
                "share " + programme.getProgrammeName() + " with you，and the name of the shared programme is " + newName + count );
        ArrayList<String> list = new ArrayList<>();
        list.add(possessStaff);
        notice.setUserIdList(list);
        sysNoticeService.insertNotice(notice);
    }


    @Override
    public String shareProgramme(Programme programme){
        String programmeId = programme.getId();
        Programme programme1 = programmeMapper.selectById(programmeId);
        if(programme1 == null){
            throw new ServiceException("The shared plan does not exist...");
        }
        Integer programmeState1 = programme.getProgrammeState();
        String possessStaff = programme.getPossessStaff();
        if(StringUtils.hasLength(programmeId) && programmeState1 == null && StringUtils.hasLength(possessStaff)){
            throw new ServiceException("Some parameters are empty");
        }

        SysNotice notice = new SysNotice();
        notice.genId();
        notice.setNoticeTitle("Sharing plan");
        notice.setNoticeType("3");
        notice.setStatus("0");
        notice.setNoticeContent(SecurityUtils.getLoginUser().getSysUser().getNickName() +
                "share " + programme.getProgrammeName() + " with you");
        ArrayList<String> list = new ArrayList<>();
        list.add(possessStaff);
        notice.setUserIdList(list);
        sysNoticeService.insertNotice(notice);

        String username = SecurityUtils.getUserId();
        Date date = new Date();
        Information information = new Information();
        Assembler.assemble(programme,information);
        information.setPackFileName(programme1.getPackFileName());
        information.setPackType(programme1.getPackType());
        information.setInformationState(0);
        information.setId(notice.getNoticeId());
        information.setProgrammeId(programmeId);
        information.setCreateStaffNo(username);
        information.setCreateTime(date);
        iInformationService.save(information);
        return "1";
    }


    @Override
    public List<Information> getDisposeShareProgramme(){
        QueryWrapper<Information> queryWrapper = new QueryWrapper<>();

        HashMap<String ,Object> ss = new HashMap<String ,Object>(10);
        ss.put("possess_staff", SecurityUtils.getUserId());

        ss.put("information_state", 0);

        queryWrapper.allEq(ss);
        List<Information> list = iInformationService.list(queryWrapper);
        return list;
    }


    @Override
    @Transactional
    public int removeProgrammeInProjectById(String id) {
        Programme programme = programmeMapper.selectById(id);
        if(programme == null){
            throw new ServiceException("This programme does not exist...");
        }
        String projectId = programme.getProjectId();
        programme.setProjectId("");
        int i = programmeMapper.updateById(programme);
        if(i <= 0 || i > 1){
            throw new ServiceException("Removal programme failed...");
        }
        DesignTBProjectMain designTBProjectMain = projectMapper.selectById(projectId);
        if(designTBProjectMain == null){
            throw new ServiceException("Project does not exist...");
        }
        designTBProjectMain.setProjectProgrammeNumber(designTBProjectMain.getProjectProgrammeNumber() - 1);
        logsService.saveLog(projectId,"remove","project management","design_t_b_project_main",
                "Remove the 【" + programme.getProgrammeName() + "】 from the current project");
        return projectMapper.updateById(designTBProjectMain);
    }

    @Override
    public List<Map<String, String>> getAllUser() {

        String[] roles = new String[]{"gene_general","gene_admin"};
        return userService.listExaminer(roles);
    }


    @Override
    public List<Programme> selectViewedProgrammes() {
        String username = SecurityUtils.getUserId();
        System.err.println(username);
        QueryWrapper<Programme> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("create_staff_no", username);
        queryWrapper.isNotNull("viewed_time");
        queryWrapper.orderByDesc("viewed_time");
        queryWrapper.last("limit 10");
        List<Programme> programmeList = programmeMapper.selectList(queryWrapper);
        return programmeList;
    }

    @Override
    public Boolean checkName(String programmeName,String id) {
        QueryWrapper<Programme> wrapper = new QueryWrapper<Programme>()
                .eq("programme_name", programmeName)
                .eq("create_staff_no", SecurityUtils.getUserId());

        if(id!=null){
            wrapper.ne("id", id);
        }
        Long count = programmeMapper.selectCount(wrapper);
        if(count == null || count == 0L){
            return  false;
        }
        return true;
    }


    public Map<String,String> shareProgramme2(Programme programme){
        String programmeId = programme.getId();
        Integer programmeState1 = programme.getProgrammeState();
        String possessStaff = programme.getPossessStaff();
        if(programmeId == null && programmeState1 == null && possessStaff == null){
            throw new ServiceException("Sharing failed！");
        }
        String username = SecurityUtils.getUserId();
        Date date = new Date();


        QueryWrapper<Programme> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", programmeId);
        Programme one = this.getOne(queryWrapper, false);
        if(one == null){
            throw new ServiceException("The shared plan does not exist!");
        }
        String snowflakeNextIdStr = IdUtil.getSnowflakeNextIdStr();
        String sourceFileMergeName = one.getSourceFileMergeName();
        one.setId(snowflakeNextIdStr);
        one.setProgrammeName(programme.getProgrammeName());
        one.setProgrammeState(programmeState1);
        one.setPossessStaff(possessStaff);
        one.setCreateStaffNo(username);
        one.setCreateTime(date);
        one.setProjectId("");

        if(programmeState1 != 2){
            one.setPackState(1);
        }else{
            one.setPackFileName(programme.getPackFileName());
        }

        this.save(one);
        String separator = File.separator;
        File copy = FileUtil.copy(new File(sequencePath + separator + programmeId + separator + sourceFileMergeName),
                new File(sequencePath + separator + snowflakeNextIdStr + separator + one.getSourceFileMergeName()), true);
        Map<String,String> map = new HashMap();
        map.put("fileName", copy.getName());
        map.put("programmeId", snowflakeNextIdStr);


        if(one.getPackType().contains("2")){
            List<Sequence> sequenceList = sequenceMapper.list(new Sequence().setProgrammeId(programmeId));
            if(!sequenceList.isEmpty()){
                List<Long> sequenceIds = new ArrayList<>();
                for (Sequence sequence : sequenceList) {
                    sequenceIds.add(sequence.getId());
                }
                sequenceService.removeByIdList(sequenceIds);
                sequenceOperateService.removeBySequenceIdList(sequenceIds);
                featureService.removeBySequenceIdList(sequenceIds,null);
            }
        }

        return map;
    }

    @Override
    public Map<String,String> disposeShareProgramme(String id, Integer informationState, String newProgrammeName){
        QueryWrapper<Information> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        Information one = iInformationService.getOne(queryWrapper, false);
        if(one == null){
            throw new ServiceException("Message unique identifier, data not found");
        }
        one.setInformationState(informationState);
        iInformationService.updateById(one);

        if(informationState == 2){
            Programme programme = new Programme();
            Assembler.assemble(one,programme);
            programme.setId(one.getProgrammeId());
            programme.setProgrammeName(newProgrammeName);
            return shareProgramme2(programme);
        }
        return null;
    }


    private String copyFile(String packFileId) {
        return null;
    }


    @Override
    public List<ProgrammeVO> listNoProjectProgrammes(String projectId) {
        Programme programme = new Programme().setProjectId(projectId).
                setCreateStaffNo(SecurityUtils.getLoginUser().getUserid());
        return programmeMapper.listNoProjectProgrammes(programme);
    }

    @Override
    public AjaxResult updateProject(String[] ids, String projectId, String projectName) {
        for(String id : ids){
            Programme programme = programmeMapper.selectById(id);
            if(programme == null){
                throw new ServiceException("The addition scheme does not exist");
            }
            String oldProjectId = programme.getProjectId();
            String programmeName = programme.getProgrammeName();
            String oldProjectName = "";
            if(StringUtils.hasLength(oldProjectId)){
                oldProjectName  = projectMapper.selectById(oldProjectId).getProjectName();
            }
            programme.setProjectId(projectId);
            int i = programmeMapper.updateById(programme);
            if(i < 1){
                throw new ServiceException("operation failed！");
            }
            if(StringUtils.hasLength(oldProjectName)){
                logsService.saveLog(oldProjectId,"remove","project","design_t_b_project_main",
                        "Remove 【" + programmeName + "】from the current project ,and add it to the project【" + projectName + "】");
                logsService.saveLog(projectId,"newly added","project","design_t_b_project_main",
                        "Move【" + programmeName + "】from the current project【"+ oldProjectName +"】to current project");
            }else{
                logsService.saveLog(projectId,"newly added","project","design_t_b_project_main",
                        "Add【" + programmeName + "】to current project");
            }
        }
        return AjaxResult.success();
    }

    @Override
    public void toLab(Map<String, String> params) {
        String programmeId = params.get("programmeId");
        Programme programme = programmeMapper.selectById(programmeId);
        System.err.println("params:"+params);
        SysNotice sysNotice= new SysNotice();
        String title = "Approved by Cloud Lab";
        sysNotice.setNoticeTitle(title);
        String content = "Your proposal is named《"+programme.getProgrammeName()+"》has been approved by Cloud Lab!";
        sysNotice.setNoticeContent(content);
        sysNotice.setUserIdList(Arrays.asList(programme.getCreateStaffNo()));
        sysNoticeService.insertNotice(sysNotice);
    }

    @Override
    public Programme getLastViewProgramme() {
        return programmeMapper.getLastViewProgramme(SecurityUtils.getUserId());
    }

    private String getSequenceFolderPath(String programmeId){
        return sequencePath +"/"+programmeId+"/";
    }

    private void copy(String oldId, String newId){
        List<Sequence> sequenceList = sequenceMapper.list(new Sequence().setProgrammeId(oldId));
        if(!sequenceList.isEmpty()){
            for (Sequence sequence : sequenceList) {
                Long id = sequence.getId();
                sequence.setId(null).setProgrammeId(newId);
                sequenceMapper.save(sequence);

                List<SequenceOperate> list = sequenceOperateMapper.list(new SequenceOperate().setSequenceId(id));

                List<Long> oldOperateIds = new ArrayList<>();
                List<Long> newOperateIds = new ArrayList<>();
                Map<Long,Long> operateIds = new HashMap<>();
                if(!list.isEmpty()){
                    for (SequenceOperate operate : list) {
                        oldOperateIds.add(operate.getId());
                        operate.setId(null).setSequenceId(sequence.getId());
                    }
                    Collections.reverse(list);
                    sequenceOperateMapper.saveList(list);
                    list = sequenceOperateMapper.list(new SequenceOperate().setSequenceId(sequence.getId()));
                    for (SequenceOperate operate : list) {
                        newOperateIds.add(operate.getId());
                    }
                    Long[] oldIds = new Long[oldOperateIds.size()];
                    Long[] newIds = new Long[newOperateIds.size()];
                    oldIds = oldOperateIds.toArray(oldIds);
                    Arrays.sort(oldIds);
                    newIds = newOperateIds.toArray(newIds);
                    Arrays.sort(newIds);
                    for (int i = 0; i < oldIds.length; i++) {
                        operateIds.put(oldIds[i],newIds[i]);
                    }
                }

                List<Feature> saveFeatures = new ArrayList<>(2000);
                List<Feature> fList = featureMapper.listBySequenceId(id);
                if(!fList.isEmpty()){
                    for(Feature feature : fList){
                        Map<String, String> history = feature.getHistory();
                        Map<String,String> map = new HashMap<>();
                        if (history == null) {
                            history = new HashMap<>();
                        }else{

                            for(Long key : operateIds.keySet()){
                                if(history.containsKey(key.toString())){
                                    String value = history.get(key.toString());
                                    map.put(operateIds.get(key).toString(),value);
                                }
                            }
                        }
                        feature.setHistory(map);
                        feature.setSequenceId(sequence.getId());
                        saveFeatures.add(feature);
                        if(saveFeatures.size()==2000){

                            featureMapper.saveCloneList(saveFeatures);
                            saveFeatures.clear();
                        }
                    }
                    if(!saveFeatures.isEmpty()){
                        featureMapper.saveCloneList(saveFeatures);
                        saveFeatures.clear();
                    }
                }
            }
        }
    }

}
