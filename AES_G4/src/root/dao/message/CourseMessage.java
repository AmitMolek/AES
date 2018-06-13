package root.dao.message;

import java.util.ArrayList;
import java.util.HashMap;

import root.dao.app.Course;
import root.dao.app.Subject;

/**
 * Class for course message
 * @author Omer Haimovich
 *
 */
public class CourseMessage extends AbstractMessage {

	private HashMap<String, String> courseMap;			// key = courseID, value = course name.
	private Subject courseSubject;
	private ArrayList<Course> courses;
	
	/**
	 * @author gal
	 * Use this constructor when you have courseMap filled with courseID's as key, 
	 * and you want to sent it from client to server, in order to fill it with values.
	 * @param message "get-courses"
	 * 
	 * Also: Use this constructor when you have courseMap filled with courseID's as key and values,
	 * and you want to send it from server back to client.
	 * @param message "ok-get-course"
	 * 
	 * @param courseMap
	 */
	public CourseMessage(String message, HashMap<String, String> courseMap) {
		super(message);
		this.courseMap = courseMap;
	}
	/**
	 * Constructor for message get-courses from client
	 * @param courseSubject the subject of the course
	 */
	public CourseMessage (Subject courseSubject){
		super("get-courses");
		this.courseSubject = courseSubject;
	}
	 /**
	  * Constructor for message ok-courses form server
	  * @param subjectCourses the list of the subject courses
	  */
	public CourseMessage ( ArrayList<Course> subjectCourses) {
		super("ok-get-courses");
		this.courses = subjectCourses;
	}
	/**
	 * 
	 * @return the subject of the course
	 */
	public Subject getCourseSubject() {
		return courseSubject;
	}

	
	/**
	 * Sets new value for subject of a course
	 * @param courseSubject the new subject for the course
	 */
	public void setCourseSubject(Subject courseSubject) {
		this.courseSubject = courseSubject;
	}

	/**
	 * 
	 * @return the list of the courses
	 */
	public ArrayList<Course> getCourses() {
		return courses;
	}

	/**
	 * Sets new list courses
	 * @param subjectCourses the new list courses
	 */
	public void setCourses(ArrayList<Course> subjectCourses) {
		this.courses = subjectCourses;
	}

	/**
	 * @return the courseMap
	 */
	public HashMap<String, String> getCourseMap() {
		return courseMap;
	}
	/**
	 * @param courseMap the courseMap to set
	 */
	public void setCourseMap(HashMap<String, String> courseMap) {
		this.courseMap = courseMap;
	}
	/**
	 * Returns the course as type of the message
	 */
	@Override
	public String getType() {
		return "Course";
	}

}
