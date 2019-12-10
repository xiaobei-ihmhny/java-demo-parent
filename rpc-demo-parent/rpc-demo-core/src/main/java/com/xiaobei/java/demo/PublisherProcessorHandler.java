package com.xiaobei.java.demo;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2019-12-09 13:58:58
 */
public class PublisherProcessorHandler implements Runnable {

    private Map<String, Object> rpcServiceMap;

    private Socket socket;

    public PublisherProcessorHandler(Map<String, Object> rpcServiceMap, Socket socket) {
        this.rpcServiceMap = rpcServiceMap;
        this.socket = socket;
    }

    @Override
    public void run() {
        try (InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream();
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)){
            // 从客户端拿到请求信息
            RpcRequest request = (RpcRequest)objectInputStream.readObject();
            Object result = PublishProcessorHandlerUtils.invoke(rpcServiceMap, request);
            objectOutputStream.writeObject(result);
            objectOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}