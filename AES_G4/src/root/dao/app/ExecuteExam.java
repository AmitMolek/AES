package root.dao.app;

import java.io.Serializable;
import java.security.Timestamp;

public class ExecuteExam implements Serializable {
	private String examId;
	private String startTime;
	private String examPassword;
	private String examType;// 0- manually,1-auto
	private int durationTime;
	private String teacherId;

	/**
	 * Constructor for execute exam
	 * 
	 * @param examId
	 *            the exam id
	 * @param startTime
	 *            the start time of the exam
	 * @param examPassword
	 *            the password of the exam
	 * @param examType
	 *            the exam type
	 */
	public ExecuteExam(String examId, String startTime, String examPassword, String examType, int duration) {
		super();
		this.examId = examId;
		this.startTime = startTime;
		this.examPassword = examPassword;
		this.examType = examType;
		durationTime = duration;
	}

	public ExecuteExam(String examId, String startTime, String examPassword, String examType) {
		super();
		this.examId = examId;
		this.startTime = startTime;
		this.examPassword = examPassword;
		this.examType = examType;
	}
	
	public ExecuteExam(String examId, String startTime, String examPassword, String examType,String teacherId) {
		super();
		this.examId = examId;
		this.startTime = startTime;
		this.examPassword = examPassword;
		this.examType = examType;
		this.teacherId = teacherId;
	}

	/**
	 * 
	 * @return the teacher id
	 */
	public String getTeacherId() {
		return teacherId;
	}

	/**
	 * Set new teacher id
	 * 
	 * @param teacherId
	 *            the teacher id
	 */
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	/**
	 * 
	 * @return the exam id
	 */
	public String getExamId() {
		return examId;
	}

	/**
	 * 
	 * @return the exam start time
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * 
	 * @return the exam password
	 */
	public String getExamPassword() {
		return examPassword;
	}

	/**
	 * 
	 * @return return the exam type
	 */
	public String getExamType() {
		return examType;
	}

	public int getDurationTime() {
		return durationTime;
	}

	public void setDurationTime(int durationTime) {
		this.durationTime = durationTime;
	}

}
