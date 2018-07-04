package root.client.controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import ocsf.client.ObservableClient;
import root.client.interface_classes.RealMessageFactory;
import root.client.interface_classes.RealObservableClient;
import root.client.interface_classes.RealPane;
import root.client.interface_classes.RealTextField;
import root.client.interfaces.IClient;
import root.client.interfaces.IFieldText;
import root.client.interfaces.IMessageFactory;
import root.client.interfaces.IMock;
import root.client.interfaces.IVisiblePane;
import root.client.managers.DataKeepManager;
import root.client.managers.ScreensManager;
import root.client.mocks.RealTimeMock;
import root.dao.app.LoginInfo;
import root.dao.app.User;
import root.dao.message.ErrorMessage;
import root.dao.message.LoginMessage;
import root.dao.message.MessageFactory;
import root.dao.message.UserMessage;
import root.util.log.Log;
import root.util.log.LogLine;
import root.util.log.LogLine.LineType;
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
	DataKeepManager dkm = DataKeepManager.getInstance();
	
	IMock mockService;
	
	/**
	 * Sets the mock service of this class
	 * @param mockService the mock service
	 */
	public void setMockService(IMock mockService) {
		this.mockService = mockService;
	}
	
	/**
	 * 1st step in the sign in
	 * Gets the server ip from the text field if needed 
	 * @param ipPane the server ip pane
	 * @param ipTextField the server ip text field
	 */
	public void signIn_GetIP(IVisiblePane ipPane, IFieldText ipTextField) {
    	if (ipPane.isVisible()) {
    		String ipTextFieldText = ipTextField.getText();
    		if (ipTextFieldText.isEmpty()) {
    			mockService.LogMsg("Server IP error");
    			showErrorDialog("ServerIP error","Please contact system administrator","Please enter serverIP");
    			return;
    		}
    		serverIP = ipTextFieldText;
    		dkm.keepObject("ip", serverIP);
    		mockService.LogMsg("Stored IP in keep");
    	}
	}
	
	/**
	 * This is the 2nd step in the sign in
	 * Gets the server ip and gets/creates the client that will talk to the server
	 */
	public void signIn_Client() {
    	String ip = (String)dkm.getObject_NoRemove("ip");
    	if (ip != null) {
    		IClient iClient = (IClient) dkm.getObject_NoRemove("login_client");
    		if (iClient != null && iClient.getObservableClient() != null) {
    			client = iClient.getObservableClient();
    			mockService.LogMsg("Got Client from keep");
    		}else {
            	client = new ObservableClient(serverIP, 8000);    			
            	dkm.keepObject("login_client", new RealObservableClient(client));
            	mockService.LogMsg("Created Client and stored it");
    		}
        	client.addObserver(this);

    	}else {
    		mockService.LogMsg("Server IP is null");
    		showErrorDialog("Server ip error", "Please enter server ip", "Server ip is null");
    		log.writeToLog(LineType.ERROR, "Server ip is null");
    		return;
    	}
	}
	
	/**
	 * The 3rd step in the sign in
	 * Sends to the server the login info
	 * @param realClient the client connected to the server
	 * @param usernameField the username text field
	 * @param passwordField the password text field
	 * @param factory the message factory to get the messages
	 */
	public void signInUser(IClient realClient, IFieldText usernameField, IFieldText passwordField, IMessageFactory factory) {
    	String userId = usernameField.getText();
    	String userPassword = passwordField.getText();
    	LoginInfo loginInformation = new LoginInfo(userId,userPassword);
    	LoginMessage newLoginMessage = (LoginMessage) factory.getMessage("login",loginInformation);
    	try {
    		realClient.openConnection();
    		realClient.sendToServer(newLoginMessage);
			propertFile.writeToConfig("IP", serverIP);
			mockService.LogMsg("Sent message to server");
		} catch (IOException e) {
			mockService.LogMsg("Failed sending the message");
			e.printStackTrace();
			showErrorDialog("ServerIP error","Please contact system administrator",e.getMessage());
		}
	}
	
    /**
     * This method occurs when someone presses the sign in button
     * @param event action event when someone presses the sign in button
     */
    @FXML
    public void SignIn(ActionEvent event) {
    	signIn_GetIP(new RealPane(serverIPpane), new RealTextField(txtFieldserverIP));
    	signIn_Client();
    	signInUser(new RealObservableClient(client) ,new RealTextField(txtId), new RealTextField(txtPassword), new RealMessageFactory(message));
    }
    
    /**
     * This method is called when theres a need to ErrrorDialog
     * @param HeaderTitle
     * @param HeaderText
     * @param Errormessage
     */
    private void showErrorDialog(String HeaderTitle,String HeaderText,String Errormessage){
    	Platform.runLater(() -> {								// In order to run javaFX thread.(we recieve from server a java thread)
			// Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(screenManager.getPrimaryStage());
            alert.setTitle(HeaderTitle);//"ServerIP error");
            alert.setHeaderText(HeaderText);//"Please contact system administrator");
            alert.setContentText(Errormessage);
            alert.showAndWait();       
        	log.writeToLog(LogLine.LineType.ERROR,Errormessage);
		});
    }
    /**
     * EasterEgg
     */
    @FXML
    void forgotPass(ActionEvent event) {
    	/*************/
    	   
    	        // All of our necessary variables
    	        File imageFile;
    	        File audioFile;
    	        Image image;
    	        ImageView imageView;
    	        Media audio;
    	        MediaPlayer audioPlayer;
    	        BorderPane pane;
    	        Scene scene;
    	        Stage stage;
    	        
    	        // The path to your image can be a URL,
    	        // or it can be a directory on your computer.
    	        // If the picture is on your computer, type the path
    	        // likes so:
    	        //     C:\\Path\\To\\Image.jpg
    	        // If you have a Mac, it's like this:
    	        //     /Path/To/Image.jpg
    	        // Replace the path with the one on your computer
    	        InputStream jarPath = this.getClass().getResourceAsStream("/root/client/resources/images/MRV_20180318_17_54_46.jpg");		// jar way to open inner files.
    	        //imageFile = new File("src/root/client/resources/images/MRV_20180318_17_54_46.jpg");									// Original, Eclipse way to run java
    	        image = new Image(jarPath);
    	        imageView = new ImageView(image);

//    	        // The same thing applies with audio files. Replace
//    	        // this with the path to your audio file
//    	        audioFile = new File("/Users/bryce/NetBeansProjects/Graphics_PM/src/edu/govschool/dangerzone.mp3");
//    	        audio = new Media(audioFile.toURI().toString());
//    	        audioPlayer = new MediaPlayer(audio);
//    	        audioPlayer.setAutoPlay(true);

    	        // Our image will sit in the middle of our popup.
    	        pane = new BorderPane();
    	        pane.setCenter(imageView);
    	        scene = new Scene(pane);

    	        // Create the actual window and display it.
    	        stage = new Stage();
    	        stage.setScene(scene);
    	        // Without this, the audio won't stop!
    	        stage.setOnCloseRequest(
    	            e -> {
    	                e.consume();
    	              //  audioPlayer.stop();
    	                stage.close();
    	            }
    	        );
    	        stage.showAndWait();
    	    
    	/*************/
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

    	screenManager.clearStack();
    	
    	mockService = new RealTimeMock();
    	
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
		serverIP = propertFile.getFromConfig("IP");
		if(serverIP == null) {
			serverIPpane.setVisible(true);
		}else {
			DataKeepManager.getInstance().keepObject("ip", serverIP);
			log.writeToLog(LineType.INFO, "Got the server ip from the config: " + serverIP);
		}
	}
	
    /**
     * This method occurs when the server send message to the client
     */
	@Override
	public void update(Observable arg0, Object arg1) {
		
		if(arg1 instanceof UserMessage) {
			UserMessage newMessasge = (UserMessage) arg1;
			user = newMessasge.getUser();
			DataKeepManager.getInstance().keepUser(user);
			System.out.println("Logged In Users: "+ DataKeepManager.getInstance().getUser());
			if(user.getUserPremission().equals("Principal")) {
				mockService.LogMsg("Principal detected");
				client.deleteObservers();
				client.addObserver(new WaitForPirncipleMessage());
			}
			Platform.runLater(() -> {
				try {
					screenManager.activate("home");
				} catch (IOException e) {
					e.printStackTrace();
					log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
				}
			});
		}
		else if (arg1 instanceof ErrorMessage) {
			mockService.LogMsg("Got an ErrorMessage");
			showErrorDialog("Invalid Fields","Please correct invalid fields",arg1.toString());
		}
	}
}