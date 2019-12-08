package com.xiaobei.java.demo;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2019-12-07 07:48:48
 */
public class AclDemoTest {

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
    public void createNodeWithAcl() throws Exception {
        List<ACL> aclList = new ArrayList<>();
        ACL acl = new ACL(ZooDefs.Perms.READ,
                new Id("digest", DigestAuthenticationProvider.generateDigest("admin:admin")));
        aclList.add(acl);
        curatorFramework
                .create()
                .withACL(aclList)
                .forPath("/test/acl", "2222".getBytes());
    }
}
