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

public class UpdateDeleteExamController implements Observer {

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

	private MessageFactory messageFact;
	private ObservableClient client;
	private ScreensManager screenManager;
	private Log log;
	private User teacher;
	private ArrayList<Subject> teacherSubject;
	private Subject newSubject;
	private ArrayList<Course> CourseInSubject;
	private Course newCourse;
	private DataKeepManager dbk;
	private Stage mainApp;
	private ArrayList<String> courses;

	/**
	 * Method that occurs when teacher select subject
	 * 
	 * @param event
	 *            on action in subject combo box
	 */
	@FXML
	void SelectSubject(ActionEvent event) {
		if (cmbCourse.getItems().size() != 0)
			cmbCourse.getItems().removeAll(courses);
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
	 * Method that occurs when teacher select course
	 * 
	 * @param event
	 *            on action in course combo box
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
	 * Method that occurs when teacher press update button
	 * 
	 * @param event
	 *            on action in update button
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
	 * This method occurs when the server send message to the client
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

		if (arg1 instanceof SimpleMessage) {
			SimpleMessage simple = (SimpleMessage) arg1;
			log.writeToLog(LogLine.LineType.INFO, "Exam deleted");
			Platform.runLater(() -> { // In order to run javaFX thread.(we recieve from server a java thread)
				try {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.initOwner(mainApp);
					alert.setTitle("Exam deleted");
					alert.setHeaderText("Exam deleted successeful");
					alert.setContentText("The exam was deleted successful");
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