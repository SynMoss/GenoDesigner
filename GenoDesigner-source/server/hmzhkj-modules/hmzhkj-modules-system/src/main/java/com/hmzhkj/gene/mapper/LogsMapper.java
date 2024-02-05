package com.hmzhkj.gene.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hmzhkj.gene.domain.Logs;

import java.util.List;


public interface LogsMapper extends BaseMapper<Logs>{


    public List<Logs> selectLogsList(String relationId);

}
