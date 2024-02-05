package com.hmzhkj.gene.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmzhkj.framework.utils.SecurityUtils;
import com.hmzhkj.gene.domain.Logs;
import com.hmzhkj.gene.mapper.LogsMapper;
import com.hmzhkj.gene.service.LogsService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

 
@Service
@RequiredArgsConstructor
public class LogsServiceImpl extends ServiceImpl<LogsMapper, Logs> implements LogsService {
    private final LogsMapper logsMapper;

     
    @Override
    public List<Logs> selectLogsList(String relationId) {
        return logsMapper.selectLogsList(relationId);
    }

    @Async
    @Override
    public void saveLog(String relationId, String operationType, String operationModule, String objectSource, String operationLog) {
        Logs logs = new Logs().setRelationId(relationId).setStaffId(SecurityUtils.getUserId()).
                setOperationType(operationType).setOperationModule(operationModule).
                setObjectSource(objectSource).setOperationLog(operationLog);
        logsMapper.insert(logs);
    }
}
