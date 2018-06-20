package root.client.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ocsf.client.ObservableClient;
import root.client.managers.DataKeepManager;
import root.client.managers.ScreensManager;
import root.dao.app.Course;
import root.dao.app.Exam;
import root.dao.app.ExecuteExam;
import root.dao.app.Subject;
import root.dao.app.User;
import root.dao.message.CourseMessage;
import root.dao.message.ExamMessage;
import root.dao.message.ExecuteExamMessage;
import root.dao.message.MessageFactory;
import root.dao.message.SimpleMessage;
import root.dao.message.SubjectMessage;
import root.dao.message.WordMessage;
import root.util.log.Log;
import root.util.log.LogLine;

/**
 * A class that is responsible for prepare exam window
 * 
 * @author Omer Haimovich
 *
 */
public class PrepareExamController implements Observer {

	// FXML variables **********************************************

	@FXML
	private ComboBox<String> cmbCourse;

	@FXML
	private ComboBox<String> cmbExamType;

	@FXML
	private TextField txtFinish;

	@FXML
	private ComboBox<String> cmbSubject;

	@FXML
	private AnchorPane rootPane;

	@FXML
	private TableColumn<Exam, String> tbcExamId;

	@FXML
	private TableColumn<Exam, User> tbcTeacherId;

	@FXML
	private TableColumn<Exam, Integer> tbcDuration;

	@FXML
	private TableView<Exam> tblExams;

	@FXML
	private Button brtnExecuteExam;

	// Instance variables **********************************************

	/**
	 * Counter that counts how many time teacher select subject from the subject
	 * combo box
	 */
	private int count;
	/**
	 * Generates new communications between server and client
	 */
	private MessageFactory messageFact;
	/**
	 * 
	 * 
	 * Keeps our client in order to communicate with the server
	 */
	private ObservableClient client;
	/**
	 * The manager that responsible for switching between windows in the system
	 */
	private ScreensManager screenManager;
	/**
	 * 
	 * A log file that is responsible for documenting the actions performed in the
	 * application
	 */
	private Log log;
	/**
	 * The login teacher
	 */
	private User teacher;
	/**
	 * 
	 * A list of all the subjects taught by the teacher
	 */
	private ArrayList<Subject> teacherSubject;
	/**
	 * The chosen subject
	 */
	private Subject newSubject;
	/**
	 * A list of all the courses taught by the teacher
	 */
	private ArrayList<Course> CourseInSubject;
	/**
	 * The chosen course
	 */
	private Course newCourse;
	/**
	 * The manager that responsible for transmit data between windows in the system
	 */
	private DataKeepManager dbk;
	/**
	 * The main window of the application
	 */
	private Stage mainApp;
	/**
	 * The type of the exam
	 */
	private String type;
	/**
	 * The exam that need to be executed
	 */
	private Exam executeExam;
	/**
	 * The code of the exam
	 */
	private String pass;

	// CLASS METHODS *************************************************

	/**
	 * 
	 * A method that allows the teacher to select a subject
	 * 
	 * @param event
	 * 
	 *            An event that happens when a teacher select from subject combo box
	 */
	@FXML
	void selectSubject(ActionEvent event) {

		count++;
		int i = 0;
		int size;
		if (count > 1) {
			size = cmbCourse.getItems().size();
			while (i < size) {
				cmbCourse.getItems().remove(0);
				i++;
			}
			i = 0;
			size = tblExams.getItems().size();
			while (i < size) {
				tblExams.getItems().remove(0);
				i++;
			}
		}

		String selectedVaule = cmbSubject.getValue();
		String[] selectedSubject = selectedVaule.toLowerCase().split("-");
		newSubject = new Subject(selectedSubject[0], selectedSubject[1]);
		CourseMessage getCourseSubject = (CourseMessage) messageFact.getMessage("get-courses", newSubject);
		try {
			client.sendToServer(getCourseSubject);
		} catch (IOException e) {
			log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * A method that allows the teacher to select a course
	 * 
	 * @param event
	 * 
	 *            An event that happens when a teacher select from course combo box
	 */
	@FXML
	void selectCourse(ActionEvent event) {
		if (cmbCourse.getValue() != null) {
			String selectedVaule = cmbCourse.getValue();
			String[] selectedCourse = selectedVaule.toLowerCase().split("-");
			newCourse = new Course(selectedCourse[0], selectedCourse[1]);
			String id = newSubject.getSubjectID() + newCourse.getCourseId();
			ExamMessage newMessage = (ExamMessage) messageFact.getMessage("get-exams", id);
			try {
				client.sendToServer(newMessage);
			} catch (IOException e) {
				log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
				e.printStackTrace();
			}
		}

	}

	/**
	 * A method that allows the teacher to execute the exam
	 * 
	 * @param event
	 *            An event occurs when the teacher presses a `execute exam` button
	 * 
	 */
	@FXML
	void executeExam(ActionEvent event) {
		if (isInputValidExecuteExam()) {

			DateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			try {
				Date examDate = sdf.parse(txtFinish.getText());
				Date nowDate = sdf.parse(sdf.format(new Date()));

				if (nowDate.compareTo(examDate) >= 0) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.initOwner(mainApp);
					alert.setTitle("Invalid Fields");
					alert.setHeaderText("Please correct invalid fields");
					alert.setContentText("Time is invalid");
					alert.showAndWait();
					return;
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}

			ObservableList<Exam> examSelected;
			examSelected = tblExams.getSelectionModel().getSelectedItems();
			executeExam = examSelected.get(0);
			pass = generatePass();
			if (type.equals("manually")) {
				WordMessage newMessage = (WordMessage) messageFact.getMessage("get-word", executeExam);
				try {
					client.sendToServer(newMessage);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			ExecuteExam newExecuteExam = new ExecuteExam(executeExam.getExamId(), txtFinish.getText(), pass, type,
					teacher.getUserID());
			ExecuteExamMessage addExecuteExam = (ExecuteExamMessage) messageFact.getMessage("put-executeexam",
					newExecuteExam);
			try {
				client.sendToServer(addExecuteExam);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/**
	 * 
	 * A method that allows the teacher to select a type
	 * 
	 * @param event
	 * 
	 *            An event that happens when a teacher select from type combo box
	 */
	@FXML
	void selectType(ActionEvent event) {
		type = cmbExamType.getValue();
	}

	/**
	 *
	 * A method that is responsible for handling messages sent from the server
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof SubjectMessage) {
			SubjectMessage intialSubjectMessage = (SubjectMessage) arg1;
			teacherSubject = intialSubjectMessage.getTeacherSubject();
			for (Subject s : teacherSubject) {
				cmbSubject.getItems().add(s.getSubjectID() + "-" + s.getSubjectName());
			}
		}

		if (arg1 instanceof CourseMessage) {
			CourseMessage intialCourseMessage = (CourseMessage) arg1;
			CourseInSubject = intialCourseMessage.getCourses();
			for (Course c : CourseInSubject) {
				cmbCourse.getItems().add(c.getCourseId() + "-" + c.getCourseName());
			}
			cmbCourse.setDisable(false);
		}

		if (arg1 instanceof ExamMessage) {
			ExamMessage intialExamMessage = (ExamMessage) arg1;
			ArrayList<Exam> exams = intialExamMessage.getExams();
			ObservableList<Exam> observerseExams = FXCollections.observableArrayList();
			for (Exam e : exams)
				observerseExams.add(e);
			tblExams.setItems(observerseExams);

		}

		if (arg1 instanceof SimpleMessage) {
			SimpleMessage intialSimpleMessage = (SimpleMessage) arg1;
			if (intialSimpleMessage.getMsg().equals("ok-put-executeexam")) {
				ExamMessage setExam = (ExamMessage) messageFact.getMessage("set-exams", executeExam);
				try {
					client.sendToServer(setExam);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (intialSimpleMessage.getMsg().equals("ok-set-exams")) {
				log.writeToLog(LogLine.LineType.INFO, "Exam executed");
				Platform.runLater(() -> { // In order to run javaFX thread.(we recieve from server a java thread)
					try {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.initOwner(mainApp);
						alert.setTitle("Exam executed");
						alert.setHeaderText("Exam executed created successefully and his password is:" + pass);
						alert.setContentText("The exam was created to be executed successfully");
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

	}

	/**
	 * 
	 * 
	 * The method initializes the window when it comes up
	 * 
	 * @throws IOException
	 */
	@FXML
	public void initialize() throws IOException {
		messageFact = MessageFactory.getInstance();
		screenManager = ScreensManager.getInstance();
		dbk = DataKeepManager.getInstance();
		log = Log.getInstance();
		mainApp = screenManager.getPrimaryStage();
		tbcExamId.setCellValueFactory(new PropertyValueFactory("examId"));
		tbcTeacherId.setCellValueFactory(new PropertyValueFactory("author"));
		tbcDuration.setCellValueFactory(new PropertyValueFactory("examDuration"));
		client = (ObservableClient) DataKeepManager.getInstance().getObject_NoRemove("client");
		client.addObserver(this);
		cmbExamType.getItems().add("manually");
		cmbExamType.getItems().add("auto");
		teacher = dbk.getUser();
		SubjectMessage getTeacherSubject = (SubjectMessage) messageFact.getMessage("get-subjects", teacher.getUserID());
		client.sendToServer(getTeacherSubject);

	}

	/**
	 * Checks whether all fields are valid
	 * 
	 * @return true if all inputs valid and false if not
	 */
	private boolean isInputValidExecuteExam() {
		String errorMessage = "";

		if (cmbSubject.getSelectionModel().getSelectedItem() == null) {// || firstNameField.getText().length() == 0) {
			errorMessage += "Please Select a Subject!\n";
		}
		if (cmbCourse.getSelectionModel().getSelectedItem() == null) {
			errorMessage += "Please Select a Course!\n";
		}
		if (cmbExamType.getSelectionModel().getSelectedItem() == null) {
			errorMessage += "Please Select a Type!\n";
		}
		Exam exam = tblExams.getSelectionModel().getSelectedItem();
		if (exam == null) {
			errorMessage += "Please select exam from the table\n";
		}
		if (txtFinish.getText() == null || txtFinish.getText().length() == 0) {
			errorMessage += "start time is empty\n";
		} else {
			String[] check = txtFinish.getText().split(":");
			if (check.length != 3 || check[0].length() != 2 || check[1].length() != 2 || check[2].length() != 2)
				errorMessage += "No valid start time please enter(hh:mm:ss) like: 14:00:00\n";
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(mainApp);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMessage);
			alert.showAndWait();
			return false;
		}
	}

	/**
	 * 
	 * A method that generates code for the exam
	 * 
	 * @return the exam executed code
	 */
	public String generatePass() {
		String options = "abcdefghijklmnopqrstuvwxyz1234567890";
		StringBuilder password = new StringBuilder();
		Random rnd = new Random();
		while (password.length() < 4) {
			int index = (int) (rnd.nextFloat() * options.length());
			password.append(options.charAt(index));
		}
		String saltStr = password.toString();
		return saltStr;

	}

}