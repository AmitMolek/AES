package root.dao.app;

import java.io.Serializable;

/**
 * A class that is responsible for keeping data about course
 * 
 * @author Omer Haimovich
 *
 */
public class Course implements Serializable {

	// Instance variables **********************************************

	/**
	 * The id of the course
	 */
	private String courseId;
	/**
	 * The name of the course
	 */
	private String courseName;

	// CONSTRUCTORS *****************************************************

	/**
	 * Constructs the Course
	 * 
	 * @param courseId
	 *            the id of the course
	 * @param courseName
	 *            the name of the course
	 */
	public Course(String courseId, String courseName) {
		super();
		this.courseId = courseId;
		this.courseName = courseName;
	}

	
	// CLASS METHODS *************************************************

	/**
	 * A method that returns the course id
	 * 
	 * @return the id of the course
	 */
	public String getCourseId() {
		return courseId;
	}

	/**
	 * 
	 * A method that set the id of the course
	 * 
	 * @param courseId
	 *            the id of the course
	 */
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	/**
	 * A method that returns the course name
	 * 
	 * @return the course name
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * 
	 * A method that set the name of the course
	 * 
	 * @param courseName
	 *            the name of the course
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * Prints courses info
	 */
	@Override
	public String toString() {
		return courseId + "-" + courseName;
	}

	/**
	 * Check if courses equals
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Course))
			return false;

		Course other = (Course) obj;
		if (courseId == null) {
			if (other.courseId != null)
				return false;
		} else if (!courseId.equals(other.courseId))
			return false;
		return true;
	}

}
