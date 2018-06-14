/**
 * This class implements 4 digit password screen.
 * The screen received from the user 4 digit password and move to execute exam windows
 */

package root.client.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Alert.AlertType;
import ocsf.client.ObservableClient;
import root.client.managers.DataKeepManager;
import root.client.managers.ScreensManager;
import root.dao.app.Exam;
import root.dao.message.ErrorMessage;
import root.dao.message.ExamMessage;
import root.dao.message.MessageFactory;
import root.dao.message.SimpleMessage;
import root.util.log.Log;
import root.util.log.LogLine;

/**
 * @author Naor Saadia this controller is for 4 digit password screen the screen
 *         get input from user and go to execute exam screen if the pass is OK
 */
public class Enter4digitPasswordController implements Observer {

	@FXML
	private TextField txt4Digit;

	private Log log = Log.getInstance();

	private ObservableClient client;

	private ScreensManager scrMgr;

	private DataKeepManager dataKeeper;

	/**
	 * This method happens when the screen is up in the method we create screen
	 * manager and dataKeeper and open connection with the server
	 * 
	 * @throws IOException
	 */
	public void initialize() throws IOException {
		txt4Digit.addEventFilter(KeyEvent.KEY_TYPED, maxLength(4));
		scrMgr = ScreensManager.getInstance();
		dataKeeper = DataKeepManager.getInstance();
    	client = new ObservableClient("localhost", 8000);
		client.addObserver(this);
		client.openConnection();
	}

	/**
	 * This method run when the user click on Start when he start we take the input
	 * and send to server the send message is simple message with get-exams-pass
	 */
	public void StartExam(ActionEvent e) {

		MessageFactory msgFactory = MessageFactory.getInstance();
		String txt = txt4Digit.getText();
		txt = "'" + txt + "'";
		SimpleMessage getExamMessage = (SimpleMessage) msgFactory.getMessage("get-simple", new Object());
		getExamMessage.setMessage("get-exams-pass-" + txt);
		try {
			client.sendToServer(getExamMessage);
		} catch (IOException e1) {
			log.writeToLog(LogLine.LineType.ERROR, e1.getMessage());
			e1.printStackTrace();
		}

	}

	/**
	 * This method calls from the server the server return Exam message if exams
	 * found and error message if no
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		ExamMessage examMessage = null;
		if (arg1 instanceof ExamMessage) {
			examMessage = (ExamMessage) arg1;
			ArrayList<Exam> examsList = examMessage.getExams();
			Exam newExam = examsList.get(0);
			if (newExam.getExecuteExam().getExamType().equals("auto")) {
				dataKeeper.keepObject("RunningExam", newExam);
				client.deleteObservers();
				client.addObserver(new WaitForChangeTIme());
				Platform.runLater(() -> {
					try {
						scrMgr.activate("executefull");
					} catch (IOException e) {
						log.writeToLog(LogLine.LineType.ERROR, "Trying to activate wrong window");
						e.printStackTrace();
					}
				});
			}
		} else if (arg1 instanceof ErrorMessage) {
			log.writeToLog(LogLine.LineType.ERROR, ((ErrorMessage) arg1).getErrorException().getMessage());

			// Add Here handle with wrong password
			// Add Here handle with locked exam
			Platform.runLater(() -> {
				Alert alert = new Alert(AlertType.ERROR);
				alert.initOwner(scrMgr.getPrimaryStage());
				alert.setTitle("Wrong 4 digits password");
				alert.setHeaderText("Please correct invalid fields");
				alert.setContentText(arg1.toString());
				alert.showAndWait();
				log.writeToLog(LogLine.LineType.ERROR, "Cant Find running exam");
			});
		}
	}

	/**
	 * This is event handler for check if the user enter more than 4 digit
	 */
	public EventHandler<KeyEvent> maxLength(final Integer i) {
		return new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent arg0) {

				TextField tx = (TextField) arg0.getSource();
				if (tx.getText().length() >= i) {
					arg0.consume();
				}

			}

		};

	}

}
