package root.dao.message;

import java.util.ArrayList;
import java.util.HashMap;

import root.dao.app.Course;
import root.dao.app.Subject;

/**
 * A class that transmits messages between the server and the client with regard
 * to course information
 * 
 * @author Omer Haimovich
 *
 */
public class CourseMessage extends AbstractMessage {

	// Instance variables **********************************************

	/**
	 * Map(String,String) for send course from server to client : key = courseID,
	 * value = course name.
	 */
	private HashMap<String, String> courseMap;
	/**
	 * 
	 * The subject of the course
	 * 
	 */
	private Subject courseSubject;

	/**
	 * 
	 * List of courses
	 */
	private ArrayList<Course> courses;

	// CONSTRUCTORS *****************************************************

	/**
	 * Constructs the CourseMessage
	 * 
	 * @param message
	 *            The message that you want to transmit to the server or client
	 * @param courseMap
	 *            Map(String,String) for send course from server to client : key =
	 *            courseID, value = course name.
	 * 
	 */
	public CourseMessage(String message, HashMap<String, String> courseMap) {
		super(message);
		this.courseMap = courseMap;
	}

	/**
	 * Constructs the CourseMessage
	 * 
	 * @param courseSubject
	 *            the subject of the course
	 */
	public CourseMessage(Subject courseSubject) {
		super("get-courses");
		this.courseSubject = courseSubject;
	}

	/**
	 * Constructs the CourseMessage
	 * 
	 * @param subjectCourses
	 *            the list of courses from the database
	 */
	public CourseMessage(ArrayList<Course> subjectCourses) {
		super("ok-get-courses");
		this.courses = subjectCourses;
	}

	// CLASS METHODS *************************************************

	/**
	 * A method that returns the subject of the course
	 * 
	 * @return the subject of the course
	 */
	public Subject getCourseSubject() {
		return courseSubject;
	}

	/**
	 * A method that set the subject of the course
	 * 
	 * @param courseSubject
	 *            the subject for the course
	 */
	public void setCourseSubject(Subject courseSubject) {
		this.courseSubject = courseSubject;
	}

	/**
	 * A method that returns the list of courses
	 * 
	 * @return the list of courses
	 */
	public ArrayList<Course> getCourses() {
		return courses;
	}

	/**
	 * A method that set the list of courses
	 * 
	 * @param subjectCourses
	 *            the list of courses
	 */
	public void setCourses(ArrayList<Course> subjectCourses) {
		this.courses = subjectCourses;
	}

	/**
	 * A method that returns the map(String,String)
	 * 
	 * @return the courseMap
	 */
	public HashMap<String, String> getCourseMap() {
		return courseMap;
	}

	/**
	 * A method that set the map(String,String)
	 * 
	 * @param courseMap
	 *            the courseMap
	 */
	public void setCourseMap(HashMap<String, String> courseMap) {
		this.courseMap = courseMap;
	}

	/**
	 * 
	 * Returns the type of message
	 */
	@Override
	public String getType() {
		return "Course";
	}

}
