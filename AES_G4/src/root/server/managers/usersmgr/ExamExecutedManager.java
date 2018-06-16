package root.server.managers.usersmgr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import root.dao.message.UserSolvedExamsMessage;

public class ExamExecutedManager {
	
	private HashMap<String,ArrayList<String>> exams = new HashMap<String, ArrayList<String>>();
	private boolean containsFlag; 
	
	public boolean isContains(String examId,String userId){
		if(exams.containsKey(examId)) {
			
			ArrayList<String> users = exams.get(examId);
			return users.contains(userId);
		}
		return false;
	}
	
	public void add(String examId,String userId) {
	    ArrayList<String> usersList = exams.get(examId);

	    // if list does not exist create it
	    if(usersList == null) {
	    	usersList = new ArrayList<String>();
	    	usersList.add(userId);
	    	exams.put(examId, usersList);
	    } else {
	        // add if item is not already in list
	        if(!usersList.contains(examId))
	        	usersList.add(userId);
	    }


	}
	
}
