package root.server.managers.dbmgr;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

import root.server.managers.CheatingChecker;
import root.server.managers.usersmgr.ExecuteStudentManager;

/**
 * @author Naor Saadia
 * this class take care about the execute exams
 * about the duration time
 */
public class ExamExecutedManager {
	
	/**
	 * HashMap that have the exams id 
	 * and the time of the exam
	 */
	private HashMap<String,String> examsTime;
	
	/**
	 * Execute Student manager instance
	 */
	private ExecuteStudentManager examinees;
	
	/**
	 * The constructor
	 */
	public ExamExecutedManager() {
		examinees= ExecuteStudentManager.getInstance() ;
		examsTime = new HashMap<String,String>();
		timerCheckThread.start();
	}
	
	/**
	 * isContains method check if the exam is 
	 * already solved by the student
	 * by check it in the solved exams db
	 * @param examId
	 * @param userId
	 * @return
	 */
	public boolean isContains(String examId,String userId){
		GetFromDB get = new GetFromDB();
		return get.getSolvedExamByID(examId, userId);
	}
	
	/**
	 * add new exam and his time
	 * @param examId
	 * @param time
	 */
	public void add(String examId,String time) {
		examsTime.put(examId, time);
	}
	
	/**
	 * if the time already pass the execute exam lock 
	 * and removed from the execute exams table
	 * @param examId
	 */
	public void remove(String examId) {
		if(!checkTime(examId)){
			SetInDB set = new SetInDB();
			set.deleteExecutedExam(examId);
			set.lockExam(examId);
			examsTime.remove(examId);
			examinees.removeExam(examId);
			new CheatingChecker(examId, Calendar.getInstance());
		}
	}
	
	/**
	 * check if the exam exist in the hash map
	 * @param examId
	 * @return
	 */
	public boolean isExist(String examId) {
		return examsTime.containsKey(examId);
	}

	/**
	 * check if the time of the recived exam is pass
	 * if yes return false
	 * @param examId
	 * @return
	 */
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
	
	/**
	 * Thread that run always 
	 * and check for all exams if the time is pass
	 */
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
