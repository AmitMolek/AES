package root.client.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import javax.naming.spi.DirStateFactory.Result;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import ocsf.client.ObservableClient;
import root.client.managers.DataKeepManager;
import root.client.managers.ScreensManager;
import root.dao.app.Exam;
import root.dao.app.QuestionInExam;
import root.dao.app.SolvedExams;
import root.dao.message.MessageFactory;
import root.dao.message.SimpleMessage;
import root.dao.message.SolvedExamMessage;

/**
 * @author Naor Saadia This controller implements execute exam screen the user
 *         get list of questions for specific exam and answer the quesitons
 *         
 */
public class ExecuteExamController implements Observer {

	@FXML
	private Button btnBack;

	@FXML
	private VBox vbxQuetionsTab;

	@FXML
	private BorderPane myBorder;

	@FXML
	private AnchorPane rootExecute;

	@FXML
	private AnchorPane answersPane;

	@FXML
	private Button btnNext;

	@FXML
	private Label lblTimer;

	@FXML
	private Label lblInstruction;

	@FXML
	private TextArea txtNotes;

	@FXML
	private Label lblDate;

	@FXML
	private AnchorPane buttomPane;

	private ArrayList<QuestionInExamObject> questionsInExamObject = new ArrayList<QuestionInExamObject>();

	private int displayQuestion;

	private int stopWatch;

	private DataKeepManager dataKeeper = DataKeepManager.getInstance();;

	private ArrayList<Button> tabsButton = new ArrayList<Button>();

	private ArrayList<String> intructText = new ArrayList<String>();
	private String userId;
	private Exam exam;
	Timeline examStopWatch;
	private String date;
	private Date dt;
	private SimpleDateFormat sdf;
	private String status;
	private MessageFactory messageFact = MessageFactory.getInstance();
	private ObservableClient client;
	ScreensManager scrMgr = ScreensManager.getInstance();

	/**
	 * this method happens when the window shown
	 * 
	 */

	public void initialize() {

		txtNotes.setEditable(false);
		btnBack.setDisable(true);
    	client = new ObservableClient("localhost", 8000);
		client.addObserver(this);
		try {
			client.openConnection();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		sdf = new SimpleDateFormat("dd-MM-yyyy");
		date = sdf.format(new Date());
		dt = new Date();
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		lblDate.setText(date);
		displayQuestion = 0;
		exam = (Exam) dataKeeper.getObject("RunningExam");
		ArrayList<QuestionInExam> questionsInExam = exam.getExamQuestions();
		int i = 0;
		for (QuestionInExam q : questionsInExam) {
			i++;
			intructText.add(q.getQuestion().getIdquestionIntruction());
			Button tab = new Button("question " + i);
			tab.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					changeTab(arg0);
				}

			});
			tabsButton.add(tab);
			vbxQuetionsTab.getChildren().add(tab);
			QuestionInExamObject qie = new QuestionInExamObject(q.getQuestion(), q.getQuestionGrade());
			questionsInExamObject.add(qie);
		}

		txtNotes.setText(intructText.get(displayQuestion));
		stopWatch = exam.getExamDuration() * 60;
		examStopWatch = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				int hours = (stopWatch / 60) / 60;
				int minuts = (stopWatch / 60) % 60;
				int seconds = stopWatch % 60;
				lblTimer.setText("" + hours + ":" + minuts + ":" + seconds);
				if (stopWatch == 0) {
					status = "interrupted";
					stopExam();
				}
				stopWatch--;
			}
		}));
		examStopWatch.setCycleCount(Timeline.INDEFINITE);

		myBorder.setCenter(questionsInExamObject.get(0));
		Platform.runLater(() -> {
			TextInputDialog dialog = new TextInputDialog();
			dialog.initOwner(scrMgr.getPrimaryStage());
			dialog.setTitle("Enter ID");
			dialog.setHeaderText("Enter your id:");

			final Button ok = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
			ok.addEventFilter(ActionEvent.ACTION, event -> System.out.println("Ok was definitely pressed"));

			final Button cancel = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
			cancel.addEventFilter(ActionEvent.ACTION, event -> System.out.println("Cancel was definitely pressed"));

			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()) {
				System.out.println("You can start Your exam");
				userId = dialog.getResult();
				examStopWatch.play();
			} else {
				try {
					scrMgr.activate("home");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		);

	}

	/**
	 * This method happens when the user click on next button the method check if
	 * the next should disabled the method allways make back button to enable
	 */
	@FXML
	public void nextQuestion(ActionEvent event) {
		displayQuestion++;
		txtNotes.setText(intructText.get(displayQuestion));
		if (displayQuestion == tabsButton.size() - 1)
			btnNext.setDisable(true);
		btnBack.setDisable(false);
		myBorder.setCenter(questionsInExamObject.get(displayQuestion));
	}

	/**
	 * This method happens when the user click on back button the method check if
	 * the back should disabled the method always make next button to enable
	 */
	public void prevQuestion(ActionEvent event) {
		displayQuestion--;
		if (displayQuestion == 0)
			btnBack.setDisable(true);
		txtNotes.setText(intructText.get(displayQuestion));
		if (!(displayQuestion == tabsButton.size() - 1))
			btnNext.setDisable(false);
		myBorder.setCenter(questionsInExamObject.get(displayQuestion));
	}

	/**
	 * This method stop the exams
	 */
	public void stopExam() {
		examStopWatch.stop();

	}

	/**
	 * This method implements the changing question
	 */
	public void changeTab(ActionEvent e) {
		Button tabButton = (Button) e.getSource();
		displayQuestion = tabsButton.indexOf(tabButton);
		txtNotes.setText(intructText.get(displayQuestion));
		myBorder.setCenter(questionsInExamObject.get(displayQuestion));
		if (displayQuestion == tabsButton.size() - 1)
			btnNext.setDisable(true);
		else
			btnNext.setDisable(false);
		if (displayQuestion == 0)
			btnBack.setDisable(true);
		else
			btnBack.setDisable(false);
	}

	public void submitPress(ActionEvent e) {
		Platform.runLater(() -> {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.initOwner(scrMgr.getPrimaryStage());
			alert.setTitle("Submit message");
			alert.setHeaderText("");
			alert.setContentText("are you sure you want to submit?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				status = "submitted";
				submitTest();
			}

		});

	}

	public void submitTest() {
		CheckedExamsAuto checkedExams = new CheckedExamsAuto(questionsInExamObject);
		int grade = checkedExams.calculateGrade();
		Date newDate;
		try {
			newDate = sdf.parse(date);
			String currentTime = sdf.format(dt);
			int solvedTime = exam.getExamDuration() - (stopWatch / 60);
			//SolvedExams(userId, exam.getExamId(), grade, solvedTime, status, newDate);
			SolvedExams newExam = new SolvedExams(userId, exam.getExamId(), grade, solvedTime, status, newDate);
			SolvedExamMessage newMessage = (SolvedExamMessage) messageFact.getMessage("put-solvedexams", newExam);
			try {
				client.sendToServer(newMessage);
			} catch (IOException e) {
				e.printStackTrace();
			}
			stopExam();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof SimpleMessage) {
			Platform.runLater(() -> {
				try {
					ScreensManager.getInstance().activate("endExam");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		}
	}

}
