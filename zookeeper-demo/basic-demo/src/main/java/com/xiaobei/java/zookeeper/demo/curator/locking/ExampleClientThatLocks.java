package com.xiaobei.java.zookeeper.demo.curator.locking;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.util.concurrent.TimeUnit;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/27 13:39
 */
public class ExampleClientThatLocks {

    private final InterProcessMutex lock;

    private final FakeLimitedResource resource;

    private final String clientName;

    public ExampleClientThatLocks(CuratorFramework client, String lockPath,
                                  FakeLimitedResource resource, String clientName) {
        this.resource = resource;
        this.clientName = clientName;
        this.lock = new InterProcessMutex(client, lockPath);
    }

    public void doWork(long time, TimeUnit timeUnit) throws Exception {
        if(!lock.acquire(time, timeUnit)) {
            throw new Exception(clientName + " could not acquire the lock");
        }
        try {
            System.out.println(clientName + " has the lock");
            resource.use();
        } finally {
            System.out.println(clientName + " releasing the lock");
            // 应该始终在 finally 块中关闭资源
            lock.release();
        }
    }
}
