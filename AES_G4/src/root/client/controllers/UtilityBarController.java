package root.client.controllers;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import ocsf.client.ObservableClient;
import root.client.managers.DataKeepManager;
import root.client.managers.ScreensManager;
import root.dao.app.User;
import root.dao.message.ErrorMessage;
import root.dao.message.LoggedOutMessage;
import root.dao.message.LogoutErrorMessage;
import root.dao.message.SimpleMessage;
import root.util.log.LogLine;

public class UtilityBarController implements Observer{

    @FXML
    private AnchorPane mainBar;
	
    @FXML
    private Label errorLbl;
	
    @FXML
    private ImageView appLogoutBtn;

    @FXML
    private ImageView appMiniBtn;

    @FXML
    private ImageView appLogExitBtn;
    
    @FXML
    private ImageView appExitBtn;

    private Stage primaryStage;
    
    private DataKeepManager dkm;
    
    private double xOffset;
    private double yOffset;
    private boolean exitApp;
    
    private static final int ERROR_DISPLAY_TIME = 7;
    
    @FXML
	public void initialize() throws IOException{
    	dkm = DataKeepManager.getInstance();
    	primaryStage = ScreensManager.getInstance().getPrimaryStage();
    	exitApp = false;
    	
    	errorLbl.setStyle("-fx-text-fill: #b71c1c;");
    	
    	if (dkm.getUser() == null) {
    		appLogoutBtn.setOpacity(0);
    		appLogoutBtn.setDisable(true);
    	}
    	
    	initDrag();
    }
    
    private void initDrag() {
    	mainBar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
    	mainBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });
    }
    
    private void setErrorLabelText(String msg, int displayTime) {
		PauseTransition delay = new PauseTransition(Duration.seconds(displayTime));
		
		EventHandler<ActionEvent> e = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				errorLbl.setText("");
			}
		};
		
		delay.setOnFinished(e);
		errorLbl.setText(msg);
		delay.play();
    }
    
    @FXML
    void logExitBtn(MouseEvent event) {
    	exitApp = true;
    	sendLogoutMsg();
    }
    
    @FXML
    void exitBtn(MouseEvent event) {
    	exitApp();
    }

    void exitApp() {
    	User user = dkm.getUser();
    	if (user == null) {
    		Platform.exit();
    		System.exit(0);
    	}else {
    		setErrorLabelText("You must logout first", ERROR_DISPLAY_TIME);
    	}
    }
    
    void sendLogoutMsg() {
    	User user = dkm.getUser();
    	if (user == null) return;
    	ObservableClient client = (ObservableClient)dkm.getObject_NoRemove("client");
    	client.addObserver(this);
    	LoggedOutMessage msg = new LoggedOutMessage(user.getUserID());
    	try {
    		client.sendToServer(msg);
    	} catch (IOException e1) {
    		exitApp = false;
    		e1.printStackTrace();
    	}
    }
    
    @FXML
    void logoutBtn(MouseEvent event) {
    	sendLogoutMsg();
    }

    @FXML
    void minimizeBtn(MouseEvent event) {
    	primaryStage.setIconified(true);
    }

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof LogoutErrorMessage) {
			Platform.runLater(()->{
				setErrorLabelText("Failed logging out", ERROR_DISPLAY_TIME);
			});
		}else if(arg instanceof SimpleMessage) {
			if (((SimpleMessage)arg).getMsg().contains("ok-loggedout")) {
				dkm.removeUser();
				if (exitApp) {
					exitApp();
				}
				ScreensManager.getInstance().switchScreen("main");
			}
		}
	}

}
