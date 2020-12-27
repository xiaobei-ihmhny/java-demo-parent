package com.xiaobei.java.zookeeper.demo.curator.leader;

import com.xiaobei.java.zookeeper.demo.curator.framework.CreateClientExamples;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.utils.CloseableUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/27 22:54
 */
public class LeaderSelectorExample {

    private static final int CLIENT_QTY = 10;

    private static final String PATH = "/examples/leader";

    public static void main(String[] args) throws IOException {
        System.out.printf("Create %d clients, " +
                "have each negotiate for leadership and then wait a random " +
                "number of seconds before letting another leader election occur.", CLIENT_QTY);
        System.out.println("Notice that leader election is fair: " +
                "all clients will become leader and will do so the same number of times.");
        List<CuratorFramework> clients = new ArrayList<>();
        List<ExampleClient> examples = new ArrayList<>();
        try {
            for (int i = 0; i < CLIENT_QTY; i++) {
                CuratorFramework client = CreateClientExamples.createSimple(CreateClientExamples.CONNECTION_STRING);
                client.start();
                ExampleClient example = new ExampleClient(client, PATH, "Client #" + i);
                // 启动
                example.start();
                clients.add(client);
                examples.add(example);
            }
            System.out.println("Press enter/return to quit");
            new BufferedReader(new InputStreamReader(System.in)).readLine();
        } finally {
            System.out.println("Shutting down...");
            for (ExampleClient example : examples) {
                CloseableUtils.closeQuietly(example);
            }
            for (CuratorFramework client : clients) {
                CloseableUtils.closeQuietly(client);
            }
        }
    }
}
