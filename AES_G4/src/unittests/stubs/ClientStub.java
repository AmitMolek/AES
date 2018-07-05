package unittests.stubs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observer;

import ocsf.client.ObservableClient;
import root.dao.app.LoginInfo;
import root.dao.app.User;
import root.dao.message.LoginMessage;

public class ClientStub implements IClient {

	private ArrayList<LoginInfo> stubUser;
	private String message;

	

	@Override
	public void openConnection() throws IOException {
	}

	@Override
	public void sendToServer(Object msg) throws IOException {
		LoginMessage newLoginMessage = (LoginMessage) msg;
		LoginInfo log = newLoginMessage.getUser();
		userExist(log);
	}

	@Override
	public void addObserver(Observer o) {

	}

	@Override
	public void deleteObservers() {
	}


	public void userExist(LoginInfo log) {
		for(LoginInfo i: stubUser) {
			if(i.getUserID().equals(log.getUserID())) {
				if(i.getPassword().equals(log.getPassword())) {
					message = "Login success";
				}
			}
		}
		message = "Login Failed";
		
	}
	
	public ArrayList<LoginInfo> getStubUser() {
		return stubUser;
	}

	public void setStubUser(ArrayList<LoginInfo> stubUser) {
		this.stubUser = stubUser;
	}
	
	public String getMessage() {
		return message;
	}

}
