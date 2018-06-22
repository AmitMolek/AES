package root.server.managers.usersmgr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import ocsf.server.ConnectionToClient;
import root.dao.message.SimpleMessage;

/**
 * @author Naor Saadia
 * this class is singleTone
 * the class manage all the student that in the execute exam
 * it also manage the change time duration observes
 */
public class ExecuteStudentManager {
	
	/**
	 * HashMap of exams, for each exam have the connected clients 
	 */
	private HashMap<String,ArrayList<ConnectionToClient>> studentInExecute = new HashMap();
	
	/**
	 * HashMap for the clients that wait for change duration 
	 */
	private HashMap<String,ArrayList<ConnectionToClient>> waitForChangeDuration = new HashMap();
	
	/**
	 * Create the instance 
	 */
	private static ExecuteStudentManager INSTANCE = new ExecuteStudentManager();
	
	/**
	 * 
	 * @return the instance
	 */
	public static ExecuteStudentManager getInstance() {
		return INSTANCE;
	}

	/**
	 * add the duration to the map
	 * if the exam was there already the map just andd the client
	 * @param examId
	 * @param client
	 */
	public void addDuration(String examId, ConnectionToClient client) {
		if(waitForChangeDuration.containsKey(examId)){
			waitForChangeDuration.get(examId).add(client);
		}
		else {
			ArrayList<ConnectionToClient> ar = new ArrayList<ConnectionToClient>();
			ar.add(client);
			waitForChangeDuration.put(examId, ar);
		}
	}
	
	/**
	 * this method send to all durations the new time 
	 * @param examID
	 * @param newTime
	 */
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
	
	/**
	 * this method return the num of the 
	 * students that execute specific exam
	 * @param examId
	 * @return
	 */
	public int getNumOfStudents(String examId) {
		if(studentInExecute.get(examId)==null) {
			return 0;
		}
		return studentInExecute.get(examId).size();
	}
	
	/**
	 * 
	 * @param examId
	 * @param client
	 */
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
	
	/**
	 * remove students from the map
	 * @param examId
	 * @param client
	 */
	public void removeStudent(String examId,ConnectionToClient client) {
		ArrayList<ConnectionToClient> c = studentInExecute.get(examId);
		c.remove(c.indexOf(client));
		studentInExecute.replace(examId, c);
	}
	
	/**
	 * remove duration from the map
	 * @param examId
	 * @param client
	 */
	public void removeDuration(String examId,ConnectionToClient client) {
		waitForChangeDuration.remove(examId, client);
	}

	/**
	 * send all durations the new time
	 * @param examID
	 * @param newTime
	 */
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

	public void removeExam(String examId) {
		studentInExecute.remove(examId);
	}
	
}
