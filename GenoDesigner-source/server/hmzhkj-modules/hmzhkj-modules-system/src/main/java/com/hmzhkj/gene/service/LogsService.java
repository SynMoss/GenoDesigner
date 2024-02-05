package com.hmzhkj.gene.service;

import com.hmzhkj.gene.domain.Logs;

import java.util.List;


public interface LogsService
{


    public List<Logs> selectLogsList(String relationId);


    public void saveLog(String relationId, String operationType, String operationModule, String objectSource, String operationLog);
}
