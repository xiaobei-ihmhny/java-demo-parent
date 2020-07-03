package com.xiaobei.java.demo.thread;


import java.util.concurrent.*;

/**
 * Java多线程创建方式四：通过线程池实现多线程
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/7/1 21:59
 */
public class ThreadPoolDemo {

    private ExecutorService pool = new ThreadPoolExecutor(
            10,
            20,
            60L,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1024),
            r -> new Thread("%d_thread"),
            new ThreadPoolExecutor.AbortPolicy());


}
