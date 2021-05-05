package com.company.im.chat.ui.controller;

import com.company.im.chat.context.SpringContext;
import com.company.im.chat.model.User;
import com.company.im.chat.ui.ControlledStage;
import com.company.im.chat.ui.StageController;
import com.company.im.chat.ui.UiBaseService;
import com.company.im.chat.ui.View;
import com.company.im.chat.ui.container.ImageContainer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.net.URL;
import java.util.ResourceBundle;

/*
**主界面
 */
public class MainViewController implements ControlledStage, Initializable{

	@FXML
	private ImageView close;
	@FXML
	private ImageView min;
	@FXML
	private ImageView shineImage;
	@FXML
	private Accordion friends;
	@FXML
	private ScrollPane friendSp;
	@FXML
	private Label username;
	@FXML
	private Label signature;

	private static  final Logger logger= LoggerFactory.getLogger(MainViewController.class);

	private User userModel ;

    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	if(userModel==null){
    		userModel=new User();
		}
		username.textProperty().bind(userModel.getUserNameProperty());
		signature.textProperty().bind(userModel.getPasswordProperty());
	}

	@FXML
	private void headEx() {
		shineImage.setVisible(false);
	}

	@FXML
	private void headEn() {
		shineImage.setVisible(true);
	}

	@FXML
	private void close() {
		System.exit(1);
	}

	@FXML
	private void closeEntered() {
		Image image = ImageContainer.getClose_1();
		close.setImage(image);
	}

	@FXML
	private void closeExited() {
		Image image = ImageContainer.getClose();
		close.setImage(image);
	}

	@FXML
	private void min() {
		getStage().setIconified(true);
	}

	@FXML
	private void minEntered() {
		Image image = ImageContainer.getMin_1();
		min.setImage(image);
	}

	@FXML
	private void minExited() {
		Image image = ImageContainer.getMin();
		min.setImage(image);
	}

	@FXML
	private void bind() {
		friendSp.setFitToWidth(false);
		friends.expandedPaneProperty().addListener(new ChangeListener<TitledPane>() {
			@Override
			public void changed(ObservableValue<? extends TitledPane> arg0, TitledPane arg1, TitledPane arg2) {
				if (arg2 != null) {
					logger.info("-------11111111--------");
				}
				if (arg1 != null) {
					logger.info("-------2222222222---------");
				}
			}
		});
	}

	@FXML
	private void username_entered() {
		username.setStyle("-fx-background-radius:4;-fx-background-color: #136f9b");
	}

	@FXML
	private void username_exited() {
		username.setStyle("");
	}

	@FXML
	private void autograph_entered() {
		signature.setStyle("-fx-background-radius:4;-fx-background-color: #136f9b");
	}

	@FXML
	private void autograph_exited() {
		signature.setStyle("");
	}


	@FXML
	private void queryEvent() {
		//SearchManager.getInstance().refreshRecommendFriends(new ResSearchFriends());
	}

	@Override
	public Stage getStage() {
		StageController stageController = UiBaseService.INSTANCE.getStageController();
		return stageController.getStageBy(View.id.MainView);
	}
}
