package root.dao.app;

import java.io.Serializable;
/**
 * @author Alon Ben-yosef
 * Question in exam data represents a single question in exam row as exists in the database
 */
public class QuestionInExamData implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * The question's ID
	 */
	private String questionId;
	/**
	 * The exam ID
	 */
	private String examId;
	/**
	 * The question's weight in points
	 */
	private int grade;
	/**
	 * A note for the student
	 */
	private String studentText;
	/**
	 * A note for the teacher
	 */
	private String teacherText;
	
	/**
	 * Used to pull question in exam data from the DB
	 * @param questionId
	 * @param examId
	 * @param grade
	 * @param studentText
	 * @param teacherText
	 */
	public QuestionInExamData(String questionId, String examId, int grade, String studentText, String teacherText) {
		super();
		this.questionId = questionId;
		this.examId = examId;
		this.grade = grade;
		this.studentText = studentText;
		this.teacherText = teacherText;
	}
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public String getStudentText() {
		return studentText;
	}
	public void setStudentText(String studentText) {
		this.studentText = studentText;
	}
	public String getTeacherText() {
		return teacherText;
	}
	public void setTeacherText(String teacherText) {
		this.teacherText = teacherText;
	}

	
}
