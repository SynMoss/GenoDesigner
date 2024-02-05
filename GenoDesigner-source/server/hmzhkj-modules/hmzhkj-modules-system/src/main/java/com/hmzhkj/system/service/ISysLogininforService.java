package com.hmzhkj.system.service;

import com.hmzhkj.system.domain.SysLogininfor;

import java.util.List;


public interface ISysLogininforService
{

    public List selectLoginforListByIds(String[] ids);

    public int insertLogininfor(SysLogininfor logininfor);


    public List<SysLogininfor> selectLogininforList(SysLogininfor logininfor);


    public int deleteLogininforByIds(String[] infoIds);


    public void cleanLogininfor();
}
