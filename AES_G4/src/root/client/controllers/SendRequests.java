package root.client.controllers;

import java.io.IOException;

import ocsf.client.ObservableClient;
import root.dao.message.ChangeTimeDurationRequest;

public class SendRequests {
	ObservableClient client;
	public SendRequests() {
		client = new ObservableClient("localhost", 8000);
    	try {
			client.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void send(ChangeTimeDurationRequest cht) throws IOException {
		client.sendToServer(cht);
	}

}
