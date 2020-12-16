package com.xiaobei.java.demo.locks;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试累加计数时的线程安全性问题
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/16 22:20
 */
public class ReentrantLockDemo {

    private static int count = 0;

    private static int safeCount = 0;

    /**
     * 可重入锁保证线程安全
     */
    private final static Lock lock = new ReentrantLock();

    private final static int threadNums = 1000;

    private final static int latchNums = threadNums * 2;

    /**
     * 保证主线程在所有子线程计算结束后再结束
     */
    private final static CountDownLatch LATCH = new CountDownLatch(latchNums);

    public static void incrNotThreadSafe() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        count++;
        LATCH.countDown();
    }

    public static void incrThreadSafe() {
        lock.lock();
        try {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            safeCount++;
            LATCH.countDown();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 非线程安全的计数结果为：999
     * 线程安全的计数结果为：1000
     * @throws InterruptedException
     */
    @Test
    public void threadSafeTest() throws InterruptedException {
        for (int i = 0; i < threadNums; i++) {
            // 启动线程并计算不安全的累加计数
            new Thread(ReentrantLockDemo::incrNotThreadSafe).start();
            // 启动线程并计算安全的累加计数
            new Thread(ReentrantLockDemo::incrThreadSafe).start();
        }
        LATCH.await();
        System.out.printf("非线程安全的计数结果为：%d\n", count);
        System.out.printf("线程安全的计数结果为：%d\n", safeCount);
    }


}
