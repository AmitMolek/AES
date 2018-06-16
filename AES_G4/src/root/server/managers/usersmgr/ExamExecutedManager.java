package root.server.managers.usersmgr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import root.dao.message.UserSolvedExamsMessage;
import root.server.managers.dbmgr.GetFromDB;

public class ExamExecutedManager {
	
	private HashMap<String,String> examsTime = new HashMap<String,String>();
	private HashMap <String,Boolean> lockedExams = new HashMap<String,Boolean>();
	
	private boolean lock=false;	
	
	public boolean isContains(String examId,String userId){
		GetFromDB get = new GetFromDB();
		return get.getSolvedExamByID(examId, userId);
		
	}
	
	public void add(String examId,String time) {
		boolean value =false;
		examsTime.put(examId, time);
		lockedExams.put(examId, new Boolean(value));
	}
	
	public boolean isExist(String examId) {
		return examsTime.containsKey(examId);
	}

	public void endTime(String examId) {
		
		
	}
	
	public boolean isLock(String exmamId) {
		return lockedExams.get(exmamId).booleanValue();
	}
	
	public void lockExam(String examId) {
		
	}
	
	
}
