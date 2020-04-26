package com.xiaobei.java.demo.一.c.互斥;

/**
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/4/25 7:22
 *
 */
public class SynchronizedDemo {

    public synchronized void deposit1(int m) {
        System.out.println("This is synchronized method.");
    }

    public static synchronized void deposit2(int m) {
        System.out.println("This is a synchronized static method.");
    }

    public void deposit3(int m) {
        synchronized (this) {
            System.out.println("This is synchronized statement with this lock");
        }
        synchronized (SynchronizedDemo.class) {
            System.out.println("This is synchronized statement with class lock");
        }
    }
}
