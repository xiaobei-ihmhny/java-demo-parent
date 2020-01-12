package com.xiaobei.java.demo.phaser;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-01-12 11:32:32
 */
public class PhaserDemo4 {

    // 每个Phaser对象对应的工作线程（任务）数
    private static final int TASK_PER_PHASER = 4;

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

        Task4[] tasks = new Task4[10];
        // 根据任务数为每个任务分配Phaser
        build(tasks, 0, tasks.length, phaser);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < tasks.length; i++) {
            Thread thread = new Thread(tasks[i]);
            thread.start();
        }
    }

    private static void build(Task4[] tasks, int lo, int hi, Phaser phaser) {
        if(hi - lo > TASK_PER_PHASER) {
            for(int i = lo; i < hi; i += TASK_PER_PHASER) {
                int j = Math.min(hi, i + TASK_PER_PHASER);
                build(tasks, i, j, new Phaser(phaser));
            }
        } else {
            for(int i = lo; i < hi; ++i) {
                tasks[i] = new Task4(phaser);
            }
        }
    }
}
