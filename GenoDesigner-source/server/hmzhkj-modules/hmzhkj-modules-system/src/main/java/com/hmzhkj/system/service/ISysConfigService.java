package com.hmzhkj.system.service;

import java.util.List;
import com.hmzhkj.system.domain.SysConfig;


public interface ISysConfigService
{

    public SysConfig selectConfigById(String configId);


    public String selectConfigByKey(String configKey);


    public List<SysConfig> selectConfigList(SysConfig config);


    public int insertConfig(SysConfig config);


    public int updateConfig(SysConfig config);


    public void deleteConfigByIds(String[] configIds);


    public void loadingConfigCache();


    public void clearConfigCache();


    public void resetConfigCache();


    public String checkConfigKeyUnique(SysConfig config);
}
