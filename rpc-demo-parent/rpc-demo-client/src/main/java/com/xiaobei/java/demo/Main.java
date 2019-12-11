package com.xiaobei.java.demo;

import com.xiaobei.java.demo.client.RpcProxy;

import java.io.IOException;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2019-12-09 13:42:42
 */
public class Main {

    public static void main(String[] args) throws IOException {
        IHelloService iHelloService =
                RpcProxy.proxy(IHelloService.class);
        String result = iHelloService.sayHello("test");
        System.out.println(result);
    }
}