package root.dao.message;

import java.util.ArrayList;

import root.dao.app.Subject;

/**
 * Class for subject message
 * @author Omer Haaimovich
 *
 */
public class SubjectMessage extends AbstractMessage {

	private ArrayList<Subject> teacherSubject;
	private String teacherId;
	
	/**
	 * Constructor for message get-subject from client
	 * @param msg
	 * @param teacherId
	 */
	public SubjectMessage (String teacherId)
	{
		super("get-subjects");
		this.teacherId = teacherId;
	}
	 /**
	  * Constructor for message ok-subject form server
	  * @param teacherSubject
	  */
	public SubjectMessage (ArrayList<Subject> teacherSubject)
	{
		super("ok-subjects");
		this.teacherSubject = teacherSubject;
	}
	
	/**
	 * 
	 * @return the teacher id
	 */
	public String getTeacherId() {
		return teacherId;
	}
	/**
	 * Set new value for teacher id
	 * @param teacherId the new value of teacher id
	 */
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	/**
	 * 
	 * @return the list of subjects that this teacher teach
	 */
	public ArrayList<Subject> getTeacherSubject() {
		return teacherSubject;
	}
	/**
	 * Set new subject for this teacher
	 * @param teacherSubject the new list of teacher subject
	 */
	public void setTeacherSubject(ArrayList<Subject> teacherSubject) {
		this.teacherSubject = teacherSubject;
	}

	/**
	 * Returns subject as type
	 */
	@Override
	public String getType() {
		return "Subject";
	}

}
