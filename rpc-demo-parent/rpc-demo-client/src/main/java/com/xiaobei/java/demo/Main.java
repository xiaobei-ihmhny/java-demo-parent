package com.xiaobei.java.demo;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2019-12-09 13:42:42
 */
public class Main {

    public static void main(String[] args) {
        IHelloService iHelloService =
                RpcProxy.proxy(IHelloService.class, "127.0.0.1", 8080);
        String result = iHelloService.sayHello("test");
        System.out.println(result);
    }
}