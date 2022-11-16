package com.easycache.cacheapi.common;

import com.easycache.cacheapi.util.AbstractConstant;
import com.easycache.cacheapi.util.ConstantPool;



/**
 * @author : zach
 */
public final class CacheType extends AbstractConstant<CacheType> {

    /**
     * 枚举对象池
     */
    private static final ConstantPool<CacheType> CACHE_TYPE_POOL = new ConstantPool<CacheType>() {
        @Override
        protected CacheType newInstance(int id, String name) {
            return new CacheType(id, name);
        }
    };

    /**
     * 内存缓存类型
     */
    public static final CacheType MEMORY = CACHE_TYPE_POOL.valueOf("MEMORY");

    /**
     * 分布式缓存
     */
    public static final CacheType DISTRIBUTED = CACHE_TYPE_POOL.valueOf("DISTRIBUTED");

    /**
     * 多级缓存
     */
    public static final CacheType MULTISTEP = CACHE_TYPE_POOL.valueOf("MULTISTEP");


    private CacheType(int id, String name) {
        super(id, name);

        CacheType.MEMORY.name();
    }
}
