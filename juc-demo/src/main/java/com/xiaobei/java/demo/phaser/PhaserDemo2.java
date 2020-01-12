package com.xiaobei.java.demo.phaser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Phaser;

/**
 * 通过 {@link java.util.concurrent.Phaser} 实现一个开关的功能
 * 模拟用 {@link java.util.concurrent.CountDownLatch} 实现开关
 *
 * <p>我们希望一些外部条件得到满足之后，然后打开开关，线程才能继续执行
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-01-12 10:55:55
 */
public class PhaserDemo2 {

    public static void main(String[] args) throws IOException {
        // 注册主线程，当外部条件满足时，由主线程打开开关
        Phaser phaser = new Phaser(1);
        for (int i = 0; i < 10; i++) {
            phaser.register();
            new Thread(new Task(phaser), "Thread-" + i).start();
        }

        // 外部条件：等待用户输入
        System.out.println("Press ENTER to continue.");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        bufferedReader.readLine();

        // 打开开关
        phaser.arriveAndDeregister();
        System.out.println("主线程打开了开关");
    }

}