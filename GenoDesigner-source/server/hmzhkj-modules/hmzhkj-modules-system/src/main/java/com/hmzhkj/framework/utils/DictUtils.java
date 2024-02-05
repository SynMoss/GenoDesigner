package com.hmzhkj.framework.utils;

import java.util.Collection;
import java.util.List;
import com.alibaba.fastjson2.JSONArray;
import com.hmzhkj.common.core.constant.CacheConstants;
import com.hmzhkj.common.core.utils.SpringUtils;
import com.hmzhkj.common.core.utils.StringUtils;
import com.hmzhkj.common.redis.service.RedisService;
import com.hmzhkj.system.domain.SysDictData;

 
public class DictUtils
{
     
    public static void setDictCache(String key, List<SysDictData> dictDatas)
    {
        SpringUtils.getBean(RedisService.class).setCacheObject(getCacheKey(key), dictDatas);
    }

     
    public static List<SysDictData> getDictCache(String key)
    {
        JSONArray arrayCache = SpringUtils.getBean(RedisService.class).getCacheObject(getCacheKey(key));
        if (StringUtils.isNotNull(arrayCache))
        {
            return arrayCache.toList(SysDictData.class);
        }
        return null;
    }

     
    public static void removeDictCache(String key)
    {
        SpringUtils.getBean(RedisService.class).deleteObject(getCacheKey(key));
    }

     
    public static void clearDictCache()
    {
        Collection<String> keys = SpringUtils.getBean(RedisService.class).keys(CacheConstants.SYS_DICT_KEY + "*");
        SpringUtils.getBean(RedisService.class).deleteObject(keys);
    }

     
    public static String getCacheKey(String configKey)
    {
        return CacheConstants.SYS_DICT_KEY + configKey;
    }
}
