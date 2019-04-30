package com.sdsoon.netty4;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.FileRegion;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * Created By Chr on 2019/4/29.
 */
public class FileServerHandler extends SimpleChannelInboundHandler<String> {

    private static final String cr = System.getProperty("line.separator");


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        File file = new File(msg);
        if (file.exists()) {
            if (!file.isFile()) {
                ctx.writeAndFlush(" NOt a file  :" + file + cr);
                return;
            }

            ctx.write(file + "" + file.length() + cr);

            RandomAccessFile randomAccessFile = new RandomAccessFile(msg, "r");

            FileRegion region = new DefaultFileRegion(
                    randomAccessFile.getChannel(), 0, randomAccessFile.length()
            );
            ctx.write(region);
            ctx.writeAndFlush(cr);
            randomAccessFile.close();
        } else {
            ctx.writeAndFlush(" File not found :" + file + cr);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.getStackTrace();

        ctx.close();
    }
}
