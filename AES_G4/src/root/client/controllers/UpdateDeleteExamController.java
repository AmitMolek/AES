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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import ocsf.client.ObservableClient;
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
    
    private MessageFactory messageFact;
    private ObservableClient client;
    private ScreensManager screenManager;
    private Log log;
    private User teacher;
    private ArrayList<Subject> teacherSubject;
    private Subject newSubject;
    private ArrayList<Course> CourseInSubject;
    private Course newCourse;
    
    /**
     * Method that occurs when teacher select subject
     * @param event on action in subject combo box
     */
    @FXML
    void SelectSubject(ActionEvent event) {
    	cmbCourse.getItems().clear();
    	String selectedVaule = cmbSubject.getValue();
    	String[] selectedSubject = selectedVaule.toLowerCase().split("-");
    	newSubject = new Subject(selectedSubject[0],selectedSubject[1]);
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
     * @param event on action in course combo box
     */
    @FXML
    void SelectCourse(ActionEvent event) {
    	String selectedVaule = cmbCourse.getValue();
    	String[] selectedCourse = selectedVaule.toLowerCase().split("-");
    	newCourse = new Course(selectedCourse[0],selectedCourse[1]);
    	String id = newSubject.getSubjectID() + newCourse.getCourseId();
    	ExamMessage newMessage = (ExamMessage)messageFact.getMessage("get-exams", id);
    	try {
			client.sendToServer(newMessage);
		} catch (IOException e) {
			log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
			e.printStackTrace();
		}
    	
    }

    /**
     * Method that occurs when teacher press update button
     * @param event on action in update button
     */
    @FXML
    void UpdateExam(ActionEvent event) {

    }

    /**
     * Method that occurs when teacher press Delete button
     * @param event on action in delete button
     */
    @FXML
    void DeleteExam(ActionEvent event) {
    	ObservableList<Exam> examSelected;
    	examSelected = tblExams.getSelectionModel().getSelectedItems();
    	Exam removeExam = examSelected.get(0);
    	ExamMessage deleteExam = (ExamMessage)messageFact.getMessage("delete-exams", removeExam);
    	try {
			client.sendToServer(deleteExam);
		} catch (IOException e) {
			log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
			e.printStackTrace();
		}
    }


	
	
	/**
	 * This method occurs when the server send message to the client
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg1 instanceof SubjectMessage) {
			SubjectMessage intialSubjectMessage = (SubjectMessage)arg1;
			teacherSubject = intialSubjectMessage.getTeacherSubject();
			for(Subject s: teacherSubject ) {
				cmbSubject.getItems().add(s.getSubjectID() + "-" + s.getSubjectName());
			}
		}
		
		if(arg1 instanceof CourseMessage) {
			CourseMessage intialCourseMessage = (CourseMessage)arg1;
			CourseInSubject = intialCourseMessage.getCourses();
			for(Course c: CourseInSubject ) {
				cmbCourse.getItems().add(c.getCourseId() + "-" + c.getCourseName());
			}
			cmbCourse.setDisable(false);
		}
		
		if(arg1 instanceof ExamMessage) {
			ExamMessage intialCourseMessage = (ExamMessage)arg1;
			ArrayList<Exam> exams = intialCourseMessage.getExams();
			ObservableList<Exam> observerseExams = FXCollections.observableArrayList();
			for(Exam e: exams)
				observerseExams.add(e);
			tblExams.setItems(observerseExams);

		}
		
		if(arg1 instanceof SimpleMessage) {
			SimpleMessage simple = (SimpleMessage)arg1;
			log.writeToLog(LogLine.LineType.INFO, "Exam deleted");
			Platform.runLater(() -> {				// In order to run javaFX thread.(we recieve from server a java thread)
				try {
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
     * @throws IOException if the window cannot be shown
     */
    @FXML
	public void initialize() throws IOException{
    	messageFact = MessageFactory.getInstance();
    	screenManager = ScreensManager.getInstance();
    	log = Log.getInstance();
    	tbcExamId.setCellValueFactory(new PropertyValueFactory("examId"));
    	tbcTeacherId.setCellValueFactory(new PropertyValueFactory("author"));
    	tbcDuration.setCellValueFactory(new PropertyValueFactory("examDuration"));
    	client = new ObservableClient("localhost", 8000);
    	client.addObserver(this);
    	client.openConnection();
    	cmbCourse.setPromptText("Choose course");
    	cmbSubject.setPromptText("Choose subject");
    	teacher = new User("204403257","omer","haimovich" ,"12345","teacher");
    	SubjectMessage getTeacherSubject = (SubjectMessage) messageFact.getMessage("get-subjects", teacher.getUserID());
    	client.sendToServer(getTeacherSubject);
    	
    }

}