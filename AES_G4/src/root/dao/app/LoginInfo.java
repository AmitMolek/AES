package root.dao.app;

import java.io.Serializable;


/**
 * this dao class is the payload of userInfoMessage, after inserting login information, this payload is sent to server
 * 
 *  @author gal & omer
 *  
 */
public class LoginInfo implements Serializable{

	// Instance variables **********************************************
	
	private static final long serialVersionUID = 1L;
	/**
	 * The user entered ID
	 */
	private String userID;
	/**
	 * The user entered password
	 */
	private String password;
	
	// CONSTRUCTORS *****************************************************

		/**
		 * Constructs the LoginInfo
		 * 
		 * @param userID
		 *            the id of the user
		 * @param password
		 *            the password of the user
		 */
	public LoginInfo(String userID, String password)
	{
		this.userID = userID;
		this.password = password;
	}
	
	// CLASS METHODS *************************************************

	/**
	 * A method that returns the user id
	 * 
	 * @return the id of the user
	 */
	public String getUserID() {
		return userID;
	}
	
	/**
	 * 
	 * A method that set the id of the user
	 * 
	 * @param userId
	 *            the id of the user
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	/**
	 * A method that returns the user password
	 * 
	 * @return the user password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * 
	 * A method that set the user password
	 * 
	 * @param password
	 *            the user entered password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Prints login info
	 */
	@Override
	public String toString() {
		return "userID ="+ userID +"\npassword = "+password;
		
	}
	
	
}
