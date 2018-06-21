package root.dao.app;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A class that is responsible for keeping data about course
 * 
 * @author Omer Haimovich
 *
 */
public class Exam implements Serializable {

	// Instance variables **********************************************

	/**
	 * The id of the exam
	 */
	private String examId;

	/**
	 * The teacher who wrote this exam
	 */
	private User author;

	/**
	 * The duration time of the exam
	 */
	private int examDuration;
	/**
	 * The state of the exam : 0 - clean, 1- dirty
	 */
	private int ExamState;
	/**
	 * The exam is locked or unlocked : 0-locked, 1- unlocked
	 */
	private int lock;

	/**
	 * 
	 * The questions that belong to the exam
	 */
	private ArrayList<QuestionInExam> examQuestions;
	/**
	 * The id of the teacher who wrote the exam
	 */
	private String teacherId;
	/**
	 * The exam after the teacher prepared him to be executed
	 */
	private ExecuteExam executeExam;

	// CONSTRUCTORS *****************************************************

	/**
	 * Constructs the Exam
	 * 
	 * @param examId
	 *            the id of the exam
	 * @param author
	 *            the teacher who wrote the exam
	 * @param examDuration
	 *            the duration time of the exam
	 */
	public Exam(String examId, String teacherId, int examDuration) {
		super();
		this.examId = examId;
		this.examDuration = examDuration;
		ExamState = 1;
		this.lock = 1;
		this.teacherId = teacherId;
	}

	/**
	 * Constructs the Exam
	 * 
	 * @param examId
	 *            the id of the exam
	 * @param author
	 *            the teacher who wrote the exam
	 * @param examDuration
	 *            the duration time of the exam
	 * @param examQuestions
	 *            the list of all questions that belong to the exam
	 */
	public Exam(String examId, User author, int examDuration, ArrayList<QuestionInExam> examQuestions) {
		super();
		this.examId = examId;
		this.author = author;
		this.examDuration = examDuration;
		ExamState = 0;
		this.lock = 0;
		this.examQuestions = examQuestions;
		teacherId = author.getUserID();
	}

	// CLASS METHODS *************************************************

	/**
	 * A method that returns the id of the exam
	 * 
	 * @return the id of the exam
	 */
	public String getExamId() {
		return examId;
	}

	/**
	 * A method that set the id of the exam
	 * 
	 * @param examId
	 *            the id of the exam
	 */
	public void setExamId(String examId) {
		this.examId = examId;
	}

	/**
	 * A method that returns the teacher who wrote the exam
	 * 
	 * @return the teacher who wrote the exam
	 */
	public User getAuthor() {
		return author;
	}

	/**
	 * A method that set the teacher who wrote the exam
	 * 
	 * @param author
	 *            the teacher who wrote the exam
	 */
	public void setAuthor(User author) {
		this.author = author;
	}

	/**
	 * A method that returns the duration time of the exam
	 * 
	 * @return the the duration time of the exam
	 */
	public int getExamDuration() {
		return examDuration;
	}

	/**
	 * A method that set the duration time of the exam
	 * 
	 * @param examDuration
	 *            the new duration value
	 */
	public void setExamDuration(int examDuration) {
		this.examDuration = examDuration;
	}

	/**
	 * A method that returns the state of the exam
	 * 
	 * @return the exam state(clean or dirty)
	 */
	public int getExamState() {
		return ExamState;
	}

	/**
	 * A method that set the state of the exam
	 * 
	 * @param examState
	 *            the exam state(clean or dirty)
	 */
	public void setExamState(int examState) {
		ExamState = examState;
	}

	/**
	 * A method that returns the lock state of the exam
	 * 
	 * @return the lock state of exam (locked or unlocked)
	 */
	public int getLock() {
		return lock;
	}

	/**
	 * A method that set the lock state of the exam
	 * 
	 * @param lock
	 *            the lock state of exam (locked or unlocked)
	 */
	public void setLock(int lock) {
		this.lock = lock;
	}

	/**
	 * A method that returns the questions that belong to the exam
	 * 
	 * @return list of all the questions that belong to the exam
	 */
	public ArrayList<QuestionInExam> getExamQuestions() {
		return examQuestions;
	}

	/**
	 * A method that set new list of questions that belong to the exam
	 * 
	 * @param examQuestions
	 *            list of questions that belong to the exam
	 */
	public void setExamQuestions(ArrayList<QuestionInExam> examQuestions) {
		this.examQuestions = examQuestions;
	}

	/**
	 * A method that returns The exam after the teacher prepared him to be executed
	 * 
	 * @return the execute exam
	 */
	public ExecuteExam getExecuteExam() {
		return executeExam;
	}

	/**
	 * A method that set execute exam
	 * 
	 * @param executeExam
	 *            the execute exam
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
