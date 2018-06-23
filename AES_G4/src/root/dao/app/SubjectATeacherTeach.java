package root.dao.app;

import java.io.Serializable;

/**
 * A class that is responsible for keeping data about SubjectATeacherTeach
 * 
 * @author Gal Brandwine
 *
 */
public class SubjectATeacherTeach implements Serializable {

	// Instance variables **********************************************
	
	private static final long serialVersionUID = 1L;
	

	/**
	 * The id of the teacher
	 */
	private String teacherID;
	
	/**
	 * The id of the subject
	 */
	private String subjectID;
	
	// CONSTRUCTORS *****************************************************

		/**
		 * Constructs the SubjectATeacherTeach
		 * 
		 * @param teacherID
		 *            the id of the teacher
		 * @param subjectID
		 *            the name of the subject
		 */
	public SubjectATeacherTeach(String teacherID, String subjectID) {
		super();
		this.teacherID = teacherID;
		this.subjectID = subjectID;
	}
	
	// CLASS METHODS *************************************************

	/**
	 * A method that returns the TeacherID
	 * 
	 * @return the id of the Teacher
	 */
	public String getTeacherID() {
		return teacherID;
	}
	
	/**
	 * 
	 * A method that set the id of the Teacher
	 * 
	 * @param teacherID
	 *            the id of the Teacher
	 */
	public void setTeacherID(String teacherID) {
		this.teacherID = teacherID;
	}
	
	/**
	 * A method that returns the Subject ID
	 * 
	 * @return the Subject ID
	 */
	public String getSubjectID() {
		return subjectID;
	}
	
	/**
	 * 
	 * A method that set the ID of the subject
	 * 
	 * @param subjectID
	 *            the id of the subject
	 */
	public void setSubjectID(String subjectID) {
		this.subjectID = subjectID;
	}

}
