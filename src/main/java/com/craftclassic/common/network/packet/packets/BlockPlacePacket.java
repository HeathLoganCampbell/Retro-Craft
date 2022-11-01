package com.craftclassic.common.network.packet.packets;

import com.craftclassic.common.network.ConnectionManager;
import com.craftclassic.common.network.packet.Packet;
import com.craftclassic.common.network.player.PlayerConnection;
import io.netty.buffer.ByteBuf;

public class BlockPlacePacket extends Packet
{
    private int x, y, z;
    private int blockId;

    public BlockPlacePacket() {
    }

    public BlockPlacePacket(int x, int y, int z, int blockId)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.blockId = blockId;
    }

    @Override
    public void decode(ByteBuf buf)
    {
        this.blockId = buf.readInt();
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
    }

    @Override
    public void encode(ByteBuf buf)
    {
        buf.writeInt(this.blockId);
        buf.writeInt(this.x);
        buf.writeInt(this.y);
        buf.writeInt(this.z);
    }

    @Override
    public void process(PlayerConnection playerConnection)
    {
        ConnectionManager.INSTANCE.broadcastPacketBesidesSelf(playerConnection.getPlayer(), this);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public int getBlockId() {
        return blockId;
    }

    @Override
    public String toString() {
        return "BlockPlacePacket{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", blockId=" + blockId +
                '}';
    }
}
