package root.dao.app;
import java.io.Serializable;
import java.sql.Date;

public class Statistic implements Serializable {
	private String exam_ID;
	private Date date;
	private String real_time_duration;
	private int submitted_students_counter;
	private int interrupted_students_counter;
	private int students_started_counter;
	private double exams_avg;
	private double exams_median;
	private int grade_derivative_0_10;
	private int grade_derivative_11_20;
	private int grade_derivative_21_30;
	private int grade_derivative_31_40;
	private int grade_derivative_41_50;
	private int grade_derivative_51_60;
	private int grade_derivative_61_70;
	private int grade_derivative_71_80;
	private int grade_derivative_81_90;
	private int grade_derivative_91_100;
	public String getExam_ID() {
		return exam_ID;
	}
	public void setExam_ID(String exam_ID) {
		this.exam_ID = exam_ID;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getReal_time_duration() {
		return real_time_duration;
	}
	public void setReal_time_duration(String real_time_duration) {
		this.real_time_duration = real_time_duration;
	}
	public int getSubmitted_students_counter() {
		return submitted_students_counter;
	}
	public void setSubmitted_students_counter(int submitted_students_counter) {
		this.submitted_students_counter = submitted_students_counter;
	}
	public int getInterrupted_students_counter() {
		return interrupted_students_counter;
	}
	public void setInterrupted_students_counter(int interrupted_students_counter) {
		this.interrupted_students_counter = interrupted_students_counter;
	}
	public int getStudents_started_counter() {
		return students_started_counter;
	}
	public void setStudents_started_counter(int students_started_counter) {
		this.students_started_counter = students_started_counter;
	}
	public double getExams_avg() {
		return exams_avg;
	}
	public void setExams_avg(int exams_avg) {
		this.exams_avg = exams_avg;
	}
	public double getExams_median() {
		return exams_median;
	}
	public void setExams_median(int exams_median) {
		this.exams_median = exams_median;
	}
	public int getGrade_derivative_0_10() {
		return grade_derivative_0_10;
	}
	public void setGrade_derivative_0_10(int grade_derivative_0_10) {
		this.grade_derivative_0_10 = grade_derivative_0_10;
	}
	public int getGrade_derivative_11_20() {
		return grade_derivative_11_20;
	}
	public void setGrade_derivative_11_20(int grade_derivative_11_20) {
		this.grade_derivative_11_20 = grade_derivative_11_20;
	}
	public int getGrade_derivative_21_30() {
		return grade_derivative_21_30;
	}
	public void setGrade_derivative_21_30(int grade_derivative_21_30) {
		this.grade_derivative_21_30 = grade_derivative_21_30;
	}
	public int getGrade_derivative_31_40() {
		return grade_derivative_31_40;
	}
	public void setGrade_derivative_31_40(int grade_derivative_31_40) {
		this.grade_derivative_31_40 = grade_derivative_31_40;
	}
	public int getGrade_derivative_41_50() {
		return grade_derivative_41_50;
	}
	public void setGrade_derivative_41_50(int grade_derivative_41_50) {
		this.grade_derivative_41_50 = grade_derivative_41_50;
	}
	public int getGrade_derivative_51_60() {
		return grade_derivative_51_60;
	}
	public void setGrade_derivative_51_60(int grade_derivative_51_60) {
		this.grade_derivative_51_60 = grade_derivative_51_60;
	}
	public int getGrade_derivative_61_70() {
		return grade_derivative_61_70;
	}
	public void setGrade_derivative_61_70(int grade_derivative_61_70) {
		this.grade_derivative_61_70 = grade_derivative_61_70;
	}
	public int getGrade_derivative_71_80() {
		return grade_derivative_71_80;
	}
	public void setGrade_derivative_71_80(int grade_derivative_71_80) {
		this.grade_derivative_71_80 = grade_derivative_71_80;
	}
	public int getGrade_derivative_81_90() {
		return grade_derivative_81_90;
	}
	public void setGrade_derivative_81_90(int grade_derivative_81_90) {
		this.grade_derivative_81_90 = grade_derivative_81_90;
	}
	public int getGrade_derivative_91_100() {
		return grade_derivative_91_100;
	}
	public void setGrade_derivative_91_100(int grade_derivative_91_100) {
		this.grade_derivative_91_100 = grade_derivative_91_100;
	}
}
