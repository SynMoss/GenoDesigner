package com.hmzhkj.gene.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hmzhkj.gene.domain.dto.ProgrammeQueryParam;
import com.hmzhkj.gene.domain.vo.ProgrammeVO;
import com.hmzhkj.gene.domain.Programme;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

 
@Mapper
public interface ProgrammeMapper extends BaseMapper<Programme> {

    List<ProgrammeVO> queryProgrammeList(ProgrammeQueryParam queryParams);

    List<ProgrammeVO> listNoProjectProgrammes(Programme programme);
    Programme getLastViewProgramme(String possessStaff);
}
