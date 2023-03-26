package com.five.spring_demo.common;

/**
 * 基于ThreadLocal封装工具类，用于保存和获取Employee ID
 */
public class BaseContext {
    static private final ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    static public Long getCurrentId() {
        return threadLocal.get();
    }

    static public void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    static public void removeCurrentId() {
        threadLocal.remove();
    }
}
