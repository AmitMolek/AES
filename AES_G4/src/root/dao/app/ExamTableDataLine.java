package root.dao.app;

import java.sql.Date;

public class ExamTableDataLine {
	private String examID;
	private String date;
	private String courseName;
	private String subjectName;
	
	public ExamTableDataLine(String examID, String date, String courseName, String subjectName) {
		this.examID=examID;
		this.date=date;
		this.courseName=courseName;
		this.subjectName=subjectName;
	}
	public String getExamID() {
		return examID;
	}
	public void setExamID(String examID) {
		this.examID = examID;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
}
