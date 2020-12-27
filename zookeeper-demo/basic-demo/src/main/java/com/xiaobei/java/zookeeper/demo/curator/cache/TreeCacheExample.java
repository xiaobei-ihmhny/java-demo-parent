package com.xiaobei.java.zookeeper.demo.curator.cache;

import com.xiaobei.java.zookeeper.demo.curator.framework.CreateClientExamples;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.TreeCache;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/27 10:12
 */
public class TreeCacheExample {

    public static void main(String[] args) throws Exception {
        try (CuratorFramework client = CreateClientExamples
                .createSimple(CreateClientExamples.CONNECTION_STRING);
             TreeCache cache = TreeCache.newBuilder(client, "/").setCacheData(false).build()) {
            client.getUnhandledErrorListenable().addListener((message, e) -> {
                System.err.println("error=" + message);
                e.printStackTrace();
            });
            client.getConnectionStateListenable().addListener((c, newState) -> {
                System.out.println("state=" + newState);
            });
            client.start();
            cache.getListenable().addListener((c, event) -> {
                if (event.getData() != null) {
                    System.out.println("type=" + event.getType() + " path=" + event.getData().getPath());
                } else {
                    System.out.println("type=" + event.getType());
                }
            });
            cache.start();

            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            in.readLine();

        }
    }
}
