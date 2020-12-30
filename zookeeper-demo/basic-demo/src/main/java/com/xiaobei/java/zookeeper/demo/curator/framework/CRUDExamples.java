package com.xiaobei.java.zookeeper.demo.curator.framework;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/26 13:30
 */
public class CRUDExamples {

    private static final Logger LOGGER = LoggerFactory.getLogger(CRUDExamples.class);

    @Test
    public void create() {
        CuratorFramework client = CreateClientExamples.createSimple(CreateClientExamples.CONNECTION_STRING);
        client.start();
        try {
            create(client, "/data", "huihui".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("创建过程中出现异常，异常信息为：", e);
        }
    }

    @Test
    public void createEphemeral() {
        CuratorFramework client = CreateClientExamples.createSimple(CreateClientExamples.CONNECTION_STRING);
        client.start();
        try {
            createEphemeral(client, "/data2", "tietie".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createEphemeralSequential() {
        CuratorFramework client = CreateClientExamples.createSimple(CreateClientExamples.CONNECTION_STRING);
        client.start();
        try {
            createEphemeralSequential(client, "/data2", "tietie".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void setData() {
        CuratorFramework client = CreateClientExamples.createSimple(CreateClientExamples.CONNECTION_STRING);
        client.start();
        try {
            setData(client, "/data", "xiaohui".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void setDataAsync() throws Exception {
        CuratorFramework client = CreateClientExamples.createSimple(CreateClientExamples.CONNECTION_STRING);
        client.start();
        // 添加一个监听，用于获取异步通知
        client.getCuratorListenable().addListener((client1, event) -> {
            // examine event for details
            String path = event.getPath();
            // 输出结果为："/data"
            System.out.println(path);
        });
        // 异步的设置给定节点为给定的值，完成后会回调对应的监听
        client.setData().inBackground().forPath("/data", "xiaoxiao".getBytes());
        System.in.read();
    }

    /**
     * 通过 ${@link org.apache.curator.framework.api.BackgroundCallback} 来完成异步通知
     */
    @Test
    public void setDataAsyncWithCallback() throws Exception {
        CuratorFramework client = CreateClientExamples.createSimple(CreateClientExamples.CONNECTION_STRING);
        client.start();
        // 通过 {@code BackgroundCallback} 来完成异步回调
        client.setData().inBackground((curatorClient, event) -> {
            System.out.println(event.getPath());
        }).forPath("/data", "xiaotietie".getBytes());
    }

    /**
     * 删除给定的节点
     * @throws Exception
     */
    @Test
    public void delete() throws Exception {
        CuratorFramework client = CreateClientExamples.createSimple(CreateClientExamples.CONNECTION_STRING);
        client.start();
        client.delete().forPath("/data");
    }

    /**
     * 确保删除
     *
     * Guaranteed Delete
     * Solves this edge case: deleting a node can fail due to connection issues.
     * Further, if the node was ephemeral, the node will not get auto-deleted as the session is still valid.
     * This can wreak havoc with lock implementations.
     *
     * When guaranteed is set,
     * Curator will record failed node deletions and attempt to delete them in the background until successful.
     * NOTE: you will still get an exception when the deletion fails.
     * But, you can be assured that as long as the CuratorFramework instance is open attempts will be made to delete the node.
     *
     * 解决此极端情况：由于连接问题，删除节点可能会失败。
     * 此外，如果该节点是临时节点，则该节点将不会被自动删除，
     * 因为会话仍然有效。 这可能会对锁实现造成严重破坏。
     *
     * 设置了保证后，Curator将记录失败的节点删除，并尝试在后台删除它们直到成功。
     * 注意：删除失败时，您仍然会收到异常消息。
     * 但是，您可以放心，只要CuratorFramework实例处于打开状态，就将尝试删除该节点。
     */
    @Test
    public void guaranteeDelete() throws Exception {
        CuratorFramework client = CreateClientExamples.createSimple(CreateClientExamples.CONNECTION_STRING);
        client.start();
        // 保证删除成功
        client.delete().guaranteed().forPath("/data");
    }

    /**
     * 通过方法 {@code watched()} 完成监听
     * @throws Exception
     */
    @Test
    public void watchedGetChildrenByWatchedMethod() throws Exception {
        CuratorFramework client = CreateClientExamples.createSimple(CreateClientExamples.CONNECTION_STRING);
        client.start();
        CuratorListener listener = new CuratorListener() {
            @Override
            public void eventReceived(CuratorFramework client, CuratorEvent event) throws Exception {
                // examine event for details
                String path = event.getPath();
                // 输出结果为："/data"
                System.out.println(path);
                // 重新监听
                client.getChildren().watched().forPath("/data");
            }
        };
        // 添加一个监听，用于获取异步通知
        client.getCuratorListenable().addListener(listener);
        // 监听指定节点的子节点行为
        client.getChildren().watched().forPath("/data");
        System.in.read();
    }

    /**
     * 通过 {@link org.apache.zookeeper.Watcher} 来完成 监听
     */
    @Test
    public void watchedGetChildrenByWatcher() throws Exception {
        CuratorFramework client = CreateClientExamples.createSimple(CreateClientExamples.CONNECTION_STRING);
        client.start();
        Watcher watcher = new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println(event);
            }
        };
        client.getChildren().usingWatcher(watcher).forPath("/data");
        System.in.read();
    }

    /**
     * TODO 乐观锁删除？？
     */
    @Test
    public void deleteData() {
        try (CuratorFramework client = CreateClientExamples.createSimple(CreateClientExamples.CONNECTION_STRING)) {
            client.start();
            Stat stat = new Stat();
            String value = new String(client.getData().storingStatIn(stat).forPath("/date/xiaobei"));
            client.delete().withVersion(stat.getVersion()).forPath("/date/xiaobei");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * TODO 详情学习？？？
     * 权限模式
     * IP/Digest/world/Super
     */
    @Test
    public void withAcl() {
        try (CuratorFramework client = CreateClientExamples.createSimple(CreateClientExamples.CONNECTION_STRING)) {
            client.start();
            List<ACL> list = new ArrayList<>();
            list.add(new ACL(ZooDefs.Perms.READ,
                    new Id("digest",
                            DigestAuthenticationProvider.generateDigest("admin:admin"))));// 对用户名和密码进行加密
            client.create().withMode(CreateMode.PERSISTENT).withACL(list).forPath("/data");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 使用给定的路径和内容创建相应的节点
     * @param client 客户端
     * @param path 指定的路径
     * @param payload 指定的内容
     * @throws Exception
     */
    public static void create(CuratorFramework client, String path, byte[] payload) throws Exception {
        client.create().forPath(path, payload);
    }

    /**
     * 使用给定的路径和内容创建临时节点
     * @param client 客户端
     * @param path 指定的路径
     * @param payload 指定的内容
     * @throws Exception
     */
    public static void createEphemeral(CuratorFramework client, String path, byte[] payload) throws Exception {
        client.create().withMode(CreateMode.EPHEMERAL).forPath(path, payload);
    }

    /**
     * 使用给定的路径和内容在保护模式下创建临时有序节点
     *
     * <p>Protection Mode:
     *
     * <p>It turns out there is an edge case that exists when creating sequential-ephemeral nodes.
     * The creation can succeed on the server,
     * but the server can crash before the created node name is returned to the client.
     * However, the ZK session is still valid so the ephemeral node is not deleted.
     * Thus, there is no way for the client to determine what node was created for them.
     *
     * <p>Even without sequential-ephemeral, however,
     * the create can succeed on the sever but the client (for various reasons) will not know it.
     * Putting the create builder into protection mode works around this.
     * The name of the node that is created is prefixed with a GUID.
     * If node creation fails the normal retry mechanism will occur.
     * On the retry, the parent path is first searched for a node that has the GUID in it.
     * If that node is found,
     * it is assumed to be the lost node that was successfully
     * created on the first try and is returned to the caller.
     *
     * <p> 保护模式：
     *
     * <p>事实证明，在创建顺序短暂节点时存在一种极端情况。
     * 创建可以在服务器上成功完成，但是在创建的节点名称返回给客户端之前，
     * 服务器可能会崩溃。 但是，ZK会话仍然有效，因此临时节点不会被删除。
     * 因此，客户端无法确定为其创建了哪个节点。
     *
     * <p>但是，即使没有顺序暂存器，创建也可以在服务器上成功，
     * 但是客户端（由于各种原因）将不知道。 可以将创建构建器置于保护模式下。
     * 创建的节点的名称以GUID为前缀。 如果节点创建失败，则将发生正常的重试机制。
     * 重试时，首先在父路径中搜索其中具有GUID的节点。
     * 如果找到该节点，则假定该节点是在第一次尝试中成功创建的丢失节点，并返回给调用方。
     *
     * @param client 客户端
     * @param path 指定的路径
     * @param payload 指定的内容
     * @throws Exception
     */
    public static void createEphemeralSequential(CuratorFramework client, String path, byte[] payload) throws Exception {
        client.create().withProtection().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(path, payload);
    }

    /**
     * 更新指定的路径下的节点值为给定值
     * @param client 客户端
     * @param path 指定路径
     * @param payload 内容
     * @throws Exception
     */
    public static void setData(CuratorFramework client, String path, byte[] payload) throws Exception {
        client.setData().forPath(path, payload);
    }



}
