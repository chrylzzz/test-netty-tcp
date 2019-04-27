package com.sdsoon.netty2.kryo.code;

import com.sdsoon.netty2.bean.Message;
import com.sdsoon.netty2.kryo.KryoSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * netty编码器KryoEncoder对数据进行Kryo序列化
 *
 * Created By Chr on 2019/4/25/0025.
 */
public class KryoEncoder extends MessageToByteEncoder<Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message message, ByteBuf out) throws Exception {
        KryoSerializer.serialize(message, out);
        ctx.flush();
    }
}
