package root.client.controllers;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import javax.swing.filechooser.FileSystemView;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
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
import root.dao.message.MyFile;
import root.dao.message.SimpleMessage;
import root.dao.message.SubjectMessage;
import root.dao.message.WordMessage;
import root.util.log.Log;
import root.util.log.LogLine;

/**
 * Class for manually exam
 * @author Omer Haimovich
 *
 */
public class ManuallyExamController implements Observer {

	@FXML
	private Button btnGetExam;

	@FXML
	private Label lblFirstName;

	@FXML
	private Label lblQuestionNum;

	@FXML
	private Label lblLastName;

	@FXML
	private AnchorPane rootPane;

	@FXML
	private Label lblTime;

	@FXML
	private Label lblExamId;

	@FXML
	private Button btnSubmit;
	


	private User student;
	private MessageFactory messageFact;
	private ObservableClient client;
	private Log log;
	private ScreensManager screenManager;
	private DataKeepManager dkm;
	private Stage mainApp;
	private int stopWatch;
	private Exam newExam;
	private Timeline examStopWatch;
	public static String CLIENTPATH;

	/**
	 * This method occurs when the window is shown up.
	 * 
	 * @throws IOException
	 *             if the window cannot be shown
	 */
	@FXML
	public void initialize() throws IOException {
		log = Log.getInstance();
		int i = 0;
		String s = System.getProperty("user.home");
		new File(s+"//exams").mkdir();
		String fullPath = s+"//exams//";
		CLIENTPATH = fullPath;
		dkm = DataKeepManager.getInstance();
		screenManager = ScreensManager.getInstance();
		mainApp = screenManager.getPrimaryStage();
		client = (ObservableClient) DataKeepManager.getInstance().getObject_NoRemove("client");
		client.addObserver(this);
		messageFact = MessageFactory.getInstance();
		student = dkm.getUser();
		lblFirstName.setText(student.getUserFirstName());
		lblLastName.setText(student.getUserLastName());
		newExam = (Exam) dkm.getObject("RunningExam");
		lblExamId.setText(newExam.getExamId());
		lblQuestionNum.setText(Integer.toString(newExam.getExamQuestions().size()));
		btnSubmit.setDisable(true);
		stopWatch = newExam.getExamDuration() * 60;
		examStopWatch = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				int hours = (stopWatch / 60) / 60;
				int minuts = (stopWatch / 60) % 60;
				int seconds = stopWatch % 60;
				lblTime.setText("" + hours + ":" + minuts + ":" + seconds);
				if (stopWatch == 0) {
					btnSubmit.setDisable(true);
					Platform.runLater(() -> { // In order to run javaFX thread.(we recieve from server a java thread)
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.initOwner(mainApp);
						alert.setTitle("Time finished");
						alert.setHeaderText("The time of the exam finish");
						alert.setContentText("You can not submit anymore");
						alert.showAndWait();
						try {
							screenManager.activate("home");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					});
					
				}
				stopWatch--;
			}
		}));
		examStopWatch.setCycleCount(Timeline.INDEFINITE);
		Platform.runLater(() -> {
			TextInputDialog dialog = new TextInputDialog();
			dialog.initOwner(screenManager.getPrimaryStage());
			dialog.setTitle("Enter ID");
			dialog.setHeaderText("Enter your id:");

			final Button ok = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
			ok.addEventFilter(ActionEvent.ACTION, event -> System.out.println("Ok was definitely pressed"));

			final Button cancel = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
			cancel.addEventFilter(ActionEvent.ACTION, event -> System.out.println("Cancel was definitely pressed"));

			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()) {
				System.out.println("You can start Your exam");
				examStopWatch.play();
			} else {
				try {
					screenManager.activate("home");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		);

	}

	/**
	 * Method that occurs when Student press get exam button
	 * 
	 * @param event
	 *            on action when student press get exam button
	 */
	@FXML
	void getExamDocument(ActionEvent event) {
		WordMessage newMessage = (WordMessage) messageFact.getMessage("get-wordexam",
				student.getUserID() + "-" + newExam.getExamId());
		btnSubmit.setDisable(false);
		btnGetExam.setDisable(true);
		try {
			client.sendToServer(newMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Method that occurs when student press submit button
	 * 
	 * @param event
	 */

	@FXML
	void SubmitExam(ActionEvent event) {
		root.dao.message.MyFile wordFile = new root.dao.message.MyFile(
				student.getUserID() + "-" + newExam.getExamId() + ".docx");
		String LocalfilePath = CLIENTPATH+ student.getUserID() + "-" + newExam.getExamId() + ".docx";

		try {

			File newFile = new File(LocalfilePath);

			byte[] mybytearray = new byte[(int) newFile.length()];
			FileInputStream fis = new FileInputStream(newFile);
			BufferedInputStream bis = new BufferedInputStream(fis);
			wordFile.initArray(mybytearray.length);
			wordFile.setSize(mybytearray.length);
			bis.read(wordFile.getMybytearray(), 0, mybytearray.length);
			wordFile.setDescription(LocalfilePath);
			fis.close();
			bis.close();
			WordMessage sendMessage = (WordMessage) messageFact.getMessage("put-wordexam", wordFile);
			client.sendToServer(sendMessage);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error send (Files)msg) to Server");
		}
	}

	/**
	 * This method occurs when the server send message to the client
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof WordMessage) {
			WordMessage newMessage = (WordMessage) arg1;
			root.dao.message.MyFile wordFile = new root.dao.message.MyFile(
					student.getUserID() + "-" + newExam.getExamId() + ".docx");
			MyFile serverFile = newMessage.getNewFile();
			String LocalfilePath = serverFile.getDescription();

			try {

				File newFile = new File(LocalfilePath);

				byte[] mybytearray = new byte[(int) newFile.length()];
				FileInputStream fis = new FileInputStream(newFile);
				BufferedInputStream bis = new BufferedInputStream(fis);
				wordFile.initArray(mybytearray.length);
				wordFile.setSize(mybytearray.length);
				bis.read(wordFile.getMybytearray(), 0, mybytearray.length);
				File Word = new File(CLIENTPATH+ student.getUserID() + "-" + newExam.getExamId() + ".docx");
				FileOutputStream fos = new FileOutputStream(CLIENTPATH + student.getUserID()
								+ "-" + newExam.getExamId() + ".docx");
				fos.write(wordFile.getMybytearray());
				fos.close();
				bis.close();
				fis.close();
				Platform.runLater(() -> { // In order to run javaFX thread.(we recieve from server a java thread)
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.initOwner(mainApp);
					alert.setTitle("Exam path");
					alert.setHeaderText("Exam path in the computer");
					alert.setContentText("You can start your exam\n The path is: "+ CLIENTPATH+ student.getUserID() + "-" + newExam.getExamId() + ".docx\n" + "do not change the path");
					alert.showAndWait();
				});

			} catch (Exception e) {
				System.out.println("Error send (Files)msg) to Server");
				e.printStackTrace();
			}
		}

		if (arg1 instanceof SimpleMessage) {
			Platform.runLater(() -> { // In order to run javaFX thread.(we recieve from server a java thread)
				try {
					File newFile = new File(CLIENTPATH + student.getUserID() + "-" + newExam.getExamId() + ".docx" );
					newFile.delete();
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.initOwner(mainApp);
					alert.setTitle("Exam Finish");
					alert.setHeaderText("Exam finish successeful");
					alert.setContentText("The exam was finish successful");
					alert.showAndWait();
					screenManager.activate("endExam");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
				}

			});
		}
	}

}