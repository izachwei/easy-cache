package com.easycache.cacheapi.core.service;


import com.easycache.cacheapi.core.CacheManager;
import com.easycache.cacheapi.core.annotation.CacheSourceType;
import lombok.extern.slf4j.Slf4j;
import org.apache.groovy.util.concurrent.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.concurrent.ThreadSafe;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author : zach
 */
@ThreadSafe
@Slf4j
public class MemoryCacheManager implements CacheManager, InitializingBean {

    private ConcurrentLinkedHashMap<String, CacheData> cache = new ConcurrentLinkedHashMap.Builder<String, CacheData>()
            .concurrencyLevel(5)
            .maximumWeightedCapacity(100)
            .build();

    @Override
    public void put(String key, Object value) {
        CacheData cacheData = new CacheData(value, -1);
        cache.put(key, cacheData);
    }

    @Override
    public void put(String key, Object value, long expire, TimeUnit timeUnit) {
        long expireMillis = timeUnit.toMillis(expire);
        if (expire > 0) {
            expireMillis = getCurrentSystemMillis() + expireMillis;
        }
        CacheData cacheData = new CacheData(value, expireMillis);
        cache.put(key, cacheData);
    }

    @Override
    public void remove(String key) {
        cache.remove(key);
    }

    @Override
    public <T> T get(String key, Class<T> tClass) {
        CacheData cacheData = cache.get(key);
        T value = null;
        if (Objects.nonNull(cacheData)) {
            if (keyExpired(cacheData)) {
                remove(key);
            } else {
                value = Objects.nonNull(cacheData.value) ? (T) cacheData.value : null;
            }
        }
        return value;
    }

    @Override
    public CacheSourceType getType() {
        return CacheSourceType.MEMORY;
    }

    @Override
    public void afterPropertiesSet() {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(run -> {
            Thread thread = new Thread(run, "cache");
            thread.setDaemon(true);
            return thread;
        });

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            cache.forEach((key, value) -> {
                if (keyExpired(value)) {
                    remove(key);
                    log.info("key:{} be removed.", key);
                }
            });
        }, 0, 5, TimeUnit.MINUTES);
    }

    private boolean keyExpired(CacheData value) {
        return value.expire > 0 && getCurrentSystemMillis() >= value.expire;
    }

    private long getCurrentSystemMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 缓存数据
     */
    private static class CacheData {
        /**
         * 具体值
         */
        private Object value;

        /**
         * 过期时间
         */
        private long expire;

        public CacheData(Object value, long expire) {
            this.value = value;
            this.expire = expire;
        }
    }
}
