package root.dao.app;

import java.io.Serializable;

/**
 * A class that is responsible for keeping data about question in exam
 * 
 * @author Omer Haimovich
 *
 */
public class QuestionInExam implements Serializable {


	// Instance variables **********************************************



	private static final long serialVersionUID = 1L;
	

	/**
	 * 
	 * The question that we want to add to the exam
	 */
	private Question question;
	/**
	 * The score of the question
	 */
	private int questionGrade;
	/**
	 * 
	 * Free text of the question intended only for teachers
	 */
	private String freeTextForTeacher;
	/**
	 * 
	 * Free text of the question intended for students
	 */
	private String freeTextForStudent;

	// CONSTRUCTORS *****************************************************

	/**
	 * Constructs the Exam
	 * 
	 * @param question
	 *            the question that we want to add to the exam
	 * @param questionGrade
	 *            the score of the question
	 * @param freeTextForTeacher
	 *            the free text of the question intended only for teachers
	 * @param freeTextForStudent
	 *            the free text of the question intended for students
	 */
	public QuestionInExam(Question question, int questionGrade, String freeTextForTeacher, String freeTextForStudent) {
		super();
		this.question = question;
		this.questionGrade = questionGrade;
		this.freeTextForTeacher = freeTextForTeacher;
		this.freeTextForStudent = freeTextForStudent;
	}

	/**
	 * Constructs the Exam
	 */
	public QuestionInExam() {
	}

	// CLASS METHODS *************************************************

	/**
	 * A method that returns the question
	 * 
	 * @return the question
	 */
	public Question getQuestion() {
		return question;
	}

	/**
	 * A method that set the question
	 * 
	 * @param question
	 *            the question
	 */
	public void setQuestion(Question question) {
		this.question = question;
	}

	/**
	 * A method that returns the score of the question
	 * 
	 * @return the score of the question
	 */
	public int getQuestionGrade() {
		return questionGrade;
	}

	/**
	 * A method that set the score of the question
	 * 
	 * @param questionGrade
	 *            the score of the question
	 */
	public void setQuestionGrade(int questionGrade) {
		this.questionGrade = questionGrade;
	}

	/**
	 * A method that returns the free text of the question intended only for
	 * teachers
	 * 
	 * @return the free text of the question intended only for teachers
	 */
	public String getFreeTextForTeacher() {
		return freeTextForTeacher;
	}

	/**
	 * A method that set the free text of the question intended only for teachers
	 * 
	 * @param freeTextForTeacher
	 *            the free text of the question intended only for teachers
	 */
	public void setFreeTextForTeacher(String freeTextForTeacher) {
		this.freeTextForTeacher = freeTextForTeacher;
	}

	/**
	 * A method that returns the free text of the question intended for students
	 * 
	 * @return the free text of the question intended for students
	 */
	public String getFreeTextForStudent() {
		return freeTextForStudent;
	}

	/**
	 * A method that set the free text of the question intended for students
	 * 
	 * @param freeTextForStudent
	 *           the free text of the question intended for students
	 */
	public void setFreeTextForStudent(String freeTextForStudent) {
		this.freeTextForStudent = freeTextForStudent;
	}

}
