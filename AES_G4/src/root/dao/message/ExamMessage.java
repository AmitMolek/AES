package root.dao.message;

import java.util.ArrayList;

import root.dao.app.Exam;

public class ExamMessage extends AbstractMessage {
	private static final long serialVersionUID = 1L;
	private ArrayList<Exam> exams;
	
	public ExamMessage(ArrayList<Exam> exams) {
		super("ok-get-exams");
		this.exams=exams;
	}

	@Override
	public String getType() {
		return "exam";
	}


}
