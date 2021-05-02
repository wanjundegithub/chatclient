package com.company.im.chat.message.chat;

import com.company.im.chat.common.PacketType;
import com.company.im.chat.message.AbstractPacket;
import com.company.im.chat.message.MessageRouter;
import com.company.im.chat.message.chat.req.ReqChatPacket;
import com.company.im.chat.message.chat.res.ResChatPacket;
import com.company.im.chat.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class ChatService {

    private static final Logger logger= LoggerFactory.getLogger(ChatService.class);

    @PostConstruct
    public void init(){
        MessageRouter.Instance.registerHandle(PacketType.ResUserChat,this::respondChatMessage);
    }

    /*
    **发送消息（客户端发起）
     */
    public void requestChatMessage(String toUserName,String content){
        AbstractPacket packet=new ReqChatPacket(toUserName,content);
        logger.info("launch session to:"+toUserName);
        SessionManager.Instance.sendPacket(packet);
    }
    
    /*
    **回复消息（服务器端应答）
     */
    public void respondChatMessage(AbstractPacket packet){
        if(packet==null) {
            logger.error("ChatMessage from server is null");
            return;
        }
        ResChatPacket resChatPacket=(ResChatPacket) packet;
        String fromUserName=resChatPacket.getFromUserName();
        String content=resChatPacket.getContent();
        //界面处理
    }

}
