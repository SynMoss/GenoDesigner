package com.hmzhkj.system.service;

import com.hmzhkj.system.domain.SysOperLog;

import java.util.List;

 
public interface ISysOperLogService
{
     
    public int insertOperlog(SysOperLog operLog);


    public List selectOperLogListByIds(String[] ids);


     
    public List<SysOperLog> selectOperLogList(SysOperLog operLog);

     
    public int deleteOperLogByIds(String[] operIds);

     
    public SysOperLog selectOperLogById(String operId);

     
    public void cleanOperLog();
}
