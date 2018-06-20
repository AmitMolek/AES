package root.dao.message;

import javafx.scene.Parent;

/**
 * A class that transmits messages between the server and the client with regard
 * to simple message information
 * 
 * @author Omer Haimovich
 *
 */
public class SimpleMessage extends AbstractMessage {

	// CONSTRUCTORS *****************************************************

	/**
	 * Constructs the SimpleMessage
	 * 
	 * @param string
	 *            the message that you want to transmit to the server or client
	 */
	public SimpleMessage(String string) {
		super(string);
	}

	/**
	 * 
	 * Returns the type of message
	 */
	@Override
	public String getType() {
		return "SimpleMessage";
	}

	/**
	 * A method that set the message that you want to transmit to the server or
	 * client
	 * 
	 * @param message
	 *            the message that you want to transmit to the server or client
	 */
	public void setMessage(String message) {
		super.setMsg(message);
	}

}
