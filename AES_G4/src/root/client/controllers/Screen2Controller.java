package root.client.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import root.client.resources.view.ScreensManager;

public class Screen2Controller {

	private ScreensManager screensController = ScreensManager.getInstance();;
    @FXML
    void backToMain(ActionEvent event) throws IOException {
    	screensController.activate("main");
    }

}


