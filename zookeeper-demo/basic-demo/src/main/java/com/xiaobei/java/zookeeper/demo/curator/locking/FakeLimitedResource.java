package com.xiaobei.java.zookeeper.demo.curator.locking;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * Simulates some external resource that can only be access by one process at a time
 * 模拟某些一次只能由一个线程访问的外部资源
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/27 13:41
 */
public class FakeLimitedResource {

    private final AtomicBoolean inUser = new AtomicBoolean(false);

    public void use() throws InterruptedException {
        // in a real application this would be accessing/manipulating a shared resource
        // 在一个真实应用中，这将是访问或操纵共享资源
        if(!inUser.compareAndSet(false, true)) {
            throw new IllegalStateException("Needs to be used by one client at a time");
        }
        try {
            Thread.sleep((long) (3 * Math.random()));
        } finally {
            inUser.set(false);
        }
    }
}
