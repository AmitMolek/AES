package root.dao.app;

import java.io.Serializable;
/**
 * @author gal
 *  this dao class contain subject information
 */
public class Subject implements Serializable{

	private static final long serialVersionUID = 1L;

	private String subjectID;
	private String subjectName;
	
	public Subject(String subjectID, String subjectName) {
		super();
		this.subjectID = subjectID;
		this.subjectName = subjectName;
	}

	public String getSubjectID() {
		return subjectID;
	}

	public void setSubjectID(String subjectID) {
		this.subjectID = subjectID;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	@Override
	public String toString() {
		return "Subject = " + subjectID + ", subjectName=" + subjectName ;
	}
	
}
