package com.xiaobei.java.demo;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2019-12-09 18:30:30
 */
public class Main {

    public static void main(String[] args) {
        IHelloService iHelloService = new HelloServiceImpl();
        RpcPublisher rpcPublisher = new RpcPublisher();
        rpcPublisher.publish(iHelloService, 8080);
    }
}