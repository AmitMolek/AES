package root.dao.message;

import java.util.ArrayList;
import java.util.HashMap;

import junit.framework.Test;
import root.dao.app.Course;
import root.dao.app.CsvDetails;
import root.dao.app.Exam;
import root.dao.app.ExecuteExam;
import root.dao.app.LoginInfo;
import root.dao.app.Question;
import root.dao.app.SolvedExams;
import root.dao.app.Statistic;
import root.dao.app.Subject;
import root.dao.app.User;
import root.dao.app.UserInfo;
import root.dao.app.ExamTableDataLine;
import root.server.managers.dbmgr.GetFromDB;

/**
 * The MessageFactory is a singleton class that is responsible for creating new
 * communications messages between the server and the client
 * 
 * @author Omer Haimovich
 *
 */

public class MessageFactory {

	// Instance variables **********************************************

	/**
	 * The instance of the MessageFactory singleton class
	 */
	private static MessageFactory instance = null;

	// CONSTRUCTORS *****************************************************

	/**
	 * Constructs the MessageFactory
	 */
	private MessageFactory() {

	}

	// CLASS METHODS *************************************************

	/**
	 * A method that returns an instance of the MessageFactory class
	 * 
	 * @return the MessageFactory instance
	 */
	public static MessageFactory getInstance() {
		if (instance == null) {
			instance = new MessageFactory();
		}
		return instance;
	}

	/**
	 * 
	 * A method that receives a string from the server or from the client and
	 * generates a new message according to the received string
	 * 
	 * @param msg
	 *            the message that the client or the server asks for
	 * @param payload
	 *            the object with the message(arrayList, exam ,question , etc), can
	 *            be also null
	 * @return abstract message with right object
	 */
	public AbstractMessage getMessage(String msg, Object payload) {
		String[] msgContent = msg.toLowerCase().split("-");
		switch (msgContent[0]) {
		case "ok":
			return getOkMessage(msgContent, payload);
		case "login":
			return getLoginMessage(msgContent, payload);
		case "get":
			return getGetMessage(msgContent, payload);
		case "set":
			return getSetMessage(msgContent, payload);
		case "put":
			return getPutMessage(msgContent, payload);
		case "delete":
			return getDelMessage(msgContent,payload);
		case "error":
			return getErrorMessage(msgContent,payload);
		case "simple":
			return new SimpleMessage("simple");

		}
		return new ErrorMessage(new Exception("Invalid request"));
	}

	/**
	 * A method that generates a new error message that includes the exception
	 * 
	 * @param msgContent
	 *            array that contains the message
	 * @param payload
	 *            the exception
	 * @return the abstract message includes the exception
	 */
	private AbstractMessage getErrorMessage(String[] msgContent, Object payload) {
		switch (msgContent[1]) {
		case "loggedout":
			return new LogoutErrorMessage((Exception) payload);
		}
		return new ErrorMessage((Exception)payload);		
	}

	/**
	 * A method that generates a new delete message that includes the object with
	 * the message(arrayList, exam ,question , etc)
	 * 
	 * @param msgContent
	 *            array that contains the message
	 * @param payload
	 *            the object with the message(arrayList, exam ,question , etc), can
	 *            be also null
	 * @return the abstract message includes the object with the message(arrayList,
	 *         exam ,question , etc), can be also null
	 */
	private AbstractMessage getDelMessage(String[] msgContent, Object payload) {
		switch (msgContent[1]) {
		case "exams":
			return new ExamMessage("delete-exams", (Exam) payload);
		case "questions":
			return new QuestionsMessage("delete-questions", (Question) payload);
		}
		return null;
	}

	/**
	 * A method that generates a new put message that includes the object with the
	 * message(arrayList, exam ,question , etc)
	 * 
	 * @param msgContent
	 *            array that contains the message
	 * @param payload
	 *            the object with the message(arrayList, exam ,question , etc), can
	 *            be also null
	 * @return the abstract message includes the object with the message(arrayList,
	 *         exam ,question , etc), can be also null
	 */
	private AbstractMessage getPutMessage(String[] msgContent, Object payload) {
		switch (msgContent[1]) {
		case "exams":
			return new ExamMessage((Exam) payload);
		case "questions":
			return new QuestionsMessage((Question) payload);
		case "questioninexam":
			return new QuestionInExamMessage((ArrayList<root.dao.app.QuestionInExam>) payload);
		case "solvedexams":
			return new SolvedExamMessage((SolvedExams) payload);
		case "executeexam":
			return new ExecuteExamMessage((ExecuteExam) payload);
		case "wordexam":
			return new WordMessage("put-wordexam", (MyFile) payload);
		case "updatesolvedexam":
			return (UpdateSolvedExam) payload;
		}
		return null;
	}

	/**
	 * A method that generates a new set message that includes the object with the
	 * message(arrayList, exam ,question , etc)
	 * 
	 * @param msgContent
	 *            array that contains the message
	 * @param payload
	 *            the object with the message(arrayList, exam ,question , etc), can
	 *            be also null
	 * @return the abstract message includes the object with the message(arrayList,
	 *         exam ,question , etc), can be also null
	 */
	private AbstractMessage getSetMessage(String[] msgContent, Object payload) {
		switch (msgContent[1]) {
		case "questions":
			return new QuestionsMessage("set-questions", (Question) payload);
		case "exams":
			return new ExamMessage("set-exams", (Exam) payload);
		default:
			break;
		}
		return null;
	}

	/**
	 * A method that generates a new get message that includes the object with the
	 * message(arrayList, exam ,question , etc)
	 * 
	 * @param msgContent
	 *            array that contains the message
	 * @param payload
	 *            the object with the message(arrayList, exam ,question , etc), can
	 *            be also null
	 * @return the abstract message includes the object with the message(arrayList,
	 *         exam ,question , etc), can be also null
	 */
	private AbstractMessage getGetMessage(String[] msgContent, Object payload) {
		switch (msgContent[1]) {
		case "usersubjects":
			return new UserSubjectMessage((User) payload);
		case "subjectbysubjectid":
			return new SubjectMessage("get-subjectbysubjectid", (HashMap<String, String>) payload);
		case "questions":
			if (payload instanceof ArrayList<?>)
				return new QuestionsMessage("get-questions-questionid", (ArrayList<String>) payload);
			return new QuestionsMessage((Subject) payload);
		case "subjects":
			return new SubjectMessage((String) payload);
		case "courses":
			return new CourseMessage((Subject) payload);
		case "coursesbyid":
			return new CourseMessage("get-coursesbyid", (HashMap<String, String>) payload);
		case "simple":
			return new SimpleMessage("simple");
		case "exams":
			return new ExamMessage((String) payload);
		case "user":
			return getUserRelatedMessage(msgContent, payload);
		case "solvedexams":
			return getUserSolvedExams(msgContent, payload); // get message related to solvedExams Table
		case "word":
			return new WordMessage((Exam) payload);
		case "wordexam":
			return new WordMessage((String) payload);
		case "executed":
			return new ExecutedExamsMessage();
		case "csv":
			return new CsvMessage((CsvDetails) payload);
		case "csvfromserver":
			return new CsvMessage("get-csvfromserver", (CsvDetails) payload);
		case "examstatsbyiddate":
			return new ExamStatsByIdDateMessage("get-examstatsbyiddate", (String[]) payload);
		case "examtabledataline":
			return createGetStatisticsByAssemblingTeacherId((User) payload);
		case "solvedbysubjectcourse":
			return new SolvedExamBySubjectCourseMessage((SolvedExamBySubjectCourseMessage) payload);
		case "query":
			return new QueryMessage("get-query",(String)payload);
		case "alltables":
			return new SimpleMessage("get-alltables");
		default:
			break;
		}

		return null;

	}

	/**
	 * 
	 * A method that creates new userID message
	 * 
	 * @author Alon Ben-yosef
	 * @param payload
	 *            to get relevant statistics for teacher
	 * @return a userID message for teacher
	 */
	private AbstractMessage createGetStatisticsByAssemblingTeacherId(User payload) {
		UserIDMessage msg = new UserIDMessage(payload);
		msg.setMsg("get-examTableDataLine");
		return msg;
	}

	/**
	 * A method that creates new userSolvedExam message
	 *
	 * @param payload
	 *            - User
	 * @return {@link AbstractMessage} with arrayList<SolvedExams>
	 */
	private AbstractMessage getUserSolvedExams(String[] msgContent, Object payload) {
		switch (msgContent[2]) {
		case "user":
			return new UserSolvedExamsMessage((User) payload);
		}
		return null;
		// return new UserSolvedExamsMessage(payload);
	}

	
	/**
	 * A method that creates new userInfo message
	 *  @param msgContent
	 *            array that contains the message
	 * @param payload
	 *            the object with the message(arrayList, exam ,question , etc), can
	 *            be also null
	 * @return the userInfoMessage
	 */
	private AbstractMessage getUserRelatedMessage(String[] msgContent, Object payload) {
		switch (msgContent[2]) {
		case "name":
			return new UserInfoMessage((UserInfo) payload);
		}
		return null;
	}


	/**
	 * A method that generates a new login message that includes the object with the
	 * message(arrayList, exam ,question , etc)
	 * 
	 * @param msgContent
	 *            array that contains the message
	 * @param payload
	 *            the object with the message(arrayList, exam ,question , etc), can
	 *            be also null
	 * @return the abstract message includes the object with the message(arrayList,
	 *         exam ,question , etc), can be also null
	 */
	private AbstractMessage getLoginMessage(String[] msgContent, Object payload) {

		return new LoginMessage((LoginInfo) payload);
	}

	

	/**
	 * A method that generates a new ok message that includes the object with the
	 * message(arrayList, exam ,question , etc)
	 * 
	 * @param msgContent
	 *            array that contains the message
	 * @param payload
	 *            the object with the message(arrayList, exam ,question , etc), can
	 *            be also null
	 * @return the abstract message includes the object with the message(arrayList,
	 *         exam ,question , etc), can be also null
	 */
	@SuppressWarnings("unchecked")
	public AbstractMessage getOkMessage(String[] msgContent, Object payload) {
		switch (msgContent[1]) {
		case "login":
			return new UserMessage((User) payload);
		case "get":
			return getOkGetMessage(msgContent, payload);
		case "set":
			return new SimpleMessage("ok-set-" + msgContent[2]);
		case "put":
			return new SimpleMessage("ok-put-" + msgContent[2]);
		case "delete":
			return new SimpleMessage("ok-delete-" + msgContent[2]);
		case "loggedout":
			return new SimpleMessage("ok-loggedout");
		}
		return new ErrorMessage(new Exception("Invalid request"));
	}


	/**
	 * A method that generates a new ok-get message that includes the object with the
	 * message(arrayList, exam ,question , etc)
	 * 
	 * @param msgContent
	 *            array that contains the message
	 * @param payload
	 *            the object with the message(arrayList, exam ,question , etc), can
	 *            be also null
	 * @return the abstract message includes the object with the message(arrayList,
	 *         exam ,question , etc), can be also null
	 */
	public AbstractMessage getOkGetMessage(String[] msgContent, Object payload) {
		switch (msgContent[2]) {

		case "questions":
			if (payload instanceof ArrayList<?>)
				return new QuestionsMessage(((ArrayList<Question>) payload));
			else
				return new ErrorMessage(new Exception("Your payload is not arraylist"));
		case "usersubjects":
			return new UserSubjectMessage((UserSubjectMessage) payload);
		case "exams":
			if (payload instanceof ArrayList<?>)
				return new ExamMessage((ArrayList<Exam>) payload);
			else
				return new ErrorMessage(new Exception("Your payload is not arraylist"));
		case "subjects":
			if (payload instanceof ArrayList<?>)
				return new SubjectMessage((ArrayList<Subject>) payload);
			if (payload instanceof HashMap<?, ?>)
				return new SubjectMessage("ok-get-subjects", (HashMap<String, String>) payload);
			else
				return new ErrorMessage(
						new Exception("Your payload is not arraylist NOR hashMap.\nIn ok-get-subjects"));
		case "courses":
			if (payload instanceof ArrayList<?>)
				return new CourseMessage((ArrayList<Course>) payload);
			if (payload instanceof HashMap<?, ?>)
				return new CourseMessage("ok-get-courses", (HashMap<String, String>) payload);
			else
				return new ErrorMessage(
						new Exception("Your payload is not arraylist NOR hashMap.\\nIn ok-get-courses"));
		case "examtabledataline":
			if (payload instanceof ArrayList<?>) {
				ExamDataLinesMessage linesMsg = new ExamDataLinesMessage((ArrayList<ExamTableDataLine>) payload);
				linesMsg.setMsg("ok-get-examtabledataline");
				return linesMsg;
			} else
				return new ErrorMessage(new Exception("Your payload is not arraylist"));
		case "users":
			if (payload instanceof HashMap<?, ?>)
				return new UserInfoMessage((HashMap<String, String>) payload);
			else
				return new ErrorMessage(new Exception("Your pyaload is not hashmap"));
		case "solvedexams":
			if (payload instanceof ArrayList<?>)
				return new UserSolvedExamsMessage((ArrayList<SolvedExams>) payload);
			else
				return new ErrorMessage(new Exception("Your payload is not arraylist"));
		case "word":
			return new SimpleMessage("ok-get-" + msgContent[2]);
		case "wordexam":
			return new WordMessage("ok-get-" + msgContent[2], (MyFile) payload);
		case "csv":
			if (payload instanceof ArrayList<?>)
				return new CsvMessage("ok-get-" + msgContent[2], (ArrayList<String[]>) payload);
			return new SimpleMessage("ok-get-" + msgContent[2]);
		case "examstatsbyiddate":
			if (payload instanceof Statistic)
				return new StatsMessage("ok-get-examstatsbyiddate", (Statistic) payload);
		case "solvedbysubjectcourse":
			return new SolvedExamBySubjectCourseMessage((SolvedExamBySubjectCourseMessage) payload);
		case "query":
			return new StatsMessage("ok-get-query", (Statistic)payload);
		case "alltables":
			return new AllTablesMessage("ok-get-alltables");
		}
		return new ErrorMessage(new Exception("Invalid request"));
	}

}