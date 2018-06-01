package root.dao.message;

import root.dao.app.LoginInfo;

public class LoginMessage extends AbstractMessage {
	private LoginInfo lgInfo;
	
	public LoginMessage(String msg, LoginInfo lgInfo) {
		super(msg);
		this.lgInfo = lgInfo;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "Login";
	}

}
