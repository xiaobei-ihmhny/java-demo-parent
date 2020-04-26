package com.xiaobei.java.demo.二.f.Double_Checked_Locking_Pattern;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/4/25 9:00
 */
public class Main extends Thread {

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Main().start();
        }
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "-" + MySystem.getInstance().getDate());
    }
}
