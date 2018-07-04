package root.client.mocks;

import root.client.interfaces.IMock;

public class SignInMock implements IMock{

	String msg;
	
	public SignInMock() {
		this.msg = "Default msg";
	}
	
	public SignInMock(String msg) {
		this.msg = msg;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void LogMsg(String msg) {
		this.msg = msg;
	}
	
}
