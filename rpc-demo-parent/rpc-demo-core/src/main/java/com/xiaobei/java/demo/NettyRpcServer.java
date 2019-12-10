package com.xiaobei.java.demo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2019-12-09 21:27:27
 */
public class NettyRpcServer implements ApplicationContextAware, InitializingBean {

    private Map<String, Object> rpcServiceMap = new HashMap<>();

    private int port;

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
            }
        }
    }
}
