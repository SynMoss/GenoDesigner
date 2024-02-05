package com.hmzhkj.framework.service;

import com.hmzhkj.system.domain.SysOperLog;
import com.hmzhkj.system.service.ISysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

 
@Service
public class AsyncLogService
{
    @Autowired
    private ISysOperLogService sysOperLogService;

     
    @Async
    public void saveSysLog(SysOperLog sysOperLog)
    {
        sysOperLogService.insertOperlog(sysOperLog);
    }
}
