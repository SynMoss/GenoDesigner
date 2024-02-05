package com.hmzhkj.system.mapper;

import java.util.List;
import com.hmzhkj.system.domain.SysConfig;


public interface SysConfigMapper
{

    SysConfig selectConfig(SysConfig config);


    List<SysConfig> selectConfigList(SysConfig config);


    SysConfig checkConfigKeyUnique(String configKey);


    int insertConfig(SysConfig config);


    int updateConfig(SysConfig config);


    int deleteConfigById(String configId);


    int deleteConfigByIds(String[] configIds);
}