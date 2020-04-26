package com.xiaobei.java.demo.一.a.创建;

/**
 * 通过继承 {@link java.lang.Thread} 类实现多线程
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/4/25 6:37
 */
public class ThreadMain {

    public static void main(String[] args) {
        ThreadDemo demo = new ThreadDemo();
        // 启动子线程
        demo.start();
        // 主线程继续向下执行
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread() + "-" + i);
        }
    }
}


class ThreadDemo extends Thread {

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread() + "-" + i);
        }
    }
}