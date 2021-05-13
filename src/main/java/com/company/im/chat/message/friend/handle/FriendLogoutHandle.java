package com.company.im.chat.message.friend.handle;

import com.company.im.chat.context.SpringContext;
import com.company.im.chat.message.MessageHandle;
import com.company.im.chat.message.friend.res.ResFriendLogoutPacket;

/**
 * 好友下线消息处理
 */
public class FriendLogoutHandle extends MessageHandle<ResFriendLogoutPacket> {

    @Override
    public void action(ResFriendLogoutPacket packet) {
        SpringContext.getFriendService().RespondFriendLogout(packet);
    }
}
