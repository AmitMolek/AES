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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import ocsf.client.ObservableClient;
import root.client.managers.DataKeepManager;
import root.client.managers.ScreensManager;
import root.dao.app.LoginInfo;
import root.dao.app.Question;
import root.dao.app.Subject;
import root.dao.app.User;
import root.dao.message.LoginMessage;
import root.dao.message.Message;
import root.dao.message.MessageFactory;
import root.dao.message.QuestionsMessage;
import root.dao.message.UserMessage;
import root.dao.message.UserSubjectMessage;
import root.util.log.Log;
import root.util.log.LogLine;


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
    private TableColumn<Question, String> tbcIdNum; // Value injected by FXMLLoader

    @FXML // fx:id="tbcName"
    private TableColumn<Question, String> tbcName; // Value injected by FXMLLoader

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
    private TableColumn<Question, String> tbcCorr; // Value injected by FXMLLoader

    @FXML // fx:id="txtFieldId"
    private TextField txtFieldId; // Value injected by FXMLLoader

    @FXML // fx:id="txtFieldName"
    private TextField txtFieldName; // Value injected by FXMLLoader

    @FXML // fx:id="lblUpdateError"
    private Label lblUpdateError; // Value injected by FXMLLoader

    @FXML // fx:id="txtFieldQuestion"
    private TextField txtFieldQuestion; // Value injected by FXMLLoader

    @FXML // fx:id="subjectCombobox"
    private ComboBox<Subject> subjectCombobox; // Value injected by FXMLLoader


    @FXML
    private Button updateQuestion;

    @FXML
    private Button newQuestion;


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

    private ObservableList<Subject> observableSubjects;
    private ObservableList<Question> observabaleQuestions; //= FXCollections.observableArrayList(new Person("A", "B"), new Person("C", "D"));
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
		System.out.println(event);
		System.out.println(subjectCombobox.getSelectionModel().getSelectedItem().toString());
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
		
		/*
		tbcId.setCellValueFactory(new PropertyValueFactory<Question, String>("id"));
		tbcName.setCellValueFactory(new PropertyValueFactory<Question, String>("teacherName"));
		tbcIdText.setCellValueFactory(new PropertyValueFactory<Question, String>("questionIns"));
//		tbcCorr.setCellValueFactory(new PropertyValueFactory<Question, Integer>("correctAns"));
		tbcAns1.setCellValueFactory(new PropertyValueFactory<Question, String>("ans1"));
		tbcAns2.setCellValueFactory(new PropertyValueFactory<Question, String>("ans2"));
		tbcAns3.setCellValueFactory(new PropertyValueFactory<Question, String>("ans3"));
		tbcAns4.setCellValueFactory(new PropertyValueFactory<Question, String>("ans4"));
	//	tbcIdNum.setCellValueFactory(new PropertyValueFactory<Question, Integer>("questionNum"));
		tblQuestions.setItems(queryQuestions);
		

		txtFieldId.clear();
		txtFieldName.clear();
		txtFieldQuestion.clear();
		 */
	



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
    	user = (User) DataKeepManager.getInstance().getObject("user");//loggedInManager.getUser();
    	questions = new ArrayList<Question>();
    
    	setUserDetails(user);
    	getUserSubjects(user);
    	
    	initQuestionsTable();
//<<<<<<< HEAD
//    	// calling it here for debug
//    	
//    	
//=======
//    	addNewQuestion(userSubjects.get(1));
//>>>>>>> refs/remotes/origin/Amit
    	/*
    	 // Initialize the person table with the two columns.
        firstNameColumn.setCellValueFactory(
                cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(
                cellData -> cellData.getValue().lastNameProperty());

        // Clear person details.
        showPersonDetails(null);

        // Listen for selection changes and show the person details when changed.
        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
        */
        
    	/*// Listen for selection changes and show the person details when changed.
    	txtId.setOnMouseClicked(e -> {
    		btnSignIn.setDisable(false);
        });
    	btnSignIn.setDisable(true);*/
    }

	private void initQuestionsTable() {
			// TODO Auto-generated method stub
		tblQuestions = new TableView<>();
		tblQuestions.getItems().clear();
		tblQuestions.setEditable(true);
	
	}

	//	/**
//	 * This method happens when the window shown 
//	 */
//	
//	@FXML
//	public void initialize() throws IOException {
//		Platform.runLater(() -> rootPane.requestFocus());
//		tblQuestions.getItems().clear();
//		tblQuestions.setEditable(true);
//		//tbcCorr.setCellFactory(TextFieldTableCell.<Question, Integer>forTableColumn(new IntegerStringConverter()));
//		tbcCorr.setCellFactory(ComboBoxTableCell.forTableColumn(1,2,3,4));
//		newValues = new HashMap<String,Integer>();
//		//client = new ObservableClient("192.168.178.54",8000);
//		btnSearch.setDisable(true);
//		lblUpdateError.setVisible(false);
//		//client.addObserver(this);
//		//client.openConnection();
//		Message send = new Message("get-questions");
//		//client.sendToServer(send);
//
//	}
	/**
	 * This method happens when the user press on the update button 
	 * @param event
	 */
//    @FXML
//    void updateQuestion(ActionEvent event) {
//    	try {
//    		if(newValues.size() == 0)
//    		{
//    			lblUpdateError.setText("Please update the row!");
//    			lblUpdateError.setVisible(true);
//    		}
//    		else
//    			lblUpdateError.setVisible(false);
//    		Message send = new Message("set-questions-map",newValues);
//			client.sendToServer(send);
//			newValues.clear();
//		} catch (IOException e) {
//			e.printStackTrace();
//			log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
//		}
//    	
//    }
    
   
    /**
	 * This method happens when the server send an message 
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		
		if (arg1 instanceof QuestionsMessage) {
			System.out.println(arg1.toString());
			
			if(this.getQuestions().size() == 0)
				this.setQuestions(((QuestionsMessage) arg1).getQuestions());
			addQuestions(((QuestionsMessage) arg1).getQuestions());
			observabaleQuestions = FXCollections.observableArrayList(questions); 
			//this.getQuestions().addAll(((QuestionsMessage) arg1).getQuestions());
		}
		
		if(arg1 instanceof UserSubjectMessage) {
			this.setUserSubjects(((UserSubjectMessage) arg1).getSubjects());
			fillCombobox(this.userSubjects);
			getUserQuestions(this.userSubjects);
			System.out.println(this.userSubjects.toString());
			
		}
		
		
		if(arg1 instanceof String)
		{
			String s = (String)arg1;
			System.out.println(s);
		}
		/*if(arg1 instanceof Message)
		{
			Message handleMsg = (Message) arg1;
			String[] recivedMSG = handleMsg.getMsg().split("-");
			if (recivedMSG[0].equals("ok") &&  recivedMSG[1].equals("arraylist"))
			{
				questions = handleMsg.getQuestions();
				String[] possibleIDs = new String [questions.size()];
				String[] possibleNames = new String [questions.size()];
				String[] possibleQuestion = new String [questions.size()];
				int i=0;
				
				for(Question q : questions)
				{
					
	//				possibleIDs[i] = q.getId();
		//			possibleNames[i] = q.getTeacherName();
			//		possibleQuestion[i] = q.getQuestionIns();
					i++;
				}		
				TextFields.bindAutoCompletion(txtFieldId, possibleIDs);
				TextFields.bindAutoCompletion(txtFieldName, possibleNames);
				TextFields.bindAutoCompletion(txtFieldQuestion, possibleQuestion);
				btnSearch.setDisable(false);
			}
			
			if(recivedMSG[0].equals("ok") &&  recivedMSG[1].equals("map"))
			{
				Message send = new Message("get-questions");
				try {
					client.sendToServer(send);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			
		}*/
	
	}

	
	 

	 @FXML
	    void onOpenDialog(ActionEvent event) throws IOException {
		 Platform.runLater(() -> {				// In order to run javaFX thread.(we recieve from server a java thread)
				try {
			//		screenManager.activate("questions");
			
		 	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../resources/view/AddQuestionWizzard.fxml"));
	        Parent parent = fxmlLoader.load();
	        AddNewQuestionController dialogController = fxmlLoader.<AddNewQuestionController>getController();
	        dialogController.setAppMainObservableList(observebaleNewQuestion);
	        dialogController.setUser(this.user);
	        dialogController.setUserSubjects(this.userSubjects);
	        Scene scene = new Scene(parent, 300, 200);
	        Stage stage = new Stage();
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.setScene(scene);
	        stage.showAndWait();
	        
	        String questionId = prepareQuestionID(observebaleNewQuestion.get(0).getQuestionId());
	        observebaleNewQuestion.get(0).setQuestionId(questionId);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
				}
			});
	    }

	
//	private void addNewQuestion(Subject subject) {
//	Question newQuestion;
//	String questionId = prepareQuestionID(subject);
//	String questionText ;
//	String questionIntruction;
//	String ans1;
//	String ans2;
//	String ans3;
//	String ans4;
//	int correctAns;
//	String teacherAssembeld;
//}


private String prepareQuestionID(String subjectID) {
	// TODO Auto-generated method stub
	String newId = subjectID;
	int newQuestionID = 0;
	for (Question question: this.getQuestions()) {
		String questionID = question.getQuestionId();
		if (subjectID.equals(questionID.substring(0, 2))) {
//=======
//private void addNewQuestion(Subject subject) {
//	Question newQuestion;
//	String questionId = prepareQuestionID(subject);
//	String questionText;
//	String idquestionIntruction;
//	String ans1;
//	String ans2;
//	String ans3;
//	String ans4;
//	int correctAns;
//	String teacherAssembeld;
//}
//private String prepareQuestionID(Subject subject) {
//	// TODO Auto-generated method stub
//	String newId = new String(subject.getSubjectID());
//	int newQuestionID = 0;
//	for (Question question: this.getQuestions()) {
//		String questionID = question.getQuestionId();
//		if (subject.getSubjectID().equals(questionID.substring(0, 2))) {
//>>>>>>> refs/remotes/origin/Amit
			int tempId = Integer.parseInt(questionID.substring(2));
			if (newQuestionID <= tempId) newQuestionID = tempId;
		}
		
		//		newId +=tempId;
	}
	newQuestionID++;
	if (newQuestionID < 10)newId+= "00"+newQuestionID;
	else if(newQuestionID <100)newId+= "0"+newQuestionID;
	
	return newId;
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
	subjectCombobox.getItems().addAll(observableSubjects);
	
	//addNewQuestion(this.getUserSubjects().get(1));
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
	
	@FXML public void updateCorrect(TableColumn.CellEditEvent<Question, Integer> correctEditEvent) {
		Question question = tblQuestions.getSelectionModel().getSelectedItem();
		Integer newValue = correctEditEvent.getNewValue();
		/*if(newValue>=1 && newValue <= 4)
			newValues.put(question.getId(),newValue);
		else
		{
			lblUpdateError.setText("Please enter valid input!");
			lblUpdateError.setVisible(true);
		}*/
		
	}





	@FXML public void updateCorrect() {}



}