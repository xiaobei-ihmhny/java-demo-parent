package com.xiaobei.java.demo.countdownlatch;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-12-18 09:33:33
 */
@SuppressWarnings("all")
public class CountDownLatchDemo {

    /**
     * 通过 {@link CountDownLatch} 可以在一定程度上控制多线程的执行顺序
     */
    @Test
    public void demo1() {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "-执行中");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "-执行完毕");
            countDownLatch.countDown();
        }, "t1").start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "-执行中");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "-执行完毕");
            countDownLatch.countDown();
        }, "t2").start();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "-执行中");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "-执行完毕");
            countDownLatch.countDown();
        }, "t3").start();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("所有线程执行完毕");
    }

}