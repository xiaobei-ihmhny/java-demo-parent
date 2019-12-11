package com.xiaobei.java.demo.registry;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2019-12-10 17:28:28
 */
public class ZookeeperRegistry implements IRegistry {

    private static CuratorFramework curatorFramework;

    static {
        curatorFramework
                = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                .sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .namespace("rpc")
                .build();
        curatorFramework.start();
    }

    @Override
    public void registry(String serviceName, String serviceAddress) {
        String servicePath = "/" + serviceName;
        try {
            // 创建服务节点
            if (curatorFramework.checkExists().forPath(servicePath) == null) {
                curatorFramework.create()
                        .creatingParentsIfNeeded()
                        .withMode(CreateMode.PERSISTENT)
                        .forPath(servicePath);
            }
            // 创建对应的实例
            String nodePath = servicePath + "/" + serviceAddress;
            curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath(nodePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}