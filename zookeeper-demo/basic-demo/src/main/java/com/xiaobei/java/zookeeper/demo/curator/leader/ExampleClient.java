package com.xiaobei.java.zookeeper.demo.curator.leader;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * An example leader selector client. Note that {@link LeaderSelectorListenerAdapter} which
 * has the recommended handling for connection state issues
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/27 22:34
 */
public class ExampleClient extends LeaderSelectorListenerAdapter implements Closeable {

    private final String name;

    private final LeaderSelector leaderSelector;

    private final AtomicInteger leaderCount = new AtomicInteger();

    public ExampleClient(CuratorFramework client, String path, String name) {
        this.name = name;
        /*
         * create a leader selector using the given path for management
         * all participants in a given leader selection must use the same path
         * ExampleClient here is also a LeaderSelectorListener but this isn't required
         */
        this.leaderSelector = new LeaderSelector(client, path, this);
        // for most cases you will want your instance to requeue when it relinquishes leadership
        leaderSelector.autoRequeue();
    }

    /**
     * 通过调用 {@link LeaderSelector#start()} 方法可以启动当前节点
     */
    public void start() {
        /*
         * the selection for this instance doesn't start until the leader selector is started
         * leader selection is done in the background so this call to leaderSelector.start() returns immediately
         */
        leaderSelector.start();
    }

    @Override
    public void close() throws IOException {
        leaderSelector.close();
    }

    /**
     * 若能进入该方法说明当前节点已经成为了leader，同时当该方法执行结束时就代表着当前节点放弃了leader权限
     * @param client
     * @throws Exception
     */
    @Override
    public void takeLeadership(CuratorFramework client) throws Exception {
        final int waitSeconds = (int) (5 * Math.random()) + 1;
        System.out.printf("%s is now the leader. Waiting %d seconds...", name, waitSeconds);
        System.out.printf("%s has been leader %d time(s) before.", name, leaderCount.getAndIncrement());
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(waitSeconds));
        } catch (InterruptedException e) {
            System.err.printf("%s was interrupted.", name);
            Thread.currentThread().interrupt();
        } finally {
            System.out.printf("%s relinquishing leadership. \n", name);
        }
    }


}
