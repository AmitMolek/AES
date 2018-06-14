package root.server.managers.usersmgr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import ocsf.server.ConnectionToClient;
import root.dao.message.SimpleMessage;

public class ExecuteStudentManager {

	private HashMap<String,ArrayList<ConnectionToClient>> studentInExecute = new HashMap();
	
	public void addStudent(String examId, ConnectionToClient client) {
		if(studentInExecute.containsKey(examId)){
			studentInExecute.get(examId).add(client);
		}
		else {
			ArrayList<ConnectionToClient> ar = new ArrayList<ConnectionToClient>();
			ar.add(client);
			studentInExecute.put(examId, ar);
		}
	}
	
	public void removeStudent(String examId,ConnectionToClient client) {
		studentInExecute.remove(examId, client);
	}
	
	public void sendAll(String examID, int newTime) {
		ArrayList<ConnectionToClient> ar = studentInExecute.get(examID);
		if(ar!=null) {
			for(ConnectionToClient c:ar) {
				try {
					c.sendToClient(newTime);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
