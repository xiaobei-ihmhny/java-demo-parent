package com.xiaobei.java.demo;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2019-12-09 21:27:27
 */
public class RpcServer implements ApplicationContextAware, InitializingBean {

    private Map<String, Object> rpcServiceMap = new HashMap<>();


    ThreadFactory threadFactory = new ThreadFactoryBuilder()
            .setNameFormat("rpc-demo-pool-%d")
            .build();

    ExecutorService executorService =
            new ThreadPoolExecutor(5,
                    100,
                    60L,
                    TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>(1024),
                    threadFactory,
                    new ThreadPoolExecutor.AbortPolicy());

    private int port;

    public RpcServer(int port) {
        this.port = port;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while(true) {
                Socket socket = serverSocket.accept();
                executorService.submit(new PublisherProcessorHandler(rpcServiceMap, socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

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
