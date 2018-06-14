package root.dao.message;

import root.dao.app.Exam;

/**
 * Class for create word message
 * @author omer1
 *
 */
public class WordMessage extends AbstractMessage  {

	private Exam exam;
	private MyFile file;
	private String userId;
	private MyFile newFile;
	


	
	/**
	 * Constructor for client message
	 * @param exam the exam
	 */
	public WordMessage(Exam exam) {
		super("get-word");
		this.exam = exam;
	}
	
	public WordMessage(String userId) {
		super("get-wordexam");
		this.userId = userId;
	}
	
	public WordMessage(String message,MyFile newFile) {
		super(message);
		this.newFile = newFile;
	}

	/**
	 * 
	 * @return the exam
	 */
	public Exam getExam() {
		return exam;
	}

	/**
	 * Set new exam
	 * @param exam the new exam
	 */
	public void setExam(Exam exam) {
		this.exam = exam;
	}

	/**
	 * 
	 * @return the user id
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Returns type word
	 */
	@Override
	public String getType() {
		return "word";
	}
	/**
	 * 
	 * @return the new file
	 */
	public MyFile getNewFile() {
		return newFile;
	}


}
