package com.xiaobei.java.demo.exchanger;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-01-06 22:30:30
 */
public class Consumer implements Runnable {

    private final Exchanger<Message> exchanger;

    public Consumer(Exchanger<Message> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        Message message = new Message(null);
        while(true) {
            try {
                TimeUnit.SECONDS.sleep(1);
                message = exchanger.exchange(message);
                System.out.println(Thread.currentThread().getName() + "：消费了数据["+ message.getV() +"]");
                message.setV(null);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
