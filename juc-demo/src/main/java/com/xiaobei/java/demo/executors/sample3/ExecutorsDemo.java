package com.xiaobei.java.demo.executors.sample3;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/22 07:05
 */
public class ExecutorsDemo {

    /**
     * 会报错
     */
    @Test
    public void singleThreadExecutor() {
        ThreadPoolExecutor singleThreadExecutor = (ThreadPoolExecutor)Executors.newSingleThreadExecutor();
    }

    @Test
    public void fixedThreadPool() {
        ThreadPoolExecutor fixedThreadPool = (ThreadPoolExecutor)Executors.newFixedThreadPool(1);
        fixedThreadPool.setCorePoolSize(1);
        System.out.println(fixedThreadPool);
    }
}
