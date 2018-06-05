package root.client.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.controlsfx.control.textfield.TextFields;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.IntegerStringConverter;
import ocsf.client.ObservableClient;
import root.client.managers.LoggedInUserManager;
import root.client.managers.ScreensManager;
import root.dao.app.LoginInfo;
import root.dao.app.Question;
import root.dao.app.Subject;
import root.dao.app.User;
import root.dao.message.LoginMessage;
import root.dao.message.Message;
import root.dao.message.MessageFactory;
import root.dao.message.UserMessage;
import root.dao.message.UserSubjectMessage;

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
	/*
    @FXML
    private TextField txtFieldQuestion;

    @FXML
    private TableColumn<Question, String> tbcAns1;

    @FXML
    private TableColumn<Question, Integer> tbcCorr;

    @FXML
    private TableColumn<Question, String> tbcId;

    @FXML
    private TableColumn<Question, String> tbcAns3;
    
    @FXML
    private TableColumn<Question, String> tbcAns2;

    @FXML
    private TextField txtFieldId;

    @FXML
    private TableColumn<Question, String> tbcAns4;

    @FXML
    private Button btnSearch;

    @FXML
    private TableColumn<Question, String> tbcIdText;

    @FXML
    private TableColumn<Question, String> tbcName;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField txtFieldName;

    @FXML
    private Button update;

    @FXML
    private TableView<Question> tblQuestions;

    @FXML
    private Label lblUpdateError;
    
    @FXML
    private TableColumn<Question, Integer> tbcIdNum;
*/
    private ObservableClient client;
    private Map<String,Integer> newValues;
	private ArrayList<Question> questions;
	
    private MessageFactory message;
    private User user;
    private ScreensManager screenManager;
    private LoggedInUserManager loggedInManager;
	private ArrayList<Subject> userSubjects;
    
  

	public QuestionsController() {
		super();
	
	}
	/*private void init_for_testing() {
    	String userId = "301726717";
    	String userPassword = "gal";
    	LoginInfo loginInformation = new LoginInfo(userId,userPassword);
    	LoginMessage newLoginMessage = (LoginMessage) message.getMessage("login",loginInformation);
    	try {
			client.sendToServer(newLoginMessage);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }*/
	
	public ArrayList<Subject> getUserSubjects() {
		return userSubjects;
	}

	public void setUserSubjects(ArrayList<Subject> userSubjects) {
		this.userSubjects = userSubjects;
	}
		
	private void fillCombobox(Subject teacherSubject) {
		
	}
    /**
	 * This method happens when the user press on the search button 
	 * @param event
	 */

	@FXML
 	void searchQuestion(ActionEvent event) {
		tblQuestions.getItems().clear();
		String questionId = txtFieldId.getText();
		String questionName = txtFieldName.getText();
		String questionIns = txtFieldQuestion.getText();
		ObservableList<Question> queryQuestions = FXCollections.observableArrayList();
		if (questionId != null) {
			for (int i = 0; i < questions.size(); i++) {
				Question q = questions.get(i);
				if (q.getId().equals(questionId)) {
					queryQuestions.add(q);
				}
			}

		}

		if (questionName != null) {
			for (int i = 0; i < questions.size(); i++) {
				Question q = questions.get(i);
				if (q.getTeacherName().equals(questionName) && (!queryQuestions.contains(q))) {
					queryQuestions.add(q);
				}
			}

		}
		if (questionIns != null) {
			for (int i = 0; i < questions.size(); i++) {
				Question q = questions.get(i);
				if (q.getQuestionIns().equals(questionIns) && (!queryQuestions.contains(q))) {
					queryQuestions.add(q);
				}
			}

		}
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
    	loggedInManager = LoggedInUserManager.getInstance();
    	client = new ObservableClient("localhost", 8000);
    	client.addObserver(this);
    	client.openConnection();
    	user = loggedInManager.getUser();
    	
    	setUserDetails(user);
    	getUserSubjects(user);
    	//fillCombobox()
    	//init_for_testing();
    	
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
private void getUserSubjects(User user) {
		// TODO Auto-generated method stub
		//UserMessage userMessage = new UserMessage(user);
		UserSubjectMessage newUserSubjectMessage = (UserSubjectMessage) message.getMessage("get-UserSubjects",user);
		try {
			client.sendToServer(newUserSubjectMessage);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
    @FXML
    void updateQuestion(ActionEvent event) {
    	try {
    		if(newValues.size() == 0)
    		{
    			lblUpdateError.setText("Please update the row!");
    			lblUpdateError.setVisible(true);
    		}
    		else
    			lblUpdateError.setVisible(false);
    		Message send = new Message("set-questions-map",newValues);
			client.sendToServer(send);
			newValues.clear();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
    
    /**
	 * This method happens when the server send an message 
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		
		if(arg1 instanceof UserSubjectMessage) {
			this.setUserSubjects(((UserSubjectMessage) arg1).getSubjects());
			System.out.println(this.userSubjects);
		}
		
		
		if(arg1 instanceof String)
		{
			String s = (String)arg1;
			System.out.println(s);
		}
		if(arg1 instanceof Message)
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
					
					possibleIDs[i] = q.getId();
					possibleNames[i] = q.getTeacherName();
					possibleQuestion[i] = q.getQuestionIns();
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
			
			
		}
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
		if(newValue>=1 && newValue <= 4)
			newValues.put(question.getId(),newValue);
		else
		{
			lblUpdateError.setText("Please enter valid input!");
			lblUpdateError.setVisible(true);
		}
		
	}





	@FXML public void updateCorrect() {}



}