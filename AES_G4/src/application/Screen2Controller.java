package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class Screen2Controller {

	private ScreensController screensController = ScreensController.getInstance();;
    @FXML
    void backToMain(ActionEvent event) throws IOException {
    	screensController.activate("main");
    }

}


