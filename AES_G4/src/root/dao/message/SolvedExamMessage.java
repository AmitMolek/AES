package root.dao.message;

import root.dao.app.SolvedExams;

/**
 * Class for solved exam message
 * @author Omer Haimovich
 *
 */
public class SolvedExamMessage extends AbstractMessage {

	private SolvedExams solvedExam;
	
	/**
	 * Constructor for client message
	 * @param solvedExam the solved exam
	 */
	public SolvedExamMessage(SolvedExams payload) {
		super("put-solvedexams");
		this.solvedExam = payload;	
		}


	/**
	 * 
	 * @return the solved exam
	 */
	public SolvedExams getSolvedExam() {
		return solvedExam;
	}


	/**
	 * Set new solved exam
	 * @param solvedExam the new solved exam
	 */
	public void setSolvedExam(SolvedExams solvedExam) {
		this.solvedExam = solvedExam;
	}


	/**
	 * Returns solvedExams type
	 */
	@Override
	public String getType() {
		return "SolvedExams";
	}

}
