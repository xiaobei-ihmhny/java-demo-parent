package com.xiaobei.log.realtime.netty;

import com.xiaobei.log.realtime.FileWatchedListener;
import com.xiaobei.log.realtime.netty.protocol.IMMessage;
import com.xiaobei.log.realtime.netty.protocol.IMP;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;

/**
 * TODO
 *
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-04-18 11:11:11
 */
@Slf4j
public class FileWatcherNettyClientHandler
        extends SimpleChannelInboundHandler<ByteBuf>
        implements FileWatchedListener {

    private ChannelHandlerContext ctx;

    private String nickName;

    public FileWatcherNettyClientHandler(String nickName) {
        this.nickName = nickName;
    }

    /**
     * 当与服务器连接之后被调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
        IMMessage message = new IMMessage(IMP.LOGIN.getName(),"Console",System.currentTimeMillis(),this.nickName);
        log.info("成功连接服务器,已执行登录动作");
    }

    /**
     * 当从服务器接收到一条消息时被调用，每当接收到数据时，都会被调用，
     * 即该方法可能被调用多次，只是TCP面向流的协议特点，保证了数据的顺序。
     * @param channelHandlerContext
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf msg) throws Exception {
        System.out.println("收到服务端的消息：" + msg.toString(CharsetUtil.UTF_8));
    }

    /**
     * 在处理过程中发生异常时被调用
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //发生异常时记录信息，并关闭Channel。
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void onModify(String str) {
        // 收到日志消息，发送到服务端
//        str = str.replace("\r", "<div>");
        IMMessage message = new IMMessage(IMP.CHAT.getName(),System.currentTimeMillis(),nickName,str);
        System.out.println(str);
        ctx.channel().writeAndFlush(message);
    }

    @Override
    public void onModify(Path file) {
    }

    @Override
    public void onCreate(Path file) {

    }

    @Override
    public void onDelete(Path file) {

    }

    @Override
    public void onOverflowed(Path file) {

    }
}
