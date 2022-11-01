package com.craftclassic.common.network.packet.packets;

import com.craftclassic.common.network.packet.Packet;
import com.craftclassic.common.network.player.PlayerConnection;
import com.craftclassic.common.utils.Location;
import io.netty.buffer.ByteBuf;

public class EntitySpawnPacket extends Packet
{
    private int entityId;
    private double x, y, z;

    public EntitySpawnPacket() {
    }

    public EntitySpawnPacket(int entityId, Location location)
    {
        this(entityId, location.getX(), location.getY(), location.getZ());
    }

    public EntitySpawnPacket(int entityId, double x, double y, double z)
    {
        this.entityId = entityId;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void decode(ByteBuf buf)
    {
        this.entityId = buf.readInt();
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
    }

    @Override
    public void encode(ByteBuf buf)
    {
        buf.writeInt(this.entityId);
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.z);
    }

    @Override
    public void process(PlayerConnection playerConnection)
    {

    }

    public int getEntityId() {
        return entityId;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    @Override
    public String toString() {
        return "EntitySpawnPacket{" +
                "entityId=" + entityId +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
