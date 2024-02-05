package com.hmzhkj.common.redis.service;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;


@SuppressWarnings(value = { "unchecked", "rawtypes" })
@Component
public class RedisService
{
    @Autowired
    public RedisTemplate redisTemplate;


    public <T> void setCacheObject(final String key, final T value)
    {
        redisTemplate.opsForValue().set(key, value);
    }


    public <T> void setCacheObject(final String key, final T value, final Long timeout, final TimeUnit timeUnit)
    {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("The increment factor must be greater than 0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    public boolean expire(final String key, final long timeout)
    {
        return expire(key, timeout, TimeUnit.SECONDS);
    }


    public boolean expire(final String key, final long timeout, final TimeUnit unit)
    {
        return redisTemplate.expire(key, timeout, unit);
    }


    public long getExpire(final String key)
    {
        return redisTemplate.getExpire(key);
    }


    public Boolean hasKey(String key)
    {
        return redisTemplate.hasKey(key);
    }


    public <T> T getCacheObject(final String key)
    {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }


    public boolean deleteObject(final String key)
    {
        return redisTemplate.delete(key);
    }
    public long deleteAll(final String key)
    {
        Set<String> keys = redisTemplate.keys(key + "*");
        return redisTemplate.delete(keys);
    }

    public boolean deleteObject(final Collection collection)
    {
        return redisTemplate.delete(collection) > 0;
    }


    public <T> long setCacheList(final String key, final List<T> dataList)
    {
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        return count == null ? 0 : count;
    }


    public <T> List<T> getCacheList(final String key)
    {
        return redisTemplate.opsForList().range(key, 0, -1);
    }


    public <T> BoundSetOperations<String, T> setCacheSet(final String key, final Set<T> dataSet)
    {
        BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
        Iterator<T> it = dataSet.iterator();
        while (it.hasNext())
        {
            setOperation.add(it.next());
        }
        return setOperation;
    }


    public <T> Set<T> getCacheSet(final String key)
    {
        return redisTemplate.opsForSet().members(key);
    }


    public <T> void setCacheMap(final String key, final Map<String, T> dataMap)
    {
        if (dataMap != null) {
            redisTemplate.opsForHash().putAll(key, dataMap);
        }
    }


    public <T> Map<String, T> getCacheMap(final String key)
    {
        return redisTemplate.opsForHash().entries(key);
    }


    public <T> void setCacheMapValue(final String key, final String hKey, final T value)
    {
        redisTemplate.opsForHash().put(key, hKey, value);
    }


    public <T> T getCacheMapValue(final String key, final String hKey)
    {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(key, hKey);
    }


    public <T> List<T> getMultiCacheMapValue(final String key, final Collection<Object> hKeys)
    {
        return redisTemplate.opsForHash().multiGet(key, hKeys);
    }


    public boolean deleteCacheMapValue(final String key, final String hKey)
    {
        return redisTemplate.opsForHash().delete(key, hKey) > 0;
    }


    public Collection<String> keys(final String pattern)
    {
        return redisTemplate.keys(pattern);
    }
}
