package com.xiaobei.java.demo.exchanger;

import java.util.concurrent.Exchanger;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-01-06 22:32:32
 */
public class Main {

    /**
     * 运行结果如下：
     *
     * 生产者-t2：生产了数据[0]
     * 生产者-t2：交换得到的数据[null]
     * 消费者-t1：消费了数据[0]
     * 生产者-t2：生产了数据[1]
     * 生产者-t2：交换得到的数据[null]
     * 消费者-t1：消费了数据[1]
     * 生产者-t2：生产了数据[2]
     * 生产者-t2：交换得到的数据[null]
     * 消费者-t1：消费了数据[2]
     * @param args
     */
    public static void main(String[] args) {
        Exchanger<Message> exchanger = new Exchanger<>();
        Thread t1 = new Thread(new Consumer(exchanger), "消费者-t1");
        Thread t2 = new Thread(new Producer(exchanger), "生产者-t2");
        t1.start();
        t2.start();
    }
}
