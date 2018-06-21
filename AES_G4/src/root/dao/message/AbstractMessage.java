package root.dao.message;

import java.io.Serializable;

public abstract class AbstractMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	
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
	
	public void setMsg(String msg) {
		this.msg=msg;
	}
	
	//Returns the string message as an array
	public String[] splitMsg() {
		return msg.split("-");
	}
	
	public abstract String getType();
}
