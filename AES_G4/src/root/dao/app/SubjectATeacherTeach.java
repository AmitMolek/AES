package root.dao.app;

import java.io.Serializable;

public class SubjectATeacherTeach implements Serializable {

	private static final long serialVersionUID = 1L;
	private String teacherID;
	private String subjectID;
	
	public SubjectATeacherTeach(String teacherID, String subjectID) {
		super();
		this.teacherID = teacherID;
		this.subjectID = subjectID;
	}
	
	public String getTeacherID() {
		return teacherID;
	}
	public void setTeacherID(String teacherID) {
		this.teacherID = teacherID;
	}
	public String getSubjectID() {
		return subjectID;
	}
	public void setSubjectID(String subjectID) {
		this.subjectID = subjectID;
	}

}
