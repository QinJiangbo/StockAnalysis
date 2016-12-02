package com.whu.util;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 03/12/2016
 * Author: qinjiangbo@github.io
 */
public class EhCacheUtil {

    private static EhCacheUtil ehCache;
    private CacheManager cacheManager;

    /**
     * 私有构造函数
     */
    private EhCacheUtil() {
        cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("myCache", CacheConfigurationBuilder
                        .newCacheConfigurationBuilder(String.class, List.class, ResourcePoolsBuilder.heap(10)))
                .build();
        cacheManager.init();
    }

    /**
     * 获取单例
     * @return
     */
    public static EhCacheUtil getInstance() {
        if (ehCache == null) {
            ehCache = new EhCacheUtil();
        }
        return ehCache;
    }

    /**
     * 存入缓存
     * @param key
     * @param value
     */
    public void put(String key, List<String> value) {
        Cache<String, List> myCache = cacheManager.getCache("myCache", String.class, List.class);
        myCache.put(key, value);
    }

    /**
     * 查询缓存
     * @param key
     * @return
     */
    public List<String> get(String key) {
        Cache<String, List> myCache = cacheManager.getCache("myCache", String.class, List.class);
        return myCache.get(key);
    }

    /**
     * 移除缓存
     * @param key
     */
    public void remove(String key) {
        Cache<String, List> myCache = cacheManager.getCache("myCache", String.class, List.class);
        myCache.remove(key);
    }

    /**
     * 销毁缓存
     * @return
     */
    public void disposeCache() {
        cacheManager.removeCache("myCache");
        cacheManager.close();
    }

}
