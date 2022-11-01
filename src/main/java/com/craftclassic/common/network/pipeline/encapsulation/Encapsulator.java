package com.craftclassic.common.network.pipeline.encapsulation;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class Encapsulator extends MessageToMessageEncoder<ByteBuf>
{
    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception
    {
        ByteBuf lengthBuf = ctx.alloc().buffer(5);
        lengthBuf.writeInt(in.readableBytes());

        out.add(lengthBuf);
        out.add(in.retain());
    }
}