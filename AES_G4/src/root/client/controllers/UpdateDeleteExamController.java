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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ocsf.client.ObservableClient;
import root.client.managers.DataKeepManager;
import root.client.managers.ScreensManager;
import root.dao.app.Course;
import root.dao.app.Exam;
import root.dao.app.Subject;
import root.dao.app.User;
import root.dao.message.CourseMessage;
import root.dao.message.ExamMessage;
import root.dao.message.MessageFactory;
import root.dao.message.QuestionsMessage;
import root.dao.message.SimpleMessage;
import root.dao.message.SubjectMessage;
import root.util.log.Log;
import root.util.log.LogLine;

/**
 * A class that is responsible for choose exam for update window
 * 
 * @author Omer Haimovich
 *
 */
public class UpdateDeleteExamController implements Observer {

	// FXML variables **********************************************

	@FXML
	private ComboBox<String> cmbCourse;

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
	private GridPane gridPane;

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
	 * A list of all data in course combo box
	 */
	private ArrayList<String> courses;

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
	void SelectSubject(ActionEvent event) {
		int i = 0;
		int size;
		count++;
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
	void SelectCourse(ActionEvent event) {
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
	 * A method that allows the teacher to update an exam
	 * 
	 * @param event
	 *            An event occurs when the teacher presses a `update exam` button
	 * 
	 */
	@FXML
	void UpdateExam(ActionEvent event) {
		Exam exam = tblExams.getSelectionModel().getSelectedItem();
		if (exam == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(mainApp);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText("You did not select any exam");
			alert.showAndWait();
			return;
		}
		ObservableList<Exam> examSelected;
		examSelected = tblExams.getSelectionModel().getSelectedItems();
		Exam updateExam = examSelected.get(0);
		dbk.keepObject("updateExam", updateExam);
		try {
			screenManager.activate("updateExam");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
		}
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
			courses = new ArrayList<String>();
			for (Course c : CourseInSubject) {
				cmbCourse.getItems().add(c.getCourseId() + "-" + c.getCourseName());
				courses.add(c.getCourseId() + "-" + c.getCourseName());
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
		cmbCourse.setPromptText("Choose course");
		cmbSubject.setPromptText("Choose subject");
		teacher = dbk.getUser();
		SubjectMessage getTeacherSubject = (SubjectMessage) messageFact.getMessage("get-subjects", teacher.getUserID());
		client.sendToServer(getTeacherSubject);

	}

}