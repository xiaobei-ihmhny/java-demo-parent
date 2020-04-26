package com.xiaobei.java.demo.一.a.创建;

import java.util.concurrent.TimeUnit;

/**
 * 通过实现 {@link java.lang.Runnable} 接口来实现多线程
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/4/25 7:01
 */
public class RunnableMan {

    public static void main(String[] args) {
        Thread runnableDemo = new Thread(new RunnableDemo());
        runnableDemo.start();
        System.out.println(Thread.currentThread());

        // java8语法，使用lambda表达式
        runnableDemo = new Thread(() -> System.out.println(Thread.currentThread()));
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        runnableDemo.start();
        System.out.println(Thread.currentThread());

        try {
            Thread.sleep(1000);// 线程的暂停，当前线会暂停1s再执行
        } catch (InterruptedException e) {// 如果暂停过程中被interrupt，则会抛出InterruptedException异常
            e.printStackTrace();
        }


    }
}

class RunnableDemo implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread());
    }
}
