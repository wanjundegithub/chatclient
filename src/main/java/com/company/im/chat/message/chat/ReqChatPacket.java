package com.company.im.chat.message.chat;

import com.company.im.chat.message.AbstractPacket;
import io.netty.buffer.ByteBuf;

public class ReqChatPacket extends AbstractPacket {
    @Override
    public int getPacketID() {
        return 0;
    }

    @Override
    public void writeBody(ByteBuf byteBuf) {

    }

    @Override
    public void readBody(ByteBuf byteBuf) {

    }
}
