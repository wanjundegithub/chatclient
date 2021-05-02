package com.company.im.chat.message;

/*
**消息处理
 */
@FunctionalInterface
public interface MessageHandle<T extends AbstractPacket> {

    void action(T packet);
}
