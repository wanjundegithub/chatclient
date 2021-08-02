package com.company.im.chat.message.chat;

import com.company.im.chat.context.SpringContext;
import com.company.im.chat.message.AbstractPacket;
import com.company.im.chat.message.chat.req.ReqChatPacket;
import com.company.im.chat.message.chat.res.ResChatPacket;
import com.company.im.chat.session.SessionManager;
import com.company.im.chat.ui.StageController;
import com.company.im.chat.ui.UiBaseService;
import com.company.im.chat.ui.View;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ChatService  {

    private static final Logger logger= LoggerFactory.getLogger(ChatService.class);

    @PostConstruct
    public void init(){

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
        String toUserName=resChatPacket.getFromUserName();
        String content=resChatPacket.getContent();
        logger.info("接收者:{},接收消息:{}",toUserName,content);
        UiBaseService.INSTANCE.runTaskInFxThread(()-> {
            //界面处理
            StageController stageController = UiBaseService.INSTANCE.getStageController();
            Stage stage = stageController.getStageBy(View.id.ChatToFriend);
            VBox msgContainer = (VBox)stage.getScene().getRoot().lookup("#msgContainer");
            if(msgContainer==null){
                logger.info("接收者:"+toUserName+" 消息窗为空");
            }
            Pane pane = null;
            //发送者发送的消息在消息框左端显示
            if (toUserName.equals(SpringContext.getUserService().getUser().getUserName())) {
                pane = stageController.load(View.layout.PrivateChatItemLeft, Pane.class);
            }
            //接收者接收到的消息在消息框右端显示
            else {
                pane = stageController.load(View.layout.PrivateChatItemRight, Pane.class);
            }
            decorateChatRecord(content, pane);
            msgContainer.getChildren().add(pane);
        });
    }

    private void decorateChatRecord(String message, Pane chatRecord) {
        Hyperlink nickname = (Hyperlink) chatRecord.lookup("#nameUi");
        nickname.setText(message);
        nickname.setVisible(false);
        Label _createTime = (Label) chatRecord.lookup("#timeUi");
        _createTime.setText(new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss").format(new Date()));
        Label _body = (Label) chatRecord.lookup("#contentUi");
        _body.setText(message);
    }

}
