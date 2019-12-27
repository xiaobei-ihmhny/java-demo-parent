package com.xiaobei.java.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 该示例并没有使用锁，而是使用自旋+CAS的无锁操作保证共享变量的线程安全。
 * 1000个线程，每个线程对金额加1，最终结果为2000，
 * 如果线程不安全，最终结果可能会小于2000。
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2019-12-26 08:49:49
 */
public class AtomicReferenceDemo {

    public static void main(String[] args) throws InterruptedException {
        // 以无锁的方式访问共享资源
        AtomicReference<Integer> ref = new AtomicReference<>(1000);

        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Thread t = new Thread(new Task(ref), "Thread-" + i);
            list.add(t);
            t.start();
        }

        for (Thread thread : list) {
            thread.join();
        }
        // 结果为：2000
        System.out.println(ref.get());

    }
}

class Task implements Runnable {

    private AtomicReference<Integer> ref;

    public Task(AtomicReference<Integer> ref) {
        this.ref = ref;
    }

    @Override
    public void run() {
        for(;;) {
            Integer oldVaule = ref.get();
            // cas操作
            if(ref.compareAndSet(oldVaule, oldVaule + 1)) {
                break;
            }
        }
    }
}
