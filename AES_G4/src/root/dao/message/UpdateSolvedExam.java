package root.dao.message;

import root.dao.app.SolvedExams;

/**
 * @author Naor Saadia
 * this message is proper to ask to insert solved exam
 */
public class UpdateSolvedExam extends AbstractMessage{

	/**
	 * hold the teacher id
	 */
	private String teacher_id;
	
	/**
	 * hold solved exams
	 */
	private SolvedExams exam;
	
	/**
	 * this method proper to update solved exam
	 * @param teacher_id
	 * @param se
	 */
	public UpdateSolvedExam(String teacher_id, SolvedExams se) {
		super("put-updatesolvedexam");
		this.teacher_id = teacher_id;
		this.exam = se;
	}

	/**
	 * getter for teacher id 
	 * @return
	 */
	public String getTeacher_id() {
		return teacher_id;
	}

	/**
	 * setter for teacher id
	 * @param teacher_id
	 */
	public void setTeacher_id(String teacher_id) {
		this.teacher_id = teacher_id;
	}

	/**
	 * getter for exam
	 * @return
	 */
	public SolvedExams getExam() {
		return exam;
	}

	/**
	 * setter for exam
	 * @param exam
	 */
	public void setExam(SolvedExams exam) {
		this.exam = exam;
	}

	/**
	 * getter for type
	 */
	@Override
	public String getType() {
		return "updatesolvedexam";
	}
}
