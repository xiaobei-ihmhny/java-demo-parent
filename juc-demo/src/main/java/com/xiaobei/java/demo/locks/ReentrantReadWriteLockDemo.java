package com.xiaobei.java.demo.locks;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/16 23:07
 */
public class ReentrantReadWriteLockDemo {

    private static Map<String, Object> cacheMap = new HashMap<>();

    private static ReentrantReadWriteLock rrw = new ReentrantReadWriteLock();

    static Lock readLock = rrw.readLock();

    static Lock writeLock = rrw.writeLock();

    public static Object get(String key) {
        System.out.println("开始获取缓存数据");
        readLock.lock();
        try {
            return cacheMap.get(key);
        } finally {
            readLock.unlock();
        }
    }

    public static Object set(String key, Object value) {
        writeLock.lock();
        try {
            return cacheMap.put(key, value);
        } finally {
            writeLock.unlock();
        }
    }
}
