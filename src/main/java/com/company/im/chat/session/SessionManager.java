package com.company.im.chat.session;


import com.company.im.chat.message.AbstractPacket;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
**Session管理
 */
public enum SessionManager {

    Instance;

    private static final Logger logger= LoggerFactory.getLogger(SessionManager.class);

    private IOSession session;

    /*
    **客户端注册会话（单例）
     */
    public void registerSession(Channel channel){
        this.session=new IOSession(channel);
    }

    /*
    **发送消息包
     */
    public void sendPacket(AbstractPacket packet){
        if(session==null){
            logger.error("session is null");
            return;
        }
        if(packet==null){
            logger.error("message is null");
            return;
        }
        session.sendPacket(packet);
    }

}
