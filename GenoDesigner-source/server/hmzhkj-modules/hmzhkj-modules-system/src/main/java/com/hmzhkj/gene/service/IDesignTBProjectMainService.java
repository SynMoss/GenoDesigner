package com.hmzhkj.gene.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hmzhkj.gene.domain.DesignTBProjectMain;
import com.hmzhkj.gene.domain.vo.ProjectVo;

import java.util.List;
import java.util.Map;

 
public interface IDesignTBProjectMainService extends IService<DesignTBProjectMain>{
     
    public DesignTBProjectMain selectDesignTBProjectMainById(String id);

     
    public List<DesignTBProjectMain> selectDesignTBProjectMainList(DesignTBProjectMain designTBProjectMain);

     
    public int insertDesignTBProjectMain(DesignTBProjectMain designTBProjectMain);

     
    public int updateDesignTBProjectMain(DesignTBProjectMain designTBProjectMain);

     
    public int deleteDesignTBProjectMainById(DesignTBProjectMain designTBProjectMain);

     
    public List<Map<String,String>> selectProjectCreateStaffList();

     
    public List<ProjectVo> selectAllProjects();

    Boolean checkName(String projectName, String id);
}
