package com.hmzhkj.system.mapper;

import com.hmzhkj.system.domain.SysOperLog;

import java.util.List;


public interface SysOperLogMapper
{

    int insertOperlog(SysOperLog operLog);



    List<SysOperLog> selectOperLogList(SysOperLog operLog);


    int deleteOperLogByIds(String[] operIds);


    SysOperLog selectOperLogById(String operId);


    void cleanOperLog();
}
