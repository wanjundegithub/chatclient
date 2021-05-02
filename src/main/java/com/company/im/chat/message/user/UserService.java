package com.company.im.chat.message.user;

import com.company.im.chat.common.PacketType;
import com.company.im.chat.message.AbstractPacket;
import com.company.im.chat.message.MessageRouter;
import com.company.im.chat.message.user.req.ReqUserLoginPacket;
import com.company.im.chat.message.user.req.ReqUserRegisterPacket;
import com.company.im.chat.message.user.res.ResUserLoginPacket;
import com.company.im.chat.message.user.res.ResUserRegisterPacket;
import com.company.im.chat.model.User;
import com.company.im.chat.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


@Service
public class UserService {

    private static  final Logger logger= LoggerFactory.getLogger(UserService.class);

    private User user;

    @PostConstruct
    public void init(){
        MessageRouter.Instance.registerHandle(PacketType.ResUserRegister,this::respondRegister);
        MessageRouter.Instance.registerHandle(PacketType.ResUserLogin,this::respondLogin);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /*
    **请求注册（客户端发起）
     */
    public void requestRegister(User user){
        AbstractPacket packet=new ReqUserRegisterPacket(user);
        logger.info(user.getUserName()+" request register ");
        SessionManager.Instance.sendPacket(packet);
    }

    /*
    **注册应答（来自服务器端）
     */
    public void respondRegister(AbstractPacket packet){
        if(packet==null){
            logger.error("message from server is null");
            return;
        }
        var resRegisterPacket=(ResUserRegisterPacket)packet;
        byte registerResult=resRegisterPacket.getResult();
        String message=resRegisterPacket.getMessage();
        //处理
    }

    /*
    **请求登录（客户端发起）
     */
    public void requestLogin(String userName,String password){
        AbstractPacket packet=new ReqUserLoginPacket(userName,password);
        logger.info(userName+" request login");
        SessionManager.Instance.sendPacket(packet);
    }

    /*
    **登录回应（来自服务器端）
     */
    public void respondLogin(AbstractPacket packet){
        if(packet==null){
            logger.error("message from server is null");
            return;
        }
        var resUserLoginPacket=(ResUserLoginPacket)packet;
        byte result=resUserLoginPacket.getResult();
        String message=resUserLoginPacket.getMessage();
        //处理
    }
}
