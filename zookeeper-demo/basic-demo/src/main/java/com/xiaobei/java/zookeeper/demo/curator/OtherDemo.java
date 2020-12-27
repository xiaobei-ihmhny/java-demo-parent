package com.xiaobei.java.zookeeper.demo.curator;

import com.xiaobei.java.zookeeper.demo.curator.framework.CreateClientExamples;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.*;
import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * TODO 补充 zookeeper 具体的一些应用场景？？？2020年12月27日11:18:51
 *
 * TODO 补充相关代码
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/26 23:01
 */
public class OtherDemo {

    /**
     * NodeCreated 节点的创建
     * NodeChildrenChanged 子节点变化
     * NodeDataChanged
     * NodeDeleted 节点删除
     */
    @Test
    public void watcher() {

    }

    /*
     * {@link org.apache.curator.framework.recipes.cache.PathChildrenCache} 针对于子节点的创建、删除和更新 触发事件
     * replace by {@link org.apache.curator.framework.recipes.cache.CuratorCache}
     * {@link org.apache.curator.framework.recipes.cache.NodeCache} 针对当前节点的变化触发事件
     * replace by {@link org.apache.curator.framework.recipes.cache.CuratorCache}
     * {@link org.apache.curator.framework.recipes.cache.TreeCache} 综合了当前节点和子节点的事件
     * replace by {@link org.apache.curator.framework.recipes.cache.CuratorCache}
     *
     */

    /**
     * {@link NodeCache} 只监听指定节点的创建、删除和更新操作，不包括其子孙节点的创建、删除和更新
     */
    @Test
    public void recipesCacheNodeCache() {
        Lock lock = new ReentrantLock();
        Condition stop = lock.newCondition();
        try (CuratorFramework client = CreateClientExamples.createSimple(CreateClientExamples.CONNECTION_STRING)) {
            client.start();
            NodeCache nodeCache = new NodeCache(client, "/home", false);
            NodeCacheListener nodeCacheListener = new NodeCacheListener() {
                @Override
                public void nodeChanged() throws Exception {
                    System.out.println("receive Node Changed");
                    System.out.println(nodeCache.getCurrentData().getPath() + "---"
                            + new String(nodeCache.getCurrentData().getData()));
                }
            };
            nodeCache.getListenable().addListener(nodeCacheListener);
            nodeCache.start();
            lock.lock();
            try {
                // 主线程不结束
                stop.await();
            } finally {
                lock.unlock();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 若用于实现服务注册中心时，可以针对服务做动态感知
     * TODO 但 dubbo 中貌似没有用这种方式吧？？？2020年12月27日7:53:28
     */
    @Test
    public void recipesCachePathChildrenCache() {
        Lock lock = new ReentrantLock();
        Condition stop = lock.newCondition();
        try (CuratorFramework client = CreateClientExamples.createSimple(CreateClientExamples.CONNECTION_STRING)) {
            client.start();
            PathChildrenCache pathChildrenCache = new PathChildrenCache(client, "/home", false);
            PathChildrenCacheListener pathChildrenCacheListener = new PathChildrenCacheListener() {
                @Override
                public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                    byte[] data;
                    if(event != null && event.getData() != null) {
                        System.out.printf("%s --- ", event.getData().getPath());
                        // TODO 为什么 {@code event.getData().getData()} 方法始终为 null 呢？
                        Object newData = (data = event.getData().getData()) == null ? null : new String(data);
                        System.out.println(newData);
                    }
                }
            };
            pathChildrenCache.getListenable().addListener(pathChildrenCacheListener);
            pathChildrenCache.start();
            lock.lock();
            try {
                // 主线程不结束
                stop.await();
            } finally {
                lock.unlock();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 客户端命令：
     * [zk: localhost:2181(CONNECTED) 26] create /home/xiaobei 18
     * Created /home/xiaobei
     * [zk: localhost:2181(CONNECTED) 27] set /home/xiaobei 16
     * [zk: localhost:2181(CONNECTED) 28] delete /home/xiaobei
     *
     * 程序响应：
     * NODE_ADDED, /home/xiaobei --- 18
     * NODE_UPDATED, /home/xiaobei --- 16
     * NODE_REMOVED, /home/xiaobei --- 16
     */
    @Test
    public void recipesCacheTreeCache() {
        Lock lock = new ReentrantLock();
        Condition stop = lock.newCondition();
        try (CuratorFramework client = CreateClientExamples.createSimple(CreateClientExamples.CONNECTION_STRING)) {
            client.start();
            TreeCache treeCache = new TreeCache(client, "/home");
            TreeCacheListener treeCacheListener = new TreeCacheListener() {
                @Override
                public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
                    byte[] data;
                    if(event != null && event.getData() != null) {
                        System.out.printf("%s, %s --- ", event.getType(), event.getData().getPath());
                        Object newData = (data = event.getData().getData()) == null ? null : new String(data);
                        System.out.println(newData);
                    }
                }
            };
            treeCache.getListenable().addListener(treeCacheListener);
            treeCache.start();
            lock.lock();
            try {
                // 主线程不结束
                stop.await();
            } finally {
                lock.unlock();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
