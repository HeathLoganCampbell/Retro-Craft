package com.craftclassic.common.network.pipeline.handlers;

import com.craftclassic.common.network.PacketProcessor;
import com.craftclassic.common.network.packet.Packet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerProcessingHandler extends SimpleChannelInboundHandler<Packet> {

    public PacketProcessor packetProcessor;

    public ServerProcessingHandler(PacketProcessor packetProcessor) {
        this.packetProcessor = packetProcessor;
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        System.out.println("Client joined - " + ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if(!ctx.channel().isActive())
        {
            return;
        }

        cause.printStackTrace();
        System.out.println("Closing connection for client - " + ctx);
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Packet packet)
    {
        try
        {
            packetProcessor.process(channelHandlerContext, packet);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}