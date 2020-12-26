package com.xiaobei.java.zookeeper.demo.curator;

import org.junit.jupiter.api.Test;

/**
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

    /**
     * {@link org.apache.curator.framework.recipes.cache.PathChildrenCache} 针对于子节点的创建、删除和更新 触发事件
     * replace by {@link org.apache.curator.framework.recipes.cache.CuratorCache}
     * {@link org.apache.curator.framework.recipes.cache.NodeCache} 针对当前节点的变化触发事件
     * replace by {@link org.apache.curator.framework.recipes.cache.CuratorCache}
     * {@link org.apache.curator.framework.recipes.cache.TreeCache} 综合了当前节点和子节点的事件
     * replace by {@link org.apache.curator.framework.recipes.cache.CuratorCache}
     *
     */
    @Test
    public void recipesCache() {

    }
}
