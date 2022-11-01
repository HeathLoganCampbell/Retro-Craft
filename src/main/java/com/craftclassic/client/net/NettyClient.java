package com.craftclassic.client.net;

import com.craftclassic.common.network.packet.Packet;
import com.craftclassic.common.network.pipeline.ClientNettyPipeline;
import com.craftclassic.server.RetroCraftServer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public final class NettyClient {
    static final String HOST = "127.0.0.1";

    public Channel serverChannel;

    public NettyClient()
    {
    }

    public void run()
    {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();

        b.group(group) // Set EventLoopGroup to handle all eventsf for client.
                .channel(NioSocketChannel.class)// Use NIO to accept new connections.
                .handler(new ClientNettyPipeline());

        // Start the client.
        try {
            ChannelFuture f = b.connect(HOST, RetroCraftServer.PORT).sync();
            this.serverChannel = f.channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Channel getServerChannel()
    {
        return serverChannel;
    }

    public void sendPacket(Packet packet)
    {
        this.getServerChannel().writeAndFlush(packet);
    }
}

