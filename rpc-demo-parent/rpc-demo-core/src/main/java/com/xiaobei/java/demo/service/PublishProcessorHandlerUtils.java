package com.xiaobei.java.demo.service;

import com.xiaobei.java.demo.RpcRequest;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2019-12-10 07:47:47
 */
public class PublishProcessorHandlerUtils {

    public static Object invoke(Map<String, Object> rpcServiceMap, RpcRequest request) throws Exception {
        String className = request.getClassName();
        String version = request.getVersion();
        String serviceKey = className;
        if (!StringUtils.isEmpty(version)) {
            serviceKey = serviceKey + ":" + version;
        }

        Object service = rpcServiceMap.get(serviceKey);
        if (null == service) {
            throw new RuntimeException("service not found " + className);
        }

        String methodName = request.getMethodName();
        Object[] parameters = request.getParameters();
        Class<?> clazz = Class.forName(className);
        Method method = null;
        if (null != parameters) {
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