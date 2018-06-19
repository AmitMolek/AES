package root.client.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import ocsf.client.ObservableClient;
import root.client.managers.DataKeepManager;
import root.client.managers.ScreensManager;
import root.dao.app.Exam;
import root.dao.app.QuestionInExam;
import root.dao.app.Question;
import root.dao.message.ExamMessage;
import root.dao.message.MessageFactory;
import root.dao.message.QuestionInExamMessage;
import root.dao.message.SimpleMessage;
import root.util.log.Log;
import root.util.log.LogLine;

/**
 * Class for Update exam
 * 
 * @author Omer Haimovich
 *
 */
public class UpdateExamController implements Observer {

	@FXML
	private AnchorPane rootPane;

	@FXML
	private TableColumn<root.dao.app.QuestionInExam, Integer> tbcPoints;

	@FXML
	private TableColumn<root.dao.app.QuestionInExam, Question> tbcQuestion;

	@FXML
	private TableView<root.dao.app.QuestionInExam> tblQuestion;

	@FXML
	private Button btnUpdate;

	@FXML
	private Button btnRemove;

	private MessageFactory messageFact;
	private ObservableClient client;
	private ScreensManager screenManager;
	private Log log;
	private DataKeepManager dbk;
	private Exam updateExam;
	private ObservableList<root.dao.app.QuestionInExam> examQuestions;
	private int totalPoints;
	private Stage mainApp;

	/**
	 * Update the question in the specific exam
	 * 
	 * @param event
	 */
	@FXML
	void UpdateQuestionInExam(ActionEvent event) {
		totalPoints = 0;
		ArrayList<QuestionInExam> examInQuestions = new ArrayList<QuestionInExam>();
		for (QuestionInExam q : examQuestions) {
			totalPoints = totalPoints + q.getQuestionGrade();
			examInQuestions.add(q);
		}
		if (totalPoints != 100) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(mainApp);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText("Not 100 points");
			alert.showAndWait();
			return;
		}
		QuestionInExamMessage sendMessage = (QuestionInExamMessage) messageFact.getMessage("put-questioninexam",
				examInQuestions);
		sendMessage.setExamId(updateExam.getExamId());
		try {
			client.sendToServer(sendMessage);
		} catch (IOException e) {
			log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * Delete question from the exam
	 * 
	 * @param event
	 */
	@FXML
	void RemoveQuestionFromExam(ActionEvent event) {
		QuestionInExam question = tblQuestion.getSelectionModel().getSelectedItem();
		if (question == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(mainApp);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText("You did not select any Question");
			alert.showAndWait();
			return;
		}
		ObservableList<QuestionInExam> questionSelected;
		questionSelected = tblQuestion.getSelectionModel().getSelectedItems();
		QuestionInExam q = questionSelected.get(0);
		examQuestions.remove(q);
		tblQuestion.setItems(examQuestions);

	}

	/**
	 * This method occurs when the window is shown up.
	 * 
	 * @throws IOException
	 *             if the window cannot be shown
	 */
	@FXML
	public void initialize() throws IOException {
		messageFact = MessageFactory.getInstance();
		screenManager = ScreensManager.getInstance();
		log = Log.getInstance();
		dbk = DataKeepManager.getInstance();
		mainApp = screenManager.getPrimaryStage();
		client = (ObservableClient) DataKeepManager.getInstance().getObject_NoRemove("client");
		client.addObserver(this);
		tbcPoints.setCellValueFactory(new PropertyValueFactory("questionGrade"));
		tbcQuestion.setCellValueFactory(new PropertyValueFactory("question"));
		tblQuestion.setEditable(true);
		tbcPoints.setCellFactory(
				TextFieldTableCell.<root.dao.app.QuestionInExam, Integer>forTableColumn(new IntegerStringConverter()));
		updateExam = (Exam) dbk.getObject("updateExam");
		examQuestions = FXCollections.observableArrayList();
		ArrayList<root.dao.app.QuestionInExam> questionList = updateExam.getExamQuestions();
		for (root.dao.app.QuestionInExam q : questionList) {
			examQuestions.add(q);
		}
		tblQuestion.setItems(examQuestions);
		if (updateExam.getExamQuestions().size() <= 1) {
			btnRemove.setDisable(true);
		}

	}

	/**
	 * Edit the points of question
	 * 
	 * @param pointEditEvent
	 */
	@FXML
	void updatePoints(TableColumn.CellEditEvent<QuestionInExam, Integer> pointEditEvent) {
		int i = 0;
		QuestionInExam question = tblQuestion.getSelectionModel().getSelectedItem();
		Integer newValue = pointEditEvent.getNewValue();
		question.setQuestionGrade(newValue);
		for (QuestionInExam q : examQuestions) {
			if (q.getQuestion().getQuestionId().equals(question.getQuestion().getQuestionId())) {
				examQuestions.set(i, question);
				break;
			}
			i++;
		}

	}

	/**
	 * This method occurs when the server send message to the client
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof SimpleMessage) {
			SimpleMessage simple = (SimpleMessage) arg1;
			log.writeToLog(LogLine.LineType.INFO, "Exam updated");
			Platform.runLater(() -> { // In order to run javaFX thread.(we recieve from server a java thread)
				try {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.initOwner(mainApp);
					alert.setTitle("Exam updated");
					alert.setHeaderText("Exam updated successefully");
					alert.setContentText("The exam was updated successful");
					alert.showAndWait();
					screenManager.activate("home");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
				}
			});
		}
	}

	@FXML
	public void updatePoints() {
	}

}