package com.sdsoon.netty4;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * Netty权威指南：基于ByteBuf发送消息
 * <p>
 * Created By Chr on 2019/4/11/0011.
 */

public class TimeServer {


    private final static String serviceAddress = "127.0.0.1:700";


    public void bind(int port) {

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        //启动一个监听 Netty ServerSocket(ip,port)     Socket 监听端口，io交互
        try {

            //启动netty服务
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.option(ChannelOption.SO_BACKLOG, 128);//连接数
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);//有数据立即发送
            bootstrap.childHandler(new ChildHandler());

            ChannelFuture future = bootstrap.bind(port).sync();

            System.err.println(" Netty服务端启动成功，等待客户端的连接: ");

            //等待服务端链路关闭,main才退出
            future.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    private class ChildHandler extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new TimeServerHandler());
            ch.pipeline().addLast(new FileServerHandler());
            ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
            ch.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
            ch.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
        }
    }

    public static void main(String args[]) {
        int port = 8080;

        new TimeServer().bind(port);
    }

}
