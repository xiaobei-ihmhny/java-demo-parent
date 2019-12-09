package com.xiaobei.java.demo;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2019-12-09 13:39:39
 */
@RpcService(version = "V2.0")
public class HelloServiceImpl2 implements IHelloService {

    @Override
    public String sayHello(String content) {
        System.out.println("request come in V2.0 " + HelloServiceImpl2.class);
        return "Hello " + content;
    }
}