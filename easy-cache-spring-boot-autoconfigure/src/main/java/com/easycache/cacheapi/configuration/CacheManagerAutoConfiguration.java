package com.easycache.cacheapi.configuration;

import com.easycache.cacheapi.core.CacheManager;
import com.easycache.cacheapi.core.aspect.CacheAspect;
import com.easycache.cacheapi.core.service.MemoryCacheManager;
import com.easycache.cacheapi.enable.MarkerConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : zach
 */
@Configuration
@ConditionalOnBean(MarkerConfiguration.Mark.class)
public class CacheManagerAutoConfiguration {
    @Bean
    public CacheAspect injectCacheAspect() {
        return new CacheAspect();
    }

    @Bean
    public CacheManager memoryCacheManager() {
        return new MemoryCacheManager();
    }
}
