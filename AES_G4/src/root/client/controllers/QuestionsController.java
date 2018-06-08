package root.client.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import org.controlsfx.control.textfield.TextFields;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import ocsf.client.ObservableClient;
import root.client.managers.DataKeepManager;
import root.client.managers.ScreensManager;
import root.dao.app.LoginInfo;
import root.dao.app.Question;
import root.dao.app.Subject;
import root.dao.app.User;
import root.dao.message.LoginMessage;
import root.dao.message.MessageFactory;
import root.dao.message.QuestionsMessage;
import root.dao.message.SimpleMessage;
import root.dao.message.UserMessage;
import root.dao.message.UserSubjectMessage;
import root.util.log.Log;
import root.util.log.LogLine;
//import tableButtonTest.Main.Record;
//import tableButtonTest.Main.ButtonCell;
//import tableButtonTest.Mai//n.Record;


public class QuestionsController implements Observer{
	
	/**
	 * This class handle with questions window
	 * @author gal
	 */

    @FXML // fx:id="rootPane"
    private AnchorPane rootPane; // Value injected by FXMLLoader

    @FXML // fx:id="tblQuestions"
    private TableView<Question> tblQuestions; // Value injected by FXMLLoader

    @FXML // fx:id="tbcId"
    private TableColumn<Question, String> tbcId; // Value injected by FXMLLoader

    @FXML // fx:id="tbcIdNum"
    private TableColumn<Question, String> tbcIdInstruction; // Value injected by FXMLLoader

    @FXML // fx:id="tbcName"
    private TableColumn<Question, String> tbcTeacherName; // Value injected by FXMLLoader

    @FXML // fx:id="tbcIdText"
    private TableColumn<Question, String> tbcIdText; // Value injected by FXMLLoader

    @FXML // fx:id="tbcAns1"
    private TableColumn<Question, String> tbcAns1; // Value injected by FXMLLoader

    @FXML // fx:id="tbcAns2"
    private TableColumn<Question, String> tbcAns2; // Value injected by FXMLLoader

    @FXML // fx:id="tbcAns3"
    private TableColumn<Question, String> tbcAns3; // Value injected by FXMLLoader

    @FXML // fx:id="tbcAns4"
    private TableColumn<Question, String> tbcAns4; // Value injected by FXMLLoader

    @FXML // fx:id="tbcCorr"
    private TableColumn<Question, Integer> tbcCorr; // Value injected by FXMLLoader


    @FXML // fx:id="txtFieldId"
    private TextField txtFieldId; // Value injected by FXMLLoader

    @FXML // fx:id="txtFieldName"
    private TextField txtFieldName; // Value injected by FXMLLoader

    @FXML // fx:id="txtFieldQuestion"
    private TextField txtFieldQuestion; // Value injected by FXMLLoader

    @FXML // fx:id="subjectCombobox"
    private ComboBox<Subject> subjectCombobox; // Value injected by FXMLLoader

    @FXML // fx:id="btnSearch"
    private Button btnSearch; // Value injected by FXMLLoader

    @FXML // fx:id="rstNameLbl"
    private Label fstNameLbl; // Value injected by FXMLLoader

    @FXML // fx:id="lstNamelbl"
    private Label lstNamelbl; // Value injected by FXMLLoader

    @FXML // fx:id="teacherIDLbl"
    private Label teacherIDLbl; // Value injected by FXMLLoader

    @FXML // fx:id="TeacherPremissionLbl"
    private Label TeacherPremissionLbl; // Value injected by FXMLLoader
    
    @FXML
    private Button deleteQuestion;

    @FXML
    private Button newQuestion;

    @FXML
    private Button editQuestion;
    
    private ObservableList<Subject> observableSubjects;
    private ObservableList<Question> observabaleQuestions;
    private ObservableList<Question> observebaleNewQuestion;
    private ObservableClient client;
    private Map<String,Integer> newValues;
	private ArrayList<Question> questions;
    private MessageFactory message;
    private User user;
    private ScreensManager screenManager;
	private ArrayList<Subject> userSubjects;
	Log log = Log.getInstance();
  

	public QuestionsController() {
		super();
	
	}

	@FXML
    void selectFromCombobox(ActionEvent event) {
		Subject selectedSucjet = subjectCombobox.getSelectionModel().getSelectedItem();
		observabaleQuestions.clear();
		if (selectedSucjet.getSubjectID().equals("00"))observabaleQuestions.addAll(questions);
		else {
			for(Question question: questions) {
				if (question.getQuestionId().substring(0, 2).equals(selectedSucjet.getSubjectID()) ) {
					observabaleQuestions.add(question);
				}
			}
		}
		
    }
	
	   
	@FXML
 	void searchQuestion(ActionEvent event) {
//		tblQuestions.getItems().clear();
//		String questionId = txtFieldId.getText();
//		String questionName = txtFieldName.getText();
//		String questionIns = txtFieldQuestion.getText();
//		ObservableList<Question> queryQuestions = FXCollections.observableArrayList();
//		if (questionId != null) {
//			for (int i = 0; i < questions.size(); i++) {
//				Question q = questions.get(i);
//				if (q.getId().equals(questionId)) {
//					queryQuestions.add(q);
//				}
//			}
//
//		}
//
//		if (questionName != null) {
//			for (int i = 0; i < questions.size(); i++) {
//				Question q = questions.get(i);
//				if (q.getTeacherName().equals(questionName) && (!queryQuestions.contains(q))) {
//					queryQuestions.add(q);
//				}
//			}
//
//		}
//
////		if (questionIns != null) {
////			for (int i = 0; i < questions.size(); i++) {
////				Question q = questions.get(i);
////				if (q.getQuestionIns().equals(questionIns) && (!queryQuestions.contains(q))) {
////					queryQuestions.add(q);
////				}
////			}
////
////		}
	}
		
	/**
     * This method occurs when the window is shown up.
     * @throws IOException if the window cannot be shown
     */
    @FXML
	public void initialize() throws IOException{
    	Platform.runLater(() -> rootPane.requestFocus());
    	message = MessageFactory.getInstance();
    	screenManager = ScreensManager.getInstance();
    	client = new ObservableClient("localhost", 8000);
    	client.addObserver(this);
    	client.openConnection();
    	user = (User) DataKeepManager.getInstance().getUser();//loggedInManager.getUser();
    	questions = new ArrayList<Question>();
    	
    	// Listen for selection changes and show the person details when changed.
    	txtFieldId.setOnMouseClicked(e -> {
    		btnSearch.setDisable(false);
        });
    	// Listen for selection changes and show the person details when changed.
    	txtFieldName.setOnMouseClicked(e -> {
    		btnSearch.setDisable(false);
        });
    	// Listen for selection changes and show the person details when changed.
    	txtFieldQuestion.setOnMouseClicked(e -> {
    		btnSearch.setDisable(false);
        });
    	// Listen for selection changes and show the person details when changed.
    	tblQuestions.setOnMouseClicked(e -> {
    		editQuestion.setDisable(false);
        });
    	
    	editQuestion.setDisable(true);
    	btnSearch.setDisable(true);
    	
    	setUserDetails(user);
    	getUserSubjects(user);
    	initQuestionsTable();
 
    }

	private void initQuestionsTable() {
		// TODO Auto-generated method stub
		tbcId.setCellValueFactory(new PropertyValueFactory<Question, String>("questionId"));
		tbcIdText.setCellValueFactory(new PropertyValueFactory<Question, String>("questionText"));
		tbcIdInstruction.setCellValueFactory(new PropertyValueFactory<Question, String>("idquestionIntruction"));
		tbcAns1.setCellValueFactory(new PropertyValueFactory<Question, String>("ans1"));
		tbcAns2.setCellValueFactory(new PropertyValueFactory<Question, String>("ans2"));
		tbcAns3.setCellValueFactory(new PropertyValueFactory<Question, String>("ans3"));
		tbcAns4.setCellValueFactory(new PropertyValueFactory<Question, String>("ans4"));
		tbcCorr.setCellValueFactory(new PropertyValueFactory<Question, Integer>("correctAns"));
		tbcTeacherName.setCellValueFactory(new PropertyValueFactory<Question, String>("teacherAssembeld"));

		}
	
	/**
	 * This method will set a edit option
	 * @param event
	 */
	@FXML
    void editSelectedQuestion(ActionEvent event) {
		int selectedIndex = tblQuestions.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
        	runNewQuestionWizzard(tblQuestions.getSelectionModel().getSelectedItem());
        	tblQuestions.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(screenManager.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No question Selected");
            alert.setContentText("Please select a questionin the table.");

            alert.showAndWait();
        }
    }
	
	/**
	 * This method happens when the user press on the delete button 
	 * @param event
	 */
    @FXML
    void deleteSelectedQuestion(ActionEvent event) {
    	Question questionToDelete;
    	int selectedIndex = tblQuestions.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {  	
        	questionToDelete = tblQuestions.getSelectionModel().getSelectedItem();
        	deleteQuestionFromDB(questionToDelete);				// remove question from DB
        	tblQuestions.getItems().remove(selectedIndex);		// remove question from tableview
        	questions.remove(questionToDelete);					// remove question from THIS.questions
        	
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(screenManager.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No question Selected");
            alert.setContentText("Please select a question in the table.");

            alert.showAndWait();
        }
    }
    
    /**
     * this method called when deleting Question from DB
     * @param questionToDelete
     */
	private void deleteQuestionFromDB(Question questionToDelete) {
		// TODO Auto-generated method stub
		QuestionsMessage questionDeleteMessage = (QuestionsMessage) message.getMessage("delete-Questions",questionToDelete);	// we can send the specific question because we have table "Questions"
    	try {
			client.sendToServer(questionDeleteMessage);
		} catch (IOException e) {
			e.printStackTrace();
			log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
		}
	}

	
	/**
	 *  if NEW Question pressed, open NewQuestionWizzard, than, after pressing "Save&Close" 
	 *  add newly created question to Questions in THIS, and to DB.
	 * @param event
	 * @throws IOException
	 */
	 @FXML
	 void newQuestionDialog(ActionEvent event) throws IOException {
		 runNewQuestionWizzard(null);
	}
	
	private void runNewQuestionWizzard(Question selectedQuestionToEdit) {

			Platform.runLater(() -> {				// In order to run javaFX thread.(we receive from server a java thread)
				try {
			    	observebaleNewQuestion = FXCollections.observableArrayList(); 
			    	String questionId;
			    	
				 	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../resources/view/NewQuestionWizzard.fxml"));
				    Parent parent = fxmlLoader.load();
				    AddNewQuestionController dialogController = fxmlLoader.<AddNewQuestionController>getController();
				    dialogController.setAppMainObservableList(observebaleNewQuestion);
				    dialogController.setUser(this.user);
				    dialogController.setUserSubjects(this.userSubjects);
				    dialogController.setMainApp(screenManager.getPrimaryStage());
				    if (selectedQuestionToEdit != null) {
						dialogController.setQuestion(selectedQuestionToEdit);
					}
				    Scene scene = new Scene(parent);
				    Stage stage = new Stage();
				    stage.initModality(Modality.APPLICATION_MODAL);
				    stage.setScene(scene);
					
				    stage.setTitle("New question wizzard");
				    stage.showAndWait();
				    
				    if (observebaleNewQuestion.isEmpty() == false) {	// if false, than no new question created, that a question was updated
				    	if (selectedQuestionToEdit != null) {		// if 'selectedQuestionToEdit' changed, remove old question form list's 
				    		String tempOldQID =selectedQuestionToEdit.getQuestionId(); 
				    		observabaleQuestions.remove(selectedQuestionToEdit);
				    		questions.remove(selectedQuestionToEdit);
				    		questionId = prepareQuestionID(observebaleNewQuestion.get(0).getQuestionId());
						    observebaleNewQuestion.get(0).setQuestionId(questionId);
						    observabaleQuestions.add(observebaleNewQuestion.get(0));
						    questions.add(observebaleNewQuestion.get(0));
						    if ( tempOldQID.equals(questionId.substring(0, 2)) ){	// if equal than updated quastion didnt changed its subject
						    	setChangedQuestion(observebaleNewQuestion.get(0));
						    }
						    deleteQuestionFromDB(selectedQuestionToEdit);			// delete from DB, old question
						    putNewQuestion(observebaleNewQuestion.get(0));			// insert updated question
				    	}
				    	else {
						    questionId = prepareQuestionID(observebaleNewQuestion.get(0).getQuestionId());
						    observebaleNewQuestion.get(0).setQuestionId(questionId);
						    observabaleQuestions.add(observebaleNewQuestion.get(0));
						    questions.add(observebaleNewQuestion.get(0));
						    
						    putNewQuestion(observebaleNewQuestion.get(0));
				    	}
				    	//System.out.println(observebaleNewQuestion.get(0));
				    }
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
				}
			});
			
		}
	
	/**
	 * This method happens when the server send an message 
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		
		if (arg1 instanceof QuestionsMessage) {
			if(this.getQuestions().size() == 0)
				this.setQuestions(((QuestionsMessage) arg1).getQuestions());		// only when there no question's - at first load or a new Teacher.
			else addQuestions(((QuestionsMessage) arg1).getQuestions());			// add new questions to a teacher.
			observabaleQuestions = FXCollections.observableArrayList(); 			// add new question to ObservebaleList
			for (Question question: questions) {
				observabaleQuestions.add(question);
			}
			tblQuestions.setItems(observabaleQuestions);
		}
		
		if(arg1 instanceof UserSubjectMessage) {
			this.setUserSubjects(((UserSubjectMessage) arg1).getSubjects());
			fillCombobox(this.userSubjects);
			getUserQuestions(this.userSubjects);
			System.out.println(this.userSubjects.toString());
			
		}
		
		if(arg1 instanceof SimpleMessage) {
			SimpleMessage simple = (SimpleMessage)arg1;
			log.writeToLog(LogLine.LineType.INFO, "Question deleted");
			/*Platform.runLater(() -> {				// In order to run javaFX thread.(we recieve from server a java thread)
				try {
					screenManager.activate("home");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
				}
			});*/
		}
	}
	

	
	/**
	  *  function to create a valid Question ID
	  * @param subjectID
	  * @return String a Question valid ID
	  */
	private String prepareQuestionID(String subjectID) {
		// TODO Auto-generated method stub
		String newId = subjectID;
		int newQuestionID = 0;
		for (Question question: this.getQuestions()) {
			String questionID = question.getQuestionId();
			if (subjectID.equals(questionID.substring(0, 2))) {
				int tempId = Integer.parseInt(questionID.substring(2));
				if (newQuestionID <= tempId) newQuestionID = tempId;
			}
		}
		newQuestionID++;
		int temp  =100;
		while(temp > newQuestionID){
			newId =  newId.concat("0");
			temp = temp/10;
		}
		newId = newId.concat(String.valueOf(newQuestionID));
		System.out.println("My tst "+newId);
		return newId;
	}
	
	private void putNewQuestion(Question question) {
		// TODO Auto-generated method stub
		// here well prepare a message with {"put-new-Question", Question }
		QuestionsMessage newQuestionMessage = (QuestionsMessage) message.getMessage("put-Questions",question);	// we can send the specific question because we have table "Questions"
		try {
			client.sendToServer(newQuestionMessage);
		} catch (IOException e) {
			e.printStackTrace();
			log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
		}
	}
	
	/**
	 * this method is called when updating existing message.
	 * @param question updated question needed to be inserted the DB
	 */
    private void setChangedQuestion(Question question) {
		// TODO Auto-generated method stub
    	// here well prepare a message with {"set-new-Question", Question }
    			QuestionsMessage updatedQuestionMessage = (QuestionsMessage) message.getMessage("set-Questions",question);	// we can send the specific question because we have table "Questions"
    			try {
    				client.sendToServer(updatedQuestionMessage);
    			} catch (IOException e) {
    				e.printStackTrace();
    				log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
    			}
		
	}
	
	private void getUserQuestions(ArrayList<Subject> userSubjects) {
			// TODO Auto-generated method stub
			// Here well get all question that in the same subject of the user
			for (Subject subject: userSubjects) {
				QuestionsMessage newQuestionMessage = (QuestionsMessage) message.getMessage("get-Questions",subject);
				try {
					client.sendToServer(newQuestionMessage);
				} catch (IOException e) {
					e.printStackTrace();
					log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
				}
			}
		}
	
	private void getUserSubjects(User user) {
			// TODO Auto-generated method stub
			UserSubjectMessage newUserSubjectMessage = (UserSubjectMessage) message.getMessage("get-UserSubjects",user);
			try {
				client.sendToServer(newUserSubjectMessage);
			} catch (IOException e) {
				e.printStackTrace();
				log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
			}
		}
	
	public ArrayList<Subject> getUserSubjects() {
		return userSubjects;
	}
	
	public void setUserSubjects(ArrayList<Subject> userSubjects) {
		this.userSubjects = userSubjects;
	}
		
	private void fillCombobox(ArrayList<Subject> teacherSubject) {
		observableSubjects = FXCollections.observableArrayList(teacherSubject);
		subjectCombobox.getItems().add(new Subject("00", "Show all Questions"));		// DUMMY subject, for enabling to unfillter table rows
		subjectCombobox.getItems().addAll(observableSubjects);	
	}
		
	/**
	 * @return the questions
	 */
	public ArrayList<Question> getQuestions() {
		return questions;
	}
	
	/**
	 * @param questions the questions to set
	 */
	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}
	public void addQuestions(ArrayList<Question> questions) {
		this.getQuestions().addAll(questions);
		
	}
	
	private void setUserDetails(User user1) {
		// TODO Auto-generated method stub
		teacherIDLbl.setText(user.getUserID());
		fstNameLbl.setText(user.getUserFirstName());
		lstNamelbl.setText(user.getUserLastName());
		TeacherPremissionLbl.setText(user.getUserPremission());
	}
	
}