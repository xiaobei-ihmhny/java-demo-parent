package com.xiaobei.java.demo;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2019-12-09 13:53:53
 */
public class RpcPublisher {

    ThreadFactory threadFactory = new ThreadFactoryBuilder()
            .setNameFormat("rpc-demo-pool-%d")
            .build();

    ExecutorService executorService =
            new ThreadPoolExecutor(5,
                    100,
                    60L,
                    TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>(1024),
                    threadFactory,
                    new ThreadPoolExecutor.AbortPolicy());

    public void publish(Object service, int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while(true) {
                Socket socket = serverSocket.accept();
                executorService.submit(new PublisherProcessorHandler(service, socket));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}