package com.xiaobei.java.demo.phaser;

import java.util.concurrent.Phaser;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-01-12 11:19:19
 */
public class PhaserDemo3 {

    public static void main(String[] args) {

        // 指定任务最多执行的次数
        int repeats = 3;

        Phaser phaser = new Phaser() {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                System.out.println("----------PHASE[" + phase + "],Parties[" + registeredParties + "]----------");
                return phase + 1 >= repeats || registeredParties == 0;
            }
        };

        for (int i = 0; i < 10; i++) {
            // 注册各个参与者线程
            phaser.register();
            new Thread(new Task2(phaser), "Thread-" + i).start();
        }
    }
}
