package com.xiaobei.java.demo;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2019-12-10 07:34:34
 */
public class NettyPublisherChannelHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyPublisherChannelHandler.class);

    private Map<String, Object> rpcServiceMap;

    public NettyPublisherChannelHandler(Map<String, Object> rpcServiceMap) {
        this.rpcServiceMap = rpcServiceMap;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest rpcRequest) throws Exception {
        LOGGER.info("收到远程调用信息..{}", rpcRequest.toString());
        Object result = PublishProcessorHandlerUtils.invoke(rpcServiceMap, rpcRequest);
        LOGGER.info("调用结果为：{}", result);
        // TODO 待研究 2019年12月10日8:8:31
        ctx.writeAndFlush(result).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.info("调用过程发生异常.. {}", cause.fillInStackTrace());
        ctx.close();
    }
}