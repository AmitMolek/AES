package root.dao.app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import root.client.controllers.QuestionInExamObject;

/**
 * Class for create csv
 * @author Omer Haimovich
 *
 */
public class CsvDetails implements Serializable{
	private Exam examId;
	private User userId;
	private Map<String,Integer> questionInExam;
	// Gal addition:
	private SolvedExams solvedExam;
	private String examSolverID;
	
	/**
	 * constructor for getting a CSV from server to client
	 */

	public CsvDetails(SolvedExams solvedExam, String examSolverID) {
		super();
		this.solvedExam = solvedExam;
		this.examSolverID = examSolverID;
	}
	/**
	 * Constructor for csv
	 * @param examId the exam
	 * @param userId the user
	 * @param questionsInExamObject the exam question
	 */
	public CsvDetails(Exam examId, User userId, Map<String,Integer> questionInExam) {
		super();
		this.examId = examId;
		this.userId = userId;
		this.questionInExam = questionInExam;
	}

	/**
	 * 
	 * @return the exam 
	 */
	public Exam getExamId() {
		return examId;
	}
	
	/**
	 * 
	 * @return the user
	 */
	public User getUserId() {
		return userId;
	}
	
	public Map<String, Integer> getQuestionInExam() {
		return questionInExam;
	}
	/**
	 * @return the solvedExam
	 */
	public SolvedExams getSolvedExam() {
		return solvedExam;
	}
	/**
	 * @param solvedExam the solvedExam to set
	 */
	public void setSolvedExam(SolvedExams solvedExam) {
		this.solvedExam = solvedExam;
	}
	/**
	 * @return the examSolverID
	 */
	public String getExamSolverID() {
		return examSolverID;
	}
	/**
	 * @param examSolverID the examSolverID to set
	 */
	public void setExamSolverID(String examSolverID) {
		this.examSolverID = examSolverID;
	}

}
