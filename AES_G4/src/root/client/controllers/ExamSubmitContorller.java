package root.client.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ocsf.client.ObservableClient;
import root.client.managers.DataKeepManager;
import root.client.managers.ScreensManager;
import root.dao.app.Course;
import root.dao.app.Exam;
import root.dao.app.Question;
import root.dao.app.QuestionInExam;
import root.dao.app.Subject;
import root.dao.app.User;
import root.dao.message.MessageFactory;
import root.dao.message.SubjectMessage;
import root.util.log.Log;
import root.util.log.LogLine;

/**
 * A class that is responsible for exam submit window
 * 
 * @author Omer Haimovich
 *
 */
public class ExamSubmitContorller {

	// FXML variables **********************************************

	@FXML
	private Label lblThankYou;

	@FXML
	private Button btnHomePage;

	@FXML
	private AnchorPane rootPane;

	// Instance variables **********************************************

	/**
	 * The login student
	 */
	private User student;
	/**
	 * 
	 * A log file that is responsible for documenting the actions performed in the
	 * application
	 */
	private Log log;
	/**
	 * The manager that responsible for switching between windows in the system
	 */
	private ScreensManager screenManager;
	/**
	 * The manager that responsible for transmit data between windows in the system
	 */
	private DataKeepManager dkm;

	// CLASS METHODS *************************************************

	/**
	 * A method that allows the student to go back to the home screen
	 * 
	 * @param event
	 *            An event occurs when the student presses a `go home` button
	 * 
	 */
	@FXML
	void backToHomePage(ActionEvent event) {
		Platform.runLater(() -> { // In order to run javaFX thread.(we recieve from server a java thread)
			try {
				screenManager.activate("home");
			} catch (IOException e) {
				e.printStackTrace();
				log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
			}
		});

	}

	/**
	 * 
	 * 
	 * The method initializes the window when it comes up
	 * 
	 * @throws IOException
	 *             if the window cannot be shown
	 */
	@FXML
	public void initialize() throws IOException {
		log = Log.getInstance();
		dkm = DataKeepManager.getInstance();
		screenManager = ScreensManager.getInstance();
		student = dkm.getUser();
		lblThankYou.setText("Thank You! " + student.getUserFirstName() + " " + student.getUserLastName());

	}

}
