package root.dao.message;

import java.io.Serializable;

public class LoggedOutMessage extends AbstractMessage implements Serializable{
	
	private static final long serialVersionUID = 1L;
	String user_id;
	
	public LoggedOutMessage(String user_id) {
		super("loggedOut");
		this.user_id = user_id;
	}
	
	public String getUserID() {
		return user_id;
	}
	
	public void setUserID(String user_id) {
		this.user_id = user_id;
	}
	
	@Override
	public String getType() {
		return "loggedOut";
	}
}
