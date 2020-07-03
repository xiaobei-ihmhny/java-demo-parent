package com.xiaobei.java.demo.thread;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Java多线程创建方式三：实现Callable接口
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-07-01 11:39:39
 */
public class CallableDemo implements Callable<String> {

    @Override
    public String call() throws Exception {
        return threadBody();
    }

    private String threadBody() {
        System.out.println(Thread.currentThread().getName() + "正在行军~~~");
        System.out.println(Thread.currentThread().getName() + "遭遇敌军~~~");
        System.out.println(Thread.currentThread().getName() + "奋勇杀敌！！！！");
        return "战斗胜利，俘虏敌军50000人";
    }

    /**
     * 利用Callable接口的实现创建子线程类
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void usage1() throws ExecutionException, InterruptedException {
        FutureTask<String> ft = new FutureTask<>(new CallableDemo());
        new Thread(ft, "usage1").start();
        String result = ft.get();
        System.out.println(result);
    }

    /**
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void usage2() throws ExecutionException, InterruptedException {
        Callable<String> c1 = this::threadBody;
        FutureTask<String> ft = new FutureTask<>(c1);
        new Thread(ft, "usage2").start();
        System.out.println(ft.get());
    }

}