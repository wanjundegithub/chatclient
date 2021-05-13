package com.company.im.chat.message.user.handle;

import com.company.im.chat.context.SpringContext;
import com.company.im.chat.message.MessageHandle;
import com.company.im.chat.message.user.res.ResUserInfoPacket;

/**
 * 用户登录用户信息消息处理
 */
public class UserInfoHandle extends MessageHandle<ResUserInfoPacket> {

    @Override
    public void action(ResUserInfoPacket packet) {
        SpringContext.getUserService().respondUserInfo(packet);
    }
}
