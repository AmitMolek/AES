package root.dao.message;

import root.dao.app.SolvedExams;

public class UpdateSolvedExam extends AbstractMessage{

	private String teacher_id;
	private SolvedExams exam;
	
	public UpdateSolvedExam(String teacher_id, SolvedExams se) {
		super("put-updatesolvedexam");
		this.teacher_id = teacher_id;
		this.exam = se;
	}

	public String getTeacher_id() {
		return teacher_id;
	}

	public void setTeacher_id(String teacher_id) {
		this.teacher_id = teacher_id;
	}

	public SolvedExams getExam() {
		return exam;
	}

	public void setExam(SolvedExams exam) {
		this.exam = exam;
	}

	@Override
	public String getType() {
		return "updatesolvedexam";
	}
}
