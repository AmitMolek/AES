package root.client.interface_classes;

import root.client.interfaces.IMessageFactory;
import root.dao.message.AbstractMessage;
import root.dao.message.MessageFactory;

/**
 * This class is used to hold the real message factory when running
 * @author Amit Molek
 *
 */

public class RealMessageFactory implements IMessageFactory{

	MessageFactory factory;
	
	public RealMessageFactory(MessageFactory factory) {
		this.factory = factory;
	}
	
	@Override
	public AbstractMessage getMessage(String msg, Object payload) {
		return factory.getMessage(msg, payload);
	}

	public void setMessageFactory(MessageFactory factory) {
		this.factory = factory;
	}
	
	public MessageFactory getMessageFactory() {
		return factory;
	}
	
}
