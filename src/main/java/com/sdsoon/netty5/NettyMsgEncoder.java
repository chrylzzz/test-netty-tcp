package com.sdsoon.netty5;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;
import java.util.Map;

/**
 * 消息编码类
 * <p>
 * Created By Chr on 2019/4/29.
 */
public class NettyMsgEncoder extends MessageToMessageEncoder<NettyMessage> {

    MarshallingEncoder marshallingEncoder;

    public NettyMsgEncoder(MarshallingEncoder marshallingEncoder) {
        this.marshallingEncoder = new MarshallingEncoder();
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, NettyMessage msg, List<Object> out) throws Exception {

        if (msg == null || msg.getHandler() == null) {
            throw new Exception("the encode message is null");
        }

        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeInt(msg.getHandler().getCrcCode());
        byteBuf.writeInt(msg.getHandler().getLength());
        byteBuf.writeLong(msg.getHandler().getSessionID());
        byteBuf.writeByte(msg.getHandler().getType());
        byteBuf.writeByte(msg.getHandler().getPriority());
        byteBuf.writeInt(msg.getHandler().getAttachment().size());


        String key = null;
        byte[] keyArray = null;
        Object value = null;
        for (Map.Entry<String, Object> param : msg.getHandler().getAttachment().entrySet()) {
            key = param.getKey();
            keyArray = key.getBytes("UTF-8");
            byteBuf.writeInt(keyArray.length);
            byteBuf.writeBytes(keyArray);
            value = param.getValue();

            marshallingEncoder.encode(value, byteBuf);
        }
        key = null;
        keyArray = null;
        value = null;
        if (msg.getBody() != null) {

            marshallingEncoder.encode(msg.getBody(), byteBuf);
        } else {
            byteBuf.writeInt(0);
            byteBuf.setInt(4, byteBuf.readableBytes());
        }


    }
}
