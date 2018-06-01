package root.dao.app;

public class User {
private String userID;
private String userFirstName;
private String userLastName;
private String userPassword;
private Integer userPremission;

public User(String userID, String userFirstName, String userLastName, String userPassword, Integer userPremission) {
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

public Integer getUserPremission() {
	return userPremission;
}

public void setUserPremission(Integer userPremission) {
	this.userPremission = userPremission;
}



}
