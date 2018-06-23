package root.dao.message;

import java.util.ArrayList;

import root.dao.app.ExecuteExam;

/**
 * @author Naor Saadia
 * this message is proper to ask all executed exams
 * the server response this message with arraylist of executed exams
 */
public class ExecutedExamsMessage extends AbstractMessage{
	
	/**
	 * ArrayList of the execute exams
	 */
	private ArrayList<ExecuteExam> exams;
	
	/**
	 * the teacher id 
	 */
	private String teacherID;
	
	/**
	 * Constructor change the message to get-executed
	 */
	public ExecutedExamsMessage() {
		super("get-executed");		
	}
	
	/**
	 * return all the execute exams
	 * @return
	 */
	public ArrayList<ExecuteExam> getExecutedExams(){
		return exams;
	}
	
	/**
	 * add exam to the list of the exeucte exams
	 * @param exams
	 */
	public void addExams(ArrayList<ExecuteExam> exams) {
		this.exams=exams;
	}
	
	/**
	 * setter for teacher id
	 * @param id
	 */
	public void setTeacher(String id) {
		teacherID = id;
	}
	
	/**
	 * getter for teacher id
	 * @return
	 */
	public String getTeacher() {
		return teacherID;
	}
	
	/**
	 * getter for type
	 */
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
