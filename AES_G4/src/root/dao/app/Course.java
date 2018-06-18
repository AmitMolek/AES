package root.dao.app;

import java.io.Serializable;
/**
 * Class for course
 * @author Omer Haimovich
 *
 */
public class Course implements Serializable {

	private String courseId;
	private String courseName;
	 /**
	  * Constructor for making new course
	  * @param courseId the id of the course
	  * @param courseName the name of the course
	  */
	public Course(String courseId, String courseName) {
		super();
		this.courseId = courseId;
		this.courseName = courseName;
	}
	
	/**
	 * 
	 * @return the course id
	 */
	public String getCourseId() {
		return courseId;
	}
	
	/**
	 * Set new id for the course
	 * @param courseId the new id value
	 */
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	
	/**
	 * 
	 * @return the course name
	 */
	public String getCourseName() {
		return courseName;
	}
	
	/**
	 * Sets new name for the course
	 * @param courseName the new name for the course
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	@Override
	public String toString() {
		return courseId + "-"+ courseName;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof Course)) return false;
		
		Course other = (Course) obj;
		if (courseId == null) {
			if (other.courseId != null) return false;
		}else if (!courseId.equals(other.courseId)) return false;
		return true;
	}

	
}
