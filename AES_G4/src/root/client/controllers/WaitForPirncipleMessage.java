package root.client.controllers;

import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import root.client.managers.ScreensManager;

public class WaitForPirncipleMessage implements Observer{
	
	private ScreensManager screenManager = ScreensManager.getInstance();
	
	@Override
	public void update(Observable o, Object arg) {
		//TODO change the alert
		Platform.runLater(()->{
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(screenManager.getPrimaryStage());
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("New Check");
            alert.showAndWait();       
		});
	}

}
