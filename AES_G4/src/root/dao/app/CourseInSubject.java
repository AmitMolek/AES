package root.dao.app;

import java.io.Serializable;
/**
 * A class that utilize the CourseInsubject data
 * 
 * @author Gal Brandwine
 *
 */
public class CourseInSubject implements Serializable {
	
	// Instance variables **********************************************
	
	private static final long serialVersionUID = 1L;
	
	private String subject_id;
	private String course_id;
	
	// CONSTRUCTORS *****************************************************
	/**
	 * Constructs the CourseInSubject
	 * 
	 * @param subject_id
	 *            The message that you want to transmit to the server or client
	 * @param course_id
	 *            The id of a course that we want to fetch its name, from connectionTable courseInSubject
	 *            
	 * 
	 */
	public CourseInSubject(String subject_id, String course_id) {
		super();
		this.subject_id = subject_id;
		this.course_id = course_id;
	}

	// CLASS METHODS *************************************************
	
	/**
	 * A method that returns the subjectID
	 * 
	 * @return string - subject ID
	 */
	public String getSubject_id() {
		return subject_id;
	}

	/**
	 * A method to set subject ID
	 * 
	 * @param subject_id the id to set
	 */
	public void setSubject_id(String subject_id) {
		this.subject_id = subject_id;
	}

	/**
	 * A method that returns the getCourse_id
	 * 
	 * @return string - course_id
	 */
	public String getCourse_id() {
		return course_id;
	}

	/**
	 * A method to set Course_id
	 * 
	 * @param course_id the id to set
	 */
	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}
}
