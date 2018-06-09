package root.dao.message;

import root.dao.app.User;

public class UserIDMessage extends AbstractMessage {
	private String id;
	
	public UserIDMessage(User user) {
		id=user.getUserID();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getType() {
		return "UserID";
	}
	
	

}
