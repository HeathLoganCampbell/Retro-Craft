package com.craftclassic.common.network.player;

import com.craftclassic.common.entities.Player;
import com.craftclassic.common.network.packet.Packet;
import io.netty.channel.Channel;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PlayerConnection
{
    private Channel channel;

    private final Queue<Packet> recievedPackets = new ConcurrentLinkedQueue();

    private Player player;

    public PlayerConnection(Channel channel) {
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void addPacketToQueue(Packet packet)
    {
        recievedPackets.add(packet);
    }

    public Queue<Packet> getRecievedPackets()
    {
        return recievedPackets;
    }

    public void sendPacket(Packet packet)
    {
        if(!this.channel.isOpen())
        {
            return;
        }

        this.channel.write(packet).addListener(future -> {
            if (!future.isSuccess()) {
                System.out.println("Data failed to write:");
                future.cause().printStackTrace();
            }
        });
    }

    public void flush()
    {
        this.channel.flush();
    }
}
