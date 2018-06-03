package root.dao.message;


import java.io.Serializable;

import root.dao.app.LoginInfo;
import root.dao.app.User;

public class LoginMessage extends AbstractMessage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LoginInfo user;
	
	public LoginMessage(LoginInfo user) {
		super("login");
		this.user = user;
	}
	public LoginInfo getUser() {
		return user;
	}

	public void setUser(LoginInfo user) {
		
		this.user = user;
	}
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "Login";
	}

}
