package root.dao.app;

import java.io.Serializable;
/**
 * this dao class contain user information
 * 
 * @author gal & omer
 *
 */
public class User implements Serializable{

	// Instance variables **********************************************

	/**
	 * The id of the User
	 */
	private static final long serialVersionUID = 1L;
	private String userID;
	
	/**
	 * The user first name
	 */
	private String userFirstName;
	
	/**
	 * The user last name
	 */
	private String userLastName;
	
	/**
	 * The user password
	 */
	private String userPassword;
	
	/**
	 * The user premission
	 */
	private String userPremission;

	// CONSTRUCTORS *****************************************************

	/**
	 *  Constructs the User
	 *  
	 * @param userID
	 * 				the user ID
	 * @param userFirstName
	 * 				the user first name
	 * @param userLastName
	 * 				the user last name
	 * @param userPassword
	 * 				the user password
	 * @param userPremission
	 * 				the user premission
	 */
	public User(String userID, String userFirstName, String userLastName, String userPassword, String userPremission) {
		super();
		this.userID = userID;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.userPassword = userPassword;
		this.userPremission = userPremission;
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
	 * A method that sets the user id
	 * 
	 * @param userID the id of the user
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}

	/**
	 * A method that returns the user first name
	 * 
	 * @return the user first name
	 */
	public String getUserFirstName() {
		return userFirstName;
	}
	
	/**
	 * 
	 * A method that set the user first name
	 * 
	 * @param userFirstName
	 *            the user first name
	 */
	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	/**
	 * A method that returns the user last name
	 * 
	 * @return the user user last name
	 */
	public String getUserLastName() {
		return userLastName;
	}
	
	/**
	 * 
	 * A method that set the user last name
	 * 
	 * @param userlastName
	 *            the user last name
	 */
	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	/**
	 * A method that returns the user password
	 * 
	 * @return the user first name
	 */
	public String getUserPassword() {
		return userPassword;
	}
	
	/**
	 * 
	 * A method that set the user password
	 * 
	 * @param userPassword
	 *            the user password
	 */
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	/**
	 * A method that returns the user premission
	 * 
	 * @return the user premission
	 */
	public String getUserPremission() {
		return userPremission;
	}
	
	/**
	 * 
	 * A method that set the user premission
	 * 
	 * @param userpremission
	 *            the user premission
	 */
	public void setUserPremission(String userPremission) {
		this.userPremission = userPremission;
	}

	/**
	 * Prints courses info
	 */
	@Override
	public String toString() {
		return "userID = "+userID+";\r\n" + 
				"userFirstName = "+userFirstName+";\r\n" + 
				"userLastName = "+userLastName; 
	}

}
