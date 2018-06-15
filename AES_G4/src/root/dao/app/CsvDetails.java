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



	
}
