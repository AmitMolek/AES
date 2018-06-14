package root.dao.app;

import java.io.Serializable;
import java.sql.Date;

public class SolvedExams implements Serializable {

	String user_id;
	String exam_id;
	int exam_grade;
	int duration_timer;
	String submitted_interrupted;
	Date date;
	String teacher_notes;
	String grade_explanation;
	String approving_teacher_id;
	String grade_approval;
	boolean cheating_flag;
	
	public SolvedExams(String user_id, String exam_id, int exam_grade, int duration_timer, String submitted_interrupted,
			Date date, String teacher_notes, String grade_explanation, String approving_teacher_id,
			String grade_approval, boolean cheating_flag) {
		this.user_id = user_id;
		this.exam_id = exam_id;
		this.exam_grade = exam_grade;
		this.duration_timer = duration_timer;
		this.submitted_interrupted = submitted_interrupted;
		this.date = date;
		this.teacher_notes = teacher_notes;
		this.grade_explanation = grade_explanation;
		this.approving_teacher_id = approving_teacher_id;
		this.grade_approval = grade_approval;
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
	public int getExam_grade() {
		return exam_grade;
	}
	public void setExam_grade(int exam_grade) {
		this.exam_grade = exam_grade;
	}
	public int getDuration_timer() {
		return duration_timer;
	}
	public void setDuration_timer(int duration_timer) {
		this.duration_timer = duration_timer;
	}
	public String getSubmitted_interrupted() {
		return submitted_interrupted;
	}
	public void setSubmitted_interrupted(String submitted_interrupted) {
		this.submitted_interrupted = submitted_interrupted;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getTeacher_notes() {
		return teacher_notes;
	}
	public void setTeacher_notes(String teacher_notes) {
		this.teacher_notes = teacher_notes;
	}
	public String getGrade_explanation() {
		return grade_explanation;
	}
	public void setGrade_explanation(String grade_explanation) {
		this.grade_explanation = grade_explanation;
	}
	public String getApproving_teacher_id() {
		return approving_teacher_id;
	}
	public void setApproving_teacher_id(String approving_teacher_id) {
		this.approving_teacher_id = approving_teacher_id;
	}
	public String getGrade_approval() {
		return grade_approval;
	}
	public void setGrade_approval(String grade_approval) {
		this.grade_approval = grade_approval;
	}
	public boolean isCheating_flag() {
		return cheating_flag;
	}
	public void setCheating_flag(boolean cheating_flag) {
		this.cheating_flag = cheating_flag;
	}
	
}
