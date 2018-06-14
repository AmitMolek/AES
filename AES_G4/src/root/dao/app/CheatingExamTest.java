package root.dao.app;

public class CheatingExamTest {

	String user_id;
	String exam_id;
	boolean cheating_flag;
	
	public CheatingExamTest(String user_id, String exam_id, boolean cheating_flag) {
		this.user_id = user_id;
		this.exam_id = exam_id;
		this.cheating_flag = cheating_flag;
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
	
}
