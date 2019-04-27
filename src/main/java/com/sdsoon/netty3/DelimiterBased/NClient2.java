package com.sdsoon.netty3.DelimiterBased;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * 解决半包粘包
 * 改造客户端发送代码，再数据后面加上下划线
 * channel.writeAndFlush(msg + "_");
 * <p>
 * Created By Chr on 2019/4/26.
 */
public class NClient2 {
    public static void main(String[] args) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Channel channel = null;
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast("decoder", new StringDecoder());
                    ch.pipeline().addLast("encoder", new StringEncoder());
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) {
                            System.err.println("client:" + msg.toString());
                        }
                    });
                }
            });
            ChannelFuture f = b.connect("127.0.0.1", 2222).sync();
            channel = f.channel();
            //模拟半包:首先启动服务端，然后再启动客户端，通过控制台可以看到服务接收的数据分成了2次,这就是我们要解决的问题。
            StringBuilder msg = new StringBuilder();
            for (int i = 0; i < 100; i++) {
                msg.append("hello yinjihuan");
            }
            //channel.writeAndFlush(msg);


//            改造客户端发送代码，再数据后面加上下划线
            channel.writeAndFlush(msg + "_");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
