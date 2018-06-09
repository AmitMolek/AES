package root.dao.message;

import java.util.ArrayList;

import root.dao.app.QuestionInExam;

/**
 * Class for question in exam message
 * @author omer1
 *
 */
public class QuestionInExamMessage extends AbstractMessage {

	private String examId;
	private ArrayList<QuestionInExam> questionInExam;
	
	/**
	 * Return question in exam type
	 */
	@Override
	public String getType() {
		return "QuestionInExam";
	}

	/**
	 * Constructor for send message from client
	 * @param examId the exam id
	 * @param payload the question list
	 */
	public QuestionInExamMessage(ArrayList<QuestionInExam> payload) {
		super("put-questioninexam");
		this.questionInExam = payload;
	}

	/**
	 * 
	 * @return the exam id
	 */
	public String getExamId() {
		return examId;
	}

	/**
	 * Set the exam id
	 * @param examId the new exam id value
	 */
	public void setExamId(String examId) {
		this.examId = examId;
	}

	/**
	 * 
	 * @return list of questions in exam
	 */
	public ArrayList<QuestionInExam> getQuestionInExam() {
		return questionInExam;
	}

	/**
	 * Sets new list of question in exam
	 * @param questionInExam the new list 
	 */
	public void setQuestionInExam(ArrayList<QuestionInExam> questionInExam) {
		this.questionInExam = questionInExam;
	}

}
