package root.dao.message;

import java.io.Serializable;

public abstract class AbstractMessage implements Serializable {
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
	
	public abstract String getType();
}
