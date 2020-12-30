package com.xiaobei.java.zookeeper.demo.curator.framework;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Test;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/26 13:49
 */
public class CreateClientExamples {


    public static final String CONNECTION_STRING = "192.168.163.101:2181,192.168.163.102:2181,192.168.163.103:2181";

    /**
     * TODO 重试策略
     */
    @Test
    public void retryPolicy() {

    }

    /**
     * TODO ERROR org.apache.curator.framework.imps.EnsembleTracker(main-EventThread); :Invalid config event received: {server.1=192.168.163.101:2888:3888:participant, version=0, server.3=192.168.163.103:2888:3888:participant, server.2=192.168.163.102:2888:3888:participant}
     */
    @Test
    public void createSimple() {
        CuratorFramework client = createSimple(CONNECTION_STRING);
        testClient(client);
    }

    @Test
    public void createWithOptions() {
        String connectionString = "192.168.163.101:2181,192.168.163.102:2181,192.168.163.103:2181";
        CuratorFramework client = createWithOptions(
                connectionString,
                new ExponentialBackoffRetry(1000, 3),
                10000,
                10000);
        testClient(client);
    }

    private void testClient(CuratorFramework client) {
        // 启动
        client.start();
        try {
            byte[] home = client.getData().forPath("/zookeeper");
            if (home != null) {
                System.out.println(new String(home));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 最简单的方式创建 {@link CuratorFramework} 实例
     *
     * @param connectionString
     * @return
     */
    public static CuratorFramework createSimple(String connectionString) {
        // 这些是ExponentialBackoffRetry的合理参数。 第一次重试将等待1秒-第二次将等待长达2秒-第三次将等待长达4秒。
        ExponentialBackoffRetry retryPolicy =
                new ExponentialBackoffRetry(1000, 3);
        // 最简单的方式来获取一个 {@code CuratorFramework} 的实例，只需指定连接信息和重试策略，其他参数使用缺省值。
        return CuratorFrameworkFactory.newClient(connectionString, retryPolicy);
    }

    /**
     * 使用 {@link CuratorFrameworkFactory#builder()} 可以对创建选项进行精细控制。
     * 请参阅CuratorFrameworkFactory.Builder javadoc详细信息
     * @param connectionString 连接字符串信息
     * @param retryPolicy 重试策略
     * @param connectionTimoutMs 连接超时时间
     * @param sessionTimeoutMs session超时时间
     * @return 根据指定参数构建的 {@link CuratorFramework} 实例
     */
    public static CuratorFramework createWithOptions(
            String connectionString, RetryPolicy retryPolicy,
            int connectionTimoutMs, int sessionTimeoutMs) {
        return CuratorFrameworkFactory.builder()
                .connectString(connectionString)
                .retryPolicy(retryPolicy)
                .connectionTimeoutMs(connectionTimoutMs)
                .sessionTimeoutMs(sessionTimeoutMs)
                // 等等...
                .build();
    }
}
