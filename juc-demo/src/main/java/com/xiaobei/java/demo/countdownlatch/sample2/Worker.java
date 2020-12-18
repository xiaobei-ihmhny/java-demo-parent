package com.xiaobei.java.demo.countdownlatch.sample2;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-12-18 10:22:22
 */
public class Worker implements Runnable {

    private final CountDownLatch startSignal;

    private final CountDownLatch doneSignal;

    /**
     * 当前线程（工人）负责执行哪一部分
     */
    private final int part;

    public Worker(CountDownLatch startSignal, CountDownLatch doneSignal, int part) {
        this.startSignal = startSignal;
        this.doneSignal = doneSignal;
        this.part = part;
    }

    @Override
    public void run() {
        try {
            startSignal.await();
            doWork(part);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            doneSignal.countDown();
        }

    }

    private void doWork(int part) throws InterruptedException {
        System.out.printf("当前线程 %s 开始完成工作的第 %d 部分...\n", Thread.currentThread().getName(), part);
        TimeUnit.SECONDS.sleep(2);
    }
}