package root.dao.app;

import java.io.Serializable;
import java.util.ArrayList;

import root.client.controllers.QuestionInExamObject;


/**
 * Class for create csv
 * @author Omer Haimovich
 *
 */
public class CsvDetails implements Serializable{
	private Exam examId;
	private User userId;
	private ArrayList<QuestionInExamObject> questionsInExamObject;
	
	/**
	 * Constructor for csv
	 * @param examId the exam
	 * @param userId the user
	 * @param questionsInExamObject the exam question
	 */
	public CsvDetails(Exam examId, User userId, ArrayList<QuestionInExamObject> questionsInExamObject) {
		super();
		this.examId = examId;
		this.userId = userId;
		this.questionsInExamObject = questionsInExamObject;
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
	
	/**
	 * 
	 * @return the exam question
	 */
	public ArrayList<QuestionInExamObject> getQuestionsInExamObject() {
		return questionsInExamObject;
	}



	
}
