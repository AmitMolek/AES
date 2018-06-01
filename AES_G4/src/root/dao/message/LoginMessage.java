package root.dao.message;


import root.dao.app.User;

public class LoginMessage extends AbstractMessage {
	private User user;
	
	public LoginMessage(User user) {
		super("ok-login");
		this.user = user;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "Login";
	}

}
