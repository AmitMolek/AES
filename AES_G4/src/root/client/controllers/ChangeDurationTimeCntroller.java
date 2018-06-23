package root.client.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import ocsf.client.ObservableClient;
import root.client.managers.DataKeepManager;
import root.client.managers.ScreensManager;
import root.dao.app.ExecuteExam;
import root.dao.message.ChangeTimeDurationRequest;
import root.dao.message.ExecutedExamsMessage;
import root.dao.message.MessageFactory;
import root.dao.message.SimpleMessage;

/**
 * @author Naor Saadia This controller response for change duration screen
 */
public class ChangeDurationTimeCntroller implements Observer {
	/**
	 * The connection to server
	 */
	ObservableClient client;

	/**
	 * The column of duration time
	 */
	@FXML
	private TableColumn<ExecuteExam, Integer> durationTime;

	/**
	 * the column of exam id
	 */
	@FXML
	private TableColumn<ExecuteExam, String> examId;

	/**
	 * the column of exam type
	 */
	@FXML
	private TableColumn<ExecuteExam, String> examType;

	// /**
	// * the column of the start time
	// */
	// @FXML
	// private TableColumn<ExecuteExam, String> startTime;

	/**
	 * the column of the exam password
	 */
	@FXML
	private TableColumn<ExecuteExam, String> examPassword;

	/**
	 * the table of the execute exams
	 */
	@FXML
	private TableView<ExecuteExam> exeTable;

	/**
	 * button to lock the exam
	 */
	@FXML
	private Button btnLock;

	/**
	 * Message factory to get communication messages
	 */
	MessageFactory messageFact;

	/**
	 * This method called when the screen displayed this method initialize the
	 * table. this method init the connection
	 */
	@FXML
	public void initialize() {
		exeTable.setEditable(true);
		messageFact = MessageFactory.getInstance();
		durationTime.setCellValueFactory(new PropertyValueFactory("durationTime"));
		examId.setCellValueFactory(new PropertyValueFactory("examId"));
		examType.setCellValueFactory(new PropertyValueFactory("examType"));
		// startTime.setCellValueFactory(new PropertyValueFactory("startTime"));
		examPassword.setCellValueFactory(new PropertyValueFactory("examPassword"));
		client = (ObservableClient) DataKeepManager.getInstance().getObject_NoRemove("client");
		client.addObserver(this);
		try {
			client.openConnection();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		durationTime.setCellFactory(
				TextFieldTableCell.<root.dao.app.ExecuteExam, Integer>forTableColumn(new IntegerStringConverter()));

		ExecutedExamsMessage executedMsg = (ExecutedExamsMessage) messageFact.getMessage("get-executed-", null);
		executedMsg.setTeacher(DataKeepManager.getInstance().getUser().getUserID());
		try {
			client.sendToServer(executedMsg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * this method called when the server send message to this observer
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof ExecutedExamsMessage) {
			ExecutedExamsMessage msg = (ExecutedExamsMessage) arg1;
			ObservableList<ExecuteExam> observerseExecuteExams = FXCollections.observableArrayList();
			ArrayList<ExecuteExam> arr = msg.getExecutedExams();
			for (ExecuteExam e : arr) {
				observerseExecuteExams.add(e);
			}
			exeTable.setItems(observerseExecuteExams);
		}
	}

	/**
	 * this method called when the user change the duration column
	 * 
	 * @param durationEditEvent
	 *            duration event
	 */
	@FXML
	public void updateTime(TableColumn.CellEditEvent<ExecuteExam, Integer> durationEditEvent) {
		ChangeTimeDurationRequest cht = new ChangeTimeDurationRequest();
		Platform.runLater(() -> {
			boolean enterNotes = false;
			while (!enterNotes) {
				TextInputDialog dialog = new TextInputDialog("Teacher notes");
				dialog.setTitle("You must give a reason");
				dialog.initOwner(ScreensManager.getInstance().getPrimaryStage());
				dialog.setHeaderText("Notes for change duration of an exam");
				dialog.setContentText("Please enter your notes:");

				// Traditional way to get the response value.
				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()) {
					enterNotes = true;
					cht.setMessageFromTeacher(result.get());
					ExecuteExam executed = exeTable.getSelectionModel().getSelectedItem();
					Integer newValue = durationEditEvent.getNewValue();
					sendMessage(cht, newValue, executed.getExamId());
				}
			}
		});

	}

	/**
	 * this method send message of change duration time to server
	 * 
	 * @param cht
	 *            cht
	 * @param newTime
	 *            newTime
	 * @param examID
	 *            examID
	 */
	public void sendMessage(ChangeTimeDurationRequest cht, int newTime, String examID) {
		cht.setExamId(examID);
		cht.setNewTime(newTime);
		SendRequests se = new SendRequests();
		try {
			se.send(cht);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * this method lock the exam
	 * 
	 * @param event
	 *            the event that triggers
	 */
	@FXML
	void LockExam(ActionEvent event) {
		ExecuteExam executed = exeTable.getSelectionModel().getSelectedItem();
		ChangeTimeDurationRequest cht = new ChangeTimeDurationRequest();
		cht.confirm();
		sendMessage(cht, 0, executed.getExamId());
		SimpleMessage simple = (SimpleMessage) messageFact.getMessage("simple", null);
		simple.setMessage("closeexam-" + executed.getExamId());
		Platform.runLater(() -> {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("Lock exam done");
			alert.setContentText("You choose to lock this exam");
			alert.showAndWait();
		});
		try {
			client.sendToServer(simple);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
