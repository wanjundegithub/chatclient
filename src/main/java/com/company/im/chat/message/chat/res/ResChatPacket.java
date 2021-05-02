package com.company.im.chat.message.chat.res;

import com.company.im.chat.common.PacketType;
import com.company.im.chat.message.AbstractPacket;
import io.netty.buffer.ByteBuf;

/*
**用户聊天-回应
 */
public class ResChatPacket extends AbstractPacket {

    private String fromUserName;


    private String content;

    public ResChatPacket(String fromUserName, String toUserName, String content) {
        this.fromUserName = fromUserName;
        this.content = content;
    }

    public ResChatPacket() {
    }

    @Override
    public int getPacketID() {
        return PacketType.ResUserChat;
    }

    @Override
    public void writeBody(ByteBuf byteBuf) {
        //empty
    }

    @Override
    public void readBody(ByteBuf byteBuf) {
        fromUserName=readByteToString(byteBuf);
        content=readByteToString(byteBuf);
    }

    public String getFromUserName() {
        return fromUserName;
    }


    public String getContent() {
        return content;
    }
}
