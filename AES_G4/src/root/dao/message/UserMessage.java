package root.dao.message;

import java.io.Serializable;

import root.dao.app.User;

public class UserMessage extends AbstractMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;
	
	public UserMessage(User user) {
		super("User");
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String getType() {
		return "User";
	}

}
