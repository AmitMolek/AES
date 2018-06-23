package root.client.controllers;

import java.io.IOException;

import ocsf.client.ObservableClient;
import root.client.managers.DataKeepManager;
import root.dao.message.ChangeTimeDurationRequest;

/**
 * @author Naor Saadia
 * this class send change time request
 */
public class SendRequests {
	ObservableClient client;
	private DataKeepManager dbk;
	public SendRequests() {
		client = new ObservableClient((String)dbk.getInstance().getObject_NoRemove("ip"), 8000);
    	try {
			client.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * this method send change time duration request
	 * @param cht
	 * @throws IOException
	 */
	public void send(ChangeTimeDurationRequest cht) throws IOException {
		client.sendToServer(cht);
		client.closeConnection();
	}

}
