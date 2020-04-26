package com.xiaobei.java.demo.六_balking;

/**
 * <a href="https://segmentfault.com/a/1190000015558615">Java多线程基础（六）——Balking模式</a>
 * GuardedObject参与者是一个拥有被警戒的方法(guardedMethod)的类。
 * 当线程执行guardedMethod时，只有满足警戒条件时，才会继续执行，否则会立即返回。
 * 警戒条件的成立与否，会随着GuardedObject参与者的状态而变化。
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/4/25 14:03
 */
public class Main {

    public static void main(String[] args) {
        Data data = new Data("data.txt", "(empty)");
        new ChangerThread("ChangerThread", data).start();
        new SaverThread("SaverThread", data).start();
    }
}
