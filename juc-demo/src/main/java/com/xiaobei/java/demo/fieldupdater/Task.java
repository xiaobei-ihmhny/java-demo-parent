package com.xiaobei.java.demo.fieldupdater;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2019-12-29 10:35:35
 */
public class Task implements Runnable {

    private IAccount account;

    public Task(IAccount account) {
        this.account = account;
    }

    @Override
    public void run() {
        // 增加账户金额
        account.increMoney();
    }


}
