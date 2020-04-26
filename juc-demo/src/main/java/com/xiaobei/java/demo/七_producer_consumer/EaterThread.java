package com.xiaobei.java.demo.七_producer_consumer;

import java.util.Random;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/4/26 7:13
 */
public class EaterThread extends Thread {

    private final Random random;

    private final Table table;

    public EaterThread(String name, Table table, long seed) {
        super(name);
        this.random = new Random(seed);
        this.table = table;
    }

    @Override
    public void run() {
        try {
            while(true) {
                table.take();
                Thread.sleep(random.nextInt(1000));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
