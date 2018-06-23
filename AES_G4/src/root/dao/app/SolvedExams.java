package root.dao.app;

import java.io.Serializable;

import java.sql.Date;
import java.sql.Timestamp;

import javafx.scene.image.ImageView;

/**
 * 
 * @author gal this dao class contain solvedExam information
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
	private Date date; // this is temporary field
	private String examCourse;
	private String approvingTeacherName;
	/* Vars for table use inly */
	private String action;
	private String strGrade;
	private String courseName;
	private Boolean approved = false;
	private Boolean gradeChanged = false;

	/**
	 * Constructor for solved exam
	 * 
	 * @param userId
	 *            the student id
	 * @param examId
	 *            the exam id
	 * @param examGrade
	 *            the grade of the exam
	 * @param durationTime
	 *            the time that took the student to solve the exam
	 * @param submittedOrNot
	 *            if student submitted or not
	 * @param newDate
	 *            the new date
	 */
	public SolvedExams(String userId, String examId, int examGrade, int durationTime, String submittedOrNot,
			java.util.Date newDate) {
		this.sovingStudentID = userId;
		this.examID = examId;
		this.examGrade = examGrade;
		this.solveDurationTime = durationTime;
		this.submittedOrInterruptedFlag = submittedOrNot;
		this.date = new java.sql.Date(newDate.getTime());
		this.strGrade = String.valueOf(this.examGrade);
	}

	/**
	 * the constructor abelow is a constructor used by gal.
	 * 
	 * @param examID
	 *            exam id
	 * @param sovingStudentID
	 *            studentid
	 * @param examGrade
	 *            exam grade
	 * @param solveDurationTime
	 *            the solvedexam
	 * @param submittedOrInterruptedFlag
	 *            the submittedflag
	 * @param examDateTime
	 *            the exam date
	 * @param teacherNotes
	 *            the teachernotes
	 * @param gradeAlturationExplanation
	 *            the grade alturation explanation
	 * @param approvingTeacherID
	 *            the approving id
	 * @param calculatedGradeApprovalStateByTeacher
	 *            the calculation grade approval
	 * @param cheatingFlag
	 *            the cheating flag
	 */
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
		this.strGrade = String.valueOf(this.examGrade);
	}

	public String getStrGrade() {
		return strGrade;
	}

	public void setStrGrade(String strGrade) {
		this.strGrade = strGrade;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * @return the approvingTeacherName
	 */
	public String getApprovingTeacherName() {
		return approvingTeacherName;
	}

	/**
	 * @param approvingTeacherName
	 *            the approvingTeacherName to set
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
	 * @param examCourse
	 *            the examCourse to set
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
	 * @param examID
	 *            the examID to set
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
	 * @param sovingStudentID
	 *            the sovingStudentID to set
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
	 * @param examGrade
	 *            the examGrade to set
	 */
	public void setExamGrade(int examGrade) {
		this.strGrade = String.valueOf(examGrade);
		this.examGrade = examGrade;
	}

	/**
	 * @return the solveDurationTime
	 */
	public int getSolveDurationTime() {
		return solveDurationTime;
	}

	/**
	 * @param solveDurationTime
	 *            the solveDurationTime to set
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
	 * @param submittedOrInterruptedFlag
	 *            the submittedOrInterruptedFlag to set
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
	 * @param timeStamp
	 *            the examDateTime to set
	 */
	public void setExamDateTime(Timestamp timeStamp) {
		// Date sqlDate=examDate;//new java.sql.Date(date.getTime());
		// Timestamp sqlTime=timeStamp;// new java.sql.Timestamp(date.getTime());
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
	 * @param teacherNotes
	 *            the teacherNotes to set
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
	 * @param gradeAlturationExplanation
	 *            the gradeAlturationExplanation to set
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
	 * @param approvingTeacherID
	 *            the approvingTeacherID to set
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
	 * @param calculatedGradeApprovalStateByTeacher
	 *            the calculatedGradeApprovalStateByTeacher to set
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
	 * @param cheatingFlag
	 *            the cheatingFlag to set
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

	/*
	 * Returns the approve state of the solved exam Used for the table view for the
	 * teacher view exams
	 */
	public Boolean isApproved() {
		return approved;
	}

	/**
	 * Sets the state of the solved exam (if approved or not) Used for the table
	 * view for the teacher view exams
	 * 
	 * @param approved
	 *            the state of the approve state
	 */
	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	/**
	 * Returns if the grade of this exam changed
	 * 
	 * @return true if the grade changed
	 */
	public Boolean isGradeChanged() {
		return gradeChanged;
	}

	/**
	 * Sets the the flag of grade change (if the grade changed)
	 * 
	 * @param gradeChanged
	 *            the state of the flag you want
	 */
	public void setGradeChanged(Boolean gradeChanged) {
		this.gradeChanged = gradeChanged;
	}

	/*
	 * (non-Javadoc)
	 * 
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