package com.sdsoon.netty5;

import io.netty.buffer.ByteBuf;

/**
 * 消息编码工具类
 *
 * Created By Chr on 2019/4/29.
 */
public class MarshallingEncoder {

    private static final byte[] LENGTH_PLACEHOLDER = new byte[4];

    Marshaller marshaller;


    public MarshallingEncoder() {
        marshaller = MarshallingCodeFactory.buildMarshalling();
    }

    protected void encode(Object msg, ByteBuf out) {
        try {
            int lengthPos = out.writerIndex();
            out.writeBytes(LENGTH_PLACEHOLDER);
            ChannelBufferByteOutput output = new ChannelBufferByteOutput(out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }

    }
}
