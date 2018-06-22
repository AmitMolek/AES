package root.dao.message;


import java.io.Serializable;

import root.dao.app.LoginInfo;
import root.dao.app.User;
/**
 * 
 * this dao message class used when user tries to loggin.
 * the message with login info sent to server, and if information entered is correct, than recieve relevant user.
 * 
 * @author Gal Brandwine
 *  
 */
public class LoginMessage extends AbstractMessage implements Serializable{
	
	// Instance variables **********************************************
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * ths user entered login information that needs to be sent to server
	 */
	private LoginInfo user;

	// CONSTRUCTORS *****************************************************

	/**
	 * Constructs the LoginMessage
	 * 
	 * @param user
	 *            The message that you want to transmit to the server or client
	 * 
	 */
	public LoginMessage(LoginInfo user) {
		super("login");
		this.user = user;
	}

	// CLASS METHODS *************************************************

	/**
	 * A method that returns the user
	 * 
	 * @return the user
	 */
	public LoginInfo getUser() {
		return user;
	}
	
	/**
	 * A method that sets the user
	 * @param user the user that needed to be saved in THIS
	 */
	public void setUser(LoginInfo user) {
		
		this.user = user;
	}

	/**
	 * 
	 * Returns the type of message
	 */
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "Login";
	}

}
