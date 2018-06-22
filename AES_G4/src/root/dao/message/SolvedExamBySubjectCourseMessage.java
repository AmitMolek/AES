package root.dao.message;

import java.util.ArrayList;

import root.dao.app.Course;
import root.dao.app.SolvedExams;
import root.dao.app.Subject;

/**
 * A class that transmits messages between the server and the client with regard
 * to course information
 * 
 * @author Gal Brandwine
 *
 */
public class SolvedExamBySubjectCourseMessage extends AbstractMessage{

	// Instance variables **********************************************

	/**
	 * the subject relevant to the solved exams
	 */
	private Subject subject;
	
	/**
	 * the course of the solved exams
	 */
	private Course course;
	
	/**
	 * the ArrayList of all solved exams in the specific course/subjct
	 */
	private ArrayList<SolvedExams> solvedExams;

	// CONSTRUCTORS *****************************************************

	/**
	 * Constructs the SolvedExamBySubjectCourseMessage
	 * 				This constructor is relevant when asking from server to brong back data
	 * 
	 * @param subject
	 *            The subject that we want to fetch its solved exams
	 * @param course
	 *            The course we want to fetch its solved exams
	 * 
	 */
	public SolvedExamBySubjectCourseMessage(Subject subject, Course course) {
		super("get-solvedbysubjectcourse");
		this.subject = subject;
		this.course = course;
		this.solvedExams = new ArrayList<>();
	}
	
	/**
	 * Constructs the SolvedExamBySubjectCourseMessage
	 * 				This constructor is relevant server found relevant data and want to send back to client
	 * 
	 * @param msg
	 *            the message, filled with relevant solved exams
	 * 
	 */
	public SolvedExamBySubjectCourseMessage(SolvedExamBySubjectCourseMessage msg) {
		super("ok-get-solvedbysubjectcourse");
		this.subject = msg.getSubject();
		this.course = msg.getCourse();
		this.solvedExams = msg.solvedExams;
	}
	
	// CLASS METHODS *************************************************

	/**
	 * A method that returns the solved exams
	 * 
	 * @return the solved exams
	 */
	public ArrayList<SolvedExams> getSolvedExams() {
		return solvedExams;
	}

	/**
	 * A method that set the solved exams
	 * 
	 * @param solvedExams
	 *            the solved exams
	 */
	public void setSolvedExams(ArrayList<SolvedExams> solvedExams) {
		this.solvedExams = solvedExams;
	}

	/**
	 * A method that returns subjeect
	 * 
	 * @return the subject
	 */
	public Subject getSubject() {
		return subject;
	}

	/**
	 * A method that set the subject
	 * 
	 * @param subject
	 *            the subject
	 */
	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	/**
	 * A method that returns course
	 * 
	 * @return the course
	 */
	public Course getCourse() {
		return course;
	}

	/**
	 * A method that set the course
	 * 
	 * @param course
	 *            the course
	 */
	public void setCourse(Course course) {
		this.course = course;
	}

	/**
	 * 
	 * Returns the type of message
	 */
	@Override
	public String getType() {
		return "SolvedBySubjectCourse";
	}
	
}
