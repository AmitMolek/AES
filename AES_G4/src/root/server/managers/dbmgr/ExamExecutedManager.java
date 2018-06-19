package root.server.managers.dbmgr;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

import root.server.managers.usersmgr.ExecuteStudentManager;

public class ExamExecutedManager {
	
	private HashMap<String,String> examsTime;		
	private ExecuteStudentManager examinees;
	
	int sem;
	public ExamExecutedManager() {
		examinees= ExecuteStudentManager.getInstance() ;
		examsTime = new HashMap<String,String>();
		timerCheckThread.start();
	}
	
	
	public boolean isContains(String examId,String userId){
		GetFromDB get = new GetFromDB();
		return get.getSolvedExamByID(examId, userId);
	}
	
	public void add(String examId,String time) {
		examsTime.put(examId, time);
	}
	
	public void remove(String examId) {
		if(!checkTime(examId)){
			SetInDB set = new SetInDB();
			set.deleteExecutedExam(examId);
			set.lockExam(examId);
			examsTime.remove(examId);
		}
	}
		
	public boolean isExist(String examId) {
		return examsTime.containsKey(examId);
	}

	public boolean checkTime(String examId) {
		DateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		try {
			Date examDate = sdf.parse(examsTime.get(examId));
			Date nowDate = sdf.parse(sdf.format(new Date()));
			
			if(nowDate.compareTo(examDate)<=0)
				return true;
		} catch (ParseException e) {
			e.printStackTrace();
		}  
		return false;	
	}
	
    Thread timerCheckThread = new Thread()
    {
        @Override
        public void run()
        {            	
        	
            while(true) {
            	try {
					sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            		Set<String> ids= examsTime.keySet();
            		for(String id:ids) {
                		if(!checkTime(id)){
                			if(examinees.getNumOfStudents(id)==0) {
                				remove(id);
                			}
                		}
            	} 
            }
       }
    };
}
