package root.dao.app;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Statistic implements Serializable {
	/**
	 * @author Alon Ben-yosef
	 * Repersents a statistics item as described in the AES DB, used to show histograms
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The exam's ID
	 */
	private String exam_ID;
	/**
	 * The exam's date
	 */
	private String date;
	/**
	 * The exam's date
	 */
	private Timestamp dateTime;
	/**
	 * The exam's actual duration (after all alteration requests)
	 */
	private String real_time_duration;
	/**
	 * Counts how many students submitted the exam within time
	 */
	private int submitted_students_counter;
	/**
	 * Counts how many students were interrupted by the cloak
	 */
	private int interrupted_students_counter;
	/**
	 * Counts how many students started the exam
	 */
	private int students_started_counter;
	/**
	 * The exam's avarage grade
	 */
	private double exams_avg;
	/**
	 * The exam's median grade
	 */
	private int exams_median;
	/**
	 * How many students scored between 0-10
	 */
	private int grade_derivative_0_10;
	/**
	 * How many students scored between 11-20
	 */
	private int grade_derivative_11_20;
	/**
	 * How many students scored between 21-30
	 */
	private int grade_derivative_21_30;
	/**
	 * How many students scored between 31-40
	 */
	private int grade_derivative_31_40;
	/**
	 * How many students scored between 41-50
	 */
	private int grade_derivative_41_50;
	/**
	 * How many students scored between 51-60
	 */
	private int grade_derivative_51_60;
	/**
	 * How many students scored between 61-70
	 */
	private int grade_derivative_61_70;
	/**
	 * How many students scored between 71-80
	 */
	private int grade_derivative_71_80;
	/**
	 * How many students scored between 81-90
	 */
	private int grade_derivative_81_90;
	/**
	 * How many students scored between 91-100
	 */
	private int grade_derivative_91_100;
	
	/**
	 * Used to pull a statistic data from the DB
	 * @param exam_ID
	 * @param date
	 * @param real_time_duration
	 * @param submitted_students_counter
	 * @param interrupted_students_counter
	 * @param students_started_counter
	 * @param exams_avg
	 * @param exams_median
	 * @param grade_derivative_0_10
	 * @param grade_derivative_11_20
	 * @param grade_derivative_21_30
	 * @param grade_derivative_31_40
	 * @param grade_derivative_41_50
	 * @param grade_derivative_51_60
	 * @param grade_derivative_61_70
	 * @param grade_derivative_71_80
	 * @param grade_derivative_81_90
	 * @param grade_derivative_91_100
	 */
	public Statistic(String exam_ID, String date, String real_time_duration, int submitted_students_counter,
			int interrupted_students_counter, int students_started_counter, double exams_avg, int exams_median,
			int grade_derivative_0_10, int grade_derivative_11_20, int grade_derivative_21_30,
			int grade_derivative_31_40, int grade_derivative_41_50, int grade_derivative_51_60,
			int grade_derivative_61_70, int grade_derivative_71_80, int grade_derivative_81_90,
			int grade_derivative_91_100) {
		this.exam_ID = exam_ID;
		this.date = date;
		this.real_time_duration = real_time_duration;
		this.submitted_students_counter = submitted_students_counter;
		this.interrupted_students_counter = interrupted_students_counter;
		this.students_started_counter = students_started_counter;
		this.exams_avg = exams_avg;
		this.exams_median = exams_median;
		this.grade_derivative_0_10 = grade_derivative_0_10;
		this.grade_derivative_11_20 = grade_derivative_11_20;
		this.grade_derivative_21_30 = grade_derivative_21_30;
		this.grade_derivative_31_40 = grade_derivative_31_40;
		this.grade_derivative_41_50 = grade_derivative_41_50;
		this.grade_derivative_51_60 = grade_derivative_51_60;
		this.grade_derivative_61_70 = grade_derivative_61_70;
		this.grade_derivative_71_80 = grade_derivative_71_80;
		this.grade_derivative_81_90 = grade_derivative_81_90;
		this.grade_derivative_91_100 = grade_derivative_91_100;
	}
	public Statistic() {
		this.exam_ID ="";
		this.date = "";
		this.real_time_duration = "";
		this.submitted_students_counter = 0;
		this.interrupted_students_counter = 0;
		this.students_started_counter = 0;
		this.exams_avg = 0;
		this.exams_median = 0;
		this.grade_derivative_0_10 = 0;
		this.grade_derivative_11_20 = 0;
		this.grade_derivative_21_30 = 0;
		this.grade_derivative_31_40 = 0;
		this.grade_derivative_41_50 = 0;
		this.grade_derivative_51_60 = 0;
		this.grade_derivative_61_70 = 0;
		this.grade_derivative_71_80 = 0;
		this.grade_derivative_81_90 = 0;
		this.grade_derivative_91_100 = 0;
	}
	
	/**
	 * Generate statistics by a list of grades, will calculate median and average independently
	 * @param grades a list of integer grades
	 */
	public Statistic(List<Integer> grades) {
		super();
		//To division by 0
		if(grades==null||grades.isEmpty()) return;
		
		int counter = 0;
		//Not proud of this solution
		for(Integer grade:grades) {
			counter+=grade.intValue();
			if(grade.intValue()>=0 && grade.intValue()<=10) this.grade_derivative_0_10++;
			if(grade.intValue()>=11 && grade.intValue()<=20) this.grade_derivative_11_20++;
			if(grade.intValue()>=21 && grade.intValue()<=30) this.grade_derivative_21_30++;
			if(grade.intValue()>=31 && grade.intValue()<=40) this.grade_derivative_31_40++;
			if(grade.intValue()>=41 && grade.intValue()<=50) this.grade_derivative_41_50++;
			if(grade.intValue()>=51 && grade.intValue()<=60) this.grade_derivative_51_60++;
			if(grade.intValue()>=61 && grade.intValue()<=70) this.grade_derivative_61_70++;
			if(grade.intValue()>=71 && grade.intValue()<=80) this.grade_derivative_71_80++;
			if(grade.intValue()>=81 && grade.intValue()<=90) this.grade_derivative_81_90++;
			if(grade.intValue()>=91 && grade.intValue()<=100) this.grade_derivative_91_100++;
		}
		Collections.sort(grades);
		exams_median=grades.get(grades.size()/2);
		exams_avg=(double)counter/grades.size();
	}
	public String getExam_ID() {
		return exam_ID;
	}
	public void setExam_ID(String exam_ID) {
		this.exam_ID = exam_ID;
	}
	public String getDate() {
		return date;
	}
	public Timestamp getDateTime() {
		return dateTime;
	}
	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
	}
	public void setDate(String string) {
		this.date = string;
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
	public void setExams_avg(double d) {
		this.exams_avg = d;
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