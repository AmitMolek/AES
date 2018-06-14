package root.server.managers;

import java.util.ArrayList;
import java.util.Vector;

import root.client.controllers.UpdateDeleteExamController;
import root.dao.app.CheatingExamTest;
import root.server.managers.dbmgr.GetFromDB;
import root.server.managers.dbmgr.SetInDB;

public class CheatingChecker {
	
	String exam_id;
	ArrayList<CheatingExamTest> exams;
	ArrayList<CheatingExamTest> cheatedExams;
	
	private int MIN_CHEAT_ANS = 3;
	
	public CheatingChecker(String exam_id) {
		this.cheatedExams = new ArrayList<>();
		this.exam_id = exam_id;
		getExamsFromDB();
	}
	
	/**
	 * Sets the amount of the same answers between exams the system will flag the exam
	 * @param min the min amount of same answers
	 */
	public void setMinCheatingAnswers(int min) {
		this.MIN_CHEAT_ANS = min;
	}
	
	/**
	 * The amount of the same answers between exams the system will flag the exam 
	 * @return the min amount of same answers
	 */
	public int getMinCheatingAnswers() {
		return MIN_CHEAT_ANS;
	}
	
	/**
	 * Checks the exams for cheating
	 */
	public void checkForCheating() {
		
		cheatedExams.add(new CheatingExamTest("", "", true));
	}
	
	// MUST CHANGE: NEED TO KNOW WHAT ANSWER IS WRONG AND WHAT NOT
	/**
	 * Checks two exam's vectors if they have min amount of same wrong answers between them
	 * @param a the answer vector of the first exam
	 * @param b the answer vector of the second exam
	 * @return true if the vectors have more or equal amount of same wrong answers
	 */
	private boolean checkTwoExams_UsingMinAmount(Vector<Integer> a, Vector<Integer> b) {
		int amount = 0;
		
		for (int i = 0; i < a.size(); i++) {
			if (a.get(i) == b.get(i)) amount++;
		}
		
		if (amount >= MIN_CHEAT_ANS) return true;
		return false;
	}
	
	/**
	 * Gets the exams from the database
	 */
	public void getExamsFromDB() {
		//exams = new GetFromDB().solvedExamCheatingTest(exam_id);
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
	 * Sets the user's exam cheating flag to cheated
	 * @param user_id the user you want to sets the cheating flag
	 * @param cheated if the user cheated or not
	 */
	public void setUserCheatingFlag(String user_id, boolean cheated) {
		if (!checkForUserExistance(user_id)) return;
		
		(new SetInDB()).updateSolvedExamCheatingFlag(user_id, exam_id, cheated);
	}
	
}
