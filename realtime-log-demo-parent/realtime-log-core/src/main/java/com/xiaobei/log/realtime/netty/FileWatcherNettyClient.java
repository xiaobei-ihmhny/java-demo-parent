package com.xiaobei.log.realtime.netty;

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

import java.net.InetSocketAddress;

/**
 * TODO
 *
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-04-18 11:09:09
 */
public class FileWatcherNettyClient implements Runnable {

    private String host;

    private Integer port;

    private String filePath;

    private FileWatcherNettyClientHandler fileWatcherHandler;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public FileWatcherNettyClientHandler getFileWatcherHandler() {
        return fileWatcherHandler;
    }

    public void setFileWatcherHandler(FileWatcherNettyClientHandler fileWatcherHandler) {
        this.fileWatcherHandler = fileWatcherHandler;
    }

    public FileWatcherNettyClient(String host, Integer port, String filePath, FileWatcherNettyClientHandler fileWatcherHandler) {
        this.host = host;
        this.port = port;
        this.filePath = filePath;
        this.fileWatcherHandler = fileWatcherHandler;
    }

    /**
     * 启动netty本地客户端
     */
    public void start() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 创建 Bootstrap
            Bootstrap bootstrap = new Bootstrap();
            // 指定 EventLoopGroup 以处理客户端事件
            bootstrap.group(group)
                    // 指定使用NIO传输channel
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    // 设置客户端的 InetSocketAddress
                    .remoteAddress(new InetSocketAddress(host, port))
                    // 在创建Channel时，向ChannelInitializer中添加一个处理器
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new IMDecoder())
                                    .addLast(new IMEncoder())
                                    .addLast(fileWatcherHandler);
                        }
                    });
            // 连接远程节点，阻塞等待直到连接完成
            ChannelFuture future = bootstrap.connect().sync();
            // 阻塞直到 channel 关闭
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            // 出现异常...
        } finally {
            // 关闭线程池并释放资源
            group.shutdownGracefully();
        }
    }

    @Override
    public void run() {
        try {
            start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
