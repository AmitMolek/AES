package root.dao.message;

import root.dao.app.User;

public class UserMessage extends AbstractMessage {

	private User user;
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserMessage(User user) {
		this.user = user;
	}

	@Override
	public String getType() {
		return "User";
	}

}
