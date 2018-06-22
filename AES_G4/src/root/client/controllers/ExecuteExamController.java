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
import javafx.scene.control.TextField;
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
	
	// Instance variables **********************************************

	
	/**
	 * Button for previous page
	 */
	@FXML
	private Button btnBack;

	/**
	 * VBox in the right side of the screen.
	 * for jump between questions
	 */
	@FXML
	private VBox vbxQuetionsTab;

	/**
	 * The second layer in the screen
	 */
	@FXML
	private BorderPane myBorder;

	/**
	 * The first layer of the screen
	 */
	@FXML
	private AnchorPane rootExecute;

	/**
	 * Pane in the middle of the screen. 
	 * QuestionInExam Object locate in this pane
	 */
	@FXML
	private AnchorPane answersPane;

	/**
	 * Button that move to the next question 
	 */
	@FXML
	private Button btnNext;

	/**
	 * the timer label
	 */
	@FXML
	private Label lblTimer;

	/**
	 * The teacher instruction label
	 */
	@FXML
	private Label lblInstruction;

	/**
	 * teacher notes text area 	 
	 */
	@FXML
	private TextArea txtNotes;

	/**
	 * label for date
	 */
	@FXML
	private Label lblDate;

	/**
	 * the pane in the buttom, the button located here
	 */
	@FXML
	private AnchorPane buttomPane;
	
	/**
	 * ArrayList of questionIn exam object
	 */
	private ArrayList<QuestionInExamObject> questionsInExamObject = new ArrayList<QuestionInExamObject>();

	/**
	 * the index of the question that need to be displayed
	 */
	private int displayQuestion;

	/**
	 * the exam duration time in seconds. this taken from DurationTime class
	 */
	private int stopWatch;

	/**
	 * instance of dataKeeper
	 */
	private DataKeepManager dataKeeper = DataKeepManager.getInstance();;

	/**
	 * ArrayList of tabs buttons to jump between questions
	 */
	private ArrayList<Button> tabsButton = new ArrayList<Button>();
	
	/**
	 * ArrayList for teacher instructions
	 */
	private ArrayList<String> intructText = new ArrayList<String>();

	/**
	 * ArrayList for exam points
	 */
	private ArrayList<Integer> points = new ArrayList<Integer>();

	/**
	 * the user id
	 */
	private String userId;
	
	/**
	 * the exam that executed
	 */
	private Exam exam;
	
	/**
	 * the stopwatch
	 */	
	private Timeline examStopWatch;
	
	/**
	 * the execution date
	 */
	private String date;
	
	/**
	 * temp date
	 */
	private Date dt;
	
	/**
	 * simple date format
	 */
	private SimpleDateFormat sdf;
	
	/**
	 * the status of the exam
	 */
	private String status;
	
	/**
	 * instance of MessageFactory
	 */
	private MessageFactory messageFact = MessageFactory.getInstance();
	
	/**
	 * instance of the client
	 */
	private ObservableClient client;
	
	/**
	 * instance of Screen Manager
	 */
	ScreensManager scrMgr = ScreensManager.getInstance();
	
	

	/**
	 * this method happens when the screen is display
	 * 
	 */
	public void initialize() {
		dt = new Date();
		exam = (Exam) dataKeeper.getObject("RunningExam");
		txtNotes.setEditable(false);
		btnBack.setDisable(true);
		client = new ObservableClient((String) dataKeeper.getObject_NoRemove("ip"), 8000);
    	SimpleMessage simpleMessage = (SimpleMessage)messageFact.getMessage("simple", null);
    	simpleMessage.setMessage("startExam-"+exam.getExamId());
		client.addObserver(this);
		try {
			client.openConnection();
	    	client.sendToServer(simpleMessage);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		sdf = new SimpleDateFormat("dd-MM-yyyy");
		date = sdf.format(new Date());
		lblDate.setText(date);
		displayQuestion = 0;
		ArrayList<QuestionInExam> questionsInExam = exam.getExamQuestions();
		int i = 0;
		for (QuestionInExam q : questionsInExam) {
			i++;
			intructText.add(q.getQuestion().getIdquestionIntruction());
			points.add(q.getQuestionGrade());
			Button tab = new Button("question " + i);
			tab.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					changeTab(arg0);
				}

			});
			if(questionsInExam.size()==1) {
				btnNext.setDisable(true);
			}
			tabsButton.add(tab);
			vbxQuetionsTab.getChildren().add(tab);
			QuestionInExamObject qie = new QuestionInExamObject(q.getQuestion(), q.getQuestionGrade());
			questionsInExamObject.add(qie);
		}

		txtNotes.setText(intructText.get(displayQuestion) + "\r\n" + "(" + points.get(displayQuestion) + ")");


		ExamDuration.setTime(exam.getExamDuration());
		examStopWatch = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				int hours = (ExamDuration.getTime() / 60) / 60;
				int minuts = (ExamDuration.getTime() / 60) % 60;
				int seconds = ExamDuration.getTime() % 60;
				lblTimer.setText("" + hours + ":" + minuts + ":" + seconds);
				if (ExamDuration.getTime() == 0) {
					status = "interrupted";
					stopExam();
				}
				ExamDuration.minusOne();
			}
		}));
		examStopWatch.setCycleCount(Timeline.INDEFINITE);
		myBorder.setCenter(questionsInExamObject.get(0));
		Platform.runLater(() -> {
			TextInputDialog dialog = new TextInputDialog();
			dialog.initOwner(scrMgr.getPrimaryStage());
			dialog.setTitle("Enter ID");
			dialog.setHeaderText("Enter your id:");
			TextField txt =dialog.getEditor();
			txt.setText(dataKeeper.getUser().getUserID());
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
		txtNotes.setText(intructText.get(displayQuestion) + "\r\n" + "(" + points.get(displayQuestion) + ")");
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
		txtNotes.setText(intructText.get(displayQuestion) + "\r\n" + "(" + points.get(displayQuestion) + ")");

		if (!(displayQuestion == tabsButton.size() - 1))
			btnNext.setDisable(false);
		myBorder.setCenter(questionsInExamObject.get(displayQuestion));
	}

	/**
	 * This method stop the exams
	 */ 
	public void stopExam() {
    	//SimpleMessage simpleMessage = (SimpleMessage)messageFact.getMessage("simple", null);
		examStopWatch.stop();
		submitTest();
	}

	/**
	 * This method implements the changing question
	 */
	public void changeTab(ActionEvent e) {
		Button tabButton = (Button) e.getSource();
		displayQuestion = tabsButton.indexOf(tabButton);
		txtNotes.setText(intructText.get(displayQuestion) + "\r\n" + "(" + points.get(displayQuestion) + ")");
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

	/**
	 * this Action happen when the user press on submit button
	 */
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

	/**
	 * This method call to submit exam, 
	 * or when the time is end 
	 * or when the user press on submit
	 */
	public void submitTest() {
		CheckedExamsAuto checkedExams = new CheckedExamsAuto(questionsInExamObject, exam);
		int grade = checkedExams.calculateGrade();
		checkedExams.createCsv();
		Date newDate;
		try {
			newDate = sdf.parse(date);
			String currentTime = sdf.format(dt);
			int solvedTime = exam.getExamDuration() - (stopWatch / 60);
			SolvedExams newExam = new SolvedExams(userId, exam.getExamId(), grade, solvedTime, status, newDate);
			SolvedExamMessage newMessage = (SolvedExamMessage) messageFact.getMessage("put-solvedexams", newExam);
			try {
				client.sendToServer(newMessage);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	/**
	 * this method happens when the server send back message
	 * it should recived ok message for recived csv
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof SimpleMessage) {
			SimpleMessage s = (SimpleMessage) arg1;
			if (!s.getMsg().equals("ok-get-csv")) {
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
	
	/**
	 * This method is implement by false
	 */	
	private void deleteSolvedExam() {
		SimpleMessage simpleMsg = (SimpleMessage) messageFact.getMessage("simple",null);
		simpleMsg.setMsg("delete-solvedexams-"+exam.getExamId());
		try {
			client.sendToServer(simpleMsg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * this method called when need to change the exam time
	 */
	public void changeTime(int newTime) {
		stopWatch = newTime;
	}
}
