package root.dao.message;

import root.dao.app.SolvedExams;

/**
 * The messgage object used to update the solved exam
 * @author Amit Molek
 *
 */

public class UpdateSolvedExam extends AbstractMessage{

	/**
	 * The default serial version id
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The id of the teacher that approved the exam
	 */
	private String teacher_id;
	/**
	 * The SolvedExams object
	 */
	private SolvedExams exam;
	
	/**
	 * Contructor, used to init the message with the data
	 * @param teacher_id the id of the approving teacher
	 * @param se the SolvedExams object filled with the solved exam data
	 */
	public UpdateSolvedExam(String teacher_id, SolvedExams se) {
		super("put-updatesolvedexam");
		this.teacher_id = teacher_id;
		this.exam = se;
	}

	/**
	 * Returns the id of the approving teacher
	 * @return id of the approving teacher
	 */
	public String getTeacher_id() {
		return teacher_id;
	}

	/**
	 * Sets the id of the approving teacher
	 * @param teacher_id the new id of the approving teacher
	 */
	public void setTeacher_id(String teacher_id) {
		this.teacher_id = teacher_id;
	}

	/**
	 * Returns the SolvedExams object filled with the solved exam data
	 * @return SolvedExams object of the solved exam
	 */
	public SolvedExams getExam() {
		return exam;
	}

	/**
	 * Sets the SolvedExams object
	 * @param exam the new SolvedExams data object
	 */
	public void setExam(SolvedExams exam) {
		this.exam = exam;
	}

	/**
	 * Returns the type of this message
	 */
	@Override
	public String getType() {
		return "updatesolvedexam";
	}
}
