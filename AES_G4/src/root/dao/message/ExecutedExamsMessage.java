package root.dao.message;

import java.util.ArrayList;

import root.dao.app.ExecuteExam;

public class ExecutedExamsMessage extends AbstractMessage{
	private ArrayList<ExecuteExam> exams;
	
	public ExecutedExamsMessage() {
		super("get-executed");
		
	}

	public ArrayList<ExecuteExam> getExecutedExams(){
		return exams;
	}
	
	public void addExams(ArrayList<ExecuteExam> exams) {
		this.exams=exams;
	}
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
