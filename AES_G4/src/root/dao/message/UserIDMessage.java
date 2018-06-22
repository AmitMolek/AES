package root.dao.message;

import root.dao.app.User;

/**
 * this dao message class used when theres a need to get a user from DB
 * 
 * @author gal brandwine
 * 
 */
public class UserIDMessage extends AbstractMessage {

	// Instance variables **********************************************

	/**
	 * ths user id
	 */
	private String id;

	// CONSTRUCTORS *****************************************************

	/**
	 * Constructs the UserIDMessage
	 * 
	 * @param user
	 *            The user that you want to get its ID
	 * 
	 */
	public UserIDMessage(User user) {
		id=user.getUserID();
	}

	// CLASS METHODS *************************************************

	/**
	 * A method that returns the userid
	 * 
	 * @return the id
	 */	
	public String getId() {
		return id;
	}
	
	/**
	 * A method that sets the userid
	 * @param id the userid that needed to be saved in THIS
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 
	 * Returns the type of message
	 */
	@Override
	public String getType() {
		return "UserID";
	}

}
