package com.hmzhkj.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.hmzhkj.common.core.utils.StringUtils;
import com.hmzhkj.system.domain.SysOperLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.hmzhkj.system.mapper.SysOperLogMapper;
import com.hmzhkj.system.service.ISysOperLogService;

 
@Service
@RequiredArgsConstructor
public class SysOperLogServiceImpl implements ISysOperLogService
{
    private final SysOperLogMapper operLogMapper;

     
    @Override
    public int insertOperlog(SysOperLog operLog)
    {
        String operParamStr = operLog.getOperParam();
        String key = "password";
        if(operParamStr!=null && operParamStr.indexOf(key)!=-1){
            try {
                JSONObject jo= JSON.parseObject(operParamStr);
                if(jo.containsKey(key)){
                    jo.remove(key);
                }
                operLog.setOperParam(jo.toJSONString());
            }catch (Exception e){

            }

        }
        operLog.genId();
        return operLogMapper.insertOperlog(operLog);
    }

    @Override
    public List selectOperLogListByIds(String[] ids) {
        List<SysOperLog> sysOperLogs = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            SysOperLog sysOperLog = operLogMapper.selectOperLogById(ids[i]);
            sysOperLogs.add(sysOperLog);
        }
        return sysOperLogs;
    }

     
    @Override
    public List<SysOperLog> selectOperLogList(SysOperLog operLog){
        String title = operLog.getTitle();
        String operName = operLog.getOperName();
        if(!StringUtils.isEmpty(title)){
            operLog.setTitle(title.trim());
        }
        if(!StringUtils.isEmpty(operName)){
            operLog.setOperName(operName.trim());
        }
        return operLogMapper.selectOperLogList(operLog);
    }

     
    @Override
    public int deleteOperLogByIds(String[] operIds)
    {
        return operLogMapper.deleteOperLogByIds(operIds);
    }

     
    @Override
    public SysOperLog selectOperLogById(String operId)
    {
        return operLogMapper.selectOperLogById(operId);
    }

     
    @Override
    public void cleanOperLog()
    {
        operLogMapper.cleanOperLog();
    }
}
