package com.company.im.chat;

import com.company.im.chat.common.PacketType;
import com.company.im.chat.message.MessageRouter;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestMsgRouter {

    private static final Logger logger= LoggerFactory.getLogger(TestMsgRouter.class);

    @Test
    public void testGetPacket(){
        var message= MessageRouter.Instance.createPacket(PacketType.ResUserLogin);
        logger.info(String.valueOf(message.getPacketID()));
    }
}
