package com.company.im.chat.message.friend.res;

import com.company.im.chat.common.PacketType;
import com.company.im.chat.message.AbstractPacket;
import io.netty.buffer.ByteBuf;

/*
**好友上线回应（服务器端）
 */
public class ResFriendLoginPacket extends AbstractPacket {

    private String friendName;

    public ResFriendLoginPacket(String friendName) {
        this.friendName = friendName;
    }

    public ResFriendLoginPacket() {
    }

    @Override
    public int getPacketID() {
        return PacketType.ResFriendLogin;
    }

    @Override
    public void writeBody(ByteBuf byteBuf) {
        writeStringToByte(byteBuf,friendName);
    }

    @Override
    public void readBody(ByteBuf byteBuf) {
        friendName=readByteToString(byteBuf);
    }

    public String getFriendName() {
        return friendName;
    }
}
