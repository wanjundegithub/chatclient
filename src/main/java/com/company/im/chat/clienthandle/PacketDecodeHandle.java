package com.company.im.chat.clienthandle;

import com.company.im.chat.message.MessageRouter;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
**消息解码处理
 */
public class PacketDecodeHandle extends LengthFieldBasedFrameDecoder {

    private static final Logger logger= LoggerFactory.getLogger(PacketDecodeHandle.class);

    public PacketDecodeHandle(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }

    /*
    **解码（将Bytebuffer解码为AbstractPacket）
     */
    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame=(ByteBuf) super.decode(ctx,in);
        if(frame==null){
            logger.error("receive message is null");
            return null;
        }
        int packetType=frame.readInt();
        logger.info("解码消息类型："+packetType);
        var packet= MessageRouter.Instance.createPacket(packetType);
        //读取bytebuffer来实例化packet
        packet.readBody(frame);
        logger.info(packet.toString());
        return packet;
    }
}
