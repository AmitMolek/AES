package root.dao.message;

import root.dao.app.ExecuteExam;

/**
 * Class for executeExam message
 * 
 * @author Omer Haimovich
 *
 */
public class ExecuteExamMessage extends AbstractMessage {

	private ExecuteExam newExam;

	/**
	 * Constructor for client message
	 * @param newExam the execute exam
	 */
	public ExecuteExamMessage(ExecuteExam newExam) {
		super("put-executeexam");
		this.newExam = newExam;
	}

	/**
	 * Rturn type of execute
	 */
	@Override
	public String getType() {
		return "execute";
	}

	/**
	 * 
	 * @return the execute exam
	 */
	public ExecuteExam getNewExam() {
		return newExam;
	}

	/**
	 * Sets new execute exam
	 * 
	 * @param newExam
	 *            the new execute exam
	 */
	public void setNewExam(ExecuteExam newExam) {
		this.newExam = newExam;
	}

}
