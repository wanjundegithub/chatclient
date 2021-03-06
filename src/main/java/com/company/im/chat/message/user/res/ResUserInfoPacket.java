package com.company.im.chat.message.user.res;

import com.company.im.chat.common.PacketType;
import com.company.im.chat.message.AbstractPacket;
import io.netty.buffer.ByteBuf;

/**
 * 回应用户信息(服务器发送)
 */
public class ResUserInfoPacket extends AbstractPacket {

    private String userName;

    private String password;

    private String sex;

    private int age;

    private String signature;

    @Override
    public int getPacketID() {
        return PacketType.ResUserInfo;
    }

    @Override
    public void writeBody(ByteBuf byteBuf) {
       writeStringToByte(byteBuf,userName);
       writeStringToByte(byteBuf,password);
       writeStringToByte(byteBuf,sex);
       byteBuf.writeInt(age);
       writeStringToByte(byteBuf,signature);
    }

    @Override
    public void readBody(ByteBuf byteBuf) {
        userName=readByteToString(byteBuf);
        password=readByteToString(byteBuf);
        sex=readByteToString(byteBuf);
        age=byteBuf.readInt();
        signature=readByteToString(byteBuf);
    }


    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getSex() {
        return sex;
    }

    public int getAge() {
        return age;
    }

    public String getSignature() {
        return signature;
    }

    @Override
    public String toString() {
        return "userName:"+userName
                +",password:"+password
                +",sex:"+sex
                +",age:"+age
                +",signature:"+signature;
    }
}
