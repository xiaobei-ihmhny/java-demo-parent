package com.xiaobei.java.demo.client;

import com.xiaobei.java.demo.RpcRequest;
import com.xiaobei.java.demo.discovery.IDiscovery;
import com.xiaobei.java.demo.discovery.ZookeeperDiscovery;

import java.lang.reflect.Proxy;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2019-12-09 18:32:32
 */
public class RpcProxy {

    public static <T> T proxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class[]{clazz},
                new ProxyInvocationHandler());
    }
}