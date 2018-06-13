package root.server.managers;

import java.util.ArrayList;
import java.util.HashMap;


//import root.client.controllers.TestGradesTeacherController;
import root.dao.app.Course;
import root.dao.app.Exam;
import root.dao.app.LoginInfo;
import root.dao.app.Question;
import root.dao.app.QuestionInExam;
import root.dao.app.Subject;
import root.dao.app.User;
import root.dao.app.UserInfo;
import root.dao.message.AbstractMessage;
import root.dao.message.CourseMessage;
import root.dao.message.ErrorMessage;
import root.dao.message.ExamMessage;
import root.dao.message.LoggedOutMessage;
import root.dao.message.LoginMessage;
import root.dao.message.MessageFactory;
import root.dao.message.QuestionInExamMessage;
import root.dao.message.QuestionsMessage;
import root.dao.message.SimpleMessage;
import root.dao.message.SubjectMessage;
import root.dao.message.UserInfoMessage;
import root.dao.message.UserIDMessage;
import root.dao.message.UserMessage;
import root.dao.message.UserSubjectMessage;
import root.server.managers.dbmgr.GetFromDB;
import root.server.managers.dbmgr.SetInDB;

public class ServerMessageManager {
	
	private static ServerMessageManager instance=null;
	 private static MessageFactory message = MessageFactory.getInstance();;
	 
	 private static LoggedInUsersManager usersManager = LoggedInUsersManager.getInstance();
	 
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
			return handleQuestionsMessage(msg);
		case "get":
			return handleGetMessage(msg);
		case "set":															// update an already existing data
			return handleSetMessage(msg);
		case "put":															// insert newData
			return handlePutMessage(msg);
		case "delete":
			return handleDeleteMessage(msg);
		case "loggedout":
			return handleLoggedOutMessage(msg);
		default:
			return null;
		}
	}
	
	/**
	 * Handle the logging out users
	 * @param msg the LoggedOutMessage
	 * @return if the user removed returns ok-loggedOut, else error-loggedOut
	 */
//<<<<<<< HEAD
//	private static AbstractMessage handleSetQuestionMessage(AbstractMessage msg) {
//		// TODO Auto-generated method stub
//		QuestionsMessage questionMessage = (QuestionsMessage)msg;
//		Question newQuestionTooAdd = questionMessage.getQuestions().get(0);
//		SetInDB updateExistingQuestion = new SetInDB();
//		AbstractMessage sendMessage = (AbstractMessage) updateExistingQuestion.updateExistingQuestion(newQuestionTooAdd);
//		return sendMessage;
//	}
//
//	/**
//	 * @author gal
//	 * @param msg type of QuestionMessage which contain the string "Questions" ans the subject of the questions as payload
//	 * @return	{@link AbstractMessage} of QuestionMessage filled with question from the same subject
//	 */
//	private static AbstractMessage handleQuestionsMessage(AbstractMessage msg) {
//		// TODO Auto-generated method stub
//		QuestionsMessage questionMessage = (QuestionsMessage)msg;
//		GetFromDB getQuestions = new GetFromDB();
//		ArrayList<Question> questions = getQuestions.questions(questionMessage.getThisQuestionsSubject().getSubjectID());
//		
//		if (questions.size() ==0) return message.getMessage("error-Qeustions",new Exception("No Questions in this subject"));	// return Exception
//		else if (questions.size() >= 1) return message.getOkGetMessage("ok-get-questions".split("-"),questions);	// found questions for this subject, return them
//		return message.getMessage("error-Qesutions",new Exception("Error in finding Qesutions"));
//	}
//
//	/**
//	 * @author gal
//	 * @param msg type of UserSubjectMessage wich contain string "UserSubjects" and User payload
//	 * @return AbstrackMessage of userSubjects filled with speficic user subjects.
//	 */
//	private static AbstractMessage handleUserSubjectsMessage(AbstractMessage msg) {
//		UserSubjectMessage userSubjects = (UserSubjectMessage)msg;
//		GetFromDB getUserSubjects = new GetFromDB();
//		ArrayList<Subject> subjects = getUserSubjects.subjects(userSubjects.getUser().getUserID());
//		userSubjects.setSubjects(subjects);											
//		if (subjects.size() ==0) return message.getMessage("error-userSubjects",new Exception("No subjects for the User"));	// this user has now teaching subject, return Exception
//		else if (subjects.size() >= 1) return message.getOkGetMessage("ok-get-usersubjects".split("-"),userSubjects);	// this user has teaching subjects, return his HIS subjects
//		return message.getMessage("error-userSubjects",new Exception("Error in finding userSubjects"));
//	}
//
//
//	 /* 
//	 * @param msg type of get message
//	 * @return new message for client
//	 */
//	private static AbstractMessage handleGetMessage(AbstractMessage msg) {
//		String[] msgContent = msg.getMsg().toLowerCase().split("-");
//		switch(msgContent[1]) {
//			case "subjects":
//				return handleSubjectMessage(msg);
//			case "courses":
//				return handleCourseMessage(msg);
//			case "questions":
//				return handleQuestionsMessage(msg);
//			case "exams":
//				return handleGetExamMessage(msg);
//			case "user":
//				return handleFetUserMessage(msgContent,msg);
//			case "solvedExamByTeacherId":
//				return handleGetExamByTeacherID(msg);
//		}
//		
//		return null;
//	}
//	/**
//	 * this method is called when a client need to get information about users
//	 * @param msgContent 
//	 * @param msg contain UserInfo 
//	 * @return {@link AbstractMessage} with required information
//	 */
//	private static AbstractMessage handleFetUserMessage(String[] msgContent, AbstractMessage msg) {
//		switch(msgContent[2]) {
//		case "name":
//			return getUserName(msg);
//		}
//		return null;
//	}
//
//	private static AbstractMessage getUserName(AbstractMessage msg) {
//		UserInfoMessage userInfoMessage = (UserInfoMessage)msg;
//		UserInfo userInfo = userInfoMessage.getUserInfo();
//		GetFromDB getUserName = new GetFromDB();
//		HashMap<String, String> usersMap = userInfo.getTeachersMap();
//		ArrayList<User> users = getUserName.users();			// get all users
//		for (User user: users) {
//			if (usersMap.containsKey(user.getUserID())){
//				usersMap.put(user.getUserID(),user.getUserFirstName()+" "+user.getUserLastName());
//			}
//		}
//		//UserInfo UserInfo = new UserInfo(usersMap, null);
//		return message.getMessage("ok-get-users",usersMap);
//	}
///***
// * @author Alon Ben-yosef
// * @param msg of UserIDMessage type expected
// * @return An exam message containing an arraylist of statistics assembled by teacherID
// */
//	private static AbstractMessage handleGetExamByTeacherID(AbstractMessage msg) {
//		//TODO:Convert getFromDB to singleton
//		UserIDMessage idMessage = (UserIDMessage) msg;
//		MessageFactory factory=MessageFactory.getInstance();
//		GetFromDB getExams = new GetFromDB();
//		ArrayList<Statistic> examList = getExams.getExamStatsByTeacherID(idMessage.getId());//TODO:Make a query in getManager to handle getting all the exams assembled by a single teacher
//		ExamMessage message=(ExamMessage) factory.getMessage("ok-get-solvedExamByTeacherId", examList);
//		return message;
//	}
//
//	/**
//	 * 
//	 * @param msg type of LoginMessage which contain string, and loginInfo payload.
//	 * @return AbstrackMessage with required information.
//	 */
//	private static AbstractMessage handleLoginMessage(AbstractMessage msg) {
//		LoginMessage login = (LoginMessage) msg;
//		GetFromDB getLogin = new GetFromDB();
//		ArrayList<User> users = getLogin.users(login.getUser().getUserID());
//		LoginInfo loginInformation = login.getUser();
//		for(User user: users) {
//			if (user.getUserID().equals(loginInformation.getUserID())) {
//				if (user.getUserPassword().equals(loginInformation.getPassword())) {
//					return message.getMessage("ok-login",user);
//				}
//				else {
//					return message.getMessage("error-login",new Exception("Wrong Password"));
//				}
//			}
//		}
//		return message.getMessage("error-login",new Exception("User not exist"));
//	}
//	
//
//	/**
//	 * 
//	 * @param msg type of subject message
//	 * @return the subject message that includes the subject list
//	 */
//	private static AbstractMessage handleSubjectMessage(AbstractMessage msg) {
//
//		SubjectMessage recivedMessage = (SubjectMessage) msg;
//		String teacherId = recivedMessage.getTeacherId();
//		GetFromDB getSubject = new GetFromDB();
//		ArrayList<Subject> subjects = getSubject.subjects(teacherId);
//		return message.getMessage("ok-get-subjects", subjects);
//	}
//	
//	/**
//	 * 
//	 * @param msg type of course message
//	 * @return the course message that includes the course list
//	 */
//	private static AbstractMessage handleCourseMessage(AbstractMessage msg) {
//		CourseMessage recivedMessage = (CourseMessage) msg;
//		String subjectId = recivedMessage.getCourseSubject().getSubjectID();
//		GetFromDB getCourse = new GetFromDB();
//		ArrayList<Course> courses = getCourse.coursesInSubject(subjectId);
//		return message.getMessage("ok-get-courses", courses);
//	}
//	
//	private static AbstractMessage handlePutMessage(AbstractMessage msg) {
//		String[] msgContent = msg.getMsg().toLowerCase().split("-");
//		switch(msgContent[1]) {
//		case "questions":
//			return handlePutQuestion(msg);
//		case "exams":
//			return handlePutExamMessage(msg);
//		case "questioninexam":
//			return handlePutQuestionInExamMessage(msg);
//		
//		}
//		
//		return null;
//		
//	}
//	
//	private static AbstractMessage handlePutQuestion(AbstractMessage msg) {
//		// TODO Auto-generated method stub
//		QuestionsMessage recievedNewQuestion = (QuestionsMessage)msg;
//		Question newQuestionTooAdd = recievedNewQuestion.getQuestions().get(0); // ArrayList<Question> will contain only 1 new question
//		SetInDB putQuestion = new SetInDB();
//		AbstractMessage sendMessage = (AbstractMessage) putQuestion.AddNewQuestion(newQuestionTooAdd);
//		return null;
//	}
//
//	private static AbstractMessage handlePutExamMessage(AbstractMessage msg) {
//		ExamMessage recivedMessage = (ExamMessage)msg;
//		Exam addExam = recivedMessage.getNewExam();
//		SetInDB putExam = new SetInDB();
//		AbstractMessage sendMessage = (AbstractMessage) putExam.AddExam(addExam);
//		return sendMessage;
//	}
//	
//	private static AbstractMessage handleGetExamMessage(AbstractMessage msg) {
//		if(msg instanceof SimpleMessage)
//		{
//			SimpleMessage recievedMessage = (SimpleMessage) msg;
//			String strMsg =recievedMessage.getMsg();
//			String[] msgContent = msg.getMsg().toLowerCase().split("-");
//			if(msgContent[2].equals("pass")) {
//				GetFromDB getExam = new GetFromDB();
//				ArrayList<Exam> exams= getExam.getExamByPassword(msgContent[3]);
//				if(exams!=null)
//					return message.getMessage("ok-get-exams", exams);
//				else
//					return new ErrorMessage(new NullPointerException("Exam not found"));
//			}
//			
//		}
//		else {
//			ExamMessage recivedMessage = (ExamMessage) msg;
//			String examId = recivedMessage.getId();
//			GetFromDB getExam = new GetFromDB();
//			ArrayList<Exam> exams = getExam.exams(examId);
//			return message.getMessage("ok-get-exams", exams);
//
//		}
//		return null;
//	}
//	
//	private static AbstractMessage handleDeleteMessage(AbstractMessage msg) {
//		String[] msgContent = msg.getMsg().toLowerCase().split("-");
//		switch(msgContent[1]) {
//		case "exams":
//			return handleDeleteExamMessage(msg);
//		case "questions":
//			return handleDeleteQuestionMessage(msg);
//		}
//		return null;
//	}
//	
//	private static AbstractMessage handleDeleteQuestionMessage(AbstractMessage msg) {
//		QuestionsMessage recievedMessage = (QuestionsMessage)msg;
//		Question deleteQuestion = recievedMessage.getQuestions().get(0);
//		SetInDB deletesQuestion = new SetInDB();
//		AbstractMessage sendMessage = (AbstractMessage) deletesQuestion.deleteTheQuestion(deleteQuestion);
//		return sendMessage;
//	}
//
//	private static AbstractMessage handleDeleteExamMessage(AbstractMessage msg) {
//		ExamMessage recivedMessage = (ExamMessage)msg;
//		Exam deleteExam = recivedMessage.getNewExam();
//		SetInDB deletesExam = new SetInDB();
//		AbstractMessage sendMessage = (AbstractMessage) deletesExam.deleteTheExam(deleteExam);
//		return sendMessage;
//	}
//	
//	private static AbstractMessage handlePutQuestionInExamMessage(AbstractMessage msg) {
//		QuestionInExamMessage recivedMessage = (QuestionInExamMessage)msg;
//		String id = recivedMessage.getExamId();
//		ArrayList<QuestionInExam> examQuestions = recivedMessage.getQuestionInExam();
//		SetInDB putExam = new SetInDB();
//		AbstractMessage sendMessage = (AbstractMessage) putExam.addQuestionToExam(id, examQuestions);
//		return sendMessage;
//	}
//	
//=======
	private static AbstractMessage handleLoggedOutMessage(AbstractMessage msg) {
		LoggedOutMessage loggedMsg = (LoggedOutMessage) msg;
		String user_id = loggedMsg.getUserID();
		String error_msg = "error-loggedout";
		
		if (user_id == null) {
			return message.getMessage(error_msg, new Exception("User id is null"));
		}
		
		if (usersManager.isUserLoggedIn(user_id)) {
			if (usersManager.removeLoggedInUser(user_id)) {
				return message.getMessage("ok-loggedOut", user_id);
			}else {
				return message.getMessage(error_msg, new Exception("Failed removing logged in user"));
			}
		}else {
			return message.getMessage(error_msg, new Exception("User not logged in"));
		}
	}
	
	private static AbstractMessage handleSetMessage(AbstractMessage msg) {	// when wanting to change data in the DB change existing data
		String[] msgContent = msg.getMsg().toLowerCase().split("-");
		switch(msgContent[1]) {
		case "questions":
			return handleSetQuestionMessage(msg);
		}
		return null;
	}
	/**
	 * @author gal
	 * this method called when updating an existing question in DB
	 * @param msg
	 * @return
	 */
	private static AbstractMessage handleSetQuestionMessage(AbstractMessage msg) {
		// TODO Auto-generated method stub
		QuestionsMessage questionMessage = (QuestionsMessage)msg;
		Question newQuestionTooAdd = questionMessage.getQuestions().get(0);
		SetInDB updateExistingQuestion = new SetInDB();
		AbstractMessage sendMessage = (AbstractMessage) updateExistingQuestion.updateExistingQuestion(newQuestionTooAdd);
		return sendMessage;
	}

	/**
	 * @author gal
	 * @param msg type of QuestionMessage which contain the string "Questions" ans the subject of the questions as payload
	 * @return	{@link AbstractMessage} of QuestionMessage filled with question from the same subject
	 */
	private static AbstractMessage handleQuestionsMessage(AbstractMessage msg) {
		// TODO Auto-generated method stub
		QuestionsMessage questionMessage = (QuestionsMessage)msg;
		GetFromDB getQuestions = new GetFromDB();
		ArrayList<Question> questions = getQuestions.questions(questionMessage.getThisQuestionsSubject().getSubjectID());
		
		if (questions.size() ==0) return message.getMessage("error-Qeustions",new Exception("No Questions in this subject"));	// return Exception
		else if (questions.size() >= 1) return message.getOkGetMessage("ok-get-questions".split("-"),questions);	// found questions for this subject, return them
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


	 /* 
	 * @param msg type of get message
	 * @return new message for client
	 */
	private static AbstractMessage handleGetMessage(AbstractMessage msg) {
		String[] msgContent = msg.getMsg().toLowerCase().split("-");
		switch(msgContent[1]) {
			case "subjects":
				return handleSubjectMessage(msg);
			case "courses":
				return handleCourseMessage(msg);
			case "questions":
				return handleQuestionsMessage(msg);
			case "exams":
				return handleGetExamMessage(msg);
			case "user":
				return handleFetUserMessage(msgContent,msg);
			case "solvedExamByTeacherId":
				return handleGetExamByTeacherID(msg);
		}
		
		return null;
	}
	/**
	 * this method is called when a client need to get information about users
	 * @param msgContent 
	 * @param msg contain UserInfo 
	 * @return {@link AbstractMessage} with required information
	 */
	private static AbstractMessage handleFetUserMessage(String[] msgContent, AbstractMessage msg) {
		switch(msgContent[2]) {
		case "name":
			return getUserName(msg);
		}
		return null;
	}

	private static AbstractMessage getUserName(AbstractMessage msg) {
		UserInfoMessage userInfoMessage = (UserInfoMessage)msg;
		UserInfo userInfo = userInfoMessage.getUserInfo();
		GetFromDB getUserName = new GetFromDB();
		HashMap<String, String> usersMap = userInfo.getTeachersMap();
		ArrayList<User> users = getUserName.users();			// get all users
		for (User user: users) {
			if (usersMap.containsKey(user.getUserID())){
				usersMap.put(user.getUserID(),user.getUserFirstName()+" "+user.getUserLastName());
			}
		}
		return message.getMessage("ok-get-users",usersMap);
	}
/***
 * @author Alon Ben-yosef
 * @param msg of UserIDMessage type expected
 * @return An exam message containing an arraylist of exams assembled by teacherID
 */
	private static AbstractMessage handleGetExamByTeacherID(AbstractMessage msg) {
		//TODO:Convert getFromDB to singleton
		UserIDMessage idMessage = (UserIDMessage) msg;
		MessageFactory factory=MessageFactory.getInstance();
		GetFromDB getExams = new GetFromDB();
		ArrayList<Exam> examList = getExams.exams();//TODO:Make a query in getManager to handle getting all the exams assembled by a single teacher
		ExamMessage message=(ExamMessage) factory.getMessage("ok-get-solvedExamByTeacherId", examList);
		return message;
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
					if (!usersManager.isUserLoggedIn(user.getUserID())) {
						usersManager.addLoggedInUser(user.getUserID());
						return message.getMessage("ok-login",user);
					}else {
						return message.getMessage("error-login",new Exception("User is logged in"));
					}
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
	 * @param msg type of subject message
	 * @return the subject message that includes the subject list
	 */
	private static AbstractMessage handleSubjectMessage(AbstractMessage msg) {

		SubjectMessage recivedMessage = (SubjectMessage) msg;
		String teacherId = recivedMessage.getTeacherId();
		GetFromDB getSubject = new GetFromDB();
		ArrayList<Subject> subjects = getSubject.subjects(teacherId);
		return message.getMessage("ok-get-subjects", subjects);
	}
	
	/**
	 * 
	 * @param msg type of course message
	 * @return the course message that includes the course list
	 */
	private static AbstractMessage handleCourseMessage(AbstractMessage msg) {
		CourseMessage recivedMessage = (CourseMessage) msg;
		String subjectId = recivedMessage.getCourseSubject().getSubjectID();
		GetFromDB getCourse = new GetFromDB();
		ArrayList<Course> courses = getCourse.coursesInSubject(subjectId);
		return message.getMessage("ok-get-courses", courses);
	}
	
	private static AbstractMessage handlePutMessage(AbstractMessage msg) {
		String[] msgContent = msg.getMsg().toLowerCase().split("-");
		switch(msgContent[1]) {
		case "questions":
			return handlePutQuestion(msg);
		case "exams":
			return handlePutExamMessage(msg);
		case "questioninexam":
			return handlePutQuestionInExamMessage(msg);
		
		}
		
		return null;
		
	}
	
	private static AbstractMessage handlePutQuestion(AbstractMessage msg) {
		// TODO Auto-generated method stub
		QuestionsMessage recievedNewQuestion = (QuestionsMessage)msg;
		Question newQuestionTooAdd = recievedNewQuestion.getQuestions().get(0); // ArrayList<Question> will contain only 1 new question
		SetInDB putQuestion = new SetInDB();
		AbstractMessage sendMessage = (AbstractMessage) putQuestion.AddNewQuestion(newQuestionTooAdd);
		return null;
	}

	private static AbstractMessage handlePutExamMessage(AbstractMessage msg) {
		ExamMessage recivedMessage = (ExamMessage)msg;
		Exam addExam = recivedMessage.getNewExam();
		SetInDB putExam = new SetInDB();
		AbstractMessage sendMessage = (AbstractMessage) putExam.AddExam(addExam);
		return sendMessage;
	}
	
	private static AbstractMessage handleGetExamMessage(AbstractMessage msg) {
		if(msg instanceof SimpleMessage)
		{
			SimpleMessage recievedMessage = (SimpleMessage) msg;
			String strMsg =recievedMessage.getMsg();
			String[] msgContent = msg.getMsg().toLowerCase().split("-");
			if(msgContent[2].equals("pass")) {
				GetFromDB getExam = new GetFromDB();
				ArrayList<Exam> exams= getExam.getExamByPassword(msgContent[3]);
				if(exams!=null)
					return message.getMessage("ok-get-exams", exams);
				else
					return new ErrorMessage(new NullPointerException("Exam not found"));
			}
			
		}
		else {
			ExamMessage recivedMessage = (ExamMessage) msg;
			String examId = recivedMessage.getId();
			GetFromDB getExam = new GetFromDB();
			ArrayList<Exam> exams = getExam.exams(examId);
			return message.getMessage("ok-get-exams", exams);

		}
		return null;
	}
	
	private static AbstractMessage handleDeleteMessage(AbstractMessage msg) {
		String[] msgContent = msg.getMsg().toLowerCase().split("-");
		switch(msgContent[1]) {
		case "exams":
			return handleDeleteExamMessage(msg);
		case "questions":
			return handleDeleteQuestionMessage(msg);
		}
		return null;
	}
	
	private static AbstractMessage handleDeleteQuestionMessage(AbstractMessage msg) {
		QuestionsMessage recievedMessage = (QuestionsMessage)msg;
		Question deleteQuestion = recievedMessage.getQuestions().get(0);
		SetInDB deletesQuestion = new SetInDB();
		AbstractMessage sendMessage = (AbstractMessage) deletesQuestion.deleteTheQuestion(deleteQuestion);
		return sendMessage;
	}

	private static AbstractMessage handleDeleteExamMessage(AbstractMessage msg) {
		ExamMessage recivedMessage = (ExamMessage)msg;
		Exam deleteExam = recivedMessage.getNewExam();
		SetInDB deletesExam = new SetInDB();
		AbstractMessage sendMessage = (AbstractMessage) deletesExam.deleteTheExam(deleteExam);
		return sendMessage;
	}
	
	private static AbstractMessage handlePutQuestionInExamMessage(AbstractMessage msg) {
		QuestionInExamMessage recivedMessage = (QuestionInExamMessage)msg;
		String id = recivedMessage.getExamId();
		ArrayList<QuestionInExam> examQuestions = recivedMessage.getQuestionInExam();
		SetInDB putExam = new SetInDB();
		AbstractMessage sendMessage = (AbstractMessage) putExam.addQuestionToExam(id, examQuestions);
		return sendMessage;
	}
}