package com.corundumstudio.socketio.demo;

import com.corundumstudio.socketio.listener.*;
import com.corundumstudio.socketio.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NamespaceChatLauncher {

    private static Map<UUID, Object> clientMap = new HashMap<>(16);

    public static void main(String[] args) throws InterruptedException {

        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9092);

        final SocketIOServer server = new SocketIOServer(config);

        final SocketIONamespace chat1namespace = server.addNamespace("/chat1");
        chat1namespace.addEventListener("message", ChatObject.class, new DataListener<ChatObject>() {
            @Override
            public void onData(SocketIOClient client, ChatObject data, AckRequest ackRequest) {
                // broadcast messages to all clients
                chat1namespace.getBroadcastOperations().sendEvent("message", data);
            }
        });


        final SocketIONamespace chat2namespace = server.addNamespace("/chat2");
        chat2namespace.addEventListener("message", ChatObject.class, new DataListener<ChatObject>() {
            @Override
            public void onData(SocketIOClient client, ChatObject data, AckRequest ackRequest) {
                // 获取当前的客户端标识
                UUID clientId = client.getSessionId();

                // broadcast messages to all clients
                chat2namespace.getBroadcastOperations().sendEvent("message", data);
            }
        });
        // 监听建立连接的请求
        chat2namespace.addConnectListener((client) -> {
            // 当前新建立的连接的唯一标识
            UUID sessionId = client.getSessionId();
            clientMap.put(sessionId, null);
        });

        // 监听断开链接
        chat2namespace.addDisconnectListener((client -> {
            UUID sessionId = client.getSessionId();
            client.disconnect();
            // 从客户端map中移除指定的客户端连接
            clientMap.remove(sessionId);
        }));

        server.start();

        Thread.sleep(Integer.MAX_VALUE);

        server.stop();
    }

}
