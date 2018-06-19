package root.server.managers.usersmgr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import ocsf.server.ConnectionToClient;
import root.dao.message.SimpleMessage;

public class ExecuteStudentManager {

	private HashMap<String,ArrayList<ConnectionToClient>> studentInExecute = new HashMap();
	private HashMap<String,ArrayList<ConnectionToClient>> waitForChangeDuration = new HashMap();
	private static ExecuteStudentManager INSTANCE = new ExecuteStudentManager();
	
	public static ExecuteStudentManager getInstance() {
		return INSTANCE;
	}

	public void addDuration(String examId, ConnectionToClient client) {
		if(studentInExecute.containsKey(examId)){
			studentInExecute.get(examId).add(client);
		}
		else {
			ArrayList<ConnectionToClient> ar = new ArrayList<ConnectionToClient>();
			ar.add(client);
			waitForChangeDuration.put(examId, ar);
		}
	}
	public void sendAllDurations(String examID, int newTime) {
		ArrayList<ConnectionToClient> ar = waitForChangeDuration.get(examID);
		if(ar!=null) {
			for(ConnectionToClient c:ar) {
				try {
					c.sendToClient(newTime);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public int getNumOfStudents(String examId) {
		if(studentInExecute.get(examId)==null) {
			return 0;
		}
		System.out.println(studentInExecute.get(examId).size());
		return studentInExecute.get(examId).size();
	}
	
	
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
		ArrayList<ConnectionToClient> c= studentInExecute.get(examId);
		c.remove(c.indexOf(client));
		
		studentInExecute.remove(examId, client);
	}
	
	public void removeDuration(String examId,ConnectionToClient client) {
		waitForChangeDuration.remove(examId, client);
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
