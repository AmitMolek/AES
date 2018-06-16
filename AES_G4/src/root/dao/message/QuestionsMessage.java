package root.dao.message;

import java.io.Serializable;
import java.util.ArrayList;

import root.dao.app.Question;
import root.dao.app.Subject;

public class QuestionsMessage extends AbstractMessage implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Question> questions;
	private Subject thisQuestionsSubject;
	private ArrayList<String> questionID;
	
	public QuestionsMessage(Subject payload) {
		super("get-questions");
		this.thisQuestionsSubject=payload;
	}
	
	/**
	 * use this constructor, if client sends server questions ID's and want to get the full information for each question ID. 
	 * @param questionID
	 */
	public QuestionsMessage(String message, ArrayList<String> questionID) {
		super(message);
		this.questionID = questionID;
	}
	public QuestionsMessage(ArrayList<Question> payload) {
		// TODO Auto-generated constructor stub
		super("Questions");
		this.questions = payload;
	}

	public QuestionsMessage(QuestionsMessage payload) {
		// TODO Auto-generated constructor stub
		super("Questions");
		this.questions = payload.getQuestions();
		this.thisQuestionsSubject = payload.getThisQuestionsSubject();
	}
	
	/**
	 * Constructor that send message to client
	 * @param payload
	 */
	public QuestionsMessage(Question payload) {
		// TODO Auto-generated constructor stub
		super("put-questions");
		this.questions = new ArrayList<Question>();
		this.questions.add(payload);
		
	}

	public QuestionsMessage(String string, Question payload) {
		// TODO Auto-generated constructor stub
		super(string);
		this.questions = new ArrayList<Question>();
		this.questions.add(payload);
	}

	@Override
	public String getType() {
		return "Questions";
	}

	public ArrayList<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}

	public Subject getThisQuestionsSubject() {
		return thisQuestionsSubject;
	}

	public void setThisQuestionsSubject(Subject thisQuestionsSubject) {
		this.thisQuestionsSubject = thisQuestionsSubject;
	}

	/**
	 * @return the questionID
	 */
	public ArrayList<String> getQuestionID() {
		return questionID;
	}

	/**
	 * @param questionID the questionID to set
	 */
	public void setQuestionID(ArrayList<String> questionID) {
		this.questionID = questionID;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "QuestionsMessage [questions=" + questions + ", thisQuestionsSubject=" + thisQuestionsSubject + "]";
	}

}
