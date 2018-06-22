package root.dao.message;

import java.io.Serializable;
/**
 * 
 * @author Alon Ben-yosef
 * This class is being used to communicate with the server and all messages withing the scope of the system are
 * required to inherit for AbstarctMessage, messages should be generated with the MessageFactory class and not directly
 * by calling their constructor.
 */
public abstract class AbstractMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Msg is the content of the message, always a "-" delimited string, with communications protocol described in group 4
	 * google drive.
	 */
	private String msg;
	
	/**
	 * Creates an empty message, should be avoided.
	 */
	public AbstractMessage() {
		this.msg="";
	}
	/**
	 * Creates a message with msg as content
	 * @param msg to create
	 */
	public AbstractMessage(String msg) {
		this.msg=msg;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg=msg;
	}
	
	/**
	 * Returns the string message as an array
	 * @return a string array with each element representing a word from the message
	 */
	public String[] splitMsg() {
		return msg.split("-");
	}
	
	public abstract String getType();
}
