package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class Screen1Controller {
	private ScreensController screensController = ScreensController.getInstance();
	
    @FXML
    private AnchorPane Screen1Controller;
    
    @FXML
    void MoveScreen(ActionEvent event) {
    	try {
			screensController.activate("s2");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
 

}

