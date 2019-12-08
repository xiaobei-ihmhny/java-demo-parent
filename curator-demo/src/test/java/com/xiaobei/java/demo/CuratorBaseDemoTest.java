package com.xiaobei.java.demo;

import com.xiaobei.java.demo.serializer.JavaSerializer;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

/**
 * Curator基础操作
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2019-12-07 06:33:33
 */
public class CuratorBaseDemoTest {

    private CuratorFramework curatorFramework;

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
                .retryPolicy(new ExponentialBackoffRetry(1000, 2))
                .build();
        curatorFramework.start();
    }

    /**
     * 创建普通节点
     */
    @Test
    public void createString() throws Exception {
        curatorFramework.create()
                // 当父节点不存在时，会自动创建其父节点
                .creatingParentsIfNeeded()
                // 指定节点类型，共有4种
                // 1. CreateMode.PERSISTENT 持久节点
                // 2. CreateMode.EPHEMERAL 临时节点
                // 3. CreateMode.PERSISTENT_SEQUENTIAL 持久有序节点
                // 4. CreateMode.EPHEMERAL_SEQUENTIAL 临时有序节点
                .withMode(CreateMode.PERSISTENT)
                // 指定节点信息，路径（path）及值（data）
                .forPath("/test/111", "xiaobei".getBytes());
    }

    @Test
    public void getString() throws Exception {
        byte[] result = curatorFramework
                .getData()
                .forPath("/test/111");
        System.out.println(new String(result));
    }

    /**
     * 创建对象节点
     * @throws Exception
     */
    @Test
    public void createObject() throws Exception {
        curatorFramework.create()
                // 当父节点不存在时，会自动创建其父节点
                .creatingParentsIfNeeded()
                // 指定节点类型，共有4种
                // 1. CreateMode.PERSISTENT 持久节点
                // 2. CreateMode.EPHEMERAL 临时节点
                // 3. CreateMode.PERSISTENT_SEQUENTIAL 持久有序节点
                // 4. CreateMode.EPHEMERAL_SEQUENTIAL 临时有序节点
                .withMode(CreateMode.PERSISTENT)
                // 指定节点信息，路径（path）及值（data）
                .forPath("/test/user", JavaSerializer.serializer(new User("xiaobei",30)));
    }

    /**
     * 获取对象
     * @throws Exception
     */
    @Test
    public void getObject() throws Exception {
        byte[] result = curatorFramework
                .getData()
                .forPath("/test/user");
        User user = JavaSerializer.deserialize(result, User.class);
        System.out.println(user);
    }

    /**
     * 更新节点
     * @throws Exception
     */
    @Test
    public void update() throws Exception {
        curatorFramework
                // 指定本操作为更新操作
                .setData()
                // 指定需要更新的节点路径及更新后的节点内容
                .forPath("/test/111","huihui".getBytes());
    }

    /**
     * 删除叶子节点
     * @throws Exception
     */
    @Test
    public void delete() throws Exception {
        curatorFramework
                .delete()
                .forPath("/test/111");
    }

    /**
     * 递归删除
     * @throws Exception
     */
    @Test
    public void deleteAll() throws Exception {
        curatorFramework
                .delete()
                // 需要时删除子节点，即为递归删除
                .deletingChildrenIfNeeded()
                .forPath("/test");
    }

    @Test
    public void deleteWithVersion() throws Exception {
        Stat stat = new Stat();
        // 删除操作前先获取节点当前的版本号，后面根据版本号删除
        curatorFramework.getData().storingStatIn(stat).forPath("/test/111");
        System.out.println(stat.toString());
        curatorFramework
                .delete()
                .deletingChildrenIfNeeded()
                // 根据版本号删除
                .withVersion(stat.getVersion())
                .forPath("/test/111");
    }
}
