package com.hmzhkj.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.hmzhkj.common.core.utils.StringUtils;
import com.hmzhkj.system.domain.SysLogininfor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.hmzhkj.system.mapper.SysLogininforMapper;
import com.hmzhkj.system.service.ISysLogininforService;

 
@Service
@RequiredArgsConstructor
public class SysLogininforServiceImpl implements ISysLogininforService
{

    private final SysLogininforMapper logininforMapper;

    @Override
    public List selectLoginforListByIds(String[] ids) {
        List<SysLogininfor> sysLogininfors = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            SysLogininfor sysLogininfor = logininforMapper.selectLogininforById(ids[i]);
            sysLogininfors.add(sysLogininfor);
        }
        return sysLogininfors;
    }

     
    @Override
    public int insertLogininfor(SysLogininfor logininfor)
    {
        logininfor.genId();
        return logininforMapper.insertLogininfor(logininfor);
    }

     
    @Override
    public List<SysLogininfor> selectLogininforList(SysLogininfor logininfor){
        String ipaddr = logininfor.getIpaddr();
        String userName = logininfor.getUserName();
        if(!StringUtils.isEmpty(ipaddr)){
            logininfor.setIpaddr(ipaddr.trim());
        }
        if(!StringUtils.isEmpty(userName)){
            logininfor.setUserName(userName.trim());
        }
        return logininforMapper.selectLogininforList(logininfor);
    }

     
    @Override
    public int deleteLogininforByIds(String[] infoIds)
    {
        return logininforMapper.deleteLogininforByIds(infoIds);
    }

     
    @Override
    public void cleanLogininfor()
    {
        logininforMapper.cleanLogininfor();
    }
}
