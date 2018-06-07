package root.client.controllers;

import java.io.IOException;
import java.util.ArrayList;

//import ch.makery.address.util.DateUtil;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import root.dao.app.Question;
import root.dao.app.Subject;
import root.dao.app.User;
import root.util.log.Log;
import root.util.log.LogLine;

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
	//private ArrayList<Subject> userSubjects;
	private User user;
	Log log = Log.getInstance();

	private Stage mainApp;
	
	
    @FXML
    void cancle(ActionEvent event) {
    	closeStage(event);
    }

    @FXML
    void saveAndClose(ActionEvent event) {
    	Platform.runLater(() -> {	
	    	System.out.println("btnAddPersonClicked");
	    	if (isInputValid()) {
		    	String questionId = comboboxSubjects.getSelectionModel().getSelectedItem().getSubjectID();	// Only partial Question ID. will fill that back in Question window
		    	String questionText  = txtFieldText.getText();
		    	String questionIntruction = txtFieldQuestionInfo.getText();
		    	String ans1 = txtFieldPossibleAnswer1.getText();
		    	String ans2 = txtFieldPossibleAnswer2.getText();
		    	String ans3 = txtFieldPossibleAnswer3.getText();
		    	String ans4 = txtFieldPossibleAnswer4.getText();
		    	int correctAns = comboboxCorrectQuestion.getSelectionModel().getSelectedItem();
		    	String teacherAssembeld = user.getUserID();	
		        
		    	
		        Question newQuestion = new Question(questionId,questionText,questionIntruction,ans1,ans2,ans3,ans4,correctAns,teacherAssembeld);
		        
		        appMainObservableList.add(newQuestion);
		        
		        closeStage(event);
	    	}
    	});
    }
    
    private boolean isInputValid() {
    	String errorMessage = "";

        if (comboboxSubjects.getSelectionModel().getSelectedItem() == null) {// || firstNameField.getText().length() == 0) {
            errorMessage += "Please Select a Subject!\n"; 
        }
        if (txtFieldText.getText() == null || txtFieldText.getText().length() == 0) {
            errorMessage += "No valid Question text!\n"; 
        }
        if (txtFieldPossibleAnswer1.getText() == null || txtFieldPossibleAnswer1.getText().length() == 0) {
            errorMessage += "No valid Answeer in Field: 1!\n"; 
        }

        if (txtFieldPossibleAnswer2.getText() == null || txtFieldPossibleAnswer2.getText().length() == 0) {
            errorMessage += "No valid Answeer in Field: 2!\n"; 
        }
        if (txtFieldPossibleAnswer3.getText() == null || txtFieldPossibleAnswer3.getText().length() == 0) {
            errorMessage += "No valid Answeer in Field: 3!\n"; 
        }
        if (txtFieldPossibleAnswer4.getText() == null || txtFieldPossibleAnswer4.getText().length() == 0) {
            errorMessage += "No valid Answeer in Field: 4!\n"; 
        }
        if (comboboxCorrectQuestion.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Please Select a correct answer!\n"; 
        }/*else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(postalCodeField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid postal code (must be an integer)!\n"; 
            }
        }

        if (cityField.getText() == null || cityField.getText().length() == 0) {
            errorMessage += "No valid city!\n"; 
        }

        if (birthdayField.getText() == null || birthdayField.getText().length() == 0) {
            errorMessage += "No valid birthday!\n";
        } else {
            if (!DateUtil.validDate(birthdayField.getText())) {
                errorMessage += "No valid birthday. Use the format dd.mm.yyyy!\n";
            }
        }
         */
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

	public void setAppMainObservableList(ObservableList<Question> tvObservableList) {
        this.appMainObservableList = tvObservableList;   
    }

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
		lblAssemblerID.setText(user.getUserFirstName()+" "+ user.getUserLastName());
	}

	/**
	 * @param observableSubjects2 the userSubjects to set
	 */
	public void setUserSubjects(ArrayList<Subject> userSubjects) {
	//	this.userSubjects = userSubjects;
		comboboxCorrectQuestion.getItems().addAll(1,2,3,4);
		ObservableList<Subject> observableSubjects = FXCollections.observableArrayList(userSubjects);
		comboboxSubjects.getItems().addAll(observableSubjects);
		
	}

	private void closeStage(ActionEvent event) {
		Platform.runLater(() -> {				// In order to run javaFX thread.(we recieve from server a java thread)
			
				Node  source = (Node)  event.getSource(); 
		        Stage stage  = (Stage) source.getScene().getWindow();
		        stage.close();
			
		});
		
    }

	public void setMainApp(Stage stage) {
		// TODO Auto-generated method stub
		mainApp = stage;
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
