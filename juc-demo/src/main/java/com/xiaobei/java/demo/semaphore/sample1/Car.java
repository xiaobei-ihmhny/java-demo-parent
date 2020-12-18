package com.xiaobei.java.demo.semaphore.sample1;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-12-18 14:51:51
 */
public class Car extends Thread {

    private final int num;

    private final Semaphore semaphore;

    private final CountDownLatch doneSignal;

    public Car(int num, Semaphore semaphore, CountDownLatch doneSignal) {
        this.num = num;
        this.semaphore = semaphore;
        this.doneSignal = doneSignal;
    }

    @Override
    public void run() {
        try {
            semaphore.acquire();// 获取一个许可
            System.out.printf("第 %d 辆车占用一个停车位\n", num);
            TimeUnit.SECONDS.sleep(3);
            System.out.printf("第 %d 辆车走喽\n", num);
            semaphore.release();
            doneSignal.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}