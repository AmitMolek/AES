package root.dao.message;

import java.util.ArrayList;

import root.dao.app.Exam;
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
		String[] msgContent=msg.toLowerCase().split("-");
		switch(msgContent[0]) {
		case "ok":
			return getOkMessage(msgContent,payload);
		case "login":
			return getLoginMessage(msgContent,payload);
		case "get":
			return getGetMessage(msgContent,payload);
		case "set":
			return getSetMessage(msgContent,payload);
		case "put":
			return getPutMessage(msgContent,payload);
		case "delete":
			return getDelMessage(msgContent,payload);
		case "error":
			return getErrorMessage(msgContent,payload);
		}
		return new ErrorMessage(new Exception("Invalid request"));
	}
	
	private AbstractMessage getErrorMessage(String[] msgContent, Object payload) {
		return null;
		
		// TODO Auto-generated method stub
		
	}

	private AbstractMessage getDelMessage(String[] msgContent, Object payload) {
		return null;
		// TODO Auto-generated method stub
		
	}

	private AbstractMessage getPutMessage(String[] msgContent, Object payload) {
		return null;
		// TODO Auto-generated method stub
		
	}

	private AbstractMessage getSetMessage(String[] msgContent, Object payload) {
		return null;
		// TODO Auto-generated method stub
		
	}

	private AbstractMessage getGetMessage(String[] msgContent, Object payload) {
		return null;
		// TODO Auto-generated method stub
		
	}

	private AbstractMessage getLoginMessage(String[] msgContent, Object payload) {
		// TODO Auto-generated method stub
		return null;
		
	}

	@SuppressWarnings("unchecked")
	public AbstractMessage getOkMessage(String[] msgContent,Object payload) {
		switch(msgContent[1]) {
		case "login":
			return new UserMessage(payload);
		case "get":
			return getGetMessage(msgContent,payload);
		case "set":
			return new SimpleMessage("ok-set-"+msgContent[2]);
		case "put":
			return new SimpleMessage("ok-put-"+msgContent[2]);
		case "delete":
			return new SimpleMessage("ok-delete-"+msgContent[2]);
		}
		return new ErrorMessage(new Exception("Invalid request"));
	}
	
	public AbstractMessage getOkGetMessage(String[] msgContent,Object payload)
	{
		switch(msgContent[2]) {
		case "questions":
			if(payload instanceof ArrayList<?>)
				return new QuestionsMessage((ArrayList<Question>) payload);
			else return new ErrorMessage(new Exception("Your payload is not arraylist"));
		case "exams":
			if(payload instanceof ArrayList<?>)
				return new ExamMessage((ArrayList<Exam>) payload);
			else return new ErrorMessage(new Exception("Your payload is not arraylist"));
		}
		return new ErrorMessage(new Exception("Invalid request"));
	}
	

}
