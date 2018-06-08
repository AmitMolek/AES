package root.dao.message;

import java.util.ArrayList;

import root.dao.app.Course;
import root.dao.app.Exam;
import root.dao.app.Subject;

/**
 * Class for exam
 * @author Omer Haimovich
 *
 */
public class ExamMessage extends AbstractMessage {
	private static final long serialVersionUID = 1L;
	private ArrayList<Exam> exams;
	private Exam newExam;
	private Subject examSubject;
	private Course examCourse;
	private String id;
	


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
	 * Constructor that send message from client
	 * @param newExam the exam
	 */
	public ExamMessage(String message,Exam newExam){
		super(message);
		this.newExam = newExam;
	}
	
	/**
	 * Constructor that send get message from client
	 * @param examSubject the exam subject
	 * @param examCourse the exam course
	 */
	public ExamMessage(Subject examSubject, Course examCourse) {
		super("get-exams");
		this.examSubject = examSubject;
		this.examCourse = examCourse;
	}
	
	/**
	 * Constructor that send get message from client
	 * @param id the id
	 */
	public ExamMessage(String id) {
		super("get-exams");
		this.id = id;
		
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
	
	/**
	 * 
	 * @return the exam subject
	 */
	public Subject getExamSubject() {
		return examSubject;
	}

	/**
	 * Set new subject to exam
	 * @param examSubject the new subject
	 */
	public void setExamSubject(Subject examSubject) {
		this.examSubject = examSubject;
	}

	/**
	 * 
	 * @return the exam course
	 */
	public Course getExamCourse() {
		return examCourse;
	}

	/**
	 * Set new course to exam
	 * @param examCourse the new course
	 */
	public void setExamCourse(Course examCourse) {
		this.examCourse = examCourse;
	}

	/**
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}


}