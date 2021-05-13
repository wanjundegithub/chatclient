package com.company.im.chat.message.chat.handle;

import com.company.im.chat.context.SpringContext;
import com.company.im.chat.message.MessageHandle;
import com.company.im.chat.message.chat.res.ResChatPacket;

/**
 * 聊天回复消息处理
 */
public class ChatHandle extends MessageHandle<ResChatPacket> {

    @Override
    public void action(ResChatPacket packet) {
        SpringContext.getChatService().respondChatMessage(packet);
    }
}
