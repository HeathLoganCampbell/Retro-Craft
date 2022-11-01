package com.craftclassic.common.network.packet;

import com.craftclassic.common.network.player.PlayerConnection;
import io.netty.buffer.ByteBuf;

public abstract class Packet
{
    public abstract void decode(ByteBuf buf);

    public abstract void encode(ByteBuf buf);

    public abstract void process(PlayerConnection playerConnection);
}
