package root.client.controllers;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import ocsf.client.ObservableClient;
import root.client.managers.DataKeepManager;
import root.client.managers.ScreensManager;
import root.dao.app.LoginInfo;
import root.dao.app.User;
import root.dao.message.ErrorMessage;
import root.dao.message.LoginMessage;
import root.dao.message.MessageFactory;
import root.dao.message.UserMessage;
import root.util.log.Log;
import root.util.log.LogLine;
import root.util.properties.PropertiesFile;

public class LoginController implements Observer {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Hyperlink linkForgot;

    @FXML
    private Button btnSignIn;

    @FXML
    private Label lblId;

    @FXML
    private TextField txtId;

    @FXML
    private Label lblPassword;

    @FXML
    private PasswordField txtPassword;
    
    @FXML
    private Label ErrorTxtField;
    @FXML
    private RowConstraints serverServiesRow;
    
    @FXML
    private Pane serverIPpane;

    @FXML
    private TextField txtFieldserverIP;
    
    
    private ObservableClient client;
    private MessageFactory message;
    private User user;
    private ScreensManager screenManager;
    private String serverIP;
    Log log = Log.getInstance();
	PropertiesFile propertFile = PropertiesFile.getInstance();

	
	
    /**
     * This method occurs when someone presses the sign in button
     * @param event action event when someone presses the sign in button
     */
    @FXML
    public void SignIn(ActionEvent event) {
    	
    	if (serverIPpane.isVisible()) {
    		serverIP = txtFieldserverIP.getText();
    	}
    	
    	client = new ObservableClient(serverIP, 8000);				// opens a connection only if user exist.
    	client.addObserver(this);												// --||--
    	String userId = txtId.getText();
    	String userPassword = txtPassword.getText();
    	LoginInfo loginInformation = new LoginInfo(userId,userPassword);
    	LoginMessage newLoginMessage = (LoginMessage) message.getMessage("login",loginInformation);
    	try {
    		client.openConnection();
			client.sendToServer(newLoginMessage);
			propertFile.writeToConfig("IP", serverIP);				// if no exceptions thrown by client, than save serverIP.
		} catch (IOException e) {	
			e.printStackTrace();
			Platform.runLater(() -> {								// In order to run javaFX thread.(we recieve from server a java thread)
				// Show the error message.
	            Alert alert = new Alert(AlertType.ERROR);
	            alert.initOwner(screenManager.getPrimaryStage());
	            alert.setTitle("ServerIP error");
	            alert.setHeaderText("Please contact system administrator");
	            alert.setContentText(e.getMessage());
	            alert.showAndWait();       
	        	log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
			});
		}
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
    	serverIPpane.setVisible(false);

    	tryGettingServerIP();
    	// Listen for selection changes and show the person details when changed.
    	txtId.setOnMouseClicked(e -> {
    		btnSignIn.setDisable(false);
        });
    	btnSignIn.setDisable(true);
    }
    /**
     * This method in order to reset the serverIP
     * @param event
     */
    @FXML
	private void resetServerIP(ActionEvent event) {
		propertFile.writeToConfig("IP", null);
		serverIPpane.setVisible(true);
	}
	private void tryGettingServerIP() {
		// TODO Auto-generated method stub
		serverIP = propertFile.getFromConfig("IP");
		if(serverIP == null) {
			serverIPpane.setVisible(true);
		}
	}
	
    /**
     * This method occurs when the server send message to the client
     */
	@Override
	public void update(Observable arg0, Object arg1) {
		
		if(arg1 instanceof UserMessage) {
			
			UserMessage newMessasge = (UserMessage) arg1;
			DataKeepManager.getInstance().keepObject("client", client);				// save's the client only if user exist

			user = newMessasge.getUser();
			DataKeepManager.getInstance().keepUser(user);
			System.out.println("Logged In Users: "+ DataKeepManager.getInstance().getUser());
			Platform.runLater(() -> {				// In order to run javaFX thread.(we recieve from server a java thread)
				try {
<<<<<<< HEAD
					
					AddUserSpecificScreens();
					screenManager.activate("check");
=======
					AddUserSpecificScreens();
					screenManager.activate("Enter4digitPassword");
>>>>>>> refs/remotes/origin/Omer
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
				}
			});
		}
		else if (arg1 instanceof ErrorMessage) {
			System.out.println(arg1);
			Platform.runLater(() -> {				// In order to run javaFX thread.(we recieve from server a java thread)
				// Show the error message.
	            Alert alert = new Alert(AlertType.ERROR);
	            alert.initOwner(screenManager.getPrimaryStage());
	            alert.setTitle("Invalid Fields");
	            alert.setHeaderText("Please correct invalid fields");
	            alert.setContentText(arg1.toString());
	            alert.showAndWait();       
	        	log.writeToLog(LogLine.LineType.ERROR, arg1.toString());
			});
		}
	}
	
	public void AddUserSpecificScreens() {
		if(user.getUserPremission().equals("Teacher")) {
			screenManager.addScreen("testGradesStats", "resources/view/TestGradesTeacher.fxml");
		}
		else if(user.getUserPremission().equals("Student")) {
			screenManager.addScreen("testGradesStats", "resources/view/TestGradesStudent.fxml");
		}
		else if(user.getUserPremission().equals("Principal")) {
			screenManager.addScreen("testGradesStats", "resources/view/TestGradesPrincipal.fxml");
			client.deleteObservers();
			client.addObserver(new WaitForPirncipleMessage());
		}
	}
	
}