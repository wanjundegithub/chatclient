package com.company.im.chat;

import com.company.im.chat.common.PacketType;
import com.company.im.chat.message.MessageRouter;
import com.company.im.chat.message.user.handle.UserLoginHandle;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestMsgRouter {

    private static final Logger logger= LoggerFactory.getLogger(TestMsgRouter.class);


    @Test
    public void testGetMessage(){
        var message=MessageRouter.Instance.createPacket(PacketType.ResFriendList);
        logger.info("message type is+"+message.getPacketID());
    }

    @Test
    public void testGetHandle(){
        var handle=(UserLoginHandle)MessageRouter.Instance.getHandle(PacketType.ResUserLogin);
        logger.info("handle type is :"+handle.toString());
    }
}
