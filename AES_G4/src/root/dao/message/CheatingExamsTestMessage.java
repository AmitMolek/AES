package root.dao.message;

import java.util.ArrayList;

import root.dao.app.CheatingExamTest;

public class CheatingExamsTestMessage extends AbstractMessage{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String user_id;
	String exam_id;
	ArrayList<CheatingExamTest> exams;
	
	public CheatingExamsTestMessage(String exam_id, ArrayList<CheatingExamTest> exams) {
		super("ok-get-cheatingexamstest");
		this.exam_id = exam_id;
		this.exams = exams;
	}
	
	public CheatingExamsTestMessage(String exam_id) {
		super("get-cheatingexamstest");
		this.exam_id = exam_id;
	}
	
	public CheatingExamsTestMessage(String user_id, String exam_id) {
		super("put-cheatingexamstest");
		this.user_id = user_id;
		this.exam_id = exam_id;
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

	public ArrayList<CheatingExamTest> getExams() {
		return exams;
	}

	public void setExams(ArrayList<CheatingExamTest> exams) {
		this.exams = exams;
	}

	@Override
	public String getType() {
		return "CheatingExamsTest";
	}
}
