package root.server.managers.usersmgr;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import root.dao.message.UserSolvedExamsMessage;
import root.server.managers.dbmgr.GetFromDB;
import root.server.managers.dbmgr.SetInDB;

public class ExamExecutedManager {
	
	private HashMap<String,String> examsTime = new HashMap<String,String>();
	
	private int numOfStudent=0;
	
	public boolean isContains(String examId,String userId){
		GetFromDB get = new GetFromDB();
		return get.getSolvedExamByID(examId, userId);
		
	}
	
	public void add(String examId,String time) {
		numOfStudent++;
		//int pointIndex = time.indexOf(".");
		//time = time.substring(0,pointIndex);
		examsTime.put(examId, time);
	}
	
	public void remove(String examId) {
		if(!checkTime(examId)){
			SetInDB set = new SetInDB();
			set.deleteExecutedExam(examId);
			set.lockExam(examId);
		}
			
		examsTime.remove(examId);
	}
		
	public boolean isExist(String examId) {
		return examsTime.containsKey(examId);
	}

	public boolean checkTime(String examId) {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		try {
			System.out.println(examsTime.get(examId));
			Date examDate = sdf.parse(examsTime.get(examId));
			Date nowDate = new Date();
			if(nowDate.compareTo(examDate)<=0)
				return true;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;	
	}
		
	
}
