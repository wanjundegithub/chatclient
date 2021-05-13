package com.company.im.chat.message.user.res;

import com.company.im.chat.common.PacketType;
import com.company.im.chat.message.AbstractPacket;
import io.netty.buffer.ByteBuf;

/*
**用户登录消息回应（来自服务器端）
 */
public class ResUserLoginPacket extends AbstractPacket {

    private byte result;

    private String message;

    public ResUserLoginPacket() {
    }

    public ResUserLoginPacket(byte result, String message) {
        this.result = result;
        this.message = message;
    }

    @Override
    public int getPacketID() {
        return PacketType.ResUserLogin;
    }

    @Override
    public void writeBody(ByteBuf byteBuf) {
        byteBuf.writeByte(result);
        writeStringToByte(byteBuf,message);
    }

    @Override
    public void readBody(ByteBuf byteBuf) {
        result=byteBuf.readByte();
        message=readByteToString(byteBuf);
    }

    public byte getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }
}
