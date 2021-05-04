package com.company.im.chat.message.friend;

import com.company.im.chat.common.StateHelper;
import com.company.im.chat.message.ByteBufBean;
import com.company.im.chat.model.Friend;
import io.netty.buffer.ByteBuf;

/*
**好友实体类（增加在线状态）
 */
public class FriendItemBean extends ByteBufBean {

    private Friend friend;

    private byte isOnline;

    public FriendItemBean(Friend friend, byte isOnline) {
        this.friend=friend;
        this.isOnline = isOnline;
    }

    public FriendItemBean() {
    }

    public Friend getFriend() {
        return friend;
    }

    public byte getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(byte isOnline) {
        this.isOnline = isOnline;
    }

    public boolean isFriendOnline(){
        return isOnline== StateHelper.OnLine;
    }

    public String getFriendFullName(){
        return friend.getRemark()+"("+friend.getUserName()+")";
    }

    @Override
    public void writeBody(ByteBuf byteBuf) {
        writeStringToByte(byteBuf,friend.getUserName());
        writeStringToByte(byteBuf,friend.getRemark());
        writeStringToByte(byteBuf,friend.getSignature());
        byteBuf.writeInt(friend.getAge());
        writeStringToByte(byteBuf,friend.getSex());
        byteBuf.writeByte(isOnline);
    }

    @Override
    public void readBody(ByteBuf byteBuf) {
        var name=readByteToString(byteBuf);
        var remark=readByteToString(byteBuf);
        var signature=readByteToString(byteBuf);
        var age=byteBuf.readInt();
        var sex=readByteToString(byteBuf);
        friend=new Friend(name,remark,signature,age,sex);
        isOnline=byteBuf.readByte();
    }

    @Override
    public String toString() {
        return  friend.toString()
                +",isOnline:"+isOnline;
    }
}
