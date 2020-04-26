package com.xiaobei.java.demo.二.f.Double_Checked_Locking_Pattern;

import java.util.Date;

/**
 * 采用延迟加载+双重锁的形式保证线程安全以及性能
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/4/25 9:01
 */
public class MySystem {

    private static MySystem instance = null;

    private Date date = new Date();

    private MySystem() {

    }

    public Date getDate() {
        return date;
    }

    public static MySystem getInstance() {
        if(instance == null) {
            synchronized(MySystem.class) {
                if(instance == null) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    instance = new MySystem();
                }
            }
        }
        return instance;
    }
}
