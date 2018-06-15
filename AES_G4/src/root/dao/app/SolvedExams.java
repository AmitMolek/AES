package root.dao.app;

import java.io.Serializable;

import java.sql.Date;
import java.sql.Timestamp;
/**
 * 
 * @author gal
 *
 */
public class SolvedExams implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String examID;
	private String sovingStudentID;
	private int examGrade;
	private int solveDurationTime;
	private String submittedOrInterruptedFlag;
	private Timestamp examDateTime;
	private String teacherNotes;
	private String gradeAlturationExplanation;
	private String approvingTeacherID;
	private String calculatedGradeApprovalStateByTeacher;
	private String cheatingFlag;
	private Date date;					// this is temporary field
	private String examCourse;
	private String approvingTeacherName;
	private String action;				// this field is for tableView only
	
	/**
	 * Constructor for solved exam
	 * @param userId the student id
	 * @param examId the exam id
	 * @param examGrade the grade of the exam
	 * @param durationTime the time that took the student to solve the exam
	 * @param submittedOrNot if student submitted or not
	 * @param newDate the date of the exam
	 */
	public SolvedExams(String userId, String examId, int examGrade, int durationTime, String submittedOrNot, java.util.Date newDate) {
		this.sovingStudentID = userId;
		this.examID = examId;
		this.examGrade = examGrade;
		this.solveDurationTime = durationTime;
		this.submittedOrInterruptedFlag = submittedOrNot;
		this.date = new java.sql.Date(newDate.getTime());
	}
	/**
	* kombina with Omer, the constructor abelow is a constructor used by gal.
	**/
	public SolvedExams(String examID, String sovingStudentID, int examGrade, int solveDurationTime,
			String submittedOrInterruptedFlag, Timestamp examDateTime, String teacherNotes,
			String gradeAlturationExplanation, String approvingTeacherID, String calculatedGradeApprovalStateByTeacher,
			String cheatingFlag) {
		super();
		this.examID = examID;
		this.sovingStudentID = sovingStudentID;
		this.examGrade = examGrade;
		this.solveDurationTime = solveDurationTime;
		this.submittedOrInterruptedFlag = submittedOrInterruptedFlag;
		this.examDateTime = examDateTime;
		this.teacherNotes = teacherNotes;
		this.gradeAlturationExplanation = gradeAlturationExplanation;
		this.approvingTeacherID = approvingTeacherID;
		this.calculatedGradeApprovalStateByTeacher = calculatedGradeApprovalStateByTeacher;
		this.cheatingFlag = cheatingFlag;
		this.examCourse = "";
		this.approvingTeacherName = "";
	}
	
	
	/**
	 * @return the approvingTeacherName
	 */
	public String getApprovingTeacherName() {
		return approvingTeacherName;
	}


	/**
	 * @param approvingTeacherName the approvingTeacherName to set
	 */
	public void setApprovingTeacherName(String approvingTeacherName) {
		this.approvingTeacherName = approvingTeacherName;
	}


	/**
	 * @return the examCourse
	 */
	public String getExamCourse() {
		return examCourse;
	}


	/**
	 * @param examCourse the examCourse to set
	 */
	public void setExamCourse(String examCourse) {
		this.examCourse = examCourse;
	}


	/**
	 * @return the examID
	 */
	public String getExamID() {
		return examID;
	}
	/**
	 * @param examID the examID to set
	 */
	public void setExamID(String examID) {
		this.examID = examID;
	}
	/**
	 * @return the sovingStudentID
	 */
	public String getSovingStudentID() {
		return sovingStudentID;
	}
	/**
	 * @param sovingStudentID the sovingStudentID to set
	 */
	public void setSovingStudentID(String sovingStudentID) {
		this.sovingStudentID = sovingStudentID;
	}
	/**
	 * @return the examGrade
	 */
	public int getExamGrade() {
		return examGrade;
	}
	/**
	 * @param examGrade the examGrade to set
	 */
	public void setExamGrade(int examGrade) {
		this.examGrade = examGrade;
	}
	/**
	 * @return the solveDurationTime
	 */
	public int getSolveDurationTime() {
		return solveDurationTime;
	}
	/**
	 * @param solveDurationTime the solveDurationTime to set
	 */
	public void setSolveDurationTime(int solveDurationTime) {
		this.solveDurationTime = solveDurationTime;
	}
	/**
	 * @return the submittedOrInterruptedFlag
	 */
	public String getSubmittedOrInterruptedFlag() {
		return submittedOrInterruptedFlag;
	}
	/**
	 * @param submittedOrInterruptedFlag the submittedOrInterruptedFlag to set
	 */
	public void setSubmittedOrInterruptedFlag(String submittedOrInterruptedFlag) {
		this.submittedOrInterruptedFlag = submittedOrInterruptedFlag;
	}
	/**
	 * @return the examDateTime
	 */
	public Timestamp getExamDateTime() {
		return examDateTime;
	}
	/**
	 * @param examDateTime the examDateTime to set
	 */
	public void setExamDateTime(Timestamp timeStamp) {
		// Date sqlDate=examDate;//new java.sql.Date(date.getTime());
         //Timestamp sqlTime=timeStamp;// new java.sql.Timestamp(date.getTime()); 
		this.examDateTime = timeStamp;
		System.out.println(examDateTime);
	}
	/**
	 * @return the teacherNotes
	 */
	public String getTeacherNotes() {
		return teacherNotes;
	}
	/**
	 * @param teacherNotes the teacherNotes to set
	 */
	public void setTeacherNotes(String teacherNotes) {
		this.teacherNotes = teacherNotes;
	}
	/**
	 * @return the gradeAlturationExplanation
	 */
	public String getGradeAlturationExplanation() {
		return gradeAlturationExplanation;
	}
	/**
	 * @param gradeAlturationExplanation the gradeAlturationExplanation to set
	 */
	public void setGradeAlturationExplanation(String gradeAlturationExplanation) {
		this.gradeAlturationExplanation = gradeAlturationExplanation;
	}
	/**
	 * @return the approvingTeacherID
	 */
	public String getApprovingTeacherID() {
		return approvingTeacherID;
	}
	/**
	 * @param approvingTeacherID the approvingTeacherID to set
	 */
	public void setApprovingTeacherID(String approvingTeacherID) {
		this.approvingTeacherID = approvingTeacherID;
	}
	/**
	 * @return the calculatedGradeApprovalStateByTeacher
	 */
	public String getCalculatedGradeApprovalStateByTeacher() {
		return calculatedGradeApprovalStateByTeacher;
	}
	/**
	 * @param calculatedGradeApprovalStateByTeacher the calculatedGradeApprovalStateByTeacher to set
	 */
	public void setCalculatedGradeApprovalStateByTeacher(String calculatedGradeApprovalStateByTeacher) {
		this.calculatedGradeApprovalStateByTeacher = calculatedGradeApprovalStateByTeacher;
	}
	/**
	 * @return the cheatingFlag
	 */
	public String getCheatingFlag() {
		return cheatingFlag;
	}
	/**
	 * @param cheatingFlag the cheatingFlag to set
	 */
	public void setCheatingFlag(String cheatingFlag) {
		this.cheatingFlag = cheatingFlag;
	}
	
	/**
	 * 
	 * @return the exam date
	 */
	public Date getDate() {
		return date;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SolvedExams [examID=" + examID + ", sovingStudentID=" + sovingStudentID + ", examGrade=" + examGrade
				+ ", solveDurationTime=" + solveDurationTime + ", submittedOrInterruptedFlag="
				+ submittedOrInterruptedFlag + ", examDateTime=" + examDateTime + ", teacherNotes=" + teacherNotes
				+ ", gradeAlturationExplanation=" + gradeAlturationExplanation + ", approvingTeacherID="
				+ approvingTeacherID + ", calculatedGradeApprovalStateByTeacher="
				+ calculatedGradeApprovalStateByTeacher + ", cheatingFlag=" + cheatingFlag + ", examCourse="
				+ examCourse + ", approvingTeacherName=" + approvingTeacherName + ", action=" + action + "]";
	}
	
	
}

//package root.dao.app;
//
//import java.io.Serializable;
//import java.sql.Date;

//public class SolvedExams implements Serializable {
//
//	String user_id;
//	String exam_id;
//	int exam_grade;
//	int duration_timer;
//	String submitted_interrupted;
//	Date date;
//	String teacher_notes;
//	String grade_explanation;
//	String approving_teacher_id;
//	String grade_approval;
//	boolean cheating_flag;
//	
//	public SolvedExams(String user_id, String exam_id, int exam_grade, int duration_timer, String submitted_interrupted,
//			Date date, String teacher_notes, String grade_explanation, String approving_teacher_id,
//			String grade_approval, boolean cheating_flag) {
//		this.user_id = user_id;
//		this.exam_id = exam_id;
//		this.exam_grade = exam_grade;
//		this.duration_timer = duration_timer;
//		this.submitted_interrupted = submitted_interrupted;
//		this.date = date;
//		this.teacher_notes = teacher_notes;
//		this.grade_explanation = grade_explanation;
//		this.approving_teacher_id = approving_teacher_id;
//		this.grade_approval = grade_approval;
//		this.cheating_flag = cheating_flag;
//	}
//	
//	public String getUser_id() {
//		return user_id;
//	}
//	public void setUser_id(String user_id) {
//		this.user_id = user_id;
//	}
//	public String getExam_id() {
//		return exam_id;
//	}
//	public void setExam_id(String exam_id) {
//		this.exam_id = exam_id;
//	}
//	public int getExam_grade() {
//		return exam_grade;
//	}
//	public void setExam_grade(int exam_grade) {
//		this.exam_grade = exam_grade;
//	}
//	public int getDuration_timer() {
//		return duration_timer;
//	}
//	public void setDuration_timer(int duration_timer) {
//		this.duration_timer = duration_timer;
//	}
//	public String getSubmitted_interrupted() {
//		return submitted_interrupted;
//	}
//	public void setSubmitted_interrupted(String submitted_interrupted) {
//		this.submitted_interrupted = submitted_interrupted;
//	}
//	public Date getDate() {
//		return date;
//	}
//	public void setDate(Date date) {
//		this.date = date;
//	}
//	public String getTeacher_notes() {
//		return teacher_notes;
//	}
//	public void setTeacher_notes(String teacher_notes) {
//		this.teacher_notes = teacher_notes;
//	}
//	public String getGrade_explanation() {
//		return grade_explanation;
//	}
//	public void setGrade_explanation(String grade_explanation) {
//		this.grade_explanation = grade_explanation;
//	}
//	public String getApproving_teacher_id() {
//		return approving_teacher_id;
//	}
//	public void setApproving_teacher_id(String approving_teacher_id) {
//		this.approving_teacher_id = approving_teacher_id;
//	}
//	public String getGrade_approval() {
//		return grade_approval;
//	}
//	public void setGrade_approval(String grade_approval) {
//		this.grade_approval = grade_approval;
//	}
//	public boolean isCheating_flag() {
//		return cheating_flag;
//	}
//	public void setCheating_flag(boolean cheating_flag) {
//		this.cheating_flag = cheating_flag;
//	}
//	
//}

