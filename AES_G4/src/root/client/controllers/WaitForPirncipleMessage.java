package root.client.controllers;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import ocsf.client.ObservableClient;
import root.client.managers.DataKeepManager;
import root.client.managers.ScreensManager;
import root.dao.message.ChangeTimeDurationRequest;
import root.dao.message.SimpleMessage;

/**
 * @author Naor Saadia
 * This class is observer that wait
 * for teacher that change the time duration
 */
public class WaitForPirncipleMessage implements Observer{
	
	/**
	 * instance of screen manager
	 **/
	private ScreensManager screenManager = ScreensManager.getInstance();
	
	/**
	 * this method called when
	 * the server get change time request
	 * the method fire alert for change request
	**/
	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof ChangeTimeDurationRequest)
		{
			Platform.runLater(()->{
				ChangeTimeDurationRequest msg = (ChangeTimeDurationRequest) arg;
	            Alert alert = new Alert(AlertType.CONFIRMATION);
	            alert.initOwner(screenManager.getPrimaryStage());
	            alert.setTitle("Change request");
	            alert.setHeaderText("Change exam duration time");
	            alert.setContentText(msg.getMessageFromTeacher());
	            Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					ObservableClient client = (ObservableClient) DataKeepManager.getInstance().getObject_NoRemove("client");
					msg.confirm();
					try {
						client.sendToServer(msg);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}   
			});

		}
		
	}

}
