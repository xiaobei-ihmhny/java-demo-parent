package com.xiaobei.java.demo;

import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
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
            Object result = invoke(request);
            objectOutputStream.writeObject(result);
            objectOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object invoke(RpcRequest request) throws Exception {
        String className = request.getClassName();
        String version = request.getVersion();
        String serviceKey = className;
        if(!StringUtils.isEmpty(version)) {
            serviceKey = serviceKey + ":" + version;
        }

        Object service = rpcServiceMap.get(serviceKey);
        if(null == service) {
            throw new RuntimeException("service not found " + className);
        }

        String methodName = request.getMethodName();
        Object[] parameters = request.getParameters();
        Class<?> clazz = Class.forName(className);
        Method method = null;
        if(null != parameters) {
            Class<?>[] parameterTypes = new Class[parameters.length];
            for (int i = 0; i < parameterTypes.length; i++) {
                parameterTypes[i] = parameters[i].getClass();
            }
            method = clazz.getMethod(methodName, parameterTypes);
        } else {
            method = clazz.getMethod(methodName);
        }
        return method.invoke(service, parameters);
    }
}