package com.craftclassic.common.network.pipeline.handlers;

import com.craftclassic.client.Minecraft;
import com.craftclassic.common.blocks.Block;
import com.craftclassic.common.entities.Entity;
import com.craftclassic.common.network.packet.Packet;
import com.craftclassic.common.network.packet.packets.*;
import com.craftclassic.common.utils.Location;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientProcessingHandler extends SimpleChannelInboundHandler<Packet>
{
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
    {
        if(!ctx.channel().isActive())
        {
            return;
        }

        cause.printStackTrace();
        System.out.println("Closing connection for client - " + ctx);
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx)
    {
        PlayerConnectPacket playerConnectPacket = new PlayerConnectPacket("Cookie Monster");
        ctx.writeAndFlush(playerConnectPacket);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Packet packet)
    {
        if(packet instanceof EntitySpawnPacket)
        {
            EntitySpawnPacket entitySpawnPacket = (EntitySpawnPacket) packet;

            boolean isSelf = entitySpawnPacket.getEntityId() == 0;
            if(isSelf)
            {
                return;
            }

            Entity bunny = new Entity("Bunny", entitySpawnPacket.getEntityId());
            bunny.setLocation(new Location(Minecraft.INSTANCE.world,
                    (float) entitySpawnPacket.getZ(),
                    (float) entitySpawnPacket.getY(),
                    (float) entitySpawnPacket.getZ()));
            Minecraft.INSTANCE.world.addEntity(bunny);
        }

        if(packet instanceof EntityPositionPacket)
        {
            EntityPositionPacket entityPositionPacket = (EntityPositionPacket) packet;
            for (Entity entity : Minecraft.INSTANCE.world.getEntities())
            {
                if (entity.getEntityId() == entityPositionPacket.getEntityId())
                {
                    Location location = entity.getLocation();
                    location.setX((float) entityPositionPacket.getX());
                    location.setY((float) entityPositionPacket.getY());
                    location.setZ((float) entityPositionPacket.getZ());
                    break;
                }
            }
        }

        if(packet instanceof BlockPlacePacket)
        {
            BlockPlacePacket blockPlacePacket = (BlockPlacePacket) packet;

            Block block = Block.getBlockById(blockPlacePacket.getBlockId());
            Minecraft.INSTANCE.world.setBlock(blockPlacePacket.getX(), blockPlacePacket.getY(), blockPlacePacket.getZ(), block);
        }

        if(packet instanceof BlockBreakPacket)
        {
            BlockBreakPacket blockBreakPacket = (BlockBreakPacket) packet;

            Minecraft.INSTANCE.world.setBlock(blockBreakPacket.getX(), blockBreakPacket.getY(), blockBreakPacket.getZ(), Block.AIR);
        }
    }
}
