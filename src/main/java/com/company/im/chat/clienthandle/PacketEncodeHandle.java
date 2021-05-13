package com.company.im.chat.clienthandle;

import com.company.im.chat.message.AbstractPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
**消息编码
 */
public class PacketEncodeHandle extends MessageToByteEncoder<AbstractPacket> {

    private static final Logger logger= LoggerFactory.getLogger(PacketEncodeHandle.class);

    /*
    **将消息AbstractPacket编码为bytebuf
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, AbstractPacket packet,
                          ByteBuf byteBuf) throws Exception {
        int packetType=packet.getPacketID();
        try {
            byteBuf.writeInt(packetType);
            packet.writeBody(byteBuf);
        }catch (Exception e){
            logger.error(e.getMessage());
        }

    }
}
