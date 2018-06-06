package root.client.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import ocsf.client.ObservableClient;
import root.dao.app.Course;
import root.dao.app.Exam;
import root.dao.app.Question;
import root.dao.app.QuestionInExam;
import root.dao.app.Subject;
import root.dao.app.User;
import root.dao.message.CourseMessage;
import root.dao.message.MessageFactory;
import root.dao.message.QuestionsMessage;
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
    	private Button btnAddToExam;
		
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
    private ArrayList<QuestionInExam> examQuestions;
    private ArrayList<Question> question;
    private AddQuestionToExam newQuestion;
    private static int countId = 10;
    private Subject newSubject;
    private Course newCourse;
    /**
     * Method the occurs when teacher select subject
     * @param event on action in subject combo box
     */
    @FXML
    void SelectSubject(ActionEvent event) {
    	cmbCourse.getItems().clear();
    	String selectedVaule = cmbSubject.getValue();
    	String[] selectedSubject = selectedVaule.toLowerCase().split("-");
    	newSubject = new Subject(selectedSubject[0],selectedSubject[1]);
    	CourseMessage getCourseSubject = (CourseMessage) messageFact.getMessage("get-courses", newSubject);
    	QuestionsMessage getQuestionOfsubject = (QuestionsMessage) messageFact.getMessage("get-questions", newSubject);
    	try {
			client.sendToServer(getCourseSubject);
			client.sendToServer(getQuestionOfsubject);
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
    	String selectedVaule = cmbCourse.getValue();
    	String[] selectedCourse = selectedVaule.toLowerCase().split("-");
    	newCourse = new Course(selectedCourse[0],selectedCourse[1]);
    	btnAddQuestion.setDisable(false);
    }


    /**
     * Method the occurs when teacher press on the + button
     * @param event on action in + button
     */

    @FXML
    void AddQuestionToExam(ActionEvent event) {
    	newQuestion = new AddQuestionToExam();
    	newQuestion.setQuestionCombo(question);
    	myFlow.getChildren().add(newQuestion);
    	btnAddToExam.setDisable(false);
    	btnAddQuestion.setDisable(true);
    		
    }
  
    /**
     * Method the occurs when teacher press on add exam button
     * @param event on action in add exam button
     */
    @FXML
    void AddExam(ActionEvent event) {
    	String ExamId = newSubject.getSubjectID() + newCourse.getCourseId() + Integer.toString(countId);
    	countId++;
    	String examDuration = txtDuration.getText();
    	int duration = Integer.parseInt(examDuration); 
    	Exam newExam = new Exam(ExamId,teacher,duration,examQuestions);
    	
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
    	cmbSubject.setPromptText("Choose subject");
    	teacher = new User("204403257","omer","haimovich" ,"12345","teacher");
    	txtTeacher.setText(teacher.getUserFirstName() + " " +  teacher.getUserLastName());
    	txtTeacher.setDisable(true);
    	btnAddToExam.setDisable(true);
    	btnAddQuestion.setDisable(true);
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
		
		if(arg1 instanceof QuestionsMessage) {
			QuestionsMessage intialQuestionMessage = (QuestionsMessage)arg1;
			question = intialQuestionMessage.getQuestions();
		}
		
	}
	
	/**
	 * This method occurs when the teacher press on the add exam form button
	 * @param event on action when the teacher press add exam form button
	 */
    @FXML
    void AddToTheExamForm(ActionEvent event) {
    	examQuestions = newQuestion.AddQuestion();
    	btnAddQuestion.setDisable(false);
    	btnAddToExam.setDisable(true);
    }
    
    
}