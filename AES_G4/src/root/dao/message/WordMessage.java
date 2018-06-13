package root.dao.message;

import root.dao.app.Exam;

/**
 * Class for create word message
 * @author omer1
 *
 */
public class WordMessage extends AbstractMessage  {

	private Exam exam;
	
	
	
	/**
	 * Constructor for client message
	 * @param exam the exam
	 */
	public WordMessage(Exam exam) {
		super("get-word");
		this.exam = exam;
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
	 * Returns type word
	 */
	@Override
	public String getType() {
		return "word";
	}

}
