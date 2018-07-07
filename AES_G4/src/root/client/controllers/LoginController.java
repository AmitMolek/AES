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
import root.util.log.LogLine.LineType;
import root.util.properties.PropertiesFile;
import unittests.stubs.Dependancy;
import unittests.stubs.IClient;
import unittests.stubs.IFxml;
import unittests.stubs.RealClient;
import unittests.stubs.RealPane;
import unittests.stubs.RealTextId;
import unittests.stubs.RealTextIp;
import unittests.stubs.RealTextPassword;

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
    private IFxml txtId;

    @FXML
    private Label lblPassword;

    @FXML
    private IFxml txtPassword;
    
    @FXML
    private Label ErrorTxtField;
    @FXML
    private RowConstraints serverServiesRow;
    
    @FXML
    private IFxml serverIPpane;

    @FXML
    private IFxml txtFieldserverIP;
    
    
    private IClient client;
    private MessageFactory message;
    private User user;
    private ScreensManager screenManager;
    private String serverIP;
    Log log = Log.getInstance();
	PropertiesFile propertFile = PropertiesFile.getInstance();
	DataKeepManager dkm = DataKeepManager.getInstance();
	private Dependancy dependancy;
	
	
	public void setDependancy(Dependancy dependancy) {
		/*try {
			initialize();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		this.dependancy = dependancy;
		log = dependancy.getLogMock();
		propertFile = dependancy.getPropertiesFileStub();
		client = dependancy.getClientStub();
		txtId = dependancy.getTxtId();
		txtPassword = dependancy.getTxtPassword();
		txtFieldserverIP = dependancy.getTxtIp();
		serverIPpane = dependancy.getPane();
		
		
	}
	
    /**
     * This method occurs when someone presses the sign in button
     * @param event action event when someone presses the sign in button
     */
    @FXML
    public void SignIn(ActionEvent event) {
    	
    	if (serverIPpane.isVisible()) {
    		if (txtFieldserverIP.getText().isEmpty()) {
    			showErrorDialog("ServerIP error","Please contact system administrator","Please enter serverIP");
    			return;
    		}
    		serverIP = txtFieldserverIP.getText();
    		DataKeepManager.getInstance().keepObject("ip", serverIP);
    	}
    	DataKeepManager dkm = DataKeepManager.getInstance();
    	
    	String ip = (String)dkm.getObject_NoRemove("ip");
    	if (ip != null) {
    		if ((IClient)dkm.getObject_NoRemove("login_client") != null) {
    			client = (IClient)dkm.getObject_NoRemove("login_client");
    		}else {
            	client = new RealClient(serverIP, 8000);    			
            	dkm.keepObject("login_client", client);
    		}
        	client.addObserver(this);

    	}else {
    		showErrorDialog("Server ip error", "Please enter server ip", "Server ip is null");
    		log.writeToLog(LineType.ERROR, "Server ip is null");
    		return;
    	}
    	
    	String userId = txtId.getText();
    	String userPassword = txtPassword.getText();
    	LoginInfo loginInformation = new LoginInfo(userId,userPassword);
    	LoginMessage newLoginMessage = (LoginMessage) message.getMessage("login",loginInformation);
    	try {
    		client.openConnection();
			client.sendToServer(newLoginMessage);
			propertFile.writeToConfig("IP", serverIP);
		} catch (IOException e) {	
			e.printStackTrace();
			showErrorDialog("ServerIP error","Please contact system administrator",e.getMessage());
		}
    }
    
    public void createClient() {
    	
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
    	txtId = new RealTextId(new TextField());
		txtPassword = new RealTextPassword(new PasswordField());
		txtFieldserverIP = new RealTextIp(new TextField());
		serverIPpane = new RealPane(new Pane());
    	Platform.runLater(() -> rootPane.requestFocus());
    	message = MessageFactory.getInstance();
    	screenManager = ScreensManager.getInstance();
    	serverIPpane.setVisible(false);

    	screenManager.clearStack();
    	
    	tryGettingServerIP();
    	// Listen for selection changes and show the person details when changed.
    	/*txtId.setOnMouseClicked(e -> {
    		btnSignIn.setDisable(false);
        });*/
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
			Platform.runLater(() -> {				// In order to run javaFX thread.(we recieve from server a java thread)
				try {
					if(user.getUserPremission().equals("Principal")) {
						client.deleteObservers();
						client.addObserver(new WaitForPirncipleMessage());
					}
					screenManager.activate("home");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
				}
			});
		}
		else if (arg1 instanceof ErrorMessage) {
			showErrorDialog("Invalid Fields","Please correct invalid fields",arg1.toString());
		}
	}
}