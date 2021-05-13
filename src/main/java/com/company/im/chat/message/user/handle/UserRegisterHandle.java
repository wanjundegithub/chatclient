package com.company.im.chat.message.user.handle;

import com.company.im.chat.context.SpringContext;
import com.company.im.chat.message.MessageHandle;
import com.company.im.chat.message.user.res.ResUserRegisterPacket;

/**
 * 用户注册消息处理
 */
public class UserRegisterHandle extends MessageHandle<ResUserRegisterPacket> {

    @Override
    public void action(ResUserRegisterPacket packet) {
        SpringContext.getUserService().respondRegister(packet);
    }
}
