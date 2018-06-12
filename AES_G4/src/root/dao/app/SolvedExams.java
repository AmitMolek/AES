package root.dao.app;

import java.io.Serializable;
import java.util.Date;

/**
 * Class for solved exam
 * @author Omer Haimovich
 *
 */
public class SolvedExams implements Serializable {
	private String userId;
	private String examId;
	private int examGrade;
	private int durationTime;
	private String submittedOrNot;//0-interrupted,1- submitted
	private Date date;
	private String teacherNotes;
	private String gradeChangeExplain;
	private String teacherAprroveId;
	private String gradeAprroval;
	private String cheatingFlag;
	
	/**
	 * Constructor for solved exam
	 * @param userId the student id
	 * @param examId the exam id
	 * @param examGrade the grade of the exam
	 * @param durationTime the time that took the student to solve the exam
	 * @param submittedOrNot if student submitted or not
	 * @param date the date of the exam
	 */
	public SolvedExams(String userId, String examId, int examGrade, int durationTime, String submittedOrNot, Date date) {
		this.userId = userId;
		this.examId = examId;
		this.examGrade = examGrade;
		this.durationTime = durationTime;
		this.submittedOrNot = submittedOrNot;
		this.date = date;
	}
	
	/**
	 * Set new teacher note
	 * @param teacherNotes the teacher new
	 */
	public void setTeacherNotes(String teacherNotes) {
		this.teacherNotes = teacherNotes;
	}
	/**
	 * Set new explain for change duration
	 * @param gradeChangeExplain the explain to the change
	 */
	public void setGradeChangeExplain(String gradeChangeExplain) {
		this.gradeChangeExplain = gradeChangeExplain;
	}
	/**
	 * Set new teacher approve grade id
	 * @param teacherAprroveId the new id
	 */
	public void setTeacherAprroveId(String teacherAprroveId) {
		this.teacherAprroveId = teacherAprroveId;
	}
	/**
	 * Set new status for solved exam
	 * @param gradeAprroval the new status
	 */
	public void setGradeAprroval(String gradeAprroval) {
		this.gradeAprroval = gradeAprroval;
	}
	/**
	 * Set if student cheat or not
	 * @param cheatingFlag the cheat status
	 */
	public void setCheatingFlag(String cheatingFlag) {
		this.cheatingFlag = cheatingFlag;
	}
	
}
