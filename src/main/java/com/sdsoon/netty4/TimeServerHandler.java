package com.sdsoon.netty4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * Created By Chr on 2019/4/29.
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {


//        byteBuf类似于jdk的byteBuffer
        ByteBuf byteBuf = (ByteBuf) msg;
        //获取缓冲区可读的字节数,
        byte[] req = new byte[byteBuf.readableBytes()];
        //readBytes可以把缓冲区字节写到新的byte数组
        byteBuf.readBytes(req);
        String body = new String(req, "UTF-8");
        System.err.println(" body-byteBuf : " + body);

        String current = "QUERY TIME ORDER".equals(body) ? new Date(
                System.currentTimeMillis()).toString() : "Bad ORDER";
        ByteBuf resp = Unpooled.copiedBuffer(current.getBytes());
        //消息放入到缓冲区
        ctx.write(resp);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //发生异常,关闭:channelHandlerContext,释放资源
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //发送的消息写到SocketChannel
        ctx.flush();
    }
}
