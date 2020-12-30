package com.xiaobei.java.zookeeper.demo.zkclient;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/30 21:45
 */
@DisplayName("ZkClient使用示例")
public class ZkClientTest {

    private static ZooKeeper zooKeeper;

    private static String connectString = "192.168.163.101:2181,192.168.163.102:2181,192.168.163.103:2181";

    private static String path = "/watcher";

    private Lock lock = new ReentrantLock();

    private Condition stop = lock.newCondition();

    @BeforeAll
    public static void init() {
        try {
            zooKeeper = new ZooKeeper(connectString, 40000, event -> {
                System.out.printf("事件类型为：%s\n", event.getType());
                // 循环监听
                try {
                    zooKeeper.exists(path, true);
                } catch (KeeperException | InterruptedException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    void watchDemo() throws KeeperException, InterruptedException {
        if(zooKeeper.exists(path, false) == null) {
            zooKeeper.create(path,
                    "huihui".getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.PERSISTENT);
            Thread.sleep(1000);
        }
        // true 表示
        zooKeeper.exists(path, true);
        lock.lock();
        try {
            stop.await();
        } finally {
            lock.unlock();
            System.out.println("程序退出...");
        }
    }
}
