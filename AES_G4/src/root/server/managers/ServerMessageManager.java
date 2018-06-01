package root.server.managers;

import root.dao.message.AbstractMessage;

public class ServerMessageManager {
	
	private static ServerMessageManager instance=null;
	
	private ServerMessageManager() {
		
	}
	
	public static ServerMessageManager getInstance() {
		if(instance==null) {
			instance=new ServerMessageManager();
		}
		return instance;
	}
	
	public static AbstractMessage handleMessage(AbstractMessage msg) {
		return null;
	}
}
