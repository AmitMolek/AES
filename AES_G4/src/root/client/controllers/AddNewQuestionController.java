package root.client.controllers;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import root.dao.app.Question;
import root.dao.app.Subject;
import root.dao.app.User;

public class AddNewQuestionController {

    @FXML
    private Label lblSubject;

    @FXML
    private Button btnSaveAndClose;

    @FXML
    private Button btnCancel;

    @FXML
    private Label lblQuestionID;

    @FXML
    private TextArea txtFieldText;

    @FXML
    private TextArea txtFieldQuestionInfo;

    @FXML
    private TextArea txtFieldPossibleAnswer1;

    @FXML
    private TextArea txtFieldPossibleAnswer2;

    @FXML
    private TextArea txtFieldPossibleAnswer3;

    @FXML
    private TextArea txtFieldPossibleAnswer4;

    @FXML
    private Label lblAssemblerID;
    
    @FXML
    private ComboBox<Subject> comboboxSubjects;
    
    @FXML
    private ComboBox<Integer> comboboxCorrectQuestion;
    
	private ObservableList<Question> appMainObservableList;
	private ArrayList<Subject> userSubjects;
	private User user;
	
	
	
    @FXML
    void cancle(ActionEvent event) {
    	closeStage(event);
    }

    @FXML
    void saveAndClose(ActionEvent event) {
    	System.out.println("btnAddPersonClicked");

    	String questionId = comboboxSubjects.getSelectionModel().getSelectedItem().getSubjectID();	// Only parsial Question ID. will fill that back in Question window
    	String questionText  = txtFieldText.getText();
    	String questionIntruction = txtFieldQuestionInfo.getText();
    	String ans1 = txtFieldPossibleAnswer1.getText();
    	String ans2 = txtFieldPossibleAnswer2.getText();
    	String ans3 = txtFieldPossibleAnswer3.getText();
    	String ans4 = txtFieldPossibleAnswer4.getText();
    	int correctAns = comboboxCorrectQuestion.getSelectionModel().getSelectedIndex();
    	String teacherAssembeld = user.getUserID();	
        
    	
        Question newQuestion = new Question(questionId,questionText,questionIntruction,ans1,ans2,ans3,ans4,correctAns,teacherAssembeld);
        appMainObservableList.add(newQuestion);
        
        closeStage(event);
    }
    
    public void setAppMainObservableList(ObservableList<Question> tvObservableList) {
        this.appMainObservableList = tvObservableList;   
    }

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @param userSubjects the userSubjects to set
	 */
	public void setUserSubjects(ArrayList<Subject> userSubjects) {
		this.userSubjects = userSubjects;
		ObservableList<Subject> observableSubjects = FXCollections.observableArrayList(userSubjects);
		comboboxSubjects.getItems().addAll(observableSubjects);
		
	}

	private void closeStage(ActionEvent event) {
        Node  source = (Node)  event.getSource(); 
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

}

/*
 *package customdialog;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddPersonDialogController  {
    
    @FXML
    private TextField tfId;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfAge;
    
    private ObservableList<Person> appMainObservableList;

    @FXML
    void btnAddPersonClicked(ActionEvent event) {
        System.out.println("btnAddPersonClicked");
        int id = Integer.valueOf(tfId.getText().trim());
        String name = tfName.getText().trim();
        int iAge = Integer.valueOf(tfAge.getText().trim());
        
        Person data = new Person(id, name, iAge);
        appMainObservableList.add(data);
        
        closeStage(event);
    }

    public void setAppMainObservableList(ObservableList<Person> tvObservableList) {
        this.appMainObservableList = tvObservableList;
        
    }

    private void closeStage(ActionEvent event) {
        Node  source = (Node)  event.getSource(); 
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

} 
 */
