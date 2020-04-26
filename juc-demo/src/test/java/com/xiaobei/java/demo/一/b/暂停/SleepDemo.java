package com.xiaobei.java.demo.一.b.暂停;

/**
 * 线程暂时演示
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/4/25 7:19
 */
public class SleepDemo {

    public static void main(String[] args) {
        new Thread(()-> {
            System.out.println(Thread.currentThread());
            try {
                Thread.sleep(2000);//当前线程暂停2s
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("===============");
            System.out.println(Thread.currentThread());
        }).start();
        System.out.println(Thread.currentThread());

    }
}
