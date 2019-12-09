package com.xiaobei.java.demo;

import java.lang.reflect.Proxy;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2019-12-09 18:32:32
 */
public class RpcProxy {

    public static <T> T proxy(Class<T> clazz, String host, int port) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class[]{clazz},
                new ProxyInvocationHandler(host, port));
    }
}