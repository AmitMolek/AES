package root.dao.message;

import java.util.ArrayList;

import root.dao.app.QuestionInExam;

/**
 * A class that transmits messages between the server and the client with regard
 * to question in exam information
 * 
 * @author Omer Haimovich
 *
 */
public class QuestionInExamMessage extends AbstractMessage {

	// Instance variables **********************************************

	/**
	 * The id of the exam
	 */
	private String examId;
	/**
	 * List of all question belong to the exam
	 */
	private ArrayList<QuestionInExam> questionInExam;

	// CONSTRUCTORS *****************************************************

	/**
	 * Constructs the CourseMessage
	 * 
	 * @param examId
	 *            the id of the exam
	 * @param payload
	 *            list of all question belong to the exam
	 */
	public QuestionInExamMessage(ArrayList<QuestionInExam> payload) {
		super("put-questioninexam");
		this.questionInExam = payload;
	}

	/**
	 * Returns the type of message
	 */
	@Override
	public String getType() {
		return "QuestionInExam";
	}

	/**
	 * A method that returns the id of the exam
	 * 
	 * @return the id of the exam
	 */
	public String getExamId() {
		return examId;
	}

	/**
	 * A method that set the id of the exam
	 * 
	 * @param examId
	 *            the id of the exam
	 */
	public void setExamId(String examId) {
		this.examId = examId;
	}

	/**
	 * A method that returns the list of all question belong to the exam
	 * 
	 * @return the list of all question belong to the exam
	 */
	public ArrayList<QuestionInExam> getQuestionInExam() {
		return questionInExam;
	}

	/**
	 * A method that set the list of all question belong to the exam
	 * 
	 * @param questionInExam
	 *            the list of all question belong to the exam
	 */
	public void setQuestionInExam(ArrayList<QuestionInExam> questionInExam) {
		this.questionInExam = questionInExam;
	}

}
