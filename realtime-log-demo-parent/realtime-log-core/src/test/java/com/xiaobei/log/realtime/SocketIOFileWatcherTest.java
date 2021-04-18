package com.xiaobei.log.realtime;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.store.RedissonStoreFactory;
import com.corundumstudio.socketio.transport.NamespaceClient;
import com.xiaobei.log.realtime.domain.ChatMessageObject;
import com.xiaobei.log.realtime.netty.FileWatcherNettyClient;
import com.xiaobei.log.realtime.netty.FileWatcherNettyClientHandler;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

/**
 * TODO
 *
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-04-17 06:20:20
 */
public class SocketIOFileWatcherTest {

    @Test
    public void socketServer() {
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9092);
        config.setTransports(Transport.WEBSOCKET);
        RedissonStoreFactory clientStoreFactory = new RedissonStoreFactory();
        config.setStoreFactory(clientStoreFactory);

        final SocketIOServer server = new SocketIOServer(config);
        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
                System.out.println("收到新的连接..." + client.getSessionId().toString());
            }
        });

        server.addEventListener("realtime", ChatMessageObject.class, new DataListener<ChatMessageObject>() {
            @Override
            public void onData(SocketIOClient client, ChatMessageObject data, AckRequest ackRequest) {
                server.getBroadcastOperations().sendEvent("realtime", data);
            }
        });

        server.start();
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        server.stop();
    }

    @Test
    public void socketClient() {
        try {
            CountDownLatch latch = new CountDownLatch(1);
            String filePath = "D:\\logs\\business.log";
            // 创建netty-client
            FileWatcherNettyClientHandler listener = new FileWatcherNettyClientHandler("xiaobei");
            FileWatcherNettyClient client = new FileWatcherNettyClient("127.0.0.1", 8080, filePath, listener);
            FileWatcher fileWatchedService = new FileWatcher(filePath, listener);
            // 启动netty服务
            new Thread(client).start();
            new Thread(fileWatchedService).start();
//            fileWatchedService.watchFileModify();
            latch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
