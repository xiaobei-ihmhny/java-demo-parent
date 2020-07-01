package com.xiaobei.java.demo.thread;

/**
 * Java多线程创建方式一：继承Thread类
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-07-01 11:12:12
 */
public class ThreadDemo extends Thread {

    @Override
    public void run() {
        System.out.println("Java multithreading creation method one: " +
                "inherit the Thread class");
    }

    public static void main(String[] args) {
        ThreadDemo demo = new ThreadDemo();
        demo.start();
    }
}