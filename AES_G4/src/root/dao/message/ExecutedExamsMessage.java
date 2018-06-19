package root.dao.message;

import java.util.ArrayList;

import root.dao.app.ExecuteExam;

public class ExecutedExamsMessage extends AbstractMessage{
	private ArrayList<ExecuteExam> exams;
	private String teacherID;
	
	public ExecutedExamsMessage() {
		super("get-executed");
		
	}

	public ArrayList<ExecuteExam> getExecutedExams(){
		return exams;
	}
	
	public void addExams(ArrayList<ExecuteExam> exams) {
		this.exams=exams;
	}
	
	public void setTeacher(String id) {
		teacherID = id;
	}
	
	public String getTeacher() {
		return teacherID;
	}
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
