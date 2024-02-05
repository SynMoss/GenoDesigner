package com.hmzhkj.gene.service.impl;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmzhkj.common.core.exception.ServiceException;
import com.hmzhkj.framework.utils.SecurityUtils;
import com.hmzhkj.gene.domain.Template;
import com.hmzhkj.gene.mapper.ProgrammeMapper;
import com.hmzhkj.gene.mapper.TemplateMapper;
import com.hmzhkj.gene.service.CommonService;
import com.hmzhkj.gene.service.SequenceService;
import com.hmzhkj.gene.service.TemplateService;
import com.hmzhkj.gene.domain.Programme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class TemplateServiceImpl extends ServiceImpl<TemplateMapper, Template> implements TemplateService {
    @Autowired
    private TemplateMapper templateMapper;
    @Autowired
    private ProgrammeMapper programmeMapper;
    @Autowired
    private SequenceService sequenceService;
    @Autowired
    private CommonService commonService;
    @Value("${path.sequence}")
    private String sequencePath;
    @Value("${path.template}")
    private String templatePath;

    public List<Template> getTemplateList(String name) {
        List<Template> templateList = templateMapper.getTemplateList(SecurityUtils.getUserId(),name);
        return templateList;
    }

    @Override
    public Boolean checkName(String name,String id) {
        QueryWrapper<Template> wrapper = new QueryWrapper<Template>()
                .eq("name", name)
                .eq("creater", SecurityUtils.getUserId());
                 if(id!=null){
            wrapper.ne("id", id);
        }
        Long count = templateMapper.selectCount(wrapper);
        if(count == null || count == 0L){
            return  false;
        }
        return true;
    }

    @Override
    @Transactional
    public void saveTemplate(Template template) {
        template.setCreater(SecurityUtils.getUserId());
        template.setId(IdUtil.getSnowflakeNextIdStr());
        template.setCreateTime(new Date());

        String separator = File.separator;
        String programmeId = template.getProgrammeId();
        Programme programme = programmeMapper.selectById(programmeId);
                   String sequenceFolder = commonService.getSequenceFolderPath(programmeId);
        String gbffName = programme.getProgrammeName()+".gbff";
        File localFile = new File(sequenceFolder+gbffName);
 
                 if(!localFile.exists()){
             sequenceService.pack(programmeId, Collections.singletonList("1"));
        }
                 String templateFilePath = templatePath + separator + template.getId() + ".gbff";
        File templateFile = FileUtil.copy(localFile, new File(templateFilePath), true);
        if(!templateFile.exists()){
            throw new ServiceException("Failed to create template file！");
        }
        template.setTemplateFileName(template.getId() + ".gbff");
        save(template);
    }

     
    @Override
    @Transactional
    public void deleteTemplate(String id) {
        boolean isSuccess = removeById(id);
        if(isSuccess){
            FileUtil.del(new File(templatePath + File.separator + id + ".gbff"));
        }
    }

}
