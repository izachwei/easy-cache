package com.easycache.cacheapi.util;

/**
 * @author : zach
 */
public interface Constant<T extends Constant<T>> extends Comparable<T> {

    /**
     * 返回一个唯一数字
     *
     * @return 唯一数字
     */
    int id();

    /**
     * 返回枚举名称
     *
     * @return name
     */
    String name();


}
