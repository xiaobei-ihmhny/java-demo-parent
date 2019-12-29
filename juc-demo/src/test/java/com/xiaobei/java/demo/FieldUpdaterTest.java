package com.xiaobei.java.demo;


import com.xiaobei.java.demo.fieldupdater.Account;
import com.xiaobei.java.demo.fieldupdater.AccountThreadSafe;
import com.xiaobei.java.demo.fieldupdater.IAccount;
import com.xiaobei.java.demo.fieldupdater.Task;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2019-12-29 10:32:32
 */
public class FieldUpdaterTest {

    /**
     * 模拟运行100次，不会出现小于1000的情况
     * @throws InterruptedException
     */
    @Test
    public void testThreadSafe() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            // 初始金额为0
            calculation(new AccountThreadSafe(0));
        }
    }

    /**
     * 模拟运行100次，中间会出现小于1000的情况
     * @throws InterruptedException
     */
    @Test
    public void testThreadUnSafe() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            // 初始金额为0
            calculation(new Account(0));
        }
    }

    private void calculation(IAccount account) throws InterruptedException {
        int num = 1000;
        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Thread t = new Thread(new Task(account));
            list.add(t);
            t.start();
        }
        // 等待所有线程执行完成
        for (Thread thread : list) {
            thread.join();
        }

        System.out.println(account.toString());

    }
}
