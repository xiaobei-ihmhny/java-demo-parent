package com.xiaobei.java.demo.phaser;

import java.util.concurrent.Phaser;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-01-12 11:16:16
 */
public class Task2 implements Runnable {

    private final Phaser phaser;

    public Task2(Phaser phaser) {
        this.phaser = phaser;
    }

    @Override
    public void run() {
        // 只要Phaser没有终止，各个线程的任务就会一直执行
        while(!phaser.isTerminated()) {
            // 等待其他参与者线程到达
            int i = phaser.arriveAndAwaitAdvance();
            // do something
            System.out.println(Thread.currentThread().getName() + ": 执行完任务 " + i);
        }
    }
}
