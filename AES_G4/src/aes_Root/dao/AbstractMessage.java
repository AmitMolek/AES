package aes_Root.dao;

public abstract class AbstractMessage {
	private String msg;
	
	public AbstractMessage() {
		this.msg="";
	}
	
	public AbstractMessage(String msg) {
		this.msg=msg;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public String setMsg() {
		return msg;
	}
	
	//Returns the string message as an array
	public String[] splitMsg() {
		return msg.split("-");
	}
}
