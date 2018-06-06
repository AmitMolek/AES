
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

import ocsf.client.ObservableClient;
import root.client.managers.DataKeepManager;
import root.client.managers.LoggedInUserManager;
import root.client.managers.ScreensManager;
import root.dao.app.LoginInfo;
import root.dao.app.User;
import root.dao.message.ErrorMessage;
import root.dao.message.LoginMessage;
import root.dao.message.MessageFactory;
import root.dao.message.UserMessage;
import root.util.log.Log;
import root.util.log.LogLine;

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
    
    
    private ObservableClient client;
    private MessageFactory message;
    private User user;
    private ScreensManager screenManager;
    private LoggedInUserManager loggedInManager;
    
    Log log = Log.getInstance();
    /**
     * This method occurs when someone presses the sign in button
     * @param event action event when someone presses the sign in button
     */
    @FXML
    public void SignIn(ActionEvent event) {
    	String userId = txtId.getText();
    	String userPassword = txtPassword.getText();
    	LoginInfo loginInformation = new LoginInfo(userId,userPassword);
    	LoginMessage newLoginMessage = (LoginMessage) message.getMessage("login",loginInformation);
    	try {
			client.sendToServer(newLoginMessage);
		} catch (IOException e) {
			e.printStackTrace();
			log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
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
    	//loggedInManager = LoggedInUserManager.getInstance();
    	client = new ObservableClient("localhost", 8000);
    	client.addObserver(this);
    	client.openConnection();
    	
    	
    	ErrorTxtField.setVisible(false);
    	// Listen for selection changes and show the person details when changed.
    	txtId.setOnMouseClicked(e -> {
    		btnSignIn.setDisable(false);
        });
    	btnSignIn.setDisable(true);
    }
    
    /**
     * This method occurs when the server send message to the client
     */
	@Override
	public void update(Observable arg0, Object arg1) {
		
		if(arg1 instanceof UserMessage) {
			UserMessage newMessasge = (UserMessage) arg1;
			user = newMessasge.getUser();
			DataKeepManager.getInstance().keepObject("user", user);
			//System.out.println(user);
			System.out.println("Logged In Users: "+ DataKeepManager.getInstance().getObject("user"));
			Platform.runLater(() -> {				// In order to run javaFX thread.(we recieve from server a java thread)
				try {
					screenManager.activate("questions");
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
				ErrorTxtField.setText(arg1.toString());
				ErrorTxtField.setVisible(true);	
			});
		}
	}
	
}
//=======
//package root.client.controllers;
//
//import java.io.IOException;
//import java.util.Observable;
//import java.util.Observer;
//
//import javafx.application.Platform;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//
//import javafx.scene.control.Button;
//import javafx.scene.control.Hyperlink;
//import javafx.scene.control.Label;
//import javafx.scene.control.PasswordField;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.StackPane;
//import javafx.scene.layout.VBox;
//import ocsf.client.ObservableClient;
//import root.client.managers.DataKeepManager;
//import root.client.resources.view.ScreensManager;
//import root.dao.app.LoginInfo;
//import root.dao.app.User;
//import root.dao.message.LoginMessage;
//import root.dao.message.MessageFactory;
//import root.dao.message.UserMessage;
//
//public class LoginController implements Observer {
//
//    @FXML
//    private AnchorPane rootPane;
//
//    @FXML
//    private Hyperlink linkForgot;
//
//    @FXML
//    private Button btnSignIn;
//
//    @FXML
//    private Label lblId;
//
//    @FXML
//    private TextField txtId;
//
//    @FXML
//    private Label lblPassword;
//
//    @FXML
//    private PasswordField txtPassword;
//
//    
//    private ObservableClient client;
//    private MessageFactory message;
//    private User user;
//    private ScreensManager screenManager;
//    /**
//     * This method occurs when someone presses the sign in button
//     * @param event action event when someone presses the sign in button
//     */
//    @FXML
//    public void SignIn(ActionEvent event) {
//    	String userId = txtId.getText();
//    	String userPassword = txtPassword.getText();
//    	LoginInfo loginInformation = new LoginInfo(userId,userPassword);
//    	LoginMessage newLoginMessage = (LoginMessage) message.getMessage("login",loginInformation);
//    	try {
//			client.sendToServer(newLoginMessage);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//    	
//
//    }
//    
//    /**
//     * This method occurs when the window is shown up.
//     * @throws IOException if the window cannot be shown
//     */
//    @FXML
//	public void initialize() throws IOException{
//    	Platform.runLater(() -> rootPane.requestFocus());
//    	message = MessageFactory.getInstance();
//    	screenManager = ScreensManager.getInstance();
//    	client = new ObservableClient("localhost", 8000);
//    	client.addObserver(this);
//    	client.openConnection();
//    	
//    	
//    }
//    
//    /**
//     * This method occurs when the server send message to the client
//     */
//	@Override
//	public void update(Observable arg0, Object arg1) {
//		System.out.println(arg1);
//		
//		if(arg1 instanceof UserMessage) {
//			UserMessage newMessasge = (UserMessage) arg1;
//			user = newMessasge.getUser();
//			DataKeepManager.getInstance().keepObject("user", user);
//			try {
//				screenManager.activate("mainWindow");
//
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
//    
//    
//
//}

