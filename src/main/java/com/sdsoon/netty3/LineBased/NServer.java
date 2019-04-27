package com.sdsoon.netty3.LineBased;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * 解决半包粘包:
 * LineBasedFrameDecoder:
 * (1)用LineBasedFrameDecoder 来解决需要在发送的数据结尾加上回车换行符，这样LineBasedFrameDecoder 才知道这段数据有没有读取完整。
 * (2)改造服务端代码，只需加上LineBasedFrameDecoder 解码器即可,构造函数的参数是数据包的最大长度。
 * (3)改造客户端发送代码，再数据后面加上回车换行符
 * <p>
 * Created By Chr on 2019/4/26.
 */
public class NServer {
    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {

//                        用LineBasedFrameDecoder 来解决需要在发送的数据结尾加上回车换行符，这样LineBasedFrameDecoder 才知道这段数据有没有读取完整。
//                        改造服务端代码，只需加上LineBasedFrameDecoder 解码器即可,构造函数的参数是数据包的最大长度。
                        ch.pipeline().addLast(new LineBasedFrameDecoder(10240));


                        ch.pipeline().addLast("decoder", new StringDecoder());
                        ch.pipeline().addLast("encoder", new StringEncoder());
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) {
                                System.err.println("server:" + msg.toString());
                                ctx.writeAndFlush(msg.toString() + "你好");
                            }
                        });
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        try {
            ChannelFuture f = bootstrap.bind(2222).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
