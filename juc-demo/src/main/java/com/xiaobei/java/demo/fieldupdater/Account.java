package com.xiaobei.java.demo.fieldupdater;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2019-12-29 10:33:33
 */
public class Account implements IAccount {
    private volatile int money;

    public Account(int money) {
        this.money = money;
    }

    @Override
    public void increMoney() {
        money++;
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
