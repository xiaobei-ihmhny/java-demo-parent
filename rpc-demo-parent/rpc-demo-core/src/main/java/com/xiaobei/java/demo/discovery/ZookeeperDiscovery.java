package com.xiaobei.java.demo.discovery;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * zk方式实现服务发现
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2019-12-11 13:17:17
 */
public class ZookeeperDiscovery implements IDiscovery {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperDiscovery.class);

    private static CuratorFramework curatorFramework;

    private LoadBalance loadBalance = new RandomLoadBanlance();

    /**
     * 其中map的key为服务名称，value为服务名称与
     */
    private Map<String, List<String>> serviceMap = new HashMap<>();

    private static final String RPC_NAMESPACE = "rpc";

    {
        curatorFramework
                = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                .sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .namespace(RPC_NAMESPACE)
                .build();
        curatorFramework.start();
    }

    @Override
    public String discovery(String serviceName) {
        if (!serviceMap.containsKey(serviceName)) {
            // 当map中没有以相应服务名称为key的键值对时，
            // 将相应服务注册到zk上，并发起监听
            serviceMap.put(serviceName, new ArrayList<>());
            registryWatch(serviceName);
            try {
                // 保证监听服务已经获取了最新的数据
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        List<String> serviceList = serviceMap.get(serviceName);
        return loadBalance.select(serviceList);
    }


    private void registryWatch(String serviceName) {
        PathChildrenCache childrenCache
                = new PathChildrenCache(curatorFramework, "/" + serviceName, true);
        PathChildrenCacheListener cacheListener = (framework, event) -> {
            LOGGER.info("监听到事件变化...{}", event.getType());
            // 节点变化时，动态更新对应节点下的服务列表serviceList
            // 循环获取每个节点的子节点并存入map中
            changeServiceMap(event, serviceName);
        };
        childrenCache.getListenable().addListener(cacheListener);
        try {
            childrenCache.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeServiceMap(PathChildrenCacheEvent event, String serviceName) {
        List<String> serviceList = serviceMap.get(serviceName);
        PathChildrenCacheEvent.Type eventType = event.getType();
        ChildData data = event.getData();
        if(null == data) {
            return;
        }
        String path = data.getPath();
        String serviceAddress = path.split("/" + serviceName + "/")[1];
        // 根据事件不同，更新相应的map
        switch (eventType) {
            case CHILD_ADDED:
                serviceList.add(serviceAddress);
                break;
            case CHILD_REMOVED:
                serviceList.remove(serviceAddress);
                break;
            default:
                break;
        }
    }
}