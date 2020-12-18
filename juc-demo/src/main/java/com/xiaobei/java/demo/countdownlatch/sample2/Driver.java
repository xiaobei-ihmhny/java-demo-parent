package com.xiaobei.java.demo.countdownlatch.sample2;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * <h4>同一份工作分成n份，由n个人来做
 * <ul>
 *     <li>所有工人开始工作前需要先做一些准备工作
 *     <li>所有工人工作完成之后，需要再做一些收尾工作
 * </ul>
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-12-18 10:27:27
 */
public class Driver {

    private static final ThreadFactory NAMED_THREAD_FACTORY =
            new ThreadFactoryBuilder()
                    .setNameFormat("CountDownLatch-pool-%d")
                    .build();

    private static final ExecutorService EXECUTOR_SERVICE = new ThreadPoolExecutor(
            10,
            50,
            60,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1000),
            NAMED_THREAD_FACTORY,
            new ThreadPoolExecutor.AbortPolicy()
    );

    /**
     * 进行工作开始前的准备工作...
     * 当前线程 CountDownLatch-pool-0 开始完成工作的第 0 部分...
     * 当前线程 CountDownLatch-pool-9 开始完成工作的第 9 部分...
     * 当前线程 CountDownLatch-pool-8 开始完成工作的第 8 部分...
     * 当前线程 CountDownLatch-pool-7 开始完成工作的第 7 部分...
     * 当前线程 CountDownLatch-pool-6 开始完成工作的第 6 部分...
     * 当前线程 CountDownLatch-pool-5 开始完成工作的第 5 部分...
     * 当前线程 CountDownLatch-pool-3 开始完成工作的第 3 部分...
     * 当前线程 CountDownLatch-pool-4 开始完成工作的第 4 部分...
     * 当前线程 CountDownLatch-pool-2 开始完成工作的第 2 部分...
     * 当前线程 CountDownLatch-pool-1 开始完成工作的第 1 部分...
     * 进行工作结束前的收尾工作...
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        int workerCount = 10;
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(workerCount);
        for (int i = 0; i < workerCount; i++) {
            EXECUTOR_SERVICE.submit(new Worker(startSignal, doneSignal, i));
        }
        doSomethingElse01();
        startSignal.countDown();
        // wait for all to finish work
        doneSignal.await();
        // do some finishing work
        doSomethingElse02();
        // close current thread pool
        EXECUTOR_SERVICE.shutdown();
    }

    private static void doSomethingElse01() {
        System.out.println("进行工作开始前的准备工作...");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void doSomethingElse02() {
        System.out.println("进行工作结束前的收尾工作...");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}