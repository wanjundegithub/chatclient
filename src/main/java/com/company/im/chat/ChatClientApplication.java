package com.company.im.chat;

import com.company.im.chat.client.ChatClient;
import com.company.im.chat.client.ClientNode;
import com.company.im.chat.ui.StageController;
import com.company.im.chat.ui.UiBaseService;
import com.company.im.chat.ui.View;
import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ChatClientApplication extends Application {

    public static void main(String[] args) {
        SpringApplication.run(ChatClientApplication.class, args);
        launch();
    }

    @Override
    public void start(final Stage stage) throws Exception {
        //与服务端建立连接
        connectToServer();
        StageController stageController = UiBaseService.INSTANCE.getStageController();
        stageController.setPrimaryStage("root", stage);

        Stage loginStage = stageController.loadStage(View.id.LoginView, View.layout.LoginView,
                StageStyle.UNDECORATED);
        loginStage.setTitle("QQ");

        stageController.loadStage(View.id.RegisterView, View.layout.RegisterView, StageStyle.UNDECORATED);
        Stage mainStage = stageController.loadStage(View.id.MainView, View.layout.MainView, StageStyle.UNDECORATED);

        //把主界面放在右上方
        Screen screen = Screen.getPrimary();
        double rightTopX = screen.getVisualBounds().getWidth()*0.75;
        double rightTopY = screen.getVisualBounds().getHeight()*0.05;
        mainStage.setX(rightTopX);
        mainStage.setY(rightTopY);
        stageController.loadStage(View.id.ChatToFriend, View.layout.ChatToFriend, StageStyle.UTILITY);


        //显示MainView舞台
        stageController.setStage(View.id.LoginView);
    }

    private void connectToServer() {
        new Thread() {
            public void run() {
                ClientNode client=new ChatClient();
                client.init();
                client.start();
            };
        }.start();
    }
}
