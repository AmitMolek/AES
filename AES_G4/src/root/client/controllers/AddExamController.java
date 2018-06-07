package root.client.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import ocsf.client.ObservableClient;
import root.dao.app.Course;
import root.dao.app.Subject;
import root.dao.app.User;
import root.dao.message.CourseMessage;
import root.dao.message.MessageFactory;
import root.dao.message.SubjectMessage;
import root.util.log.Log;
import root.util.log.LogLine;

/**
 *Class for add exam screen controller
 * @author Omer Haimovich
 *
 */
public class AddExamController implements Observer {

	
	 	@FXML
	    private FlowPane myFlow;

	    @FXML
	    private ComboBox<String> cmbCourse;

	    @FXML
	    private ComboBox<String> cmbSubject;

	    @FXML
	    private AnchorPane rootPane;

	    @FXML
	    private Button btnAddExam;

	    @FXML
	    private TextField txtDuration;

	    @FXML
	    private TextField txtTeacher;

	    @FXML
	    private Button btnAddQuestion;
    
    private User teacher;
    private MessageFactory messageFact;
    private ObservableClient client;
    private ArrayList<Subject> teacherSubject;
    private ArrayList<Course> CourseInSubject;
    private Log log;
    /**
     * Method the occurs when teacher select subject
     * @param event on action in subject combo box
     */
    @FXML
    void SelectSubject(ActionEvent event) {
    	cmbCourse.getItems().clear();
    	String selectedVaule = cmbSubject.getValue();
    	String[] selectedSubject = selectedVaule.toLowerCase().split("-");
    	Subject newSubject = new Subject(selectedSubject[0],selectedSubject[1]);
    	CourseMessage getCourseSubject = (CourseMessage) messageFact.getMessage("get-courses", newSubject);
    	try {
			client.sendToServer(getCourseSubject);
		} catch (IOException e) {
			log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
			e.printStackTrace();
		}
    }

    /**
     * Method the occurs when teacher select course
     * @param event on action in course combo box
     */
    @FXML
    void SelectCourse(ActionEvent event) {
    	
    }

    /**
     * Method the occurs when teacher select question
     * @param event on action in question combo box
     */
    @FXML
    void SelectQuestion(ActionEvent event) {

    }
    /**
     * Method the occurs when teacher press on the + button
     * @param event on action in + button
     */

    @FXML
    void AddQuestionToExam(ActionEvent event) {
    	myFlow.getChildren().add(new AddQuestionToExam());
    }
  
    /**
     * Method the occurs when teacher press on add exam button
     * @param event on action in add exam button
     */
    @FXML
    void AddExam(ActionEvent event) {

    }
    /**
   	 * This method occurs when the window is shown up.
     * @throws IOException if the window cannot be shown
     */
    @FXML
	public void initialize() throws IOException{
    	log = Log.getInstance();
    	cmbCourse.setDisable(true);
    	client = new ObservableClient("localhost",8000);
    	client.addObserver(this);
    	client.openConnection();
    	messageFact = MessageFactory.getInstance();
    	cmbCourse.setPromptText("Choose course");
    	//cmbQuestion.setPromptText("Question");
    	cmbSubject.setPromptText("Choose subject");
    	teacher = new User("204403257","omer","haimovich" ,"12345","teacher");
    	txtTeacher.setText(teacher.getUserFirstName() + " " +  teacher.getUserLastName());
    	txtTeacher.setDisable(true);
    	SubjectMessage getTeacherSubject = (SubjectMessage) messageFact.getMessage("get-subjects", teacher.getUserID());
    	client.sendToServer(getTeacherSubject);
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
				cmbSubject.getItems().add(s.getSubjectID() + " - " + s.getSubjectName());
			}
		}
		
		if(arg1 instanceof CourseMessage) {
			CourseMessage intialCourseMessage = (CourseMessage)arg1;
			CourseInSubject = intialCourseMessage.getCourses();
			for(Course c: CourseInSubject ) {
				cmbCourse.getItems().add(c.getCourseId() + " - " + c.getCourseName());
			}
			cmbCourse.setDisable(false);
		}
		
	}
}