package com.hmzhkj.system.mapper;

import java.util.List;

import com.hmzhkj.system.domain.SysLogininfor;


public interface SysLogininforMapper
{

    int insertLogininfor(SysLogininfor logininfor);



    List<SysLogininfor> selectLogininforList(SysLogininfor logininfor);


    SysLogininfor selectLogininforById(String infoId);


    int deleteLogininforByIds(String[] infoIds);


    int cleanLogininfor();
}
