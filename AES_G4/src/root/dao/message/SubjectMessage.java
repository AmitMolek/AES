package root.dao.message;

import java.util.ArrayList;
import java.util.HashMap;

import root.dao.app.SolvedExams;
import root.dao.app.Subject;

/**
 * A class that transmits messages between the server and the client with regard
 * to subject information
 *
 */
public class SubjectMessage extends AbstractMessage {

	// Instance variables **********************************************

	/**
	 * Map<String,String> for send subject from server to client : key = subjectID,
	 * value = subject name
	 */
	private HashMap<String, String> subjectsMap;
	/**
	 * List of subjects
	 */
	private ArrayList<Subject> teacherSubject;
	/**
	 * the id of specific teacher
	 */
	private String teacherId;

	// CONSTRUCTORS *****************************************************

	/**
	 * Constructs the SubjectMessage
	 * 
	 * @param message
	 *            the message sent between the server and the client
	 * @param subjectsMap
	 *            Map<String,String> for send subject from server to client : key =
	 *            subjectID, value = subject name
	 */
	public SubjectMessage(String message, HashMap<String, String> subjectsMap) {
		super(message);
		this.subjectsMap = subjectsMap;
	}

	/**
	 * Constructs the SubjectMessage
	 * 
	 * @param teacherId
	 *            the id of specific teacher
	 */
	public SubjectMessage(String teacherId) {
		super("get-subjects");
		this.teacherId = teacherId;
	}

	/**
	 * Constructs the SubjectMessage
	 * 
	 * @param teacherSubject
	 *            the list of subjects
	 */
	public SubjectMessage(ArrayList<Subject> teacherSubject) {
		super("ok-get-subjects");
		this.teacherSubject = teacherSubject;
	}

	// CLASS METHODS *************************************************

	/**
	 * A method that returns the id of specific teacher
	 * 
	 * @return the id of specific teacher
	 */
	public String getTeacherId() {
		return teacherId;
	}

	/**
	 * A method that set the id of specific teacher
	 * 
	 * @param teacherId
	 *            the id of specific teacher
	 */
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	/**
	 * A method that returns the list of subjects
	 * 
	 * @return the list of subjects
	 */
	public ArrayList<Subject> getTeacherSubject() {
		return teacherSubject;
	}

	/**
	 * A method that set the list of subjects
	 * 
	 * @param teacherSubject
	 *            the list of subjects
	 */
	public void setTeacherSubject(ArrayList<Subject> teacherSubject) {
		this.teacherSubject = teacherSubject;
	}

	/**
	 * A method that returns the map<String,String> for send subject from server to
	 * client : key = subjectID, value = subject name
	 * 
	 * @return the subjectsMap
	 */
	public HashMap<String, String> getSubjectsMap() {
		return subjectsMap;
	}

	/**
	 * A method that set the map<String,String> for send subject from server to
	 * client : key = subjectID, value = subject name
	 * 
	 * @param subjectsMap
	 *            the subjectsMap
	 */
	public void setSubjectsMap(HashMap<String, String> subjectsMap) {
		this.subjectsMap = subjectsMap;
	}

	/**
	 * Returns the type of message
	 */
	@Override
	public String getType() {
		return "Subject";
	}

}
