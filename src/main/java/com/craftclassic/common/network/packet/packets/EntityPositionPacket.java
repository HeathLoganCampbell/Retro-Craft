package com.craftclassic.common.network.packet.packets;

import com.craftclassic.common.entities.Player;
import com.craftclassic.common.network.ConnectionManager;
import com.craftclassic.common.network.packet.Packet;
import com.craftclassic.common.network.player.PlayerConnection;
import io.netty.buffer.ByteBuf;

public class EntityPositionPacket extends Packet
{
    private int entityId;
    private float yaw, pitch;
    private double x, y, z;
    private double vecX, vecY, vecZ;

    public EntityPositionPacket() {
    }

    public EntityPositionPacket(int entityId, double x, double y, double z) {
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
        Player player = playerConnection.getPlayer();
        if(player == null)
        {
            return;
        }

        this.entityId = player.getEntityId();

        ConnectionManager.INSTANCE.broadcastPacketBesidesSelf(player, this);
    }

    public int getEntityId() {
        return entityId;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
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

    public double getVecX() {
        return vecX;
    }

    public double getVecY() {
        return vecY;
    }

    public double getVecZ() {
        return vecZ;
    }

    @Override
    public String toString() {
        return "EntityPositionPacket{" +
                "entityId=" + entityId +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
