package com.craftclassic.common.network.pipeline;

import com.craftclassic.common.network.PacketProcessor;
import com.craftclassic.common.network.packet.PacketRegistry;
import com.craftclassic.common.network.pipeline.coders.PacketDecoder;
import com.craftclassic.common.network.pipeline.coders.PacketEncoder;
import com.craftclassic.common.network.pipeline.encapsulation.Decapsulator;
import com.craftclassic.common.network.pipeline.encapsulation.Encapsulator;
import com.craftclassic.common.network.pipeline.handlers.ServerProcessingHandler;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class ServerNettyPipeline extends ChannelInitializer<SocketChannel>
{
    @Override
    protected void initChannel(SocketChannel channel) throws Exception
    {
        try {
            channel.config().setOption(ChannelOption.TCP_NODELAY, true);
        } catch (ChannelException channelexception) {
            ;
        }

        channel.pipeline()
                .addLast("timer", new ReadTimeoutHandler(30))

                .addLast("decapsulator", new Decapsulator())
                .addLast("encapsulator", new Encapsulator())

                .addLast("decoder", new PacketDecoder(PacketRegistry.INSTANCE))
                .addLast("encoder", new PacketEncoder())

                .addLast("handler", new ServerProcessingHandler(new PacketProcessor()));
    }
}
