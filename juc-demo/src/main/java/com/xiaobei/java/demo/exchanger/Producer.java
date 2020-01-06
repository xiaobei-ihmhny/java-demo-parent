package com.xiaobei.java.demo.exchanger;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-01-06 22:24:24
 */
public class Producer implements Runnable {

    private final Exchanger<Message> exchanger;

    public Producer(Exchanger<Message> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        Message message = new Message(null);
        for (int i = 0; i < 3; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);

                message.setV(String.valueOf(i));
                System.out.println(Thread.currentThread().getName() + "：生产了数据["+ i +"]");
                message = exchanger.exchange(message);
                System.out.println(Thread.currentThread().getName() + "：交换得到的数据["+ message.getV() +"]");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
