package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import root.client.managers.ScreensManager;

public class Screen2Controller {

	private ScreensManager screensController = ScreensManager.getInstance();;
    @FXML
    void backToMain(ActionEvent event) throws IOException {
    	screensController.activate("main");
    }

}


