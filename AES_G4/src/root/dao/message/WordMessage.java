package root.dao.message;

import root.dao.app.Exam;

/**
 * A class that transmits messages between the server and the client with regard
 * to word file information
 * 
 * @author Omer Haimovich
 *
 */
public class WordMessage extends AbstractMessage {

	// Instance variables **********************************************

	/**
	 * 
	 * The exam that is supposed to become a Word file
	 */
	private Exam exam;
	/**
	 * The word file
	 */
	private MyFile file;
	/**
	 * The id of the student who submit this exam
	 */
	private String userId;
	/**
	 * The word file
	 */
	private MyFile newFile;

	// CONSTRUCTORS *****************************************************

	/**
	 * Constructs the WordMessage
	 * 
	 * @param exam
	 *            the exam that is supposed to become a Word file
	 */
	public WordMessage(Exam exam) {
		super("get-word");
		this.exam = exam;
	}

	/**
	 * Constructs the WordMessage
	 * 
	 * @param userId
	 *            the id of the student who submit this exam
	 */
	public WordMessage(String userId) {
		super("get-wordexam");
		this.userId = userId;
	}

	/**
	 * Constructs the WordMessage
	 * 
	 * @param message
	 *            the message sent between the server and the client
	 * @param newFile
	 *            the word file
	 */
	public WordMessage(String message, MyFile newFile) {
		super(message);
		this.newFile = newFile;
	}

	// CLASS METHODS *************************************************

	/**
	 * A method that returns the exam that is supposed to become a Word file
	 * 
	 * @return the exam that is supposed to become a Word file
	 */
	public Exam getExam() {
		return exam;
	}

	/**
	 * A method that set the exam that is supposed to become a Word file
	 * 
	 * @param exam
	 *            the exam that is supposed to become a Word file
	 */
	public void setExam(Exam exam) {
		this.exam = exam;
	}

	/**
	 * A method that returns the id of the student who submit this exam
	 * 
	 * @return the id of the student who submit this exam
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Returns the type of message
	 */
	@Override
	public String getType() {
		return "word";
	}

	/**
	 * A method that returns the word file
	 * 
	 * @return the the word file
	 */
	public MyFile getNewFile() {
		return newFile;
	}

}
