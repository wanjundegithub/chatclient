package com.company.im.chat.ui.controller;

import com.company.im.chat.context.SpringContext;
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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginViewController implements ControlledStage, Initializable {

	@FXML
	private Button login;
	@FXML
	private TextField userNameTextField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private CheckBox rememberPswCheckBox;
	@FXML
	private CheckBox autoLoginCheckBox;
	@FXML
	private ImageView closeBtn;
	@FXML
	private ImageView minBtn;
	@FXML
	private ProgressBar loginProgress;
	@FXML
	private Pane errorPane;
	@FXML
	private Label errorTips;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//验证规则：　userId非空且为数字　password非空
		login.disableProperty().bind(
			Bindings.createBooleanBinding(
				() -> userNameTextField.getText().length() == 0  ||
					  passwordField.getText().length() == 0,
					userNameTextField.textProperty(),
					passwordField.textProperty()));
	}

	@FXML
	private void login() {
		String userName = passwordField.getText();
		String password = passwordField.getText();
		if (!SessionManager.Instance.isConnectServer()) {
			errorPane.setVisible(true);
			errorTips.setText("login.failToConnect");
			return;
		}
		loginProgress.setVisible(true);
		login.setVisible(false);
		SpringContext.getUserService().requestLogin(userName,password);
	}

	@FXML
	private void login_Entered() {
		login.setStyle("-fx-background-radius:4;-fx-background-color: #097299");
	}

	@FXML
	private void login_Exited() {
		login.setStyle("-fx-background-radius:4;-fx-background-color: #09A3DC");
	}

	@FXML
	private void close() {
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
	private void min() {
		Stage stage = getStage();
		if (stage != null) {
			stage.setIconified(true);
		}
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

	@FXML
	private void backToLogin() {
		loginProgress.setVisible(false);
		errorPane.setVisible(false);
		login.setVisible(true);
	}


	@FXML
	private void gotoRegister() {
		StageController stageController = UiBaseService.INSTANCE.getStageController();
		stageController.switchStage(View.id.RegisterView, View.id.LoginView);
	}


	@Override
	public Stage getStage() {
		StageController stageController = UiBaseService.INSTANCE.getStageController();
		return stageController.getStageBy(View.id.LoginView);
	}
}
