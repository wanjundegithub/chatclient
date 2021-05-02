package com.company.im.chat.message.user.req;

import com.company.im.chat.common.PacketType;
import com.company.im.chat.message.AbstractPacket;
import io.netty.buffer.ByteBuf;

/*
**用户登出请求（客户端）
 */
public class ReqUserLogoutPacket extends AbstractPacket {

    private String userName;

    private String message;

    public ReqUserLogoutPacket(String userName, String message) {
        this.userName = userName;
        this.message = message;
    }

    public ReqUserLogoutPacket() {
    }

    @Override
    public int getPacketID() {
        return PacketType.ReqUserLogout;
    }

    @Override
    public void writeBody(ByteBuf byteBuf) {
        writeStringToByte(byteBuf,userName);
        writeStringToByte(byteBuf,message);
    }

    @Override
    public void readBody(ByteBuf byteBuf) {
        //empty
    }

    public String getUserName() {
        return userName;
    }

    public String getMessage() {
        return message;
    }
}
