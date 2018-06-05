package root.server.managers;

import java.util.ArrayList;

import root.dao.app.LoginInfo;
import root.dao.app.Subject;
import root.dao.app.User;
import root.dao.message.AbstractMessage;
import root.dao.message.ErrorMessage;
import root.dao.message.LoginMessage;
import root.dao.message.MessageFactory;
import root.dao.message.SubjectMessage;
import root.dao.message.UserMessage;
import root.server.managers.dbmgr.GetFromDB;

public class ServerMessageManager {
	
	private static ServerMessageManager instance=null;
	 private static MessageFactory message = MessageFactory.getInstance();;
	private ServerMessageManager() {
		
	}
	
	public static ServerMessageManager getInstance() {
		if(instance==null) {
			instance=new ServerMessageManager();
		}
		return instance;
	}
	
	public static AbstractMessage handleMessage(AbstractMessage msg) {
		String[] msgContent = msg.getMsg().toLowerCase().split("-");
		switch(msgContent[0]) {
		case "login":
			return handleLoginMessage(msg);	
		case "get":
			return handleGetMessage(msg);
		}
		return null;
	}
	/**
	 * 
	 * @param msg type of LoginMessage which contain string, and loginInfo payload.
	 * @return AbstrackMessage with required information.
	 */
	private static AbstractMessage handleLoginMessage(AbstractMessage msg) {
		LoginMessage login = (LoginMessage) msg;
		GetFromDB getLogin = new GetFromDB();
		ArrayList<User> users = getLogin.users(login.getUser().getUserID());
		LoginInfo loginInformation = login.getUser();
		for(User user: users) {
			if (user.getUserID().equals(loginInformation.getUserID())) {
				if (user.getUserPassword().equals(loginInformation.getPassword())) {
					return message.getMessage("ok-login",user);
				}
				else {
					return message.getMessage("error-login",new Exception("Wrong Password"));
				}
			}
		}
		return message.getMessage("error-login",new Exception("User not exist"));
	}
	
	/**
	 * 
	 * @param msg type of get message
	 * @return new message for client
	 */
	private static AbstractMessage handleGetMessage(AbstractMessage msg) {
		String[] msgContent = msg.getMsg().toLowerCase().split("-");
		switch(msgContent[1]) {
		case "subjects":
			return handleSubjectMessage(msg);
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param msg type of subject message
	 * @return the subject message that includes the subect list
	 */
	private static AbstractMessage handleSubjectMessage(AbstractMessage msg) {
		SubjectMessage recivedMessage = (SubjectMessage) msg;
		String teacherId = recivedMessage.getTeacherId();
		GetFromDB getSubject = new GetFromDB();
		ArrayList<Subject> subjects = getSubject.subjects(teacherId);
		return message.getMessage("ok-get-subjects", subjects);
	}
}