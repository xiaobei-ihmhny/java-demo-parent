package com.xiaobei.java.demo.client;

import com.xiaobei.java.demo.RpcRequest;
import com.xiaobei.java.demo.discovery.IDiscovery;
import com.xiaobei.java.demo.discovery.ZookeeperDiscovery;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.springframework.util.StringUtils;

import java.net.InetSocketAddress;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2019-12-10 08:39:39
 */
public class NettyClient {

    private RpcRequest rpcRequest;

    private IDiscovery discovery = new ZookeeperDiscovery();

    public NettyClient(RpcRequest rpcRequest) {
        this.rpcRequest = rpcRequest;
    }

    public void start() {
        String[] serviceAddress = getServiceAddress();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(
                            serviceAddress[0],
                            Integer.parseInt(serviceAddress[1])))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                            pipeline.addLast(new ObjectEncoder());
                            pipeline.addLast(new ProxyInvocationHandler());
                        }
                    });
            ChannelFuture future = bootstrap.connect().sync();
            // 将请求信息发送到服务端
            future.channel().writeAndFlush(rpcRequest).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                group.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String[] getServiceAddress() {
        String serviceName = rpcRequest.getClassName();
        String version = rpcRequest.getVersion();
        if(!StringUtils.isEmpty(version)) {
            serviceName = serviceName + ":" + version;
        }
        String service = discovery.discovery(serviceName);
        if(StringUtils.isEmpty(service)) {
            throw new RuntimeException("服务不存在" + serviceName);
        }
        return service.split(":");
    }
}