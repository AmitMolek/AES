package root.server.managers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.opencsv.CSVReader;

import root.dao.app.CheatingExamTest;
import root.dao.app.QuestionInExam;
import root.server.managers.dbmgr.GetFromDB;
import root.server.managers.dbmgr.SetInDB;

/**
 * Automated system for checking cheating
 * To use you need to create a new object of this class and pass to it
 * the exam's id and the date when it executed
 * @author Amit Molek
 *
 */

public class CheatingChecker {
	
	private class CSVData{
		/**
		 * Helper class for storing data
		 */
		public HashMap<String, Integer> entries;
		
		public CSVData() {
			entries = new HashMap<>();
		}
	}
	
	String exam_id;
	Calendar cal_date;
	ArrayList<CheatingExamTest> exams;
	ArrayList<CheatingExamTest> cheatedExams;
	HashMap<String, Integer> qusCorrect;
	HashMap<String, CSVData> users_data;
	
	private final String csv_path;
	
	/**
	 * Checks the exam (associated with exam_id in the date) for cheating
	 * @param exam_id the id of the exam you want to check for cheating
	 * @param date the date of the exam you want to check for cheating
	 */
	public CheatingChecker(String exam_id, Calendar date) {
		this.cal_date = date;
		csv_path = ServerMessageManager.PATHCSV;
		this.cheatedExams = new ArrayList<>();
		this.qusCorrect = new HashMap<>();
		this.users_data = new HashMap<>();
		this.exam_id = exam_id;
		getExamsFromDB();
		getCorrectAnswersFromDB();
		getEntriesFromCSV();
		checkForCheating();
		setCheatedExamsFlag();
	}
	
	/**
	 * Prints all the cheated exams
	 */
	public void printCheatedExams() {
		System.out.println("Exam ID: " + exam_id + ", Date: " + cal_date.get(Calendar.YEAR) + "-" + (cal_date.get(Calendar.MONTH) + 1) + "-" + cal_date.get(Calendar.DATE));
		System.out.println("Cheated: " + cheatedExams.size());
		for (CheatingExamTest cheatingExamTest : cheatedExams) {
			String msg = "User: " + cheatingExamTest.getUser_id() + " Exam ID: " + cheatingExamTest.getExam_id();
			System.out.println(msg);
		}
	}
	
	/**
	 * Fills the correct questions map (qusCorrect) with all the questions id and their correct answers
	 */
	private void getCorrectAnswersFromDB() {
		ArrayList<QuestionInExam> qusns = new GetFromDB().questionInExam(exam_id);
		
		for (QuestionInExam questionInExam : qusns) {
			qusCorrect.put(questionInExam.getQuestion().getQuestionId(), questionInExam.getQuestion().getCorrectAns());
		}
	}
	
	/**
	 * Gets the exam of the user with user_id
	 * @param user_id the id of the user you want the exam of
	 * @return CheatingExamTest object of the exam and the user
	 */
	private CheatingExamTest getExam(String user_id) {
		for (CheatingExamTest cheatingExamTest : exams) {
			if (cheatingExamTest.getUser_id().equals(user_id)) return cheatingExamTest;
		}
		
		return null;
	}
	
	/**
	 * Checks if the user has cheated in this exam
	 * @param user_id the id of the user you want to check if he cheated
	 * @return true if the user cheated
	 */
	private boolean isUserCheated(String user_id) {
		for (CheatingExamTest cet : cheatedExams) {
			if (cet.getUser_id().equals(user_id)) return true;
		}
		return false;
	}
	
	/**
	 * Checks the exams for cheating
	 * Constructs all the wrong answers and compares them to other wrong answers of other exams
	 */
	private void checkForCheating() {
		
		for (Entry<String, CSVData> exam_a : users_data.entrySet()) {
			
			Map<String, Integer> exam_a_wrong = constructWrongAnswer(exam_a.getValue());
			for (Entry<String, CSVData> exam_b : users_data.entrySet()) {
				if (exam_a.getKey().equals(exam_b.getKey())) continue;
				
				Map<String, Integer> exam_b_wrong = constructWrongAnswer(exam_b.getValue());
				
				CheatingExamTest a = getExam(exam_a.getKey());
				CheatingExamTest b = getExam(exam_b.getKey());
				if (checkTwoExams(exam_a_wrong, exam_b_wrong) && (!cheatedExams.contains(a) || !cheatedExams.contains(b))) {
					if (!cheatedExams.contains(a)) cheatedExams.add(a);
					if (!cheatedExams.contains(b)) cheatedExams.add(b);
				}
			}
		}
	}
	
	/**
	 * Checks two exams for cheating:
	 * first if the number of wrong answers of the exams (exam_a and exam_b) are not the same, they didnt cheat (returns false)
	 * passes all the wrong answers of both exams and compares them
	 * if they are the same they suspected for cheating, returns true
	 * @param exam_a the first exam you want to check
	 * @param exam_b the second exam you want to check
	 * @return true if the exams suspected of cheating, false otherwise
	 */
	private boolean checkTwoExams(Map<String, Integer> exam_a, Map<String, Integer> exam_b) {
		if (exam_a.size() != exam_b.size()) return false;
		int amount = exam_a.size();
		
		for (Entry<String, Integer> entry_a : exam_a.entrySet()) {
			Integer b_ans = exam_b.get(entry_a.getKey());
			if (entry_a.getValue() == b_ans) amount--;
		}
		
		if (amount <= 0) return true;
		return false;
	}
	
	/**
	 * Returns the correct answer of the qus_id question
	 * @param qus_id the id of the question you want to get the correct answer
	 * @return the number of the correct answer
	 */
	private Integer getQuestionCorrectAnswer(String qus_id) {
		return (qusCorrect.get(qus_id));
	}
	
	/**
	 * Builds a map of the wrong answers that the user selected
	 * @param data the data of the questions and the student's answers
	 * @return a map object filled with all the wrong answers that user answered
	 */
	private Map<String, Integer> constructWrongAnswer(CSVData data){
		Map<String, Integer> wrongAns = new HashMap<String, Integer>();
		for (Entry<String, Integer> entry : data.entries.entrySet()) {
			if (entry.getValue() != getQuestionCorrectAnswer(entry.getKey()))
				wrongAns.put(entry.getKey(), entry.getValue());
		}
		
		return wrongAns;
	}
	
	/**
	 * Gets the exams from the database
	 */
	public void getExamsFromDB() {
		exams = new GetFromDB().solvedExamCheatingTest(exam_id, cal_date);
	}
	
	/**
	 * Checks if the user exist in this exam
	 * @param user_id the user you want to check if exist
	 * @return return true if the user exists
	 */
	private boolean checkForUserExistance(String user_id) {
		for (CheatingExamTest cheatingExamTest : exams) {
			if (cheatingExamTest.getUser_id().equals(user_id)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Sets the cheating flag of the cheating exams in the DB to 'yes'
	 */
	private void setCheatedExamsFlag() {
		for (CheatingExamTest cheatingExamTest : exams) {
			boolean cheated = false;
			if (isUserCheated(cheatingExamTest.getUser_id()))
				cheated = true;
			setUserCheatingFlag(cheatingExamTest.getUser_id(), cheated);
		}
	}
	
	/**
	 * Sets the user's exam cheating flag to cheated
	 * @param user_id the user you want to sets the cheating flag
	 * @param cheated if the user cheated or not
	 */
	private void setUserCheatingFlag(String user_id, boolean cheated) {
		if (!checkForUserExistance(user_id)) return;
		
		(new SetInDB()).updateSolvedExamCheatingFlag(user_id, exam_id, cheated);
	}
	
	/**
	 * Fills the the users_data map with the data from the csv files 
	 */
	private void getEntriesFromCSV() {
		for (CheatingExamTest cheatingExamTest : exams) {
			String u_id = cheatingExamTest.getUser_id();
			users_data.put(u_id, getEntryFromCSV(u_id));
		}
	}
	
	/**
	 * Returns a CSVData of the wanted user
	 * @param user_id the user you want to get the csv data of
	 * @return CSVData object filled with the questions and the user's answer
	 */
	private CSVData getEntryFromCSV(String user_id) {
		try {
			
			String folderName = exam_id + "-" + String.format("%d-%02d-%d", cal_date.get(Calendar.YEAR), (cal_date.get(Calendar.MONTH) + 1), cal_date.get(Calendar.DATE));
			CSVReader reader = new CSVReader(new FileReader(csv_path + folderName + "/" + user_id + ".csv"));
	    	List<String[]> myEntries = reader.readAll();
	    	CSVData userData = new CSVData();
	    	for(int i = 1; i < myEntries.size(); i++) {
	    		String ques_id = (myEntries.get(i))[0];
	    		Integer select_ans = Integer.parseInt((myEntries.get(i))[1]);
	    		
	    		userData.entries.put(ques_id, select_ans);
	    	}
	    	
	    	reader.close();
	    	return userData;
		}catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
