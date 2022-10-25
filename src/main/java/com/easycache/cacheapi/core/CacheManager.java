package com.easycache.cacheapi.core;

import com.easycache.cacheapi.core.annotation.CacheSourceType;
import lombok.Data;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Supplier;

/**
 * @author zw35
 */
public interface CacheManager {
    /**
     * 缓存命中次数
     */
    LongAdder KEYSPACE_HITS = new LongAdder();

    /**
     * 缓存没有命中次数
     */
    LongAdder KEYSPACE_MISSES = new LongAdder();


    /**
     * 添加缓存
     *
     * @param key   缓存 key
     * @param value 缓存 值
     */
    void put(String key, Object value);


    /**
     * 添加时效缓存
     *
     * @param key      缓存 key
     * @param value    缓存 值
     * @param expire   过期时间
     * @param timeUnit 时间单位
     */
    void put(String key, Object value, long expire, TimeUnit timeUnit);

    /**
     * 删除缓存
     *
     * @param key 缓存 key
     */
    void remove(String key);

    /**
     * 获取缓存
     *
     * @param key    缓存key
     * @param tClass value 对象类型
     * @param <T> 返回对象类型
     * @return 缓存值
     */
    <T> T get(String key, Class<T> tClass);

    /**
     * 获取缓存，缓存失效或不存在，则添加缓存信息并返回
     *
     * @param key       key
     * @param expire    过期时间
     * @param timeUnit  时间单位
     * @param tClass    value 对象类型
     * @param cacheData 缓存数据接口
     * @param <T>       值类型
     * @return 缓存值
     */
    default <T> T getOrAddCache(String key, long expire, TimeUnit timeUnit, Class<T> tClass, Supplier<T> cacheData) {
        T t = get(key, tClass);
        if (t == null) {
            KEYSPACE_MISSES.increment();
            T data = cacheData.get();
            put(key, data, expire, timeUnit);
            return data;
        } else {
            KEYSPACE_HITS.increment();
            return t;
        }
    }

    /**
     * 获取当前缓存管理器信息
     * 1. 缓存命中率；
     *
     * @return 缓存管理器信息
     */
    default CacheManagerInfo getCacheManagerInfo() {
        float hitRate = KEYSPACE_HITS.sum() / (KEYSPACE_HITS.sum() + KEYSPACE_MISSES.floatValue());
        return new CacheManagerInfo(hitRate);
    }


    /**
     * 缓存实现类型
     *
     * @return 缓存存储源类型
     */
    CacheSourceType getType();

    @Data
    class CacheManagerInfo {
        private float cacheHitRate;

        public CacheManagerInfo(float cacheHitRate) {
            this.cacheHitRate = cacheHitRate;
        }
    }
}

