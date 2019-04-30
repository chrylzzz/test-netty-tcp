package com.sdsoon.netty4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * Created By Chr on 2019/4/29.
 */
@Slf4j
public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    private final ByteBuf firstMsg;

    public TimeClientHandler() {

        byte[] req = "QUERY TIME ORDER".getBytes();
        firstMsg = Unpooled.buffer(req.length);
        firstMsg.writeBytes(req);
    }


    /**
     * @param ctx 发送
     * @param msg 接收
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] req = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(req);
        String body = new String(req, "UTF-8");
        System.err.println("Now is" + body);

    }

    /**
     * 客户端和服务器连接成功后 调用ctx发送给服务端
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(firstMsg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.warn("exception" + cause.getMessage());
        //发生异常,关闭:channelHandlerContext,释放资源
        ctx.close();
    }

}
