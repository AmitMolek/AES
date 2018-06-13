package root.client.controllers;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage; 

public class GetDBinformtaionController {

    @FXML
    private TextField txtFieldPassword;

    @FXML
    private TextField txtFieldUser;

    @FXML
    private TextField txtFieldURL;

    @FXML
    private Button loginButton;


    private ObservableList<String> enteredDBinformation;
    @FXML
    void getDBpassword(ActionEvent event) {
    	enteredDBinformation.add(txtFieldPassword.getText());
    	enteredDBinformation.add(txtFieldUser.getText());
    	enteredDBinformation.add(txtFieldURL.getText());
    	
    	closeStage(event);
    }

    private void closeStage(ActionEvent event) {
		Platform.runLater(() -> {				// In order to run javaFX thread.(we recieve from server a java thread)	
				Node  source = (Node)  event.getSource(); 
		        Stage stage  = (Stage) source.getScene().getWindow();
		        stage.close();	
		});	
    }
	public void setAppMainObservableList(ObservableList<String> enteredDBinformation) {
		this.enteredDBinformation = enteredDBinformation;
		
	}

}
