package root.server.managers.usersmgr;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import ocsf.server.ConnectionToClient;
import root.dao.message.ChangeTimeDurationRequest;
import root.dao.message.SimpleMessage;

public class PrincipleManager {

	private ArrayList<ConnectionToClient> principles = new ArrayList<ConnectionToClient>();
	
	public void addPrinciple(ConnectionToClient c) {
		principles.add(c);
	}
	
	public void removePrinciple(ConnectionToClient c){
		principles.remove(c);
	}
	
	public void sendAll(ChangeTimeDurationRequest ch) {
		for(ConnectionToClient p:principles) {
			try {
				p.sendToClient(ch);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
	}
}
