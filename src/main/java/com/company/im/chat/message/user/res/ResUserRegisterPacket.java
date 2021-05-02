package com.company.im.chat.message.user.res;

import com.company.im.chat.common.PacketType;
import com.company.im.chat.message.AbstractPacket;
import io.netty.buffer.ByteBuf;

/*
**用户注册消息回应（来自服务器端）
 */
public class ResUserRegisterPacket extends AbstractPacket {

    private byte result;

    private String message;

    public ResUserRegisterPacket(byte result, String message) {
        this.result = result;
        this.message = message;
    }

    public ResUserRegisterPacket() {
    }

    @Override
    public int getPacketID() {
        return PacketType.ResUserRegister;
    }

    @Override
    public void writeBody(ByteBuf byteBuf) {
        //empty
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
