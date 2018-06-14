package root.dao.app;

import java.io.Serializable;

/**
 * Class for question in exam include grade and free text for each question
 * 
 * @author Omer Haimovich
 *
 */
public class QuestionInExam implements Serializable {
	private Question question;
	private int questionGrade;
	private String freeTextForTeacher;
	private String freeTextForStudent;

	/**
	 * Constructor for question in exam
	 * 
	 * @param question
	 *            the question itself
	 * @param questionGrade
	 *            the question scoring points
	 * @param freeTextForTeacher
	 *            the teacher free text(only teacher can see that)
	 * @param freeTextForStudent
	 *            the free text for students
	 */
	public QuestionInExam(Question question, int questionGrade, String freeTextForTeacher, String freeTextForStudent) {
		super();
		this.question = question;
		this.questionGrade = questionGrade;
		this.freeTextForTeacher = freeTextForTeacher;
		this.freeTextForStudent = freeTextForStudent;
	}

	public QuestionInExam() {
	}

	/**
	 * 
	 * @return the question itself
	 */
	public Question getQuestion() {
		return question;
	}

	/**
	 * Set new value for question in exam
	 * 
	 * @param question
	 *            the new question value
	 */
	public void setQuestion(Question question) {
		this.question = question;
	}

	/**
	 * 
	 * @return the scoring for a question
	 */
	public int getQuestionGrade() {
		return questionGrade;
	}

	/**
	 * Set new question scoring
	 * 
	 * @param questionGrade
	 *            the new question scoring
	 */
	public void setQuestionGrade(int questionGrade) {
		this.questionGrade = questionGrade;
	}

	/**
	 * 
	 * @return the question free text (for the teacher only)
	 */
	public String getFreeTextForTeacher() {
		return freeTextForTeacher;
	}

	/**
	 * Set new free text for teacher
	 * 
	 * @param freeTextForTeacher
	 *            the new free text for teacher
	 */
	public void setFreeTextForTeacher(String freeTextForTeacher) {
		this.freeTextForTeacher = freeTextForTeacher;
	}

	/**
	 * 
	 * @return the question free text for student
	 */
	public String getFreeTextForStudent() {
		return freeTextForStudent;
	}

	/**
	 * Set new free text for teacher
	 * 
	 * @param freeTextForStudent
	 *            the new free text for student
	 */
	public void setFreeTextForStudent(String freeTextForStudent) {
		this.freeTextForStudent = freeTextForStudent;
	}

}
