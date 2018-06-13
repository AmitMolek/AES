package root.client.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
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
import root.dao.message.SubjectMessage;
import root.util.log.Log;

public class ManuallyExamController implements Observer {

    @FXML
    private Button btnGetExam;

    @FXML
    private Label lblFirstName;

    @FXML
    private Label lblQuestionNum;

    @FXML
    private Pane lblLastName;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblSubject;

    @FXML
    private Label lblExamId;

    @FXML
    private Label lblCourse;

    @FXML
    private Button btnSubmit;

    private User teacher;
	private MessageFactory messageFact;
	private ObservableClient client;
	private Log log;
	private ScreensManager screenManager;
	private DataKeepManager dkm;
	private Stage mainApp;
	
	
	/**
	 * This method occurs when the window is shown up.
	 * 
	 * @throws IOException
	 *             if the window cannot be shown
	 */
	@FXML
	public void initialize() throws IOException {
		log = Log.getInstance();
		dkm = DataKeepManager.getInstance();
		screenManager = ScreensManager.getInstance();
		mainApp = screenManager.getPrimaryStage();
		client = new ObservableClient("localhost", 8000);
		client.addObserver(this);
		client.openConnection();
		messageFact = MessageFactory.getInstance();
		teacher = dkm.getUser();
		SubjectMessage getTeacherSubject = (SubjectMessage) messageFact.getMessage("get-subjects", teacher.getUserID());
		client.sendToServer(getTeacherSubject);
	}

	
    /**
     * Method that occurs when Student press get exam button
     * @param event on action when student press get exam button
     */
    @FXML
    void getExamDocument(ActionEvent event) {

    }

    /**
     * Method that occurs when student press submit button
     * @param event
     */
    @FXML
    void SubmitExam(ActionEvent event) {

    }


    /**
	 * This method occurs when the server send message to the client
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

}

