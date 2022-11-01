package com.craftclassic.common.network.pipeline.encapsulation;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class Decapsulator extends ByteToMessageDecoder
{
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception
    {
        if (!in.isReadable()) return;
        int origReaderIndex = in.readerIndex();

        for (int i = 0; i <= 2; i++)
        {
            if (!in.isReadable())
            {
                in.readerIndex(origReaderIndex);
                return;
            }

            byte read = in.readByte();
            if (read >= 0)
            {
                in.readerIndex(origReaderIndex);
                int length = in.readInt();
                if (length == 0)
                    return;

                if (in.readableBytes() < length)
                {
                    in.readerIndex(origReaderIndex);
                    return;
                }

                out.add(in.readRetainedSlice(length));
                return;
            }
        }
    }
}