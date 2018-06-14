package root.client.controllers;

import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import root.client.managers.ScreensManager;

public class WaitForChangeTIme implements Observer{

	private ScreensManager screenManager = ScreensManager.getInstance();

	@Override
	public void update(Observable arg0, Object arg1) {
		Platform.runLater(()->{
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.initOwner(screenManager.getPrimaryStage());
			alert.setTitle("Change request");
			alert.setHeaderText("Change exam duration time");
			alert.setContentText("asdasd");
            alert.showAndWait();       
		});
	}

}
