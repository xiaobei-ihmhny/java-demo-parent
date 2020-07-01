package com.xiaobei.java.demo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/6/30 21:45
 */
public class ReentrantLockVSSynchronizedDemo {

    private final Object object = new Object();

    //***************** synchronized的使用方式 *****************//
    public void synchronizedTest() {
        // 1. 用于代码块
        synchronized (this) { }
        // 2. 用于对象
        synchronized (object) {}
        // 4. 可重入
        for (int i = 0; i < 10; i++) {
            synchronized (this) {}
        }
    }
    // 3. 用于方法
    public synchronized void test () {}


    //***************** ReentrantLock的使用方式 *****************//
    public void ReentrantLockTest() throws InterruptedException {
        // 1. 初始化选择公平锁、非公平锁
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        try {
            try {
                // 3. 支持多种加锁方式，比较灵活；具有可重入特性
                if(lock.tryLock(100, TimeUnit.MILLISECONDS)) {

                }
            } finally {
                // 4. 手动释放锁
                lock.unlock();
            }
        } finally {
            lock.unlock();
        }
    }

}
