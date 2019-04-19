package com.sdsoon.netty.ser;


import com.sdsoon.netty.bean.RpcRequest;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created By Chr on 2019/4/15.
 */
@ChannelHandler.Sharable
public class RpcServerHandler extends ChannelInboundHandlerAdapter {

    public RpcServerHandler() {
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        RpcRequest rpcRequest = (RpcRequest) msg;

        System.err.println(rpcRequest);


        ctx.write(" 服务端   已经  收到了消息 ~ ");

        //===========task的任务队列==============
        //用户自定义 定时任务发送
//        ctx.channel().eventLoop().schedule((Runnable) () -> {
//            System.out.println(" 当前时间 ：" + new Date());
//        }, 60, TimeUnit.SECONDS);


        //根据channel的引用 向用户推送数据
//        ctx.write(" 各种信息 ~", ChannelPromise());

        ctx.flush();
        ctx.close();
    }

}
