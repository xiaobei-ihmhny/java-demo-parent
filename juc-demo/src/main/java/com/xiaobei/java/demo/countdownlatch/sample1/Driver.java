package com.xiaobei.java.demo.countdownlatch.sample1;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 *
 * <h4>模拟的场景是：10份一样的工作由10个工人同时去做，以便提高工作效率，有两个要求：
 * <ul>
 * <li>1. 所有工人开始工作前需要先做一些准备工作
 * <li>2. 所有工人工作完成之后，需要再做一些收尾工作
 * </ul>
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-12-18 09:52:52
 */
@SuppressWarnings("all")
public class Driver {

    private static ThreadFactory namedThreadFactory =
            new ThreadFactoryBuilder()
                    .setNameFormat("countDownLatch-%d")
                    .build();

    private static ExecutorService executorService = new ThreadPoolExecutor(
            10,
            50,
            60,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1000),
            namedThreadFactory,
            new ThreadPoolExecutor.AbortPolicy()//对于阻塞队列已满情况下的多余的线程直接
    );

    /**
     * driver 执行相关的准备工作...
     * 线程 countDownLatch-0 开始执行业务逻辑...
     * 线程 countDownLatch-9 开始执行业务逻辑...
     * 线程 countDownLatch-8 开始执行业务逻辑...
     * 线程 countDownLatch-7 开始执行业务逻辑...
     * 线程 countDownLatch-6 开始执行业务逻辑...
     * 线程 countDownLatch-5 开始执行业务逻辑...
     * 线程 countDownLatch-4 开始执行业务逻辑...
     * 线程 countDownLatch-3 开始执行业务逻辑...
     * 线程 countDownLatch-2 开始执行业务逻辑...
     * 线程 countDownLatch-1 开始执行业务逻辑...
     * 所有线程已执行完毕，进行收尾工作...
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        int workCount = 10;
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(workCount);
        for (int i = 0; i < workCount; i++) {
            executorService.submit(new Worker(startSignal, doneSignal));
        }
        // don't let run yet
        doSomethingElse01();
        // let all threads proceed
        startSignal.countDown();
        // wait for all worker to finish
        doneSignal.await();
        doSomethingElse02();
        // close the thread pool finally
        executorService.shutdown();
    }

    private static void doSomethingElse01() {
        System.out.println("driver 执行相关的准备工作...");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void doSomethingElse02() {
        System.out.println("所有线程已执行完毕，进行收尾工作...");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}