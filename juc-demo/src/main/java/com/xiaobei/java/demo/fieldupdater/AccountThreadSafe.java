package com.xiaobei.java.demo.fieldupdater;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2019-12-29 10:33:33
 */
public class AccountThreadSafe implements IAccount {
    private volatile int money;
    private AtomicIntegerFieldUpdater<AccountThreadSafe> updater
            = AtomicIntegerFieldUpdater.newUpdater(AccountThreadSafe.class, "money");

    public AccountThreadSafe(int money) {
        this.money = money;
    }

    @Override
    public void increMoney() {
        updater.incrementAndGet(this);
    }

    public int getMoney() {
        return money;
    }

    @Override
    public String toString() {
        return "Account{" +
                "money=" + money +
                '}';
    }
}
