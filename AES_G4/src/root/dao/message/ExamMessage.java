package root.dao.message;

import java.util.ArrayList;

import root.dao.app.Course;
import root.dao.app.Exam;
import root.dao.app.Subject;

/**
 * A class that transmits messages between the server and the client with regard
 * to exam information
 * 
 * @author Omer Haimovich
 *
 */
public class ExamMessage extends AbstractMessage {

	// Instance variables **********************************************

	private static final long serialVersionUID = 1L;
	/**
	 * List of exams
	 */
	private ArrayList<Exam> exams;
	/**
	 * A specific exam that will transmit between the server and the client
	 */
	private Exam newExam;
	/**
	 * The subject of the exam
	 */
	private Subject examSubject;
	/**
	 * The course of the exam
	 */
	private Course examCourse;
	/**
	 * The id of the exam
	 */
	private String id;

	// CONSTRUCTORS *****************************************************

	/**
	 * Constructs the ExamMessage
	 * 
	 * @param exams
	 *            the list of exams
	 */
	public ExamMessage(ArrayList<Exam> exams) {
		super("ok-get-exams");
		this.exams = exams;
	}

	/**
	 * Constructs the ExamMessage
	 * 
	 * @param newExam
	 *            the exam that will transmit between the server and the client
	 */
	public ExamMessage(Exam newExam) {
		super("put-exams");
		this.newExam = newExam;
	}

	/**
	 * Constructs the ExamMessage
	 * 
	 * @param message
	 *            The message that you want to transmit to the server or client
	 * @param newExam
	 *            the exam that will transmit between the server and the client
	 */
	public ExamMessage(String message, Exam newExam) {
		super(message);
		this.newExam = newExam;
	}

	/**
	 * Constructs the ExamMessage
	 * 
	 * @param examSubject
	 *            the subject of the exam
	 * @param examCourse
	 *            the course of the exam
	 */
	public ExamMessage(Subject examSubject, Course examCourse) {
		super("get-exams");
		this.examSubject = examSubject;
		this.examCourse = examCourse;
	}

	/**
	 * Constructs the ExamMessage
	 * 
	 * @param id
	 *            the id of the exam
	 */
	public ExamMessage(String id) {
		super("get-exams");
		this.id = id;

	}

	
	// CLASS METHODS *************************************************
	
	/**
	 *  Returns the type of message
	 */
	@Override
	public String getType() {
		return "exam";
	}

	/**
	 * A method that returns the list of exams
	 * @return the list of exams
	 */
	public ArrayList<Exam> getExams() {
		return exams;
	}

	/**
	 * A method that set the list of exams
	 * 
	 * @param exams
	 *            the list of exams
	 */
	public void setExams(ArrayList<Exam> exams) {
		this.exams = exams;
	}

	/**
	 *  A method that returns the specific exam
	 * @return the specific exam
	 */
	public Exam getNewExam() {
		return newExam;
	}

	/**
	 * A method that set the specific exam
	 * 
	 * @param newExam
	 *           the specific exam
	 */
	public void setNewExam(Exam newExam) {
		this.newExam = newExam;
	}

	/**
	 * A method that returns the subject of the exam
	 * @return the subject of the exam
	 */
	public Subject getExamSubject() {
		return examSubject;
	}

	/**
	 * A method that set the subject of the exam
	 * 
	 * @param examSubject
	 *            the subject of the exam
	 */
	public void setExamSubject(Subject examSubject) {
		this.examSubject = examSubject;
	}

	/**
	 * A method that returns the course of the exam
	 * @return the  course of the exam
	 */
	public Course getExamCourse() {
		return examCourse;
	}

	/**
	 * A method that set the course of the exam
	 * 
	 * @param examCourse
	 *            the  course of the exam
	 */
	public void setExamCourse(Course examCourse) {
		this.examCourse = examCourse;
	}

	/**
	 * A method that returns the id of the exam
	 * @return the id of the exam
	 */
	public String getId() {
		return id;
	}

}