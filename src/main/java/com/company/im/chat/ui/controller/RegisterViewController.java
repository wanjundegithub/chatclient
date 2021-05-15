package com.company.im.chat.ui.controller;

import com.company.im.chat.common.StateHelper;
import com.company.im.chat.context.SpringContext;
import com.company.im.chat.model.User;
import com.company.im.chat.session.SessionManager;
import com.company.im.chat.ui.ControlledStage;
import com.company.im.chat.ui.StageController;
import com.company.im.chat.ui.UiBaseService;
import com.company.im.chat.ui.View;
import com.company.im.chat.ui.container.ImageContainer;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Function;

/*
**注册界面
 */
public class RegisterViewController implements ControlledStage , Initializable {

    @FXML
    private TextField userNameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField ageTextField;

    @FXML
    private TextField signatureTextField;

    @FXML
    private Label errorTipLabel;

    @FXML
    private Button registerButton;

    @FXML
    private ToggleGroup sexGroup;

    @FXML
    private ImageView closeBtn;

    @FXML
    private ImageView minBtn;

    @FXML
    public void register(){
        if(!SessionManager.Instance.isConnectServer()){
            errorTipLabel.setVisible(true);
            errorTipLabel.setText("can not connect server");
            return;
        }
        String sexItem=sexGroup.getSelectedToggle().getUserData().toString();
        String sex=sexItem.equals("0")?StateHelper.Male:StateHelper.Female;
        int age=Integer.parseInt(ageTextField.getText());
        User user=new User();
        user.setUserName(userNameTextField.getText());
        user.setPassword(passwordField.getText());
        user.setSex(sex);
        user.setAge(age);
        user.setSignature(signatureTextField.getText());
        SpringContext.getUserService().requestRegister(user);
    }

    @FXML
    public void register_entered() {
        registerButton.setStyle("-fx-background-radius:4;-fx-background-color: #097299");

    }

    @FXML
    public void register_exit() {
        registerButton.setStyle("-fx-background-radius:4;-fx-background-color: #09A3DC");
    }

    @FXML
    public void close(){
        System.exit(1);
    }


    @FXML
    private void closeEntered() {
        Image image = ImageContainer.getClose_1();
        closeBtn.setImage(image);
    }

    @FXML
    private void closeExited() {
        Image image = ImageContainer.getClose();
        closeBtn.setImage(image);
    }

    @FXML
    public void goToLogin(){
        clearContent();
        StageController stageController=UiBaseService.INSTANCE.getStageController();
        stageController.switchStage(View.id.LoginView, View.id.RegisterView);
    }

    @FXML
    public void min(){

    }

    @FXML
    private void minEntered() {
        Image image = ImageContainer.getMin_1();
        minBtn.setImage(image);
    }

    @FXML
    private void minExited() {
        Image image = ImageContainer.getMin();
        minBtn.setImage(image);
    }

    @Override
    public Stage getStage() {
        StageController stageController= UiBaseService.INSTANCE.getStageController();
        return stageController.getStageBy(View.id.RegisterView);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //初始化性别可选
        for (int i=0;i<this.sexGroup.getToggles().size();i++) {
            Toggle sexToggle = this.sexGroup.getToggles().get(i);
            sexToggle.setUserData(String.valueOf(i));
        }
        //验证规则：　userName非空　password非空
        registerButton.disableProperty().bind(
                Bindings.createBooleanBinding(
                        () -> !checkRegisterInfo(),
                        userNameTextField.textProperty(),
                        passwordField.textProperty(),
                        ageTextField.textProperty(),
                        signatureTextField.textProperty()));
    }

    /*
    **清理注册信息
     */
    private void clearContent(){
        userNameTextField.setText("");
        passwordField.setText("");
        errorTipLabel.setText("");
        errorTipLabel.setVisible(false);
    }

    /*
    **检查注册信息
     */
    private boolean checkRegisterInfo(){
        String userName=userNameTextField.getText();
        if(!checkInput(s->{
            if(s.isEmpty()||s.isBlank()){
                return "userName is empty";
            }
            return "";
        },userName)){
            return false;
        }
        String password=passwordField.getText();
        if(!checkInput(s->{
            if(s.isEmpty()||s.isBlank()){
                return "password is empty";
            }
            return "";
        },password)){
            return false;
        }
        String sex=sexGroup.getSelectedToggle().getUserData().toString();
        if(!checkInput(s->{
            if(s.isEmpty()||s.isBlank()){
                return "not choose sex";
            }
            return "";
        },sex)){
            return false;
        }
        String ageText=ageTextField.getText();
        if(!checkInput(s->{
          try {
              int age=Integer.parseInt(s);
              if(age<0){
                  return "low than 0 is not allowed";
              }
          }catch (NumberFormatException e){
              return e.getMessage();
          }
          return "";
        },ageText)){
            return false;
        }
        String signature=signatureTextField.getText();
        if(!checkInput(s->{
            if(s.isEmpty()||s.isBlank()){
                return "not choose sex";
            }
            return "";
        },signature)){
            return false;
        }
        return true;
    }

    private <T> boolean checkInput(Function<T,String> function,T input){
        var message=function.apply(input);
        if(!message.equals("")){
            errorTipLabel.setText(message);
            return false;
        }
        return true;
    }


}
