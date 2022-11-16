package com.easycache.cacheapi.core.aspect;


import com.easycache.cacheapi.core.CacheManager;
import com.easycache.cacheapi.core.annotation.CacheSourceType;
import com.easycache.cacheapi.core.annotation.Cached;
import com.easycache.cacheapi.core.util.MD5Util;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author : zach
 */
@Aspect
public class CacheAspect {

    @Autowired
    private List<CacheManager> cacheManagers;

    @Around(value = "@annotation(cacheable)")
    public Object doAround(ProceedingJoinPoint jp, Cached cacheable) {
        CacheManager cacheManager = getCacheManagerByType(cacheable.type());
        String key = getCacheKey(jp, cacheable);
        return cacheManager.getOrAddCache(key, cacheable.expire(), cacheable.timeUnit(), Object.class, () -> {
            try {
                return jp.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return null;
        });
    }

    private String getCacheKey(ProceedingJoinPoint jp, Cached cacheable) {
        String cacheKey = cacheable.key();
        if (ObjectUtils.isEmpty(cacheKey)) {
            cacheKey = MD5Util.toMD5(jp.getSignature().toString());
        }
        return cacheKey;
    }


    private CacheManager getCacheManagerByType(CacheSourceType cacheSourceType) {
        return cacheManagers.stream()
                .filter(cacheManager -> cacheSourceType == cacheManager.getType())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("cacheType:" + cacheSourceType + " not support"));
    }
}
