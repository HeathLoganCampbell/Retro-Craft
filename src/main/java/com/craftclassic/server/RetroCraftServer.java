package com.craftclassic.server;

import com.craftclassic.common.entities.Player;
import com.craftclassic.common.network.ConnectionManager;
import com.craftclassic.common.network.packet.Packet;
import com.craftclassic.common.network.pipeline.ServerNettyPipeline;
import com.craftclassic.common.network.player.PlayerConnection;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.Map;
import java.util.Queue;

public class RetroCraftServer implements Runnable
{
    public static final int PORT = 8256;

    public void start()
    {
        this.run();
    }

    @Override
    public void run()
    {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ServerNettyPipeline())
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.IP_TOS, 0x18);

            // Start the server.
            bootstrap
                    .bind(PORT)
                    .addListener((ChannelFutureListener) future -> {
                        final Channel channel = future.channel();
                        if (future.isSuccess()) {
                            System.out.println("Listening on " + channel.localAddress());
                        }
                    })
                    .sync();

            while(true)
            {
                // process in packets
                for (Map.Entry<PlayerConnection, Player> entry : ConnectionManager.INSTANCE.getConnectedPlayers().entrySet())
                {
                    PlayerConnection playerConnection = entry.getKey();
                    Queue<Packet> recievedPackets = playerConnection.getRecievedPackets();
                    Packet packet;
                    while((packet = recievedPackets.poll()) != null)
                    {
                        packet.process(playerConnection);
                    }
                }

                // Do stuff


                // send out packets
                for (Map.Entry<PlayerConnection, Player> entry : ConnectionManager.INSTANCE.getConnectedPlayers().entrySet())
                {
                    PlayerConnection playerConnection = entry.getKey();
                    playerConnection.flush();
                }

                Thread.sleep(50);
            }
            // Wait until the server socket is closed.
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            // Shut down all event loops to terminate all threads.
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
