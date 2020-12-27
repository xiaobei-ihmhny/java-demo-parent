package com.xiaobei.java.zookeeper.demo.curator.locking;

import com.xiaobei.java.zookeeper.demo.curator.framework.CreateClientExamples;
import org.apache.curator.framework.CuratorFramework;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/27 13:34
 */
public class LockingExample {

    private static final int QTY = 5;

    private static final int REPETITIONS = QTY * 10;

    private static final String PATH = "/example/locks";

    public static void main(String[] args) {
        // FakeLimitedResource simulates some external resource that can only be access by one process at a time
        // FakeLimitedResource模拟某些外部资源，一次只能由一个线程访问
        final FakeLimitedResource resource = new FakeLimitedResource();
        ExecutorService executorService = Executors.newFixedThreadPool(QTY);
        try {
            for (int i = 0; i < QTY; ++i) {
                final int index = i;
                executorService.submit(() -> {
                    try (CuratorFramework client = CreateClientExamples.createSimple(CreateClientExamples.CONNECTION_STRING)) {
                        client.start();
                        ExampleClientThatLocks example =
                                new ExampleClientThatLocks(client, PATH, resource, "Client " + index);
                        for (int j = 0; j < REPETITIONS; ++j) {
                            System.out.printf("当前的index 为 [%d], 当前的 j 为 [%d]%n", index, j);
                            example.doWork(10, TimeUnit.SECONDS);
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
            executorService.shutdown();
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
