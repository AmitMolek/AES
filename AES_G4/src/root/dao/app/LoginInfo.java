package root.dao.app;

import java.io.Serializable;


/**
 *  @author gal & omer
 *  this dao class is the payload of userInfoMessage, after inserting login information, this payload is sent to server
 */

public class LoginInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String userID;
	private String password;
	
	public LoginInfo(String userID, String password)
	{
		this.userID = userID;
		this.password = password;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "userID ="+ userID +"\npassword = "+password;
		
	}
	
	
}
