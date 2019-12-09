package com.xiaobei.java.demo;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2019-12-09 13:58:58
 */
public class PublisherProcessorHandler implements Runnable {

    private Object service;

    private Socket socket;

    public PublisherProcessorHandler(Object service, Socket socket) {
        this.service = service;
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
            Object result = invoke(request);
            objectOutputStream.writeObject(result);
            objectOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object invoke(RpcRequest request) throws Exception {
        String className = request.getClassName();
        String methodName = request.getMethodName();
        Object[] parameters = request.getParameters();
        Class<?>[] parameterTypes = new Class[parameters.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            parameterTypes[i] = parameters[i].getClass();
        }
        Class<?> clazz = Class.forName(className);
        Method method = clazz.getMethod(methodName, parameterTypes);
        return method.invoke(service, parameters);
    }
}