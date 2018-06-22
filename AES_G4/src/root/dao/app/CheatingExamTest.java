package root.dao.app;

import java.util.Date;

/**
 * Data storage class for the cheating checker
 * @author Amit Molek
 *
 */

public class CheatingExamTest {

	/*
	 * The id of the user that solved the exam
	 */
	String user_id;
	
	/*
	 * The id of the exam
	 */
	String exam_id;
	
	/**
	 * If the user cheated in the exam or not
	 */
	boolean cheating_flag;
	
	/**
	 * Class constructor
	 * @param user_id the id of the user the solved the exam
	 * @param exam_id the id of the solved exam
	 * @param cheating_flag if the uesr cheated or not
	 */
	public CheatingExamTest(String user_id, String exam_id, boolean cheating_flag) {
		this.user_id = user_id;
		this.exam_id = exam_id;
		this.cheating_flag = cheating_flag;
	}

	/**
	 * Returns the id of the user
	 * @return the id of the user
	 */
	public String getUser_id() {
		return user_id;
	}

	/**
	 * Sets the user id to user_id
	 * @param user_id the new id of the user
	 */
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	/**
	 * Returns the id of the solved exam
	 * @return id of the solved exam
	 */
	public String getExam_id() {
		return exam_id;
	}

	/**
	 * Sets the id of the exam ti exam_id
	 * @param exam_id
	 */
	public void setExam_id(String exam_id) {
		this.exam_id = exam_id;
	}

	/**
	 * Checks if the user cheated in the exam
	 * @return true if the user cheated
	 */
	public boolean isCheating_flag() {
		return cheating_flag;
	}

	/**
	 * Sets if the user cheated in the exam or not
	 * @param cheating_flag
	 */
	public void setCheating_flag(boolean cheating_flag) {
		this.cheating_flag = cheating_flag;
	}

	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof CheatingExamTest)) return false;
		
		CheatingExamTest other = (CheatingExamTest) obj;
		
		if (user_id == other.getUser_id())
			if (exam_id == other.getExam_id())
				return true;
		
		return false;
	}
	
}
