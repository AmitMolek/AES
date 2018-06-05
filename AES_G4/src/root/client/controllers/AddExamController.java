package root.client.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 *Class for add exam screen controller
 * @author Omer Haimovich
 *
 */
public class AddExamController {

    @FXML
    private TextArea txtFreeTeacher;

    @FXML
    private ComboBox<String> cmbCourse;

    @FXML
    private ComboBox<String> cmbSubject;

    @FXML
    private TextField txtScore;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Button btnAddExam;

    @FXML
    private ComboBox<String> cmbQuestion;

    @FXML
    private TextArea txtFreeStudent;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtTeacher;

    @FXML
    private Button btnAddQuestion;

    
    /**
     * Method the occurs when teacher select subject
     * @param event on action in subject combo box
     */
    @FXML
    void SelectSubject(ActionEvent event) {
    	
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
    	cmbCourse.setPromptText("Choose course");
    	cmbQuestion.setPromptText("Question");
    	cmbSubject.setPromptText("Choose subject");
    }
}