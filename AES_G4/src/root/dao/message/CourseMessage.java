package root.dao.message;

import java.util.ArrayList;

import root.dao.app.Course;
import root.dao.app.Subject;

/**
 * Class for course message
 * @author Omer Haimovich
 *
 */
public class CourseMessage extends AbstractMessage {

	private Subject courseSubject;
	private ArrayList<Course> courses;
	
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
	 * Returns the course as type of the message
	 */
	@Override
	public String getType() {
		return "Course";
	}

}
