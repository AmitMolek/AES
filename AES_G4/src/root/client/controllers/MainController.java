package root.client.controllers;

import java.io.IOException;
import java.util.Observer;

import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import ocsf.client.ObservableClient;

public class MainController implements Observer{
	  
	private ObservableClient client;
    @FXML
    private AnchorPane paneId;
    
    @FXML
	public void initialize() throws IOException{
    	client = new ObservableClient("localhost", 8000);
    	client.addObserver(this);
    	client.openConnection();
    }

	@Override
	public void update(java.util.Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
}
