package com.zjht.manager.service;

import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.ValueOperations;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 〈redis缓存工具类〉
 *
 * @author wangpeng
 * @create 2017/10/11
 * @since 1.0.0
 */
public interface RedisCacheService<T> {
    <T> ValueOperations<String, T> setCacheObject(String key, T value);
    <T> T getCacheObject(String key);
    <T> ListOperations<String, T> setCacheList(String key, List<T> dataList);
    <T> List<T> getCacheList(String key);
    <T> BoundSetOperations<String, T> setCacheSet(String key, Set<T> dataSet);
    Set<T> getCacheSet(String key);
    <T> HashOperations<String, String, T> setCacheMap(String key, Map<String, T> dataMap);
    <T> Map<String, T> getCacheMap(String key);
    <T> HashOperations<String, Integer, T> setCacheIntegerMap(String key, Map<Integer, T> dataMap);
    <T> Map<Integer, T> getCacheIntegerMap(String key);
    void remove(String key);
}