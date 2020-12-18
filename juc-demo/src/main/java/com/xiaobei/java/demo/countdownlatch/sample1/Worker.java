package com.xiaobei.java.demo.countdownlatch.sample1;

import java.util.concurrent.CountDownLatch;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-12-18 09:52:52
 */
public class Worker implements Runnable {

    private final CountDownLatch startSignal;

    private final CountDownLatch doneSignal;

    public Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
        this.startSignal = startSignal;
        this.doneSignal = doneSignal;
    }

    @Override
    public void run() {
        try {
            startSignal.await();
            doWork();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            doneSignal.countDown();
        }
    }

    private void doWork() {
        System.out.printf("线程 %s 开始执行业务逻辑...\n", Thread.currentThread().getName());
    }
}