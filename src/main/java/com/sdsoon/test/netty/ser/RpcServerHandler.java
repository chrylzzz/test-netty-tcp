package com.sdsoon.test.netty.ser;


import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created By Chr on 2019/4/15.
 */
@ChannelHandler.Sharable
public class RpcServerHandler extends ChannelInboundHandlerAdapter {

    public RpcServerHandler() {
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {


        ctx.write(" 服务端   已经  发送了消息 ~ ");

        ctx.flush();
        ctx.close();
    }
}
