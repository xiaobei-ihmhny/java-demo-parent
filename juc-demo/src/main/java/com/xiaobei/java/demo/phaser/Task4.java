package com.xiaobei.java.demo.phaser;

import java.util.concurrent.Phaser;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-01-12 11:32:32
 */
public class Task4 implements Runnable {

    private final Phaser phaser;

    public Task4(Phaser phaser) {
        this.phaser = phaser;
        this.phaser.register();
    }

    @Override
    public void run() {
        while(!phaser.isTerminated()) {
            int i = phaser.arriveAndAwaitAdvance();
            // do something
            System.out.println(Thread.currentThread().getName() + "：执行完任务");
        }
    }
}
