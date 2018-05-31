package root.dao.message;

import java.util.ArrayList;

import root.dao.app.Question;

public class MessageFactory {
	private static MessageFactory instance=null;
	private MessageFactory(){
		
	}
	
	public static MessageFactory getInstance() {
		if(instance==null) {
			instance=new MessageFactory();
		}
		return instance;
	}
	
	public AbstractMessage getMessage(String msg,Object payload) {
		String[] msgContent=msg.split("-");
		
		if(msgContent[0].equals("ok")) {
			return getOkMessage(msg,payload);
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public AbstractMessage getOkMessage(String msg,Object payload) {
		String[] msgContent=msg.split("-");
		if(msgContent[1].equals("questions")) {
			return new QuestionsMessage((ArrayList<Question>)payload);
		}
		return null;
	}
	

}
