package root.dao.message;

import java.util.ArrayList;

import root.dao.app.Exam;

/**
 * Class for exam
 * @author Omer Haimovich
 *
 */
public class ExamMessage extends AbstractMessage {
	private static final long serialVersionUID = 1L;
	private ArrayList<Exam> exams;
	private Exam newExam;

	/**
	 * Constructor that send message from server
	 * @param exams the list of exams
	 */
	public ExamMessage(ArrayList<Exam> exams) {
		super("ok-get-exams");
		this.exams=exams;
	}
	
	/**
	 * Constructor that send message from client
	 * @param newExam the exam
	 */
	public ExamMessage(Exam newExam){
		super("put-exams");
		this.newExam = newExam;
	}

	/**
	 * Return type of exam
	 */
	@Override
	public String getType() {
		return "exam";
	}
	
	/**
	 * 
	 * @return list of exams
	 */
	public ArrayList<Exam> getExams() {
		return exams;
	}

	/**
	 * Set new list of exams
	 * @param exams the new exams list
	 */
	public void setExams(ArrayList<Exam> exams) {
		this.exams = exams;
	}

	/**
	 * 
	 * @return the exam
	 */
	public Exam getNewExam() {
		return newExam;
	}

	/**
	 * Set new exam
	 * @param newExam the new exam
	 */
	public void setNewExam(Exam newExam) {
		this.newExam = newExam;
	}



}
