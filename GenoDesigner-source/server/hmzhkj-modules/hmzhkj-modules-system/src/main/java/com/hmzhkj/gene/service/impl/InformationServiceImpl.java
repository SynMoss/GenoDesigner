package com.hmzhkj.gene.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmzhkj.gene.domain.Information;
import com.hmzhkj.gene.mapper.InformationMapper;
import com.hmzhkj.gene.service.IInformationService;
import org.springframework.stereotype.Service;

 
@Service
public class InformationServiceImpl extends ServiceImpl<InformationMapper, Information> implements IInformationService {

}
