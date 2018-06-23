package root.dao.app;

import java.io.Serializable;

/**
 * this dao class contain subject information
 * 
 * @author gal
 *  
 */
public class Subject implements Serializable{

	private static final long serialVersionUID = 1L;
	// Instance variables **********************************************

	/**
	 * The id of the subject
	 */
	private String subjectID;
	/**
	 * The name of the subject
	 */
	private String subjectName;
	
	// CONSTRUCTORS *****************************************************

	/**
	 * Constructs the Subject
	 * 
	 * @param subjectID
	 *            the id of the subject
	 * @param subjectName
	 *            the name of the subject
	 */
	public Subject(String subjectID, String subjectName) {
		super();
		this.subjectID = subjectID;
		this.subjectName = subjectName;
	}

	// CLASS METHODS *************************************************

	/**
	 * A method that returns the subject id
	 * 
	 * @return the id of the subject
	 */
	public String getSubjectID() {
		return subjectID;
	}

	/**
	 * 
	 * A method that set the id of the subject
	 * 
	 * @param subjectID
	 *            the id of the subject
	 */
	public void setSubjectID(String subjectID) {
		this.subjectID = subjectID;
	}

	/**
	 * A method that returns the subject name
	 * 
	 * @return the subject name
	 */
	public String getSubjectName() {
		return subjectName;
	}

	/**
	 * 
	 * A method that set the name of the subject
	 * 
	 * @param subjectName
	 *            the name of the subject
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	/**
	 * Prints subject's info
	 */
	@Override
	public String toString() {
		return "Subject = " + subjectID + ", subjectName=" + subjectName ;
	}
	
}
