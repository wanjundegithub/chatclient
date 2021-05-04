package com.company.im.chat;

import com.company.im.chat.ui.ControlledStage;
import com.company.im.chat.ui.StageController;
import com.company.im.chat.ui.UiBaseService;
import com.company.im.chat.ui.View;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.StageStyle;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;

public class TestLoadController {

    private static final Logger logger= LoggerFactory.getLogger(TestLoadController.class);

    @Test
    public void testGetStage(){
        StageController stageController = UiBaseService.INSTANCE.getStageController();
        if(stageController==null){
            logger.error("stageController is null");
            return;
        }
        logger.info("stageController normal ");
        var stage=stageController.loadStage(View.id.LoginView, View.layout.LoginView,
                StageStyle.UNDECORATED);
        if(stage==null){
            logger.error("stage is null");
            return;
        }
        logger.info("stage is normal");
    }

    @Test
    public void testLoadResource() throws IOException {
        String name=View.id.LoginView;
        String resource=View.layout.LoginView;
        URL url = Thread.currentThread().getContextClassLoader().getResource(resource);
        FXMLLoader loader = new FXMLLoader(url);
        //loader.setResources(ResourceBundle.getBundle("i18n/message"));
        Pane tmpPane = (Pane)loader.load();
        ControlledStage controlledStage = (ControlledStage)loader.getController();
        if(controlledStage==null){
            logger.info(" cannot load stage");
        }
    }
}
