package root.client.controllers;

import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import root.client.managers.ScreensManager;

/**
 * @author Naor Saadia
 * This class waiting for change time confirmation. 
 */
public class WaitForChangeTIme implements Observer{

	/**
	 * The method change the duration time
	**/
	@Override
	public void update(Observable arg0, Object arg1) {
		int time = (int)arg1;
		ExamDuration.setTime(time);
	}

}
