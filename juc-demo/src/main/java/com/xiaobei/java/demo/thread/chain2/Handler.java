package com.xiaobei.java.demo.thread.chain2;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-12-15 14:34:34
 */
public abstract class Handler {

    public abstract void setNextHandler(Handler handler);

    public abstract void handler(MyRequest request);
}