package com.xiaobei.java.demo.phaser;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-01-12 11:00:00
 */
public class Task implements Runnable {

    private final Phaser phaser;

    public Task(Phaser phaser) {
        this.phaser = phaser;
    }

    @Override
    public void run() {
        // 等待其他参与者线程到达
        System.out.println("线程 " + Thread.currentThread().getName() + " 开始执行 run() 方法");
        int i = phaser.arriveAndAwaitAdvance();
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // do something
        System.out.println(Thread.currentThread().getName() + "：执行完任务，当前phase=" + i + "");
    }
}