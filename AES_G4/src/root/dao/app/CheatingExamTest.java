package root.dao.app;

import java.util.Date;

/**
 * Data storage class for the cheating chcker
 * @author Amit Molek
 *
 */

public class CheatingExamTest {

	String user_id;
	String exam_id;
	Date date;
	boolean cheating_flag;
	
	public CheatingExamTest(String user_id, String exam_id, Date date, boolean cheating_flag) {
		this.user_id = user_id;
		this.exam_id = exam_id;
		this.date = date;
		this.cheating_flag = cheating_flag;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getExam_id() {
		return exam_id;
	}

	public void setExam_id(String exam_id) {
		this.exam_id = exam_id;
	}

	public boolean isCheating_flag() {
		return cheating_flag;
	}

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
