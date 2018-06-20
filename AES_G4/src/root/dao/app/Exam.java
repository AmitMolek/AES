package root.dao.app;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * Class for exams
 * @author Omer Haimovich
 *
 */
public class Exam implements Serializable {

	private String examId;
	private User author;
	private int examDuration;
	private int ExamState;//0 - clean, 1- dirty
	private int lock;// 0-locked, 1- unlocked
	private ArrayList<QuestionInExam> examQuestions;
	private String teacherId;
	private ExecuteExam executeExam;
	
	/**
	 * @author Alon Ben-yosef
	 * Constructor for exam for DB pulling
	 * @param examId
	 * @param examDuration
	 * @param examState
	 * @param lock
	 * @param teacherId
	 */
	public Exam(String examId, int examDuration, int examState, int lock, String teacherId) {
		super();
		this.examId = examId;
		this.examDuration = examDuration;
		ExamState = examState;
		this.lock = lock;
		this.setTeacherId(teacherId);
	}

	/**
	 * Constructor for exam
	 * @param examId the exam id
	 * @param author the teacher author
	 * @param examDuration the exam duration
	 * @param examQuestions the list of the questions in the exam
	 */
	public Exam(String examId, String teacherId, int examDuration) {
		super();
		this.examId = examId;
		this.examDuration = examDuration;
		ExamState = 1;
		this.lock = 1;
		this.setTeacherId(teacherId);
	}
	
	/**
	 * Constructor for exam
	 * @param examId the exam id
	 * @param author the teacher that wrote the id
	 * @param examDuration the exam duration
	 * @param examQuestions the exam questions
	 */
	public Exam(String examId, User author, int examDuration,
			ArrayList<QuestionInExam> examQuestions) {
		super();
		this.examId = examId;
		this.author = author;
		this.examDuration = examDuration;
		ExamState = 0;
		this.lock = 0;
		this.examQuestions = examQuestions;
		setTeacherId(author.getUserID());
	}

	/**
	 * 
	 * @return the exam id
	 */
	public String getExamId() {
		return examId;
	}
	
	/**
	 * Set new id value for exam
	 * @param examId new exam id value
	 */
	public void setExamId(String examId) {
		this.examId = examId;
	}
	
	/**
	 * 
	 * @return the author teacher
	 */
	public User getAuthor() {
		return author;
	}
	
	/**
	 *  Set new author value for exam
	 * @param author new teacher author
	 */
	public void setAuthor(User author) {
		this.author = author;
	}
	
	/**
	 * 
	 * @return the exam duration
	 */
	public int getExamDuration() {
		return examDuration;
	}
	/**
	 * Set new duration for exam
	 * @param examDuration the new duration value
	 */
	public void setExamDuration(int examDuration) {
		this.examDuration = examDuration;
	}
	
	/**
	 * 
	 * @return the exam state(clear or dirty)
	 */
	public int getExamState() {
		return ExamState;
	}
	
	/**
	 * Set new state for exam
	 * @param examState the new exam state
	 */
	public void setExamState(int examState) {
		ExamState = examState;
	}
	
	/**
	 * 
	 * @return the lock state of exam
	 */
	public int getLock() {
		return lock;
	}
	
	/**
	 * Set new lock state for the exam
	 * @param lock the new lock state of exam
	 */
	public void setLock(int lock) {
		this.lock = lock;
	}
	
	/**
	 * 
	 * @return the questions in the exam
	 */
	public ArrayList<QuestionInExam> getExamQuestions() {
		return examQuestions;
	}
	
	/**
	 * Set new list of questions to the exam
	 * @param examQuestions the new list of questions
	 */
	public void setExamQuestions(ArrayList<QuestionInExam> examQuestions) {
		this.examQuestions = examQuestions;
	}
	
	/**
	 * 
	 * @return the execute exam
	 */
	public ExecuteExam getExecuteExam() {
		return executeExam;
	}

	/**
	 * Set new execute exam
	 * @param executeExam the new execute exam
	 */
	public void setExecuteExam(ExecuteExam executeExam) {
		this.executeExam = executeExam;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

}
