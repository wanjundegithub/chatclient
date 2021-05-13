package com.company.im.chat.message.friend.handle;

import com.company.im.chat.context.SpringContext;
import com.company.im.chat.message.MessageHandle;
import com.company.im.chat.message.friend.res.ResFriendListPacket;

/**
 * 好友列表消息处理
 */
public class FriendListHandle extends MessageHandle<ResFriendListPacket> {

    @Override
    public void action(ResFriendListPacket packet) {
        SpringContext.getFriendService().receiveFriendsList(packet);
    }
}
