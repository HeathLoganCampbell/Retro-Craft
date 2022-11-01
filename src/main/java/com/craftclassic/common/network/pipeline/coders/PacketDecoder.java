package com.craftclassic.common.network.pipeline.coders;

import com.craftclassic.common.network.packet.Packet;
import com.craftclassic.common.network.packet.PacketRegistry;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder
{
    public static final boolean DEBUG = true;
    private static final RuntimeException DECODE_FAILED =
            new RuntimeException("A packet did not decode successfully (invalid data).");

    private PacketRegistry packetRegistry;

    public PacketDecoder(PacketRegistry packetRegistry)
    {
        this.packetRegistry = packetRegistry;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
        Packet packet = tryDecode(ctx, byteBuf);
        if(packet != null)
        {
            out.add(packet);
        }
    }

    private Packet tryDecode(ChannelHandlerContext ctx, ByteBuf buf) throws Exception
    {
        if (!ctx.channel().isActive() || !buf.isReadable())
        {
            return null;
        }

        int packetId = buf.readInt();
        Packet packet = this.packetRegistry.createPacket(packetId);

        try {
            packet.decode(buf);
        } catch (Exception e) {
            throw handleDecodeFailure(e, packet, packetId);
        }

        if (buf.isReadable()) {
            throw handleOverflow(buf, packet, buf.readerIndex(), buf.writerIndex());
        }

        return packet;
    }

    private Exception handleOverflow(ByteBuf buf, Packet packet, int expected, int actual) {
        if (DEBUG) {
            return new CorruptedFrameException("Packet sent for " + packet.getClass() + " was too "
                    + "big (expected " + expected + " bytes, got " + actual + " bytes) ");
        } else {
            return DECODE_FAILED;
        }
    }

    private Exception handleUnderflow(Packet packet, int expected, int actual) {
        if (DEBUG) {
            return new CorruptedFrameException("Packet sent for " + packet.getClass() + " was too "
                    + "small (expected " + expected + " bytes, got " + actual + " bytes)");
        } else {
            return DECODE_FAILED;
        }
    }

    private Exception handleDecodeFailure(Exception cause, Packet packet, int packetId) {
        if (DEBUG) {
            return new CorruptedFrameException(
                    "Error decoding " + packet.getClass() + " " + packetId, cause);
        } else {
            return DECODE_FAILED;
        }
    }
}