package com.company.im.chat.message;

/*
**消息处理
 */
public abstract class MessageHandle<T extends AbstractPacket> {

    public abstract void action(T packet);
}
