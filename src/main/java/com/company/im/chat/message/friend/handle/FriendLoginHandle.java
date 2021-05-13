package com.company.im.chat.message.friend.handle;

import com.company.im.chat.context.SpringContext;
import com.company.im.chat.message.MessageHandle;
import com.company.im.chat.message.friend.res.ResFriendLoginPacket;

/**
 * 好友登录消息处理
 */
public class FriendLoginHandle extends MessageHandle<ResFriendLoginPacket> {

    @Override
    public void action(ResFriendLoginPacket packet) {
        SpringContext.getFriendService().RespondFriendLogin(packet);
    }
}
