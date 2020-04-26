package com.xiaobei.java.demo.七_producer_consumer;

import java.util.Random;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/4/26 6:46
 */
public class MakerThread extends Thread {

    private final Random random;

    private final Table table;

    private static int id = 0; // 蛋糕流水号（所有厨师共通）

    public MakerThread(String name, Table table, long seed) {
        super(name);
        this.random = new Random(seed);
        this.table = table;
    }

    @Override
    public void run() {
        try {
            while(true) {
                Thread.sleep(random.nextInt(1000));
                String cake = "[ Cake No." + nextId() + " by " + getName() + "]";
                table.put(cake);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static synchronized int nextId() {
        return id++;
    }
}
