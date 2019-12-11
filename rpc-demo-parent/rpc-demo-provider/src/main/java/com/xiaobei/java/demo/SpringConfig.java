package com.xiaobei.java.demo;

import com.xiaobei.java.demo.service.NettyRpcServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2019-12-09 22:09:09
 */
@Configuration
@ComponentScan("com.xiaobei.java.demo")
public class SpringConfig {

    /*@Bean(name = "rpcServer")
    public RpcServer rpcServer() {
        return new RpcServer(8080);
    }*/

    @Bean(name = "nettyRpcServer")
    public NettyRpcServer nettyRpcServer() {
        return new NettyRpcServer(8080);
    }

}
