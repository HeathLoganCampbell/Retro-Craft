package com.craftclassic.common.network.packet;

import com.craftclassic.common.network.packet.packets.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class PacketRegistry
{
    public static PacketRegistry INSTANCE = new PacketRegistry();

    final Map<Integer, Supplier<? extends Packet>> packetIdToSupplier = new HashMap<Integer,  Supplier<? extends Packet>>();
    final Map<Class<? extends Packet>, Integer> packetClassToId = new HashMap<>();

    public PacketRegistry()
    {
        map(1, PlayerConnectPacket.class, PlayerConnectPacket::new);
        map(2, PlayerDisconnectPacket.class, PlayerDisconnectPacket::new);
        map(3, EntityPositionPacket.class, EntityPositionPacket::new);
        map(4, EntitySpawnPacket.class, EntitySpawnPacket::new);
        map(5, BlockBreakPacket.class, BlockBreakPacket::new);
        map(6, BlockPlacePacket.class, BlockPlacePacket::new);
    }

    private void map(int id, Class<? extends Packet> packetClazz, Supplier<? extends Packet> packetSupplier)
    {
        this.packetIdToSupplier.put(id, packetSupplier);
        this.packetClassToId.put(packetClazz, id);
    }

    public Packet createPacket(int packetId)
    {
        final Supplier<? extends Packet> supplier = this.packetIdToSupplier.get(packetId);

        if (supplier == null)
        {
            return null;
        }

        return supplier.get();
    }

    public int getPacketId(final Packet packet) {
        final int id = this.packetClassToId.get(packet.getClass());
        if (id == Integer.MIN_VALUE)
        {
            throw new IllegalArgumentException(String.format(
                    "Unable to find id for packet of type %s",
                    packet.getClass().getName()
            ));
        }

        return id;
    }
}
