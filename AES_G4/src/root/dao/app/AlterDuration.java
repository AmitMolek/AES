package root.dao.app;

import java.io.Serializable;

/**
 * A class that is responsible for keeping data about change the exam duration
 * time
 * 
 * @author Naor Saadia
 *
 */
public class AlterDuration implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String userID;
	private String examID;
	private String date;
	private String teacherExp;
	private String principalAns;
	private int original_duration;
	private int after_change_duration;
	
	public AlterDuration(String userID, String examID, String date, String teacherExp, String principalAns,
			int original_duration, int after_change_duration) {
		super();
		this.userID = userID;
		this.examID = examID;
		this.date = date;
		this.teacherExp = teacherExp;
		this.principalAns = principalAns;
		this.original_duration = original_duration;
		this.after_change_duration = after_change_duration;
	}
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
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
	public String getTeacherExp() {
		return teacherExp;
	}
	public void setTeacherExp(String teacherExp) {
		this.teacherExp = teacherExp;
	}
	public String getPrincipalAns() {
		return principalAns;
	}
	public void setPrincipalAns(String principalAns) {
		this.principalAns = principalAns;
	}
	public int getOriginal_duration() {
		return original_duration;
	}
	public void setOriginal_duration(int original_duration) {
		this.original_duration = original_duration;
	}
	public int getAfter_change_duration() {
		return after_change_duration;
	}
	public void setAfter_change_duration(int after_change_duration) {
		this.after_change_duration = after_change_duration;
	}
}
