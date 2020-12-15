package com.xiaobei.java.demo.thread.chain2;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-12-15 14:39:39
 */

import org.junit.Test;

import java.io.IOException;

/**
 * 测试责任链模式
 * 当你想让一个及以上的对象有机会能够处理某个请求的时候，就是用此模式
 */
public class TestChain {

    @Test
    public void firstWay() throws IOException {
        FirstHandler firstHandler = new FirstHandler();
        SecondHandler secondHandler = new SecondHandler();
        ThirdHandler thirdHandler = new ThirdHandler();
        firstHandler.setNextHandler(secondHandler);
        secondHandler.setNextHandler(thirdHandler);
        MyRequest request = new MyRequest();
        request.setName("tt");
        request.setAge(12);
        firstHandler.handler(request);
    }

    @Test
    public void secondWay() {
        MyRequest request = new MyRequest();
        request.setName("bibi");
        request.setAge(12);
        HandlerChain handlerChain =
                new HandlerChain("com.xiaobei.java.demo.thread.chain2");
        handlerChain.handler(request);
    }
}