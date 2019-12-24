package com.xiaobei.java.demo;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 *     AtomicInteger是atomic框架中用的最多的原子类了。
 *     AtomicInteger是Integer类型的线程安全的原子类，
 *     可以在应用程序中以原子的方式更新int值。
 * </p>
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2019-12-23 22:30:30
 */
public class AtomicIntegerDemo {

    public static void main(String[] args) throws InterruptedException {
        AtomicInteger ai = new AtomicInteger();
        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(new Accumlator(ai));
            list.add(t);
            t.start();
        }

        for(Thread t : list) {
            t.join();
        }
        System.out.println(ai.get());

    }

    static class Accumlator implements Runnable {

        private AtomicInteger ai;

        public Accumlator(AtomicInteger ai) {
            this.ai = ai;
        }

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                ai.incrementAndGet();
            }
        }
    }
}
