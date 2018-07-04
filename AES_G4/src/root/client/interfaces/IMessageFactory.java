package root.client.interfaces;

import root.dao.message.AbstractMessage;

/**
 * This interface is used to change the MessageFactory
 * @author Amit Molek
 *
 */

public interface IMessageFactory {
	
	public AbstractMessage getMessage(String msg, Object payload);
	
}
