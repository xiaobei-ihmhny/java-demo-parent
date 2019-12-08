package com.xiaobei.java.demo;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * NodeCreated 节点的创建
 * NodeChildrenChanged 子节点变化
 * NodeDataChanged
 * NodeDeleted
 * {@link PathChildrenCache}
 * 针对子节点的创建、删除和更新 触发事件
 * {@link NodeCache}
 * 针对当前节点的变化触发事件
 * {@link TreeCache}
 * 针对综合事件的变化触发事件
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2019-12-07 17:33:33
 */
public class WatcherDemoTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(WatcherDemoTest.class);

    private CuratorFramework curatorFramework;

    private static final ReentrantLock LOCK = new ReentrantLock();

    private static final Condition STOP = LOCK.newCondition();

    /**
     * 1. zookeeper是java开发的，所以需要安装java并配置java环境变量
     * 2. 如果本地报错：“Will not attempt to authenticate using SASL (unknown error)"，
     * 而服务端无错误，则可以请检查服务器端zookeeper端口（默认为2181）是否开放。
     */
    @Before
    public void init() {
        String connectString = "192.168.129.128:2181";
        curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(connectString)
                .sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 2))
                .build();
        curatorFramework.start();
    }

    /**
     * 用于在测试方法执行完成之后阻塞当前线程，
     * 确保可以正常测试相关监听
     */
    @After
    public void after() {
        LOCK.lock();
        try {
            STOP.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            LOGGER.error("current watcher service stopped, interrupted by other thread! ", e);
        } finally {
            LOCK.unlock();
        }
    }

    /**
     * 监听指定节点的事件（新增、修改、删除），不包括子节点
     *
     * 可以用来实现配置中心
     * TODO {@link org.apache.curator.framework.listen.ListenerContainer} 已过时，
     * 请使用新的api替换！！ 2019年12月8日9:20:38 xiaobei-ihmhny
     *
     * @throws Exception
     */
    @Test
    public void addListenerWithNode() throws Exception {
        NodeCache nodeCache = new NodeCache(curatorFramework, "/test");
        NodeCacheListener nodeCacheListener = () -> {
            LOGGER.info("receive node changed");
            LOGGER.info("{} ----> {}", nodeCache.getCurrentData().getPath()
                    , new String(nodeCache.getCurrentData().getData()));
        };
        nodeCache.getListenable().addListener(nodeCacheListener);
        nodeCache.start();
    }

    /**
     * 监听指定节点子节点的变化（新增、修改、删除事件）
     *
     * 实现服务注册时，可以对服务做动态感知
     * TODO {@link org.apache.curator.framework.listen.ListenerContainer} 已过时，
     * 请使用新的api替换！！ 2019年12月8日9:20:38 xiaobei-ihmhny
     */
    @Test
    public void addListenerWithChild() throws Exception {
        PathChildrenCache childrenCache =
                new PathChildrenCache(curatorFramework, "/test2", true);
        PathChildrenCacheListener listener = (client, event) -> {
            LOGGER.info("事件为：{}", event.getType());
            if(event.getData()!=null && event.getData().getData() != null) {
                LOGGER.info("节点当前值为：{}", new String(event.getData().getData()));
            }
        };
        childrenCache.getListenable().addListener(listener);
        childrenCache.start(PathChildrenCache.StartMode.NORMAL);
    }

    /**
     * 监听指定节点及其子节点的事件（新增、修改、删除）
     * @throws Exception
     */
    @Test
    public void addListenerNodeAndChild() throws Exception {
        TreeCache treeCache = new TreeCache(curatorFramework, "/test3");
        TreeCacheListener cacheListener = (client, event) -> {
            LOGGER.info("事件为：{}", event.getType());
            if(event.getData()!=null && event.getData().getData() != null) {
                LOGGER.info("节点当前值为：{}", new String(event.getData().getData()));
            }
        };
        treeCache.getListenable().addListener(cacheListener);
        treeCache.start();
    }


}
