package com.xiaobei.java.demo.thread.chain2;

import java.util.Objects;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-12-15 14:37:37
 */
@HandlerAnnotation(offset = 1)
public class FirstHandler extends Handler {

    private Handler handler;

    @Override
    public void setNextHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void handler(MyRequest request) {
        System.out.println("我是第一个责任链的第一个处理类,处理完成，流转到下一个处理类");
        if (Objects.isNull(handler)) {
            System.out.println("处理链已经到底了");
            System.out.println(request);
            return;
        }
        this.handler.handler(request);
    }
}