package root.dao.message;

import java.io.Serializable;

/**
 * The loggout message, sends the info of the user
 * @author Amit Molek
 *
 */

public class LoggedOutMessage extends AbstractMessage implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * The id of the user
	 */
	String user_id;
	
	/**
	 * Constructor, init the message with the user id, and the message
	 * @param user_id
	 */
	public LoggedOutMessage(String user_id) {
		super("loggedOut");
		this.user_id = user_id;
	}
	
	/**
	 * Returns the id of the user
	 * @return id of the user
	 */
	public String getUserID() {
		return user_id;
	}
	
	/**
	 * Sets the id of the user to user_id
	 * @param user_id the new id of the user
	 */
	public void setUserID(String user_id) {
		this.user_id = user_id;
	}
	
	/**
	 * Returns the type of the message
	 */
	@Override
	public String getType() {
		return "loggedOut";
	}
}
