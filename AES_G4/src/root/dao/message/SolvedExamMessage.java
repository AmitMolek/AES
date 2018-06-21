package root.dao.message;

import root.dao.app.SolvedExams;

/**
 * A class that transmits messages between the server and the client with regard
 * to solved exam information
 * 
 * @author Omer Haimovich
 *
 */
public class SolvedExamMessage extends AbstractMessage {

	// Instance variables **********************************************

	/**
	 * The solved exam
	 */
	private SolvedExams solvedExam;

	// CONSTRUCTORS *****************************************************

	/**
	 * Constructs the SolvedExamMessage
	 * 
	 * @param payload
	 *            the solved exam
	 */
	public SolvedExamMessage(SolvedExams payload) {
		super("put-solvedexams");
		this.solvedExam = payload;
	}

	// CLASS METHODS *************************************************

	/**
	 * A method that returns the solved exam
	 * 
	 * @return the solved exam
	 */
	public SolvedExams getSolvedExam() {
		return solvedExam;
	}

	/**
	 * A method that set the the solved exam
	 * 
	 * @param solvedExam
	 *            the solved exam
	 */
	public void setSolvedExam(SolvedExams solvedExam) {
		this.solvedExam = solvedExam;
	}

	/**
	 * 
	 * Returns the type of message
	 */
	@Override
	public String getType() {
		return "SolvedExams";
	}

}
