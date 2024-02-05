package com.hmzhkj.gene.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hmzhkj.gene.domain.DesignTBProjectMain;
import com.hmzhkj.gene.domain.vo.ProjectVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

 
@Mapper
public interface DesignTBProjectMainMapper extends BaseMapper<DesignTBProjectMain>{
     
    public DesignTBProjectMain selectDesignTBProjectMainById(String id);

     
    public List<DesignTBProjectMain> selectDesignTBProjectMainList(DesignTBProjectMain designTBProjectMain);

     
    public int updateViewedById(String id);

     
    public List<Map<String,String>> selectProjectCreateStaffList(String loginStaffId);

    public List<ProjectVo> selectAllProject(String loginStaffId);
}
