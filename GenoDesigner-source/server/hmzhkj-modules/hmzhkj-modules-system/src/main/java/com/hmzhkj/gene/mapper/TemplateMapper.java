package com.hmzhkj.gene.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hmzhkj.gene.domain.Template;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TemplateMapper extends BaseMapper<Template> {

    List<Template> getTemplateList(@Param("id")String id, @Param("name")String name);
}
