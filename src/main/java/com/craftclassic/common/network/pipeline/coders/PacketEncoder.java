package com.craftclassic.common.network.pipeline.coders;

import com.craftclassic.common.network.packet.Packet;
import com.craftclassic.common.network.packet.PacketRegistry;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<Packet>
{
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf byteBuf) throws Exception
    {
        byteBuf.writeInt(PacketRegistry.INSTANCE.getPacketId(packet));
        packet.encode(byteBuf);
    }
}
