package com.xiaobei.log.realtime.netty.client;

import com.xiaobei.log.realtime.FileWatcher;
import com.xiaobei.log.realtime.netty.FileWatcherNettyClient;
import com.xiaobei.log.realtime.netty.FileWatcherNettyClientHandler;
import com.xiaobei.log.realtime.netty.client.handler.ChatClientHandler;
import com.xiaobei.log.realtime.netty.protocol.IMDecoder;
import com.xiaobei.log.realtime.netty.protocol.IMEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;


/**
 * 客户端
 * @author Tom
 *
 */
public class ChatClient  {
    
    private ChatClientHandler clientHandler;
    private String host;
    private int port;
    
    public ChatClient(String nickName){
        this.clientHandler = new ChatClientHandler(nickName);
    }
    
    public void connect(String host,int port){
    		this.host = host;
    		this.port = port;

        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new IMDecoder());
                    ch.pipeline().addLast(new IMEncoder());
                    ch.pipeline().addLast(clientHandler);
                }
            });
            ChannelFuture f = b.connect(this.host, this.port).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
    
    
    public static void main(String[] args) throws IOException{
        try {
            CountDownLatch latch = new CountDownLatch(1);
            String filePath = "D:\\logs\\business.log";
            // 创建netty-client
            FileWatcherNettyClientHandler listener = new FileWatcherNettyClientHandler("xiaobei");
            FileWatcherNettyClient client = new FileWatcherNettyClient("127.0.0.1", 8080, filePath, listener);
            FileWatcher fileWatchedService = new FileWatcher(filePath, listener);
            // 启动netty服务
            new Thread(client).start();
            new Thread(fileWatchedService).start();
//            fileWatchedService.watchFileModify();
            latch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
