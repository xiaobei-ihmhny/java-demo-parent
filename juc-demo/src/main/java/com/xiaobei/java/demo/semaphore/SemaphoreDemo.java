package com.xiaobei.java.demo.semaphore;

import com.xiaobei.java.demo.semaphore.sample1.Car;
import org.junit.Test;

import java.util.concurrent.*;

/**
 * {@link Semaphore} 比较常见的就来用来做限流操作。
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-12-18 14:45:45
 */
public class SemaphoreDemo {

    private static final ExecutorService EXECUTOR_SERVICE =
            new ThreadPoolExecutor(
                    10,
                    50,
                    60,
                    TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>(1000),
                    new ThreadPoolExecutor.AbortPolicy()
            );

    /**
     * 限流场景
     */
    @Test
    public void sample1() {
        int carNums = 10;
        Semaphore semaphore = new Semaphore(5);
        CountDownLatch doneSignal = new CountDownLatch(carNums);
        for (int i = 0; i < carNums; i++) {
            EXECUTOR_SERVICE.submit(new Car(i, semaphore, doneSignal));
        }
        try {
            doneSignal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        EXECUTOR_SERVICE.shutdown();
    }

}