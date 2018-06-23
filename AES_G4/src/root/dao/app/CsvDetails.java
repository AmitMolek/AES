package root.dao.app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import root.client.controllers.QuestionInExamObject;

/**
 * A class that is responsible for keeping data about exam that will be recorded
 * in CSV file
 * 
 * @author Omer Haimovich
 *
 */
public class CsvDetails implements Serializable {

	// Instance variables **********************************************

	/**
	 * The exam that will be recorded in CSV file
	 */
	private Exam examId;
	/**
	 * The teacher that wrote this exam
	 */
	private User userId;
	/**
	 * 
	 * A map(String,Integer) that contains all the exam questions and the answers
	 * that student selected
	 */
	private Map<String, Integer> questionInExam;
	/**
	 * The exam after the student solved it
	 */
	private SolvedExams solvedExam;
	/**
	 * The id of the solved exam
	 */
	private String examSolverID;

	// CONSTRUCTORS *****************************************************

	/**
	 * Constructs the CsvDetails
	 * 
	 * @param solvedExam
	 *            The exam after the student solved it
	 * @param examSolverID
	 *            The id of the solved exam
	 */
	public CsvDetails(SolvedExams solvedExam, String examSolverID) {
		super();
		this.solvedExam = solvedExam;
		this.examSolverID = examSolverID;
	}

	/**
	 * Constructs the CsvDetails
	 * 
	 * @param examId
	 *            The exam that will be recorded in CSV file
	 * @param userId
	 *            The teacher that wrote this exam
	 * @param questionInExam
	 *            A map(String,Integer) that contains all the exam questions and the
	 *            answers that student selected
	 */
	public CsvDetails(Exam examId, User userId, Map<String, Integer> questionInExam) {
		super();
		this.examId = examId;
		this.userId = userId;
		this.questionInExam = questionInExam;
	}

	// CLASS METHODS *************************************************
	
	/**
	 * A method that returns the exam
	 * 
	 * @return the exam
	 */
	public Exam getExamId() {
		return examId;
	}

	/**
	 * A method that returns the teacher that wrote this exam
	 * 
	 * @return the teacher that wrote this exam
	 */
	public User getUserId() {
		return userId;
	}

	/**
	 * A method that returns a map(String,Integer) that contains all the exam
	 * questions and the answers
	 * 
	 * @return the map(String,Integer) that contains all the exam questions and the
	 *         answers
	 */
	public Map<String, Integer> getQuestionInExam() {
		return questionInExam;
	}

	/**
	 * A method that returns the exam after the student solved it
	 * 
	 * @return the solvedExam
	 */
	public SolvedExams getSolvedExam() {
		return solvedExam;
	}

	/**
	 * A method that set solved exam
	 * 
	 * @param solvedExam
	 *            the solvedExam
	 */
	public void setSolvedExam(SolvedExams solvedExam) {
		this.solvedExam = solvedExam;
	}

	/**
	 * A method that returns the id of the solved exam
	 * 
	 * @return the id of the solved exam
	 */
	public String getExamSolverID() {
		return examSolverID;
	}

	/**
	 * A method that set the id of the solved exam
	 * 
	 * @param examSolverID
	 *            the id of the solve exam
	 */
	public void setExamSolverID(String examSolverID) {
		this.examSolverID = examSolverID;
	}

}
