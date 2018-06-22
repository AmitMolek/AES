package root.dao.message;

import java.io.Serializable;
import java.util.ArrayList;

import root.dao.app.Question;
import root.dao.app.Subject;
import root.dao.app.User;


/**
 * 
 * this dao message class used when need to get all teaching subjects of a specific user (Teacher)
 * 
 * @author Gal Brandwine
 * 
 */
public class UserSubjectMessage  extends AbstractMessage implements Serializable{

	// Instance variables **********************************************
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * the subjects to send 
	 */
	private ArrayList<Subject> subjects;
	
	/**
	 * the user whos subjects is needed to fing
	 */
	private User user;

	// CONSTRUCTORS *****************************************************

	/**
	 * Constructs the UserSubjectMessage - client asking from server to retrieve subject relevants to this userID
	 * 
	 * @param user
	 *            The message that you want to transmit to the server or client
	 * 
	 */	
	public UserSubjectMessage(User payload) {
		super("UserSubjects");
		this.user = payload;
	}

	/**
	 * Constructs the UserSubjectMessage - toe send found relevant subject from server back to client
	 * 
	 * @param payload
	 *            The message that you want to transmit to the server or client
	 * 
	 */	
	public UserSubjectMessage(UserSubjectMessage payload) {
		super("UserSubjects");
		this.user = payload.getUser();
		this.subjects = payload.getSubjects();
	}

	/**
	 * Constructs the UserSubjectMessage - to send found relevant subject from server back to client
	 * 
	 * @param payload
	 *            The message that you want to transmit to the server or client
	 * 
	 */		
	public UserSubjectMessage(User user, ArrayList<Subject> subjects) {
		super("UserSubjects");
		this.subjects=subjects;
		this.user = user;
	}

	// CLASS METHODS *************************************************

	/**
	 * A method that returns the user subjects
	 * 
	 * @return the user subjects
	 */
	public ArrayList<Subject> getSubjects() {
		return subjects;
	}
	
	/**
	 * A method that sets the usersubjects
	 * 
	 * @param usersubjects the usersubjects that needed to be saved in THIS
	 */
	public void setSubjects(ArrayList<Subject> subjects) {
		this.subjects = subjects;
	}

	/**
	 * A method that returns the user 
	 * 
	 * @return the user 
	 */
	public User getUser() {
		return user;
	}
	
	/**
	 * A method that sets the user
	 * 
	 * @param usersubjects the user that needed to be saved in THIS
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * 
	 * Returns the type of message
	 */
	@Override
	public String getType() {
		return "UserSubject";
	}

}