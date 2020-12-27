package com.xiaobei.java.zookeeper.demo.curator.cache;

import com.xiaobei.java.zookeeper.demo.curator.framework.CreateClientExamples;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Very simple example of creating a CuratorCache that listens to events and logs the changes
 * to standard out. A loop of random changes is run to exercise the cache.
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/27 10:14
 */
public class CuratorCacheExample {

    private static final String PATH = "/example/cache";

    public static void main(String[] args) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        try(CuratorFramework client = CreateClientExamples.createSimple(CreateClientExamples.CONNECTION_STRING)) {
            client.start();
            try (CuratorCache cache = CuratorCache.build(client, PATH)) {
                // there are several ways to set a listener on a CuratorCache. You can watch for individual events
                // or for all events. Here, we'll use the builder to log individual cache actions
                CuratorCacheListener listener = CuratorCacheListener.builder()
                        .forCreates(childData -> System.out.printf("Node created: [%s]%n", childData))
                        .forChanges(((oldNode, node) -> System.out.printf("Node changed. Old: [%s] New: [%s]%n", oldNode, node)))
                        .forDeletes(childData -> System.out.printf("Node deleted. Old value: [%s]%n", childData))
                        .forInitialized(() -> System.out.println("Cache initialized"))
                        .build();
                // register the listener
                cache.listenable().addListener(listener);
                // the cache must be started
                cache.start();

                // now randomly create/change/delete nodes
                for (int i = 0; i < 1000; i++) {
                    int depth = random.nextInt(1, 4);
                    String path = makeRandomPath(random, depth);
                    if(random.nextBoolean()) {
                        client.create()
                                .orSetData()
                                .creatingParentContainersIfNeeded()
                                .forPath(path, Long.toString(random.nextLong()).getBytes());
                    } else {
                        client.delete().quietly().deletingChildrenIfNeeded().forPath(path);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static String makeRandomPath(ThreadLocalRandom random, int depth) {
        if(depth == 0) {
            return PATH;
        } else {
            return makeRandomPath(random, depth - 1) + "/" + random.nextInt(3);
        }
    }
}
