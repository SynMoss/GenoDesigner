package com.hmzhkj.gene.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hmzhkj.gene.domain.Part;
import com.hmzhkj.gene.domain.dto.QueryParamDto;

import java.util.List;

public interface PartService extends IService<Part> {
    List<Part> listPage(QueryParamDto queryDto);

    boolean addPart(Part part);

    Boolean checkName(String name, String id);
}
