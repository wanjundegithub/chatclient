package com.company.im.chat.message.user;

import com.company.im.chat.common.PacketType;
import com.company.im.chat.common.StateHelper;
import com.company.im.chat.message.AbstractPacket;
import com.company.im.chat.message.MessageRouter;
import com.company.im.chat.message.user.req.ReqUserLoginPacket;
import com.company.im.chat.message.user.req.ReqUserRegisterPacket;
import com.company.im.chat.message.user.res.ResUserInfoPacket;
import com.company.im.chat.message.user.res.ResUserLoginPacket;
import com.company.im.chat.message.user.res.ResUserRegisterPacket;
import com.company.im.chat.model.User;
import com.company.im.chat.session.SessionManager;
import com.company.im.chat.ui.StageController;
import com.company.im.chat.ui.UiBaseService;
import com.company.im.chat.ui.View;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


@Service
public class UserService  {

    private static  final Logger logger= LoggerFactory.getLogger(UserService.class);

    private User user=new User();


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
        String userName=resRegisterPacket.getUserName();
        byte registerResult=resRegisterPacket.getResult();
        StageController stageController = UiBaseService.INSTANCE.getStageController();
        Stage stage = stageController.getStageBy(View.id.RegisterView);
        Label errorTips = (Label) stage.getScene().getRoot().lookup("#errorTipLabel");
        if(registerResult== StateHelper.Action_Success){
            UiBaseService.INSTANCE.runTaskInFxThread(() -> {
                errorTips.setVisible(true);
                errorTips.setText(userName+"register success");
                logger.info("register success");
                goToLoginPanel(userName);
            });
        }
        else {
            errorTips.setVisible(true);
            errorTips.setText("register failure");
        }
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
            logger.error("login message from server is null");
            return;
        }
        var resUserLoginPacket=(ResUserLoginPacket)packet;
        byte result=resUserLoginPacket.getResult();
        String message=resUserLoginPacket.getMessage();
        logger.info("login result is:"+result+" login message is "+message);
        //处理
        if (result==StateHelper.Action_Success) {
            UiBaseService.INSTANCE.runTaskInFxThread(() -> {
                redirectToMainPanel();
            });
        } else {
            UiBaseService.INSTANCE.runTaskInFxThread(() -> {
                StageController stageController = UiBaseService.INSTANCE.getStageController();
                Stage stage = stageController.getStageBy(View.id.LoginView);
                Pane errPane = (Pane) stage.getScene().getRoot().lookup("#errorPane");
                errPane.setVisible(true);
                Label errTips = (Label) stage.getScene().getRoot().lookup("#errorTips");
                errTips.setText("login.operateFailed");
            });
        }
    }

    /*
    **获取服务器返回的用户信息
     */
    public void respondUserInfo(AbstractPacket packet){
        if(packet==null){
            logger.error("login user info from server is null");
            return;
        }
        ResUserInfoPacket testPacket=(ResUserInfoPacket) packet;
        logger.info("响应的用户名为:"+testPacket.getUserName()+"签名为:"+testPacket.getSignature());
        UiBaseService.INSTANCE.runTaskInFxThread(()-> {
            ResUserInfoPacket resUserInfoPacket=(ResUserInfoPacket) packet;
            this.user=resUserInfoPacket.createUser();
        });
    }

    /*
    **注册成功转去登录页面
     */
    private void goToLoginPanel(String userName){
        StageController stageController = UiBaseService.INSTANCE.getStageController();
        stageController.switchStage(View.id.LoginView, View.id.RegisterView);
        Stage stage = stageController.getStageBy(View.id.LoginView);
        TextField userNameField = (TextField) stage.getScene().getRoot().lookup("#userNameTextField");
        userNameField.setText(userName);
    }

    /*
    **登录成功转去主界面
     */
    private void redirectToMainPanel() {
        StageController stageController = UiBaseService.INSTANCE.getStageController();
        Stage stage = stageController.getStageBy(View.id.MainView);
        Label userNameLabel=(Label) stage.getScene().getRoot().lookup("usernameLabel");
        Label signatureLabel=(Label) stage.getScene().getRoot().lookup("signatureLabel");
        userNameLabel.setText(user.getUserName());
        signatureLabel.setText(user.getSignature());
        stageController.switchStage(View.id.MainView, View.id.LoginView);

    }
}
