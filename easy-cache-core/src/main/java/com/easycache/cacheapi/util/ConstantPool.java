package com.easycache.cacheapi.util;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : zach
 */
public abstract class ConstantPool<T extends Constant<T>> {

    private final AtomicInteger nextId = new AtomicInteger(1);
    private final ConcurrentHashMap<String, T> pool = new ConcurrentHashMap<>();

    public T valueOf(Class<?> clazz, String name) {
        if (Objects.isNull(clazz)) {
            throw new IllegalArgumentException("clazz null");
        }
        if (Objects.isNull(name)) {
            throw new IllegalArgumentException("name null");
        }
        return valueOf(clazz.getSimpleName() + "_" + name);
    }

    public T valueOf(String name) {
        checkNotNullAndNotEmpty(name);
        return getOrCreate(name);
    }

    public boolean exist(String name) {
        return pool.containsKey(name);
    }

    /**
     * 获取对象，对象不存在则创建
     *
     * @param name 对象名
     * @return 缓存对象
     */
    protected T getOrCreate(String name) {
        T o = pool.get(name);
        if (Objects.isNull(o)) {
            o = newInstance(nextId(), name);
            pool.put(name, o);
        }
        return o;
    }

    /**
     *
     */
    public final int nextId() {
        return nextId.incrementAndGet();
    }


    /**
     * 创建对象
     *
     * @param name 对象名
     * @return 对象
     */
    protected abstract T newInstance(int id, String name);

    private void checkNotNullAndNotEmpty(String name) {
        Objects.requireNonNull(name);
        if (name.isEmpty()) {
            throw new IllegalArgumentException("name is empty");
        }
    }
}
