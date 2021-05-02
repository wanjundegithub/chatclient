package com.company.im.chat.message.friend;

import com.company.im.chat.common.PacketType;
import com.company.im.chat.message.AbstractPacket;
import com.company.im.chat.message.MessageRouter;
import com.company.im.chat.message.friend.res.ResFriendLoginPacket;
import com.company.im.chat.message.friend.res.ResFriendLogoutPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class FriendService {

    private static final Logger logger= LoggerFactory.getLogger(FriendService.class);

    @PostConstruct
    public void init(){
        MessageRouter.Instance.registerHandle(PacketType.ResFriendLogin,this::RespondFriendLogin);
        MessageRouter.Instance.registerHandle(PacketType.ReqFriendLogout,this::RespondFriendLogout);
    }

    /*
    **好友登录应答（来自服务器端）
     */
    public void RespondFriendLogin(AbstractPacket packet){
        if(packet==null){
            logger.error("Friend Login message from server is null");
            return;
        }
        var resFriendLoginPacket=(ResFriendLoginPacket) packet;
        String friendName=resFriendLoginPacket.getFriendName();
        //界面处理
    }

    /*
    **好友登出应答（来自服务器端）
     */
    public void RespondFriendLogout(AbstractPacket packet){
        if(packet==null){
            logger.error("Friend Logout message from server is null");
            return;
        }
        var resFriendLogoutPacket=(ResFriendLogoutPacket)packet;
        String friendName= resFriendLogoutPacket.getFriendName();
        //界面处理
    }
}
