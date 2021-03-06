package com.xiaobei.java.demo;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2019-12-09 13:39:39
 */
@RpcService(version = "V1.0")
public class HelloServiceImpl implements IHelloService {

    @Override
    public String sayHello(String content) {
        System.out.println("request come in V1.0 " + HelloServiceImpl.class);
        return "Hello " + content;
    }
}