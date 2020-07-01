package com.xiaobei.java.demo.thread;

import java.util.concurrent.Callable;

/**
 * Java多线程创建方式三：实现Callable接口
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-07-01 11:39:39
 */
public class CallableDemo implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.out.println(Thread.currentThread().getName() + "正在行军~~~");
        System.out.println(Thread.currentThread().getName() + "遭遇敌军~~~");
        System.out.println(Thread.currentThread().getName() + "奋勇杀敌！！！！");
        return "战斗胜利，俘虏敌军50000人";
    }

    public static void usage1() {

    }

    public static void usage2() {

    }

    public static void main(String[] args) {

    }
}