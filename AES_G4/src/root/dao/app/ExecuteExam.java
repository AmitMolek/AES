package root.dao.app;

import java.io.Serializable;
import java.security.Timestamp;

/**
 * A class that is responsible for keeping data about execute exam
 * 
 * @author Omer Haimovich
 *
 */
public class ExecuteExam implements Serializable {

	// Instance variables **********************************************

	/**
	 * 
	 * The id of the exam
	 */
	private String examId;
	/**
	 * 
	 * The time when a student can not enter and do an exam
	 */
	private String startTime;
	/**
	 * The code of the exam
	 */
	private String examPassword;
	/**
	 * The type of the exam : 0- manually,1-auto
	 */
	private String examType;
	/**
	 * The duration time of the exam
	 */
	private int durationTime;
	/**
	 * The id of the teacher who execute the exam
	 */
	private String teacherId;

	// CONSTRUCTORS *****************************************************

	/**
	 * Constructs the ExecuteExam
	 * 
	 * @param examId
	 *            the id of the exam
	 * @param startTime
	 *            the time when a student can not enter and do an exam
	 * @param examPassword
	 *            the code of the exam
	 * @param examType
	 *            the type of the exam
	 * @param duration
	 *            the duration time of the exam
	 * 
	 */
	public ExecuteExam(String examId, String startTime, String examPassword, String examType, int duration) {
		super();
		this.examId = examId;
		this.startTime = startTime;
		this.examPassword = examPassword;
		this.examType = examType;
		durationTime = duration;
	}

	/**
	 * Constructs the ExecuteExam
	 * 
	 * @param examId
	 *            the id of the exam
	 * @param startTime
	 *            the time when a student can not enter and do an exam
	 * @param examPassword
	 *            the code of the exam
	 * @param examType
	 *            the type of the exam
	 */
	public ExecuteExam(String examId, String startTime, String examPassword, String examType) {
		super();
		this.examId = examId;
		this.startTime = startTime;
		this.examPassword = examPassword;
		this.examType = examType;
	}

	/**
	 * Constructs the ExecuteExam
	 * 
	 * @param examId
	 *            the id of the exam
	 * @param startTime
	 *            the time when a student can not enter and do an exam
	 * @param examPassword
	 *            the code of the exam
	 * @param examType
	 *            the type of the exam
	 * @param teacherId
	 *            the id of the teacher who execute the exam
	 */

	public ExecuteExam(String examId, String startTime, String examPassword, String examType, String teacherId) {
		super();
		this.examId = examId;
		this.startTime = startTime;
		this.examPassword = examPassword;
		this.examType = examType;
		this.teacherId = teacherId;
	}

	/**
	 * A method that set the id of the teacher who execute the exam
	 * 
	 * @return the id of the teacher who execute the exam
	 */
	public String getTeacherId() {
		return teacherId;
	}

	/**
	 * A method that set the id of the teacher who execute the exam
	 * 
	 * @param teacherId
	 *            id of the teacher who execute the exam
	 */
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	/**
	 * A method that returns the id of the exam
	 * 
	 * @return the id of the exam
	 */
	public String getExamId() {
		return examId;
	}

	/**
	 * A method that returns the time when a student can not enter and do an exam
	 * 
	 * @return the time when a student can not enter and do an exam
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * A method that returns the code of the exam
	 * 
	 * @return the code of the exam
	 */
	public String getExamPassword() {
		return examPassword;
	}

	/**
	 * A method that returns the type of the exam
	 * 
	 * @return return the type of the exam
	 */
	public String getExamType() {
		return examType;
	}

	/**
	 * A method that returns the duration time of the exam
	 * 
	 * @return the duration time of the exam
	 */
	public int getDurationTime() {
		return durationTime;
	}

	/**
	 * A method that set the duration time of the exam
	 * 
	 * @param durationTime
	 *            the the duration time of the exam
	 */
	public void setDurationTime(int durationTime) {
		this.durationTime = durationTime;
	}

}
