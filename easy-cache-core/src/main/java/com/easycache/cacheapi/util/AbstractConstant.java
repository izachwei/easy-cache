package com.easycache.cacheapi.util;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 可扩展枚举对象
 *
 * @author : zach
 */

public abstract class AbstractConstant<T extends AbstractConstant<T>> implements Constant<T> {

    /**
     * 全局唯一ID生成器 ， 递增
     */
    private static final AtomicLong UNIQUE_ID_GENERATOR = new AtomicLong(1);

    private int id;

    private String name;

    private long uniquifier;

    public AbstractConstant(int id, String name) {
        this.id = id;
        this.name = name;
        uniquifier = UNIQUE_ID_GENERATOR.getAndIncrement();
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public int compareTo(T o) {
        if (this == o) {
            return 0;
        }

        @SuppressWarnings("UnnecessaryLocalVariable")
        AbstractConstant<T> other = o;
        int returnCode;

        returnCode = hashCode() - other.hashCode();
        if (returnCode != 0) {
            return returnCode;
        }

        if (uniquifier < other.uniquifier) {
            return -1;
        }
        if (uniquifier > other.uniquifier) {
            return 1;
        }

        throw new Error("failed to compare two different constants");
    }
}
