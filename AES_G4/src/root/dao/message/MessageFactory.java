package root.dao.message;

import java.util.ArrayList;
import java.util.HashMap;

import root.dao.app.Course;
import root.dao.app.Exam;
import root.dao.app.ExecuteExam;
import root.dao.app.LoginInfo;
import root.dao.app.Question;
import root.dao.app.SolvedExams;
import root.dao.app.Statistic;
import root.dao.app.Subject;
import root.dao.app.User;
import root.dao.app.UserInfo;
import root.server.managers.dbmgr.GetFromDB;

/**
 * Class for make new message
 * @author Omer Haimovich
 *
 */
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
	
	/**
	 * make new message (factory for all types of messages)
	 * @param msg the message itself
	 * @param payload the object with the message(arrayList, exam ,question , etc)
	 * @return
	 */
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
		return new ErrorMessage((Exception)payload);
		
		// TODO Auto-generated method stub
		
	}

	private AbstractMessage getDelMessage(String[] msgContent, Object payload) {
		switch (msgContent[1]) {
		case "exams":
			return new ExamMessage("delete-exams",(Exam)payload);
		case "questions":
			return new QuestionsMessage("delete-questions",(Question)payload);
		}
		return null;
	}

	/**
	 * Make new put message
	 * @param msgContent the message itself
	 * @param payload the object with the message(arrayList, exam ,question , etc)
	 * @return the relevant message
	 */
	private AbstractMessage getPutMessage(String[] msgContent, Object payload) {
		switch (msgContent[1]) {
			case "exams":
				return new ExamMessage((Exam)payload);
			case "questions":
				return new QuestionsMessage((Question)payload);
			case "questioninexam":
				return new QuestionInExamMessage((ArrayList<root.dao.app.QuestionInExam>)payload);
		case "solvedexams":
			return new SolvedExamMessage((SolvedExams) payload);
		case "executeexam":
			return new ExecuteExamMessage((ExecuteExam)payload);
		}
		return null;
	}

	private AbstractMessage getSetMessage(String[] msgContent, Object payload) {
		switch (msgContent[1]) {
		case "questions":
			return new QuestionsMessage("set-questions", (Question)payload);
		default:
			break;
		}
		return null;
	}

	/**
	  * Make new get message
	 * @param msgContent the message itself
	 * @param payload the object with the message(arrayList, exam ,question , etc)
	 * @return the relevant message
	 */
	private AbstractMessage getGetMessage(String[] msgContent, Object payload) {
		switch (msgContent[1]) {
		case "usersubjects":
			return new UserSubjectMessage((User)payload);
		case "subjectbysubjectid":
			return new SubjectMessage("get-subjectbysubjectid",(HashMap<String, String>)payload);
		case "questions":
			return new QuestionsMessage((Subject)payload);
		case "subjects":
			return new SubjectMessage((String)payload);
		case "courses":
			return new CourseMessage((Subject)payload);
		case "coursesbyid":
			return new CourseMessage("get-coursesbyid", (HashMap<String, String>)payload);
		case "simple":
			return new SimpleMessage("simple");
		case "exams":
			return new ExamMessage((String)payload);
		case "user":
			return  getUserRelatedMessage(msgContent,payload);
		case "solvedExamByTeacherId":
			return createGetSolvedExamByTeacherId((User)payload);	
		case "solvedexams":
			return getUserSolvedExams(msgContent,payload);				// get message related to solvedExams Table
		case "word":
			return new WordMessage((Exam) payload);	
		case "executed":
			return new ExecutedExamsMessage();
		default:
			break;
		}

		return null;
		
		
	}
	/**
	 * @author gal
	 * @param payload - User
	 * @return {@link AbstractMessage} with arrayList<SolvedExams>
	 */
	private AbstractMessage getUserSolvedExams(String[] msgContent,Object payload) {
		switch (msgContent[2]) {
		case "user":
			return new UserSolvedExamsMessage((User)payload);
		}
		return null;
		//return new UserSolvedExamsMessage(payload);
	}

	private AbstractMessage getUserRelatedMessage(String[] msgContent, Object payload) {
		switch (msgContent[2]) {
		case "name":
			return new UserInfoMessage((UserInfo)payload);
		}
		return null;
	}

	/**
	  * Make new login message
	 * @param msgContent the message itself
	 * @param payload the object with the message(arrayList, exam ,question , etc)
	 * @return the relevant message
	 */
	private AbstractMessage getLoginMessage(String[] msgContent, Object payload) {
		
		return new LoginMessage((LoginInfo)payload);	
	}

	/**
	  * Make new ok message
	 * @param msgContent the message itself
	 * @param payload the object with the message(arrayList, exam ,question , etc)
	 * @return the relevant message
	 */
	@SuppressWarnings("unchecked")
	public AbstractMessage getOkMessage(String[] msgContent,Object payload) {
		switch(msgContent[1]) {
		case "login":
			return new UserMessage((User)payload);
		case "get":
			return  getOkGetMessage(msgContent,payload);
		case "set":
			return new SimpleMessage("ok-set-"+msgContent[2]);
		case "put":
			return new SimpleMessage("ok-put-"+msgContent[2]);
		case "delete":
			return new SimpleMessage("ok-delete-"+msgContent[2]);

		}
		return new ErrorMessage(new Exception("Invalid request"));
	}
	
	/**
	  * Make new ok-get message
	 * @param msgContent the message itself
	 * @param payload the object with the message(arrayList, exam ,question , etc)
	 * @return the relevant message
	 */
	public AbstractMessage getOkGetMessage(String[] msgContent,Object payload) {
		switch(msgContent[2]) {

		case "questions":
			if(payload instanceof ArrayList<?>)
					return new QuestionsMessage(((ArrayList<Question>)payload));
			else return new ErrorMessage(new Exception("Your payload is not arraylist"));
		case "usersubjects":
			return new UserSubjectMessage((UserSubjectMessage)payload);
		case "exams":
			if(payload instanceof ArrayList<?>)
				return new ExamMessage((ArrayList<Exam>) payload);
			else return new ErrorMessage(new Exception("Your payload is not arraylist"));
		case "subjects":
			if(payload instanceof ArrayList<?>)
				return new SubjectMessage((ArrayList<Subject>)payload);
			if(payload instanceof HashMap<?, ?>) 
				return new SubjectMessage("ok-get-subjects", (HashMap<String, String>)payload);
			else return new ErrorMessage(new Exception("Your payload is not arraylist NOR hashMap.\nIn ok-get-subjects"));
		case "courses":
			if(payload instanceof ArrayList<?>)
				return new CourseMessage((ArrayList<Course>)payload);
			if(payload instanceof HashMap<?, ?>) 
				return new CourseMessage("ok-get-courses", (HashMap<String, String>)payload);
			else return new ErrorMessage(new Exception("Your payload is not arraylist NOR hashMap.\\nIn ok-get-courses"));
		case "solvedExamByTeacherId":
			if(payload instanceof ArrayList<?>)
			{
				StatsMessage statsMsg = new StatsMessage((ArrayList<Statistic>)payload);
				statsMsg.setMsg("ok-get-solvedExamByTeacherId");
			}
			else return new ErrorMessage(new Exception("Your payload is not arraylist"));
		case "users":
			if (payload instanceof HashMap<?, ?>)return new UserInfoMessage((HashMap<String,String>)payload);
			else return new ErrorMessage(new Exception("Your pyaload is not hashmap"));
		case "solvedexams":
			if (payload instanceof ArrayList<?>)return new UserSolvedExamsMessage((ArrayList<SolvedExams>)payload);
			else return new ErrorMessage(new Exception("Your payload is not arraylist"));
		case "word":
			return new SimpleMessage("ok-get-" + msgContent[2]);
		}
		
			
		return new ErrorMessage(new Exception("Invalid request"));
	}
	
	private UserIDMessage createGetSolvedExamByTeacherId(User payload) {
		UserIDMessage message=new UserIDMessage((User)payload);
		message.setMsg("get-solvedExamByTeacherId");
		return message;
	}
	

}