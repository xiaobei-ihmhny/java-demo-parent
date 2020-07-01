package com.xiaobei.java.demo.thread;

/**
 * Java多线程创建方式二：实现Runnable接口
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-07-01 11:14:14
 */
public class RunnableDemo implements Runnable {

    @Override
    public void run() {
        System.out.println("Java multithreading creation method two: " +
                "Implementation Runnable interface");
    }


    /**
     * 启动 {@link RunnableDemo}，需要首先实例化一个Thread，
     * 并转入自己的{@link RunnableDemo}实例：
     * @param args
     */
    public static void main(String[] args) {
        RunnableDemo demo = new RunnableDemo();
        /*
         *   事实上，当传入一个Runnable target参数给Thread后，
         *   Thread的run()方法就会调用target.run()
         *     public void run() {
         *         if (target != null) {
         *             target.run();
         *         }
         *     }
         */
        Thread thread = new Thread(demo);
        thread.start();
    }
}