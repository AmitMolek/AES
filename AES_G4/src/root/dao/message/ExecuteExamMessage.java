package root.dao.message;

import root.dao.app.ExecuteExam;

/**
 * A class that transmits messages between the server and the client with regard
 * to execute exam information
 * 
 * @author Omer Haimovich
 *
 */
public class ExecuteExamMessage extends AbstractMessage {

	// Instance variables **********************************************

	/**
	 * The execute exam
	 */
	private ExecuteExam newExam;

	// CONSTRUCTORS *****************************************************

	/**
	 * Constructs the ExecuteExamMessage
	 *
	 * @param newExam
	 *            the execute exam
	 */
	public ExecuteExamMessage(ExecuteExam newExam) {
		super("put-executeexam");
		this.newExam = newExam;
	}

	// CLASS METHODS *************************************************
	
	/**
	 * Returns the type of message
	 */
	@Override
	public String getType() {
		return "execute";
	}

	
	/**
	 * A method that returns the execute exam
	 * @return the execute exam
	 */
	public ExecuteExam getNewExam() {
		return newExam;
	}

	/**
	 * A method that set the execute exam
	 * 
	 * @param newExam
	 *            the execute exam
	 */
	public void setNewExam(ExecuteExam newExam) {
		this.newExam = newExam;
	}

}
