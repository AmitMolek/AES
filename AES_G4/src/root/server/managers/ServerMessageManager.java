package root.server.managers;

import java.util.ArrayList;

import root.dao.app.LoginInfo;
import root.dao.app.Question;
import root.dao.app.Subject;
import root.dao.app.User;
import root.dao.message.AbstractMessage;
import root.dao.message.ErrorMessage;
import root.dao.message.LoginMessage;
import root.dao.message.MessageFactory;
import root.dao.message.QuestionsMessage;
import root.dao.message.UserMessage;
import root.dao.message.UserSubjectMessage;
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
		case "usersubjects":
			return handleUserSubjectsMessage(msg);
		case "questions":
			return handleQuestionsMassage(msg);
		default:
			return null;
		}
	}
	
	/**
	 * @author gal
	 * @param msg type of QuestionMessage which contain the string "Questions" ans the subject of the questions as payload
	 * @return	{@link AbstractMessage} of QuestionMessage filled with question from the same subject
	 */
	private static AbstractMessage handleQuestionsMassage(AbstractMessage msg) {
		// TODO Auto-generated method stub
		QuestionsMessage questionMessage = (QuestionsMessage)msg;
		GetFromDB getQuestions = new GetFromDB();
		ArrayList<Question> questions = getQuestions.questions(questionMessage.getThisQuestionsSubject().getSubjectID());
		questionMessage.setQuestions(questions);	
		questionMessage.setThisQuestionsSubject(questionMessage.getThisQuestionsSubject());
		
		if (questions.size() ==0) return message.getMessage("error-Qeustions",new Exception("No Questions in this subject"));	// return Exception
		else if (questions.size() >= 1) return message.getOkGetMessage("ok-get-questions".split("-"),questionMessage);	// found questions for this subject, return them
		return message.getMessage("error-Qesutions",new Exception("Error in finding Qesutions"));
	}

	/**
	 * @author gal
	 * @param msg type of UserSubjectMessage wich contain string "UserSubjects" and User payload
	 * @return AbstrackMessage of userSubjects filled with speficic user subjects.
	 */
	private static AbstractMessage handleUserSubjectsMessage(AbstractMessage msg) {
		UserSubjectMessage userSubjects = (UserSubjectMessage)msg;
		GetFromDB getUserSubjects = new GetFromDB();
		ArrayList<Subject> subjects = getUserSubjects.subjects(userSubjects.getUser().getUserID());
		userSubjects.setSubjects(subjects);											
		if (subjects.size() ==0) return message.getMessage("error-userSubjects",new Exception("No subjects for the User"));	// this user has now teaching subject, return Exception
		else if (subjects.size() >= 1) return message.getOkGetMessage("ok-get-usersubjects".split("-"),userSubjects);	// this user has teaching subjects, return his HIS subjects
		return message.getMessage("error-userSubjects",new Exception("Error in finding userSubjects"));
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
}