package com.company.im.chat.message.user.handle;

import com.company.im.chat.context.SpringContext;
import com.company.im.chat.message.MessageHandle;
import com.company.im.chat.message.user.res.ResUserLoginPacket;

/**
 * 用户登录消息处理
 */
public class UserLoginHandle extends MessageHandle<ResUserLoginPacket> {

    @Override
    public void action(ResUserLoginPacket packet) {
        SpringContext.getUserService().respondLogin(packet);
    }

}
