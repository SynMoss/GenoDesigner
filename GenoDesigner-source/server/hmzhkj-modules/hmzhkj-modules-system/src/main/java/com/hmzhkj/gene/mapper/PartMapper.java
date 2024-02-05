package com.hmzhkj.gene.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hmzhkj.gene.domain.Part;
import com.hmzhkj.gene.domain.dto.QueryParamDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PartMapper extends BaseMapper<Part> {
    List<Part> queryListPage(QueryParamDto queryDto);
}
