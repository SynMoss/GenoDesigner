package com.hmzhkj.gene.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hmzhkj.common.core.web.domain.AjaxResult;
import com.hmzhkj.gene.domain.Information;
import com.hmzhkj.gene.domain.dto.ProgrammeQueryParam;
import com.hmzhkj.gene.domain.vo.ProgrammeVO;
import com.hmzhkj.gene.domain.Programme;

import java.util.List;
import java.util.Map;

 
public interface IProgrammeService extends IService<Programme> {

     
    List<ProgrammeVO>queryPageList(ProgrammeQueryParam queryParams);
    void deleteBatch(String ids);

    void cloneProgramme(Programme programme);

    String shareProgramme(Programme programme);

    void share(Programme programme);

    Map<String,String> disposeShareProgramme(String id, Integer informationState, String newProgrammeName);
    List<Information> getDisposeShareProgramme();


     
    int removeProgrammeInProjectById(String id);


    List<Map<String, String>> getAllUser();

    List<Programme> selectViewedProgrammes();

    Boolean checkName(String programmeName, String id);

    Map<String,String> createByTemplate(Programme programme);

    AjaxResult createBlank(Programme programme);

    List<ProgrammeVO> listNoProjectProgrammes(String projectId);

    AjaxResult updateProject(String[] ids, String projectId, String projectName);


    void toLab(Map<String, String> params);
    Programme getLastViewProgramme();
}
