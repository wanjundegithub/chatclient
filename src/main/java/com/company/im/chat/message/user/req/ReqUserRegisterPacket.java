package com.company.im.chat.message.user.req;

import com.company.im.chat.common.PacketType;
import com.company.im.chat.message.AbstractPacket;
import com.company.im.chat.model.User;
import io.netty.buffer.ByteBuf;

/*
**用户注册消息请求（客户端）
 */
public class ReqUserRegisterPacket extends AbstractPacket {

    private String userName;

    private String password;

    private String sex;

    private int age;

    private String signature;

    public ReqUserRegisterPacket(User user){
        this.userName = user.getUserName();
        this.password = user.getPassword();
        this.sex = user.getSex();
        this.age = user.getAge();
        this.signature = user.getSignature();
    }


    public ReqUserRegisterPacket(){

    }

    @Override
    public int getPacketID() {
        return PacketType.ReqUserRegister;
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
        //empty
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
}
