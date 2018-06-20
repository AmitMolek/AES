package root.server.managers.usersmgr;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import ocsf.server.ConnectionToClient;
import root.dao.message.ChangeTimeDurationRequest;
import root.dao.message.SimpleMessage;

/**
 * @author Naor Saadia
 * this class hold all the principles 
 * that connected and keep their connection
 */
public class PrincipleManager {
	/**
	 * ArrayList of principles ConnectionToClient thread
	 */
	private ArrayList<ConnectionToClient> principles = new ArrayList<ConnectionToClient>();
	
	/**
	 * add principle ConnectionToClient to the ArrayList
	 * @param c
	 */
	public void addPrinciple(ConnectionToClient c) {
		principles.add(c);
	}
	
	/**
	 * remove principle from the arraylist
	 * @param c
	 */
	public void removePrinciple(ConnectionToClient c){
		principles.remove(c);
	}
	
	/**
	 * send principles change duration request
	 * @param ch
	 */
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
