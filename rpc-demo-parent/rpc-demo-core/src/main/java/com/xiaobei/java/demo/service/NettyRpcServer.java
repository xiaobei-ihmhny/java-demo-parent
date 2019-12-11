package com.xiaobei.java.demo.service;

import com.xiaobei.java.demo.RpcService;
import com.xiaobei.java.demo.registry.IRegistry;
import com.xiaobei.java.demo.registry.ZookeeperRegistry;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2019-12-09 21:27:27
 */
@SuppressWarnings("ALL")
public class NettyRpcServer implements ApplicationContextAware, InitializingBean {

    private Map<String, Object> rpcServiceMap = new HashMap<>();

    private int port;

    private IRegistry registry = new ZookeeperRegistry();

    public NettyRpcServer(int port) {
        this.port = port;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        new NettyServer(rpcServiceMap).start(port);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> beansMap = applicationContext.getBeansWithAnnotation(RpcService.class);
        if(null != beansMap) {
            for (Object serviceBean : beansMap.values()) {
                RpcService rpcService = serviceBean.getClass().getAnnotation(RpcService.class);
                String version = rpcService.version();
                Class<?> interfaceClazz = serviceBean.getClass().getInterfaces()[0];
                String name = interfaceClazz.getName();
                if(!StringUtils.isEmpty(version)) {
                    name = name + ":" + version;
                }
                rpcServiceMap.put(name, serviceBean);
                // 将服务注册到zookeeper
                String serviceAddress = getLocalHost() + ":" + port;
                registry.registry(name, serviceAddress);
            }
        }
    }

    public String getLocalHost() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }
}
