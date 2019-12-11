package com.xiaobei.java.demo.client;

import com.xiaobei.java.demo.RpcRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * TODO 这种写法总感觉有点不伦不类的，待调整 xiaobei-ihmhny 2019年12月10日17:0:11
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2019-12-09 18:34:34
 */
public class ProxyInvocationHandler extends SimpleChannelInboundHandler<Object> implements InvocationHandler {

    private static Object result;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest request = new RpcRequest();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameters(args);
        request.setVersion("V1.0");
        new NettyClient(request).start();
        return result;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        result = msg;
    }
}