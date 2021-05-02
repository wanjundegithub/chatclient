package com.company.im.chat.message.friend.res;

import com.company.im.chat.message.AbstractPacket;
import io.netty.buffer.ByteBuf;

/*
**好友下线回应（来自服务器端）
 */
public class ResFriendLogoutPacket extends AbstractPacket {

    private String friendName;

    public ResFriendLogoutPacket(String friendName) {
        this.friendName = friendName;
    }

    public ResFriendLogoutPacket() {
    }

    @Override
    public int getPacketID() {
        return 0;
    }

    @Override
    public void writeBody(ByteBuf byteBuf) {
        //empty
    }

    @Override
    public void readBody(ByteBuf byteBuf) {
        friendName=readByteToString(byteBuf);
    }

    public String getFriendName() {
        return friendName;
    }
}
