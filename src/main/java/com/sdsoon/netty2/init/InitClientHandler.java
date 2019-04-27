package com.sdsoon.netty2.init;

import com.sdsoon.netty2.config.TcpConfig;
import com.sdsoon.netty2.handler.HeartbeatServerHandler;
import com.sdsoon.netty2.handler.client.ClientBusinessHandler;
import com.sdsoon.netty2.handler.client.ClientConnectHandler;
import com.sdsoon.netty2.kryo.code.KryoDecoder;
import com.sdsoon.netty2.kryo.code.KryoEncoder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * ClientHandler
 * <p>
 * Created By Chr on 2019/4/25.
 */
public class InitClientHandler extends ChannelInitializer<Channel> {

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        /**
         * 编码解码
         */
        //pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
        //pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
        //pipeline.addLast("encoder", new ObjectEncoder());
        ////禁用缓存，有可能导致使用缓存出错
        //pipeline.addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));

        //kryo编码和解码，提高性能
        pipeline.addLast("decoder", new KryoDecoder());
        pipeline.addLast("encoder", new KryoEncoder());

        /**
         * 超时心跳机制:具有读超时,写超时,读写超时
         * 断连重连机制:断开连接之后5s重新心跳连接
         */
        pipeline.addLast(new IdleStateHandler(TcpConfig.READ_IDEL_TIME_OUT, TcpConfig.WRITE_IDEL_TIME_OUT, TcpConfig.ALL_IDEL_TIME_OUT, TimeUnit.SECONDS)); // 1
        //心跳机制:用来处理超时时，发送心跳
        pipeline.addLast("ping", new HeartbeatServerHandler()); // 2
        //连接机制
        pipeline.addLast("conn", new ClientConnectHandler());

        /**
         * 业务处理机制
         */
        pipeline.addLast(new ClientBusinessHandler());


    }
}
