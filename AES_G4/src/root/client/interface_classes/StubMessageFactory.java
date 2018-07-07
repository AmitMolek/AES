package root.client.interface_classes;

import root.client.interfaces.IMessageFactory;
import root.dao.app.LoginInfo;
import root.dao.message.AbstractMessage;
import root.dao.message.LoginMessage;

/**
 * This is a stub class used to replace the message factory
 * @author Amit Molek
 *
 */

public class StubMessageFactory implements IMessageFactory{
	
	@Override
	public AbstractMessage getMessage(String msg, Object payload) {
		LoginInfo info = (LoginInfo) payload;
		return new LoginMessage(info);
	}

}
