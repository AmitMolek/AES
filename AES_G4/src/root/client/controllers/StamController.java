package root.client.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import ocsf.client.ObservableClient;
import root.dao.message.ChangeTimeDurationRequest;

public class StamController {

    @FXML
    private Button btnStam;
    
    private ObservableClient client;
    
    @FXML
	public void initialize() throws IOException{
    	client = new ObservableClient("localhost", 8000);
    	client.openConnection();
    	
    }

    @FXML
    void send(ActionEvent event) {
    	try {
			client.sendToServer(new ChangeTimeDurationRequest());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}

