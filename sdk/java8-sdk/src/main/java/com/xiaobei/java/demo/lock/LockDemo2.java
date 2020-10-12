package com.xiaobei.java.demo.lock;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-10-12 16:03:03
 */
public class LockDemo2 {

//    private Object object = new Object();

    public void method() {
        // 将 object 从由成员变量变为方法的局部变量
        Object object = new Object();
        synchronized (object) {
            System.out.println("hello world");
        }
    }
}