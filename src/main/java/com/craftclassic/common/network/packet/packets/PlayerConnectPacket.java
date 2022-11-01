package com.craftclassic.common.network.packet.packets;

import com.craftclassic.common.network.ConnectionManager;
import com.craftclassic.common.network.packet.Packet;
import com.craftclassic.common.network.player.PlayerConnection;
import io.netty.buffer.ByteBuf;

import java.nio.charset.Charset;

public class PlayerConnectPacket extends Packet
{
    private String username;

    public PlayerConnectPacket(String username)
    {
        this.username = username;
    }

    public PlayerConnectPacket() {}

    @Override
    public void decode(ByteBuf buf)
    {
        int length = buf.readInt();
        this.username = buf.readCharSequence(length, Charset.forName("utf-8")).toString();
    }

    @Override
    public void encode(ByteBuf buf)
    {
        buf.writeInt(this.username.length());
        buf.writeCharSequence(this.username, Charset.forName("utf-8"));
    }

    @Override
    public void process(PlayerConnection playerConnection)
    {
        ConnectionManager.INSTANCE.startPlayer(playerConnection, username);
    }

    public String getUsername() {
        return username;
    }
}