package root.dao.message;

import java.io.Serializable;

import root.dao.app.User;


/**
 * 
 * 
 * this dao message class used when theres a need to get a user from DB
 * 
 * @author gal brandwine
 * 
 */
public class UserMessage extends AbstractMessage implements Serializable {

	// Instance variables **********************************************
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * ths user
	 */
	private User user;
	
	// CONSTRUCTORS *****************************************************

	/**
	 * Constructs the UserMessage
	 * 
	 * @param user
	 *            The user that you want to transmit to the server or client
	 * 
	 */
	public UserMessage(User user) {
		super("User");
		this.user = user;
	}

	// CLASS METHODS *************************************************

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
	 * @param user the user that needed to be saved in THIS
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
		return "User";
	}

}
