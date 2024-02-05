package com.hmzhkj.system.service.impl;

import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.hmzhkj.common.core.constant.CacheConstants;
import com.hmzhkj.common.core.constant.UserConstants;
import com.hmzhkj.common.core.exception.ServiceException;
import com.hmzhkj.common.core.text.Convert;
import com.hmzhkj.common.core.utils.StringUtils;
import com.hmzhkj.common.redis.service.RedisService;
import com.hmzhkj.system.domain.SysConfig;
import com.hmzhkj.system.mapper.SysConfigMapper;
import com.hmzhkj.system.service.ISysConfigService;

 
@Service
@RequiredArgsConstructor
public class SysConfigServiceImpl implements ISysConfigService
{
    private final SysConfigMapper configMapper;

    private final RedisService redisService;

     
    @PostConstruct
    public void init()
    {
        loadingConfigCache();
    }

     
    @Override
    public SysConfig selectConfigById(String configId)
    {
        SysConfig config = new SysConfig();
        config.setConfigId(configId);
        return configMapper.selectConfig(config);
    }

     
    @Override
    public String selectConfigByKey(String configKey)
    {
        String configValue = Convert.toStr(redisService.getCacheObject(getCacheKey(configKey)));
        if (StringUtils.isNotEmpty(configValue))
        {
            return configValue;
        }
        SysConfig config = new SysConfig();
        config.setConfigKey(configKey);
        SysConfig retConfig = configMapper.selectConfig(config);
        if (StringUtils.isNotNull(retConfig))
        {
            redisService.setCacheObject(getCacheKey(configKey), retConfig.getConfigValue());
            return retConfig.getConfigValue();
        }
        return StringUtils.EMPTY;
    }

     
    @Override
    public List<SysConfig> selectConfigList(SysConfig config)
    {
        return configMapper.selectConfigList(config);
    }

     
    @Override
    public int insertConfig(SysConfig config)
    {
        config.genId();
        int row = configMapper.insertConfig(config);
        if (row > 0)
        {
            redisService.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
        }
        return row;
    }

     
    @Override
    public int updateConfig(SysConfig config)
    {
        int row = configMapper.updateConfig(config);
        if (row > 0)
        {
            redisService.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
        }
        return row;
    }

     
    @Override
    public void deleteConfigByIds(String[] configIds)
    {
        for (String configId : configIds)
        {
            SysConfig config = selectConfigById(configId);
            if (StringUtils.equals(UserConstants.YES, config.getConfigType()))
            {
                throw new ServiceException(String.format("Built in parameters【%1$s】CANNOT DELETE ", config.getConfigKey()));
            }
            configMapper.deleteConfigById(configId);
            redisService.deleteObject(getCacheKey(config.getConfigKey()));
        }
    }

     
    @Override
    public void loadingConfigCache()
    {
        List<SysConfig> configsList = configMapper.selectConfigList(new SysConfig());
        for (SysConfig config : configsList)
        {
            redisService.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
        }
    }

     
    @Override
    public void clearConfigCache()
    {
        Collection<String> keys = redisService.keys(CacheConstants.SYS_CONFIG_KEY + "*");
        redisService.deleteObject(keys);
    }

     
    @Override
    public void resetConfigCache()
    {
        clearConfigCache();
        loadingConfigCache();
    }

     
    @Override
    public String checkConfigKeyUnique(SysConfig config)
    {
        SysConfig info = configMapper.checkConfigKeyUnique(config.getConfigKey());
        if (StringUtils.isNotNull(info) && !info.getConfigId().equals(config.getConfigId()))
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

     
    private String getCacheKey(String configKey)
    {
        return CacheConstants.SYS_CONFIG_KEY + configKey;
    }
}
