package root.dao.app;

import java.io.Serializable;

public class Subject implements Serializable{

	/**
	 * @author Omer Haimovich
	 * Class for subject
	 */
	private String subjectID;
	private String subjectName;
	
	/**
	 * Constructor for subject
	 * @param subjectID the subject id
	 * @param subjectName the subject name
	 */
	public Subject(String subjectID, String subjectName) {
		super();
		this.subjectID = subjectID;
		this.subjectName = subjectName;
	}

	/**
	 * 
	 * @return the subject id
	 */
	public String getSubjectID() {
		return subjectID;
	}

	/**
	 * Sets new id for the subject
	 * @param subjectID the new subject id
	 */
	public void setSubjectID(String subjectID) {
		this.subjectID = subjectID;
	}

	/**
	 * 
	 * @return the subject name
	 */
	public String getSubjectName() {
		return subjectName;
	}

	/**
	 * Sets new name for the subject
	 * @param subjectName the new subject name
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	/**
	 * Return subject string for print
	 */
	@Override
	public String toString() {
		return "Subject = " + subjectID + ", subjectName=" + subjectName ;
	}
	
}
