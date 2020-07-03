package com.xiaobei.java.demo.locks;


import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 利用{@link ReentrantReadWriteLock}控制对{@link TreeMap}的访问（利用写锁控制修改操作的访问），
 * 将{@link TreeMap}包装成一个线程安全的集合，并且利用了读写锁的特性来提高并发访问
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/7/2 22:08
 */
public class RWTreeMap<K, V> {

    private final Map<K, V> m = new TreeMap<>();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock r = lock.readLock();
    private final Lock w = lock.writeLock();

    public V get(K key) {
        r.lock();
        try {
            return m.get(key);
        } finally {
            r.unlock();
        }
    }

    public V put(K key, V value) {
        w.lock();
        try {
            return m.put(key, value);
        } finally {
            w.unlock();
        }
    }

    public void clear() {
        w.lock();
        try {
            m.clear();
        } finally {
            w.unlock();
        }
    }

    public Set<K> allKeys() {
        r.lock();
        try {
            return new HashSet<>(m.keySet());
        } finally {
            r.unlock();
        }
    }
}
