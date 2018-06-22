package root.client.controllers;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import root.dao.app.Question;
import root.dao.app.Subject;
import root.dao.app.User;
import root.util.log.Log;
import root.util.log.LogLine;
/**
 * 
 * 
 * 
 * A class that is responsible for adding an question window
 * 
 * @author Gal Brandwine
 *
 */
public class AddNewQuestionController {

	// FXML variables **********************************************

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

	// Instance variables **********************************************

	/**
	 * The observable that contain the Questions, to show in the addQuestionWizzard
	 */
	private ObservableList<Question> appMainObservableList;
	
	/**
	 * The Arraylist that contains all relevant subjects
	 */
	private ArrayList<Subject> userSubjects;
	
	/**
	 * The user 
	 */
	private User user;
	/**
	 * 
	 * A log file that is responsible for documenting the actions performed in the
	 * application
	 */
	Log log = Log.getInstance();

	/**
	 * the MainApp stage, that needed for getting stage controll
	 */
	private Stage mainApp;

	// CLASS METHODS *************************************************

	/**
	 * this method handle pressing the "cancle" button, in order to close the window
	 * @param event
	 * 				pressing the "cancle" button
	 */
	@FXML
	void cancle(ActionEvent event) {
		closeStage(event);
	}

	/**
	 * This method handle the saving new question
	 * @param event
	 * 				triggered when pressing the "save & close" button
	 */
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

	/**
	 * this private method handle input datafrom filled fields
	 * @return	bool
	 * 				true if data filled correctly, false otherwise
	 */
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
	 * this mthod called when passing the question we want to edit
	 * @param tvObservableList
	 * 					the wuestion we want to edit
	 */
	public void setAppMainObservableList(ObservableList<Question> tvObservableList) {
		this.appMainObservableList = tvObservableList;   
	}

	/**
	 * this method sets the user
	 * 
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	/**
	 * this method set the relevant user subjects
	 * 
	 * @param observableSubjects2 the userSubjects to set
	 */
	public void setUserSubjects(ArrayList<Subject> userSubjects) {
		this.userSubjects = userSubjects;
		comboboxCorrectQuestion.getItems().addAll(1,2,3,4);
		ObservableList<Subject> observableSubjects = FXCollections.observableArrayList(userSubjects);
		comboboxSubjects.getItems().addAll(observableSubjects);

	}

	/**
	 * this mthod handle closing the window, and going back to the main app
	 * 
	 * @param event
	 * 				when pressing "cancle" or "save & close"
	 */
	private void closeStage(ActionEvent event) {
		Platform.runLater(() -> {				// In order to run javaFX thread.(we recieve from server a java thread)	
			Node  source = (Node)  event.getSource(); 
			Stage stage  = (Stage) source.getScene().getWindow();
			stage.close();	
		});	
	}

	/**
	 * this method called when oppening this window
	 * 
	 * @param stage
	 * 				the primary stage is passed in order to gain controll over the MainApp stage
	 */
	public void setMainApp(Stage stage) {
		// TODO Auto-generated method stub
		mainApp = stage;
	}

	/**
	 * This method is called when updating a question
	 * when pressing in mainapp "edit" the relevant question to edit is being loaded into the fields
	 * 
	 * @param selectedQuestionToEdit
	 * 					the relevant question to edit					
	 */
	public void setQuestion(Question selectedQuestionToEdit) {

		txtFieldText.setText(selectedQuestionToEdit.getQuestionText());
		txtFieldQuestionInfo.setText(selectedQuestionToEdit.getIdquestionIntruction());
		txtFieldPossibleAnswer1.setText(selectedQuestionToEdit.getAns1());
		txtFieldPossibleAnswer2.setText(selectedQuestionToEdit.getAns2());
		txtFieldPossibleAnswer3.setText(selectedQuestionToEdit.getAns3());
		txtFieldPossibleAnswer4.setText(selectedQuestionToEdit.getAns4());

	}
}

