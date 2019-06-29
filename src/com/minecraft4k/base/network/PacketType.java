package com.minecraft4k.base.network;

public enum PacketType 
{
	INIT_PACKET(1),
	JOIN_PACKET(2),
	WORLD_PACKET(3),
	KEEP_ALIVE_PACKET(4),
	MOVEMENT_PACKET(5),
	UPDATE_BLOCK_PACKET(6),
	UPDATE_WORLD_PACKET(7),
	SPAWN_ENTITY_PACKET(8),
	DESTORY_ENTITY_PACKET(9),
	MESSAGE_PACKET(10),
	DISCONNECT_PACKET(11),
	;
	
	PacketType(int id) {}
}
