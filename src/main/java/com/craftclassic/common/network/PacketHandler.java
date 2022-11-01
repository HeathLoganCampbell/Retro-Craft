package com.craftclassic.common.network;

import com.craftclassic.common.network.packet.packets.*;

public interface PacketHandler
{
    void onPacket(PlayerConnectPacket packet);

    void onPacket(PlayerDisconnectPacket packet);

    void onPacket(EntityPositionPacket packet);

    void onPacket(EntitySpawnPacket packet);

    void onPacket(BlockBreakPacket packet);

    void onPacket(BlockPlacePacket packet);
}
