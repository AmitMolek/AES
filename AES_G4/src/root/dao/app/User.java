package root.dao.app;

import java.io.Serializable;

public class User implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String userID;
private String userFirstName;
private String userLastName;
private String userPassword;
private String userPremission;

public User(String userID, String userFirstName, String userLastName, String userPassword, String userPremission) {
	super();
	this.userID = userID;
	this.userFirstName = userFirstName;
	this.userLastName = userLastName;
	this.userPassword = userPassword;
	this.userPremission = userPremission;
}

public String getUserID() {
	return userID;
}

public void setUserID(String userID) {
	this.userID = userID;
}

public String getUserFirstName() {
	return userFirstName;
}

public void setUserFirstName(String userFirstName) {
	this.userFirstName = userFirstName;
}

public String getUserLastName() {
	return userLastName;
}

public void setUserLastName(String userLastName) {
	this.userLastName = userLastName;
}

public String getUserPassword() {
	return userPassword;
}

public void setUserPassword(String userPassword) {
	this.userPassword = userPassword;
}

public String getUserPremission() {
	return userPremission;
}

public void setUserPremission(String userPremission) {
	this.userPremission = userPremission;
}


@Override
public String toString() {
	return "userID = "+userID+";\r\n" + 
			"userFirstName = "+userFirstName+";\r\n" + 
			"userLastName = "+userLastName+";\r\n" + 
			"userPassword = "+userPassword+";\r\n" + 
			"userPremission = "+userPremission;
}

}
