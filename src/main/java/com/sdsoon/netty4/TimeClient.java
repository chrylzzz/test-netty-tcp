package com.sdsoon.netty4;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Netty权威指南：基于ByteBuf发送消息
 * <p>
 * Created By Chr on 2019/4/29.
 */
public class TimeClient {


    public void connect(int port, String host) {

        EventLoopGroup group = new NioEventLoopGroup();
        try {

            Bootstrap bootstrap = new Bootstrap();
            // 是否启用心跳保活机机制
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(
                                    new TimeClientHandler()
                            );
                        }
                    });

            //连接服务 地址
            ChannelFuture future = bootstrap.connect(host, port).sync();
            if (future.isSuccess()) {
                System.err.println(" Netty服务端连接成功，等待发送数据: ==================");
            }
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(" Netty服务端连接失败:============= ");
        } finally {
            //关流，客户端不回消息了
            group.shutdownGracefully();
        }

    }

    public static void main(String args[]) {
        int port = 8080;
        new TimeClient().connect(port, "127.0.0.1");
    }
}
