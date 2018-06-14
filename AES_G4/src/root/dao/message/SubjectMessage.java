package root.dao.message;

import java.util.ArrayList;
import java.util.HashMap;

import root.dao.app.SolvedExams;
import root.dao.app.Subject;

/**
 * Class for subject message
 * @author Omer Haaimovich
 *
 */
public class SubjectMessage extends AbstractMessage {

	private HashMap<String, String> subjectsMap;			// key = subjectID, value = subject name
	private ArrayList<Subject> teacherSubject;
	//private ArrayList<String> subjectsID;
	private String teacherId;
	
	/**
	 * @author gal
	 * Use this constructor when you have map with subjects ID' and want to fill map value's with subject names.
	 * @param subjectsMap
	 */
	public SubjectMessage(String message, HashMap<String, String> subjectsMap) {
		super(message);
		this.subjectsMap = subjectsMap;
	}
	/**
	 * Constructor for message get-subject from client
	 * @param teacherId the teacher id
	 */
	public SubjectMessage (String teacherId)
	{
		super("get-subjects");
		this.teacherId = teacherId;
	}
	 /**
	  * Constructor for message ok-subject form server
	  * @param teacherSubject the list of the subject of the teacher
	  */
	public SubjectMessage (ArrayList<Subject> teacherSubject)
	{
		super("ok-get-subjects");
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
	 * @return the subjectsMap
	 */
	public HashMap<String, String> getSubjectsMap() {
		return subjectsMap;
	}
	/**
	 * @param subjectsMap the subjectsMap to set
	 */
	public void setSubjectsMap(HashMap<String, String> subjectsMap) {
		this.subjectsMap = subjectsMap;
	}
	/**
	 * Returns subject as type
	 */
	@Override
	public String getType() {
		return "Subject";
	}

}
