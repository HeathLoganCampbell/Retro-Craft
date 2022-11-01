package com.craftclassic.common.network;

import com.craftclassic.common.network.packet.Packet;
import com.craftclassic.common.network.packet.packets.PlayerConnectPacket;
import com.craftclassic.common.network.player.PlayerConnection;
import io.netty.channel.ChannelHandlerContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PacketProcessor
{
    private final Map<ChannelHandlerContext, PlayerConnection> connectionPlayerConnectionMap = new ConcurrentHashMap<>();

    public void process(ChannelHandlerContext context, Packet packet)
    {
        PlayerConnection playerConnection = connectionPlayerConnectionMap.get(context);
        if(playerConnection == null)
        {
            if(packet instanceof PlayerConnectPacket)
            {
                // Login
                this.createPlayerConnection(context);
                playerConnection = connectionPlayerConnectionMap.get(context);

                packet.process(playerConnection);
                return;
            }
            else
            {
                context.close();
                return;
            }
        }

        playerConnection.addPacketToQueue(packet);
    }

    public void createPlayerConnection(ChannelHandlerContext context) {
        final PlayerConnection playerConnection = new PlayerConnection(context.channel());
        connectionPlayerConnectionMap.put(context, playerConnection);
    }

    public PlayerConnection removePlayerConnection(ChannelHandlerContext context) {
        PlayerConnection playerConnection = connectionPlayerConnectionMap.remove(context);
        if(playerConnection != null)
        {
            ConnectionManager.INSTANCE.removePlayerConnection(playerConnection);
        }

        return playerConnection;
    }

    public PlayerConnection getPlayerConnection(ChannelHandlerContext context) {
        return connectionPlayerConnectionMap.get(context);
    }
}
