package root.dao.app;

import java.io.Serializable;
import java.sql.Date;
/**
 *
 * @author Alon Ben-yosef
 * A class that represents a row in the teacher's exam table
 */
public class ExamTableDataLine implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * The exam's ID
	 */
	private String examID;
	/**
	 * The exam's date
	 */
	private String date;
	/**
	 * The name of the exam'ss course
	 */
	private String courseName;
	/**
	 * The name the exam's subject
	 */
	private String subjectName;
	
	/**
	 * A constructor that reflects this data type in the database
	 * @param examID
	 * @param date
	 * @param courseName
	 * @param subjectName
	 */
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
