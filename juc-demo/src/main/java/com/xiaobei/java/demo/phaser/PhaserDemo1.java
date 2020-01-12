package com.xiaobei.java.demo.phaser;

import java.io.IOException;
import java.util.concurrent.Phaser;

/**
 * 模拟 {@link java.util.concurrent.CyclicBarrier}
 * 和 {@link java.util.concurrent.CountDownLatch}
 * 实现所有线程到达指定点后再同时执行的需求
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-01-11 22:51:51
 */
public class PhaserDemo1 {

    public static void main(String[] args) throws IOException {
        Phaser phaser = new Phaser();
        for (int i = 0; i < 10; i++) {
            // 注册多个参与者
            phaser.register();
            new Thread(new Task(phaser), "Thread-" + i).start();
        }
    }
}