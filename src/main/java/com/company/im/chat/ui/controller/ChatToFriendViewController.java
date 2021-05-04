package com.company.im.chat.ui.controller;

import com.company.im.chat.context.SpringContext;
import com.company.im.chat.ui.ControlledStage;
import com.company.im.chat.ui.StageController;
import com.company.im.chat.ui.UiBaseService;
import com.company.im.chat.ui.View;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;


/*
**聊天界面
 */
public class ChatToFriendViewController implements ControlledStage {

    @FXML
    private Label friendNameLabel;

    @FXML
    private TextArea msgInput;

    @FXML
    public void sendMessage(){
        String toUserName= friendNameLabel.getText();
        String content= msgInput.getText();
        if(content==null||content.equals("")){
            return;
        }
        SpringContext.getChatService().requestChatMessage(toUserName,content);
    }

    @FXML
    public void close(){
        UiBaseService.INSTANCE.getStageController().closeStage(View.id.ChatToFriend);
    }

    @Override
    public Stage getStage() {
        StageController stageController= UiBaseService.INSTANCE.getStageController();
        return stageController.getStageBy(View.id.ChatToFriend);
    }
}
