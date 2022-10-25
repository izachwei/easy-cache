package com.easycache.cacheapi.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @author zw35
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cached {

    /**
     * @return 缓存 Key
     */
    String key() default "";

    /**
     * 过期时间，-1 则永不过期
     *
     * @return 过期时间
     */
    long expire();

    /**
     * 过期时间单位，默认 秒
     *
     * @return 时间单位
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * @return 缓存数据源类型
     */
    CacheSourceType type();
}
