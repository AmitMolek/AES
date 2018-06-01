package root.dao.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import root.dao.app.Question;

//DEPRICATED, DO NOT USE!!!!
@SuppressWarnings("serial")
public class Message implements Serializable {

	/**
	 * Class that responsible for communication between a server and a clients
	 */
	private static final long serialVersionUID = 1L;
	private String msg;// The message from the client or the server
	private ArrayList<Question> questions;
	private Map<String, Integer> correctAns;

	/**
	 * Constructors of message class
	 */

	public Message(String str) {
		msg = str;
	}

	public Message(String str, ArrayList<Question> questions) {
		msg = str;
		this.questions = questions;
	}

	public Message(String str, Map<String, Integer> correctAns) {
		msg = str;
		this.correctAns = correctAns;
	}

	/**
	 * 
	 * @return the communication message
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * set the communication message
	 * 
	 * @param msg
	 *            the communication message
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * 
	 * @return the array list of the questions
	 */

	public ArrayList<Question> getQuestions() {
		return questions;
	}

	/**
	 * set the array list of the questions
	 * 
	 * @param questions
	 *            the array list of questions
	 */
	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}

	/**
	 * 
	 * @return the map of question id and correct answers
	 */
	public Map<String, Integer> getCorrectAns() {
		return correctAns;
	}

	/**
	 * set the map of question id and correct answers
	 * 
	 * @param correctAns
	 *            map of question id and correct answers
	 */
	public void setCorrectAns(Map<String, Integer> correctAns) {
		this.correctAns = correctAns;
	}

}
