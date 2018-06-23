package root.server.managers;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.chainsaw.Main;

import com.opencsv.CSVWriter;
import root.client.controllers.QuestionInExamObject;
import root.dao.app.CheatingExamTest;
import com.sun.javafx.geom.transform.GeneralTransform3D;
//import root.client.controllers.TestGradesTeacherController;
import root.dao.app.Course;
import root.dao.app.CsvDetails;
import root.dao.app.Exam;
import root.dao.app.ExamTableDataLine;
import root.dao.app.ExecuteExam;
import root.dao.app.LoginInfo;
import root.dao.app.Question;
import root.dao.app.QuestionInExam;
import root.dao.app.SolvedExams;
import root.dao.app.Statistic;
import root.dao.app.Subject;
import root.dao.app.User;
import root.dao.app.UserInfo;
import root.dao.message.AbstractMessage;
import root.dao.message.AllTablesMessage;
import root.dao.message.ChangeTimeDurationRequest;
import root.dao.message.CourseMessage;
import root.dao.message.CsvMessage;
import root.dao.message.ErrorMessage;
import root.dao.message.ExamDataLinesMessage;
import root.dao.message.ExamMessage;
import root.dao.message.ExamStatsByIdDateMessage;
import root.dao.message.ExecuteExamMessage;
import root.dao.message.ExecutedExamsMessage;
import root.dao.message.LoggedOutMessage;
import root.dao.message.LoginMessage;
import root.dao.message.MessageFactory;
import root.dao.message.MyFile;
import root.dao.message.QueryMessage;
import root.dao.message.QuestionInExamMessage;
import root.dao.message.QuestionsMessage;
import root.dao.message.SimpleMessage;
import root.dao.message.SolvedExamBySubjectCourseMessage;
import root.dao.message.SolvedExamMessage;
import root.dao.message.SubjectMessage;
import root.dao.message.UpdateSolvedExam;
import root.dao.message.UserInfoMessage;
import root.dao.message.UserIDMessage;
import root.dao.message.UserMessage;
import root.dao.message.UserSolvedExamsMessage;
import root.dao.message.UserSubjectMessage;
import root.dao.message.WordMessage;
import root.server.AES_Server;
import root.server.managers.dbmgr.ExamExecutedManager;
import root.server.managers.dbmgr.GetFromDB;
import root.server.managers.dbmgr.SetInDB;
import root.server.managers.usersmgr.ExecuteStudentManager;
import root.server.managers.usersmgr.PrincipleManager;
import root.util.csvReader.CSVReader;
import root.util.worddocumentmgr.WordDocument;

/**
 * The ServerMessageManager is a singleton class that is responsible for
 * handling with messages sent to the server from the client
 * 
 * @author Omer Haimovich
 *
 */
public class ServerMessageManager {

	// Instance variables **********************************************

	/**
	 * Manager for of all students currently under exam
	 */
	private static ExecuteStudentManager examinees = ExecuteStudentManager.getInstance();
	/**
	 * Manager for of all principal currently logged in 
	 */
	private static PrincipleManager principles = new PrincipleManager();
	/**
	 * The instance of the ServerMessageManager singleton class
	 */
	private static ServerMessageManager instance = null;
	/**
	 * Generates new communications between server and client
	 */
	private static MessageFactory message = MessageFactory.getInstance();
	/**
	 * Manager all users currently logged in
	 */
	private static LoggedInUsersManager usersManager = LoggedInUsersManager.getInstance();
	/**
	 * 
	 * Absolute path to a execute exam folder in the server that contains exams word
	 * files that are ready to be executed by the students
	 */
	public static String PATH;
	/**
	 * Absolute path to a solved exam folder in the server that contains exams word
	 * files that are been executed by the students(the student solved exam)
	 */
	public static String PATHSOLUTION;
	/**
	 * Absolute path to a csv exam folder in the server that contains exams csv
	 * files that are been executed by the students that did the exam auto in the
	 * system
	 */
	public static String PATHCSV;
	/**
	 * Manager for of all students currently under exam
	 */
	public static ExamExecutedManager executedUsersManager = new ExamExecutedManager();

	// CONSTRUCTORS *****************************************************

	/**
	 * Constructs the ServerMessageManager
	 */
	private ServerMessageManager() {
		String s = System.getProperty("user.home");
		new File(s+"//CSV").mkdir();
		new File(s+"//word").mkdir();
		new File(s+"//solution").mkdir();
		String fullPath = s+"//word//";
		PATH = fullPath;
		PATHSOLUTION = s+"//solution//";
		PATHCSV = s+ "//CSV//";
	}

	// CLASS METHODS *************************************************

	/**
	 * A method that returns an instance of the ServerMessageManager class
	 * 
	 * @return the ServerMessageManager instance
	 */
	public static ServerMessageManager getInstance() {
		if (instance == null) {
			instance = new ServerMessageManager();
		}
		return instance;
	}

	/**
	 * A method that receives a message sent from the client and checks the type of
	 * message and refers to the appropriate method to handle this message
	 * 
	 * @param msg
	 *            the message that the client sent
	 * @return simple message with ok if the server handle it includes object or
	 *         null or error message otherwise
	 */
	public static AbstractMessage handleMessage(AbstractMessage msg) {
		String[] msgContent = msg.getMsg().toLowerCase().split("-");
		switch (msgContent[0]) {
		case "login":
			return handleLoginMessage(msg);
		case "usersubjects":
			return handleUserSubjectsMessage(msg);
		case "questions":
			return handleQuestionsMessage(msg);
		case "get":
			return handleGetMessage(msg);
		case "set": // update an already existing data
			return handleSetMessage(msg);
		case "put": // insert newData
			return handlePutMessage(msg);
		case "delete":
			return handleDeleteMessage(msg);
		case "loggedout":
			return handleLoggedOutMessage(msg);
		case "changetimeduration":
			 handleChangeTimeDurationRequest(msg);
			 break;
		case "confirmchangeduration":
			 handleChangeTimeConfirm(msg);
			 break;
		case "startexam":
			 handlStartExam(msg);
			 break;
		case "closeexam":
			return handlCloseExam(msg);
		default:
			return null;
		}
		return null;
	}

	/**
	 * A method that closes an exam in the database means that it changes the exams
	 * to lock and deletes the exams from a `execute exams` table in the database
	 * 
	 * @autor Naor Saadia
	 * @param msg
	 *            type of simple message sent includes exam id
	 * @return simple message with ok if the server handle it or error message
	 *         otherwise
	 */
	private static AbstractMessage handlCloseExam(AbstractMessage msg) {
		SimpleMessage newMsg = (SimpleMessage) msg;
		String examId = newMsg.getMsg().split("-")[1];
		SetInDB set = new SetInDB();
		set.deleteExecutedExam(examId);
		set.lockExam(examId);
		Calendar cal = Calendar.getInstance();
		new CheatingChecker(examId, Calendar.getInstance());
		return null;
	}

	/**
	 * 
	 * @param msg
	 * @return
	 */
	private static void handlStartExam(AbstractMessage msg) {
		SimpleMessage newMsg = (SimpleMessage) msg;
		String examId = newMsg.getMsg().split("-")[1];
		examinees.addStudent(examId, AES_Server.CLIENT);
	}

	/**
	 * A method that logout a user from the application
	 * 
	 * @author Amit Molek
	 * @param msg
	 *            the message that the client sent includes user id
	 * @return simple message with ok if the server handle it or error message
	 *         otherwise
	 */
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
			} else {
				return message.getMessage(error_msg, new Exception("Failed removing logged in user"));
			}
		} else {
			return message.getMessage(error_msg, new Exception("User not logged in"));
		}
	}

	/**
	 * A method that handle with messages of updating data in the database (must
	 * contains the word `set` in the message)
	 * 
	 * @author Omer Haimovich
	 * @param msg
	 *            the message that the client sent
	 * @return simple message with ok if the server handle it or error message
	 *         otherwise
	 */
	private static AbstractMessage handleSetMessage(AbstractMessage msg) { // when wanting to change data in the DB
																			// change existing data
		String[] msgContent = msg.getMsg().toLowerCase().split("-");
		switch (msgContent[1]) {
		case "questions":
			return handleSetQuestionMessage(msg);
		case "exams":
			return handleSetExamMessage(msg);
		}
		return null;
	}

	/**
	 * 
	 * A method that is called when the client wants to update a question in the
	 * database
	 * 
	 * @author gal
	 * @param msg
	 *            question message that sent from the client
	 * @return simple message with ok if the server handle it or error message
	 *         otherwise
	 * 
	 */
	private static AbstractMessage handleSetQuestionMessage(AbstractMessage msg) {
		// TODO Auto-generated method stub
		QuestionsMessage questionMessage = (QuestionsMessage) msg;
		Question newQuestionTooAdd = questionMessage.getQuestions().get(0);
		SetInDB updateExistingQuestion = new SetInDB();
		AbstractMessage sendMessage = (AbstractMessage) updateExistingQuestion
				.updateExistingQuestion(newQuestionTooAdd);
		return sendMessage;
	}

	/**
	 * A method that is called when the client wants to get questions from the
	 * database
	 * 
	 * @author gal
	 * @param msg
	 *            type of QuestionMessage which contain the string "Questions" ans
	 *            the subject of the questions as payload
	 * @return {@link AbstractMessage} of QuestionMessage filled with question from
	 *         the same subject
	 */
	private static AbstractMessage handleQuestionsMessage(AbstractMessage msg) {
		// TODO Auto-generated method stub
		QuestionsMessage questionMessage = (QuestionsMessage) msg;
		String questionMgs = questionMessage.getMsg();
		GetFromDB getQuestions = new GetFromDB();
		ArrayList<Question> questions = new ArrayList<Question>();
		if (questionMgs.split("-").length == 3) { // here well be getting questions by question ID, optionaly to add
													// other parameters to this switch/case
			switch (questionMgs.split("-")[2]) {
			case "questionid":
				questions = getQuestions.questions(questionMessage.getQuestionID());
				break;
			default:
				break;
			}
		} else { // get questions by a subjectID.
			questions = getQuestions.questions(questionMessage.getThisQuestionsSubject().getSubjectID());
		}
		if (questions.size() == 0)
			return message.getMessage("error-Qeustions", new Exception("No Questions in this subject")); // return
																											// Exception
		else if (questions.size() >= 1)
			return message.getOkGetMessage("ok-get-questions".split("-"), questions); // found questions for this
																						// subject, return them
		return message.getMessage("error-Qesutions", new Exception("Error in finding Qesutions"));
	}

	/**
	 * A method that is called when the client wants to get all the subject that
	 * teacher teach from the database
	 * 
	 * @author gal
	 * @param msg
	 *            type of UserSubjectMessage which contain string "UserSubjects" and
	 *            User payload
	 * @return AbstrackMessage of userSubjects filled with specific user subjects.
	 */
	private static AbstractMessage handleUserSubjectsMessage(AbstractMessage msg) {
		UserSubjectMessage userSubjects = (UserSubjectMessage) msg;
		GetFromDB getUserSubjects = new GetFromDB();
		ArrayList<Subject> subjects = getUserSubjects.subjects(userSubjects.getUser().getUserID());
		userSubjects.setSubjects(subjects);
		if (subjects.size() == 0)
			return message.getMessage("error-userSubjects", new Exception("No subjects for the User")); // this user has
																										// now teaching
																										// subject,
																										// return
																										// Exception
		else if (subjects.size() >= 1)
			return message.getOkGetMessage("ok-get-usersubjects".split("-"), userSubjects); // this user has teaching
																							// subjects, return his HIS
																							// subjects
		return message.getMessage("error-userSubjects", new Exception("Error in finding userSubjects"));
	}

	/**
	 * A method that handle with messages of getting data from the database (must
	 * contains the word `get` in the message)
	 * 
	 * @author Naor Saadia
	 * @param msg
	 *            type of abstract message includes word `get`
	 * @return abstract message that include array list of the information that
	 *         client asks or an error message if the server did not handle with
	 *         this request
	 */
	private static AbstractMessage handleGetMessage(AbstractMessage msg) {
		String[] msgContent = msg.getMsg().toLowerCase().split("-");
		switch (msgContent[1]) {
		case "subjects":
			return handleSubjectMessage(msg);
		case "subjectbysubjectid":
			return handleGetSubjectBySubjectIDMessage(msg);
		case "courses":
			return handleCourseMessage(msg);
		case "coursesbyid":
			return handleGetCourseByCourseIDMessage(msg);
		case "questions":
			return handleQuestionsMessage(msg);
		case "exams":
			return handleGetExamMessage(msg);
		case "user":
			return handleFetUserMessage(msgContent, msg);
		case "solvedexams":
			return handleGetSolvedExams(msgContent, msg); // this methos handeles all Get requests from solvedExams
															// table
		case "word":
			return handleGetWord(msg);
		case "wordexam":
			return handleGetWordExam(msg);
		case "executed":
			return handleGetExecutedExams(msg);
		case "csv":
			return handleGetCsv(msg);
		case "csvfromserver":
			return handleGetCSVfromServer(msg);
		case "examtabledataline":
			return handleExamTableDataLine(msg);
		case "examstatsbyiddate":
			return handleExamStatsByIdDate(msg);
		case "solvedbysubjectcourse":
			return handleGetSolvedExamsBySubjectIDCourseID(msg);
		case "query":
			return handleGetQuery(msg);
		case "alltables":
			return handleGetAllTables(msg);
		}
		return null;
	}

	private static AbstractMessage handleGetAllTables(AbstractMessage msg) {
		GetFromDB getDB = new GetFromDB();
		AllTablesMessage allMessage=(AllTablesMessage)message.getMessage("ok-get-alltables", null);
		allMessage.setAlterDurList(getDB.alterDuration());
		allMessage.setCourseList(getDB.courses());
		allMessage.setCourseInSubList(getDB.getCoursesInSubject());
		allMessage.setExamList(getDB.getExams());
		allMessage.setStatList(getDB.solvedExamStatistic());
		allMessage.setExecuteList(getDB.getExecutedExams());
		allMessage.setQuestionList(getDB.questions(""));
		allMessage.setQuestionInExamList(getDB.getQuestionsInExam());
		allMessage.setSolvedExamList(getDB.solvedExams());
		allMessage.setSubjectTeacherList(getDB.getSubjectsATeacherTeach());
		allMessage.setSubjectList(getDB.subjects());
		allMessage.setUserList(getDB.users());

		return allMessage;
	}

	private static AbstractMessage handleGetSolvedExamsBySubjectIDCourseID(AbstractMessage msg) {
		SolvedExamBySubjectCourseMessage sebsc = (SolvedExamBySubjectCourseMessage) msg;
		GetFromDB getSolved = new GetFromDB();
		
		sebsc.setSolvedExams(getSolved.getSolvedExamsByCourseId(sebsc.getCourse().getCourseId()));
		
		return sebsc;
	}
	
	/**
	 * @author Alon Ben-yosef
	 * @param msg Assuming QueryMessage, runs the query and returns statistics
	 * @return a StatMessage with query results
	 */
	private static AbstractMessage handleGetQuery(AbstractMessage msg) {
		GetFromDB getGrades = new GetFromDB();
		ArrayList<Integer> list;
		try {
			list = getGrades.getGradesQuery(((QueryMessage)msg).getQuery());
			if(list.isEmpty()) {
				return message.getMessage("error-get-query",new Exception("No valid results"));
			}
			else {
				Statistic data = new Statistic(list);
				return message.getMessage("ok-get-query", data);
			}
		} catch (SQLException e) {
			return message.getMessage("error-get-query",new Exception("SQL error in server"));
		}
	
		
	}

	/**
	 * A method that is called when have map<courseID,courseName> and want to fill
	 * it with course names.
	 * 
	 * @author gal
	 * @param msg
	 *            tyoe of CourseMessage
	 * @return the map<courseID,courseName> if the server handle this request and
	 *         error message otherwise
	 */
	private static AbstractMessage handleGetCourseByCourseIDMessage(AbstractMessage msg) {
		CourseMessage recivedMessage = (CourseMessage) msg;
		// String teacherId = recivedMessage.getTeacherId();
		GetFromDB getCourse = new GetFromDB();
		ArrayList<Course> courses = getCourse.courses(); // fetch all subjects from DB/
		HashMap<String, String> courseMap = recivedMessage.getCourseMap();
		for (Course course : courses) {
			if (courseMap.containsKey(course.getCourseId())) {
				courseMap.put(course.getCourseId(), course.getCourseName());
			}
		}
		return message.getMessage("ok-get-courses", courseMap);
	}

	/**
	 * A method that is called when have map<subjectID,subectName> and want to fill
	 * it with subject names.
	 * 
	 * @author gal
	 * @param msg
	 *            type of SubjectMessage
	 * @return the map<subjectID,subectName> if the server handle this request and
	 *         error message otherwise
	 */
	private static AbstractMessage handleGetSubjectBySubjectIDMessage(AbstractMessage msg) {

		SubjectMessage recivedMessage = (SubjectMessage) msg;
		// String teacherId = recivedMessage.getTeacherId();
		GetFromDB getSubject = new GetFromDB();
		ArrayList<Subject> subjects = getSubject.subjects(); // fetch all subjects from DB/
		HashMap<String, String> subjectMap = recivedMessage.getSubjectsMap();
		for (Subject subject : subjects) {
			if (subjectMap.containsKey(subject.getSubjectID())) {
				subjectMap.put(subject.getSubjectID(), subject.getSubjectName());
			}
		}
		return message.getMessage("ok-get-subjects", subjectMap);
	}

	/**
	 * 
	 * A method that is called when a client wants to get all solved exams from the
	 * database for specific student
	 * 
	 * @author gal
	 * @param msgContent
	 *            array that contains the message content
	 * @param msg
	 *            the message that the client sent
	 * @return simple message with ok if the server handle it or error message
	 *         otherwise
	 */
	private static AbstractMessage handleGetSolvedExams(String[] msgContent, AbstractMessage msg) {
		switch (msgContent[2]) {
		case "user":
			return getUserSolvedExambyUserID(msg);
		// case "exam":
		// return getUserSolvedExambyExamID(msg);
		}

		return null;
	}

	/**
	 * A method that returns all the exams of a specific student
	 * 
	 * @author gal
	 * @param msg
	 *            type of UserSolvedExamsMessage
	 * @return abstract message includes all user solved exam if the server handle
	 *         this request and error message otherwise
	 */
	private static AbstractMessage getUserSolvedExambyUserID(AbstractMessage msg) {
		UserSolvedExamsMessage userSolvedExamsMessage = (UserSolvedExamsMessage) msg;
		GetFromDB getUserSolvedExams = new GetFromDB();
		ArrayList<SolvedExams> userSolvedExams = getUserSolvedExams
				.solvedExams(userSolvedExamsMessage.getUser().getUserID()); // get all solvedExams of specific userID
		return message.getMessage("ok-get-solvedExams", userSolvedExams);
	}

	/**
	 * A method that is called when the client wants to get all data about a
	 * specific user
	 * 
	 * @author gal
	 * @param msgContent
	 *            array that contains the message content
	 * @param msg
	 *            the message that the client sent
	 * @return simple message with ok if the server handle it or error message
	 *         otherwise
	 */
	private static AbstractMessage handleFetUserMessage(String[] msgContent, AbstractMessage msg) {
		switch (msgContent[2]) {
		case "name":
			return getUserName(msg);
		}
		return null;
	}

	/**
	 * A method that returns all data about a specific user
	 * 
	 * @author gal
	 * @param msg
	 *            type of UserInfoMessage
	 * @return the map<UserId,First name + Last name> if the server handle this
	 *         request and error message otherwise
	 */
	private static AbstractMessage getUserName(AbstractMessage msg) {
		UserInfoMessage userInfoMessage = (UserInfoMessage) msg;
		UserInfo userInfo = userInfoMessage.getUserInfo();
		GetFromDB getUserName = new GetFromDB();
		HashMap<String, String> usersMap = userInfo.getTeachersMap();
		ArrayList<User> users = getUserName.users(); // get all users
		for (User user : users) {
			if (usersMap.containsKey(user.getUserID())) {
				usersMap.put(user.getUserID(), user.getUserFirstName() + " " + user.getUserLastName());
			}
		}
		return message.getMessage("ok-get-users", usersMap);
	}

	/**
	 * 
	 * A method that is called when the client sent a login message contains the
	 * word `login`
	 * 
	 * @author Omer Haimovich
	 * @param msg
	 *            type of LoginMessage which contain string, and loginInfo payload.
	 * @return AbstrackMessage with required information.
	 */
	private static AbstractMessage handleLoginMessage(AbstractMessage msg) {
		LoginMessage login = (LoginMessage) msg;
		GetFromDB getLogin = new GetFromDB();
		ArrayList<User> users = getLogin.users(login.getUser().getUserID());
		LoginInfo loginInformation = login.getUser();
		if (users.isEmpty() == false) {
			for (User user : users) {
				if (user.getUserID().equals(loginInformation.getUserID())) {
					if (user.getUserPassword().equals(loginInformation.getPassword())) {
						if (!usersManager.isUserLoggedIn(user.getUserID())) {
							usersManager.addLoggedInUser(user.getUserID());
							if (user.getUserPremission().equals("Principal")) {
								principles.addPrinciple(AES_Server.CLIENT);
							}
							return message.getMessage("ok-login", user);
						} else {
							return message.getMessage("error-login", new Exception("User is logged in"));
						}
					} else {
						return message.getMessage("error-login", new Exception("Wrong Password"));
					}
				}
			}
		}
		return message.getMessage("error-login",new Exception("User not exist"));
	}
	

	/**
	 * 
	 * A method that is called when the client wants to get all the subjects a
	 * specific teacher teach
	 * 
	 * @author Omer Haimovich
	 * @param msg
	 *            type of subject message include teacher id
	 * @return the abstract message that includes the subject list
	 */
	private static AbstractMessage handleSubjectMessage(AbstractMessage msg) {

		SubjectMessage recivedMessage = (SubjectMessage) msg;
		String teacherId = recivedMessage.getTeacherId();
		GetFromDB getSubject = new GetFromDB();
		ArrayList<Subject> subjects = getSubject.subjects(teacherId);
		return message.getMessage("ok-get-subjects", subjects);
	}

	/**
	 * A method that is called when the client wants to get all the courses of a
	 * specific subject
	 * 
	 * @author Omer Haimovich
	 * @param msg
	 *            type of course message
	 * @return the abstract message that includes the course list
	 */
	private static AbstractMessage handleCourseMessage(AbstractMessage msg) {
		CourseMessage recivedMessage = (CourseMessage) msg;
		String subjectId = recivedMessage.getCourseSubject().getSubjectID();
		GetFromDB getCourse = new GetFromDB();
		ArrayList<Course> courses = getCourse.coursesInSubject(subjectId);
		return message.getMessage("ok-get-courses", courses);
	}

	/**
	 * A method that handle with messages of insert data in the database (must
	 * contains the word `put` in the message)
	 * 
	 * @author Omer Haimovich
	 * @param msg
	 *            the message that the client sent
	 * @return simple message with ok if the server handle it or error message
	 *         otherwise
	 */

	private static AbstractMessage handlePutMessage(AbstractMessage msg) {
		String[] msgContent = msg.getMsg().toLowerCase().split("-");
		switch (msgContent[1]) {
		case "questions":
			return handlePutQuestion(msg);
		case "exams":
			return handlePutExamMessage(msg);
		case "questioninexam":
			return handlePutQuestionInExamMessage(msg);
		case "solvedexams":
			return handlePutSolvedExamMessage(msg);
		case "executeexam":
			return handlePutExecuteExamMessage(msg);
		case "wordexam":
			return handlePutwordExamMessage(msg);
		case "updatesolvedexam":
			return handleUpdateSolvedExam(msg);
		}

		return null;

	}

	/**
	 * 
	 * A method that is called when a client wants to add a new question to the
	 * database for a `questions` table
	 * 
	 * @author gal
	 * @param msg
	 *            type of QuestionsMessage
	 * @return simple message with ok if the server handle it or error message
	 *         otherwise
	 */
	private static AbstractMessage handleUpdateSolvedExam(AbstractMessage msg) {
		UpdateSolvedExam exam = (UpdateSolvedExam) msg;
		SetInDB setDb = new SetInDB();
		SolvedExams solved = exam.getExam();
		setDb.updateSolvedExamGrade_Approval_Explenation_ApprovingTeacherID(solved.getExamGrade(), solved.getGradeAlturationExplanation(), exam.getTeacher_id(), solved);
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

	/**
	 * 
	 * A method that is called when a client wants to add a new exam into the
	 * database for `exams` table
	 * 
	 * @author Omer Haimovich
	 * @param msg
	 *            type of ExamMessage
	 * @return simple message with ok if the server handle it or error message
	 *         otherwise
	 */

	private static AbstractMessage handlePutExamMessage(AbstractMessage msg) {
		ExamMessage recivedMessage = (ExamMessage) msg;
		Exam addExam = recivedMessage.getNewExam();
		SetInDB putExam = new SetInDB();
		AbstractMessage sendMessage = (AbstractMessage) putExam.AddExam(addExam);
		return sendMessage;
	}

	/**
	 * 
	 * A method that returns all exams
	 * 
	 * @author Omer Haimovich
	 * @param msg
	 *            type of SimpleMessage or ExamMessage
	 * @return abstract message includes array list of all exams
	 */

	private static AbstractMessage handleGetExamMessage(AbstractMessage msg) {
		if (msg instanceof SimpleMessage) {
			SimpleMessage recievedMessage = (SimpleMessage) msg;
			String strMsg = recievedMessage.getMsg();
			String[] msgContent = msg.getMsg().toLowerCase().split("-");
			if (msgContent[2].equals("pass")) {
				GetFromDB getExam = new GetFromDB();
				ArrayList<Exam> exams = getExam.getExamByPassword(msgContent[3]);
				if (exams != null) {
					Exam e = exams.get(0);
					examinees.addDuration(e.getExamId(), AES_Server.CLIENT);
					if (executedUsersManager.isContains(e.getExamId(), msgContent[4]))
						return new ErrorMessage(
								new NullPointerException("Sorry, this exam has already been submitted"));
					if (!executedUsersManager.checkTime(e.getExamId()))
						return new ErrorMessage(new NullPointerException("The entrey is locked"));
					ExamMessage examMsg = (ExamMessage) message.getMessage("ok-get-exams", exams);
					return examMsg;
				} else
					return new ErrorMessage(new NullPointerException("Exam not found"));
			}

		} else {
			ExamMessage recivedMessage = (ExamMessage) msg;
			String examId = recivedMessage.getId();
			GetFromDB getExam = new GetFromDB();
			ArrayList<Exam> exams = getExam.exams(examId);
			return message.getMessage("ok-get-exams", exams);

		}
		return null;
	}

	/**
	 * 
	 * A method that handle with messages of deleting data from the database (must
	 * contains the word `delete` in the message)
	 * 
	 * @author Omer Haimovich
	 * @param msg
	 *            the message that the client sent
	 * @return simple message with ok if the server handle it or error message
	 *         otherwise
	 */
	private static AbstractMessage handleDeleteMessage(AbstractMessage msg) {
		String[] msgContent = msg.getMsg().toLowerCase().split("-");
		switch (msgContent[1]) {
		case "exams":
			return handleDeleteExamMessage(msg);
		case "questions":
			return handleDeleteQuestionMessage(msg);
		case "solvedexams":
			return handleSolvedExams(msg);

		}
		return null;
	}

	/**
	 * A method that deletes questions from the database from `questions` table
	 * 
	 * @author gal
	 * @param msg
	 *            type of QuestionsMessage
	 * @return simple message with ok if the server handle it or error message
	 *         otherwise
	 */
	private static AbstractMessage handleDeleteQuestionMessage(AbstractMessage msg) {
		QuestionsMessage recievedMessage = (QuestionsMessage) msg;
		Question deleteQuestion = recievedMessage.getQuestions().get(0);
		SetInDB deletesQuestion = new SetInDB();
		AbstractMessage sendMessage = (AbstractMessage) deletesQuestion.deleteTheQuestion(deleteQuestion);
		return sendMessage;
	}

	/**
	 * A method that deletes exams from the database from `exams` table
	 * 
	 * @author Omer Haimovich
	 * @param msg
	 *            type of ExamMessage
	 * @return simple message with ok if the server handle it or error message
	 *         otherwise
	 */
	private static AbstractMessage handleDeleteExamMessage(AbstractMessage msg) {
		ExamMessage recivedMessage = (ExamMessage) msg;
		Exam deleteExam = recivedMessage.getNewExam();
		SetInDB deletesExam = new SetInDB();
		AbstractMessage sendMessage = (AbstractMessage) deletesExam.deleteTheExam(deleteExam);
		return sendMessage;
	}

	/**
	 * 
	 * A method that is called when a client wants to add a new question to specific
	 * exam into the database for `questions in exam` table
	 * 
	 * @author Omer Haimovich
	 * @param msg
	 *            type of QuestionInExamMessage
	 * @return simple message with ok if the server handle it or error message
	 *         otherwise
	 */
	private static AbstractMessage handlePutQuestionInExamMessage(AbstractMessage msg) {
		QuestionInExamMessage recivedMessage = (QuestionInExamMessage) msg;
		String id = recivedMessage.getExamId();
		ArrayList<QuestionInExam> examQuestions = recivedMessage.getQuestionInExam();
		SetInDB putExam = new SetInDB();
		AbstractMessage sendMessage = (AbstractMessage) putExam.addQuestionToExam(id, examQuestions);
		return sendMessage;
	}

	/**
	 * 
	 * A method that is called when a client wants to add a new solved exam into the
	 * database for `solved exams` table
	 * 
	 * @author Naor Saadia
	 * @param msg
	 *            type of SolvedExamMessage
	 * @return simple message with ok if the server handle it or error message
	 *         otherwise
	 */
	private static AbstractMessage handlePutSolvedExamMessage(AbstractMessage msg) {
		SolvedExamMessage recivedMessage = (SolvedExamMessage) msg;
		examinees.removeDuration(recivedMessage.getSolvedExam().getExamID(), AES_Server.CLIENT);
		examinees.removeStudent(recivedMessage.getSolvedExam().getExamID(), AES_Server.CLIENT);
		SolvedExams newSolvedExam = recivedMessage.getSolvedExam();
		SetInDB putSolvedExam = new SetInDB();
		AbstractMessage sendMessage = (AbstractMessage) putSolvedExam.addSolvedExam(newSolvedExam);
		return sendMessage;

	}

	/**
	 * 
	 * A method that creates a new word file for a specific exam
	 * 
	 * @author Omer Haimovich
	 * @param msg
	 *            type of WordMessage
	 * @return simple message with ok if the server handle it or error message
	 *         otherwise
	 */
	private static AbstractMessage handleGetWord(AbstractMessage msg) {
		WordMessage recivedMessage = (WordMessage) msg;
		Exam newExam = recivedMessage.getExam();
		WordDocument create = new WordDocument(newExam);
		create.createDocument();
		SimpleMessage sendMessage = (SimpleMessage) message.getMessage("ok-get-word", null);
		return sendMessage;
	}

	/**
	 * 
	 * A method that is called when a client wants to add a execute exam into the
	 * database for `execute exam` table
	 * 
	 * @author Omer Haimovich
	 * @param msg
	 *            type of ExecuteExamMessage
	 * @return simple message with ok if the server handle it or error message
	 *         otherwise
	 */
	private static AbstractMessage handlePutExecuteExamMessage(AbstractMessage msg) {
		ExecuteExamMessage recivedMessage = (ExecuteExamMessage) msg;
		ExecuteExam newExecuteExam = recivedMessage.getNewExam();
		SetInDB putExecuteExam = new SetInDB();
		executedUsersManager.add(newExecuteExam.getExamId(), newExecuteExam.getStartTime()); //Send time to executedManager
		AbstractMessage sendMessage = (AbstractMessage) putExecuteExam.addExecuteExam(newExecuteExam);
		return sendMessage;
	}

	/**
	 * 
	 * A method that is called when the client wants to update a exam in the
	 * database
	 * 
	 * @author Omer Haimovich
	 * @param msg
	 *            type of ExamMessage
	 * @return simple message with ok if the server handle it or error message
	 *         otherwise
	 * 
	 */
	private static AbstractMessage handleSetExamMessage(AbstractMessage msg) {
		ExamMessage recivedMessage = (ExamMessage) msg;
		Exam newExam = recivedMessage.getNewExam();
		SetInDB setExam = new SetInDB();
		AbstractMessage sendMessage = (AbstractMessage) setExam.updateExam(newExam);
		return sendMessage;
	}

	/**
	 * 
	 * A method that is called when a client sends exam change time duration has
	 * been received
	 * 
	 * @author Naor Saadia
	 * @param msg
	 *            type of ChangeTimeDurationRequest
	 * @return simple message with ok if the server handle it or error message
	 *         otherwise
	 */
	private static void handleChangeTimeDurationRequest(AbstractMessage msg) {
		ChangeTimeDurationRequest cht = (ChangeTimeDurationRequest) msg;
		principles.sendAll((ChangeTimeDurationRequest) msg);
	}

	/**
	 * 
	 * A method that is called when a client sends exam change time duration that
	 * have been approved by the principal
	 * 
	 * @author Naor Saadia
	 * @param msg
	 *            type of ChangeTimeDurationRequest
	 * @return simple message with ok if the server handle it or error message
	 *         otherwise
	 */
	private static void handleChangeTimeConfirm(AbstractMessage msg) {
		ChangeTimeDurationRequest cht = (ChangeTimeDurationRequest) msg;
		examinees.sendAllDurations(cht.getExamId(), cht.getNewTime());
	}

	/**
	 * 
	 * A method that is called when a client wants to add a new solved manually exam
	 * into solved exam folder that is in the server
	 * 
	 * @author Omer Haimovich
	 * @param msg
	 *            type of WordMessage
	 * @return simple message with ok if the server handle it or error message
	 *         otherwise
	 */
	private static AbstractMessage handlePutwordExamMessage(AbstractMessage msg) {
		WordMessage newMessage = (WordMessage) msg;
		MyFile serverFile = newMessage.getNewFile();
		root.dao.message.MyFile wordFile = new root.dao.message.MyFile(serverFile.getFileName());
		String LocalfilePath = serverFile.getDescription();

		try {

			File newFile = new File(LocalfilePath);

			byte[] mybytearray = new byte[(int) newFile.length()];
			FileInputStream fis = new FileInputStream(newFile);
			BufferedInputStream bis = new BufferedInputStream(fis);
			wordFile.initArray(mybytearray.length);
			wordFile.setSize(mybytearray.length);
			bis.read(wordFile.getMybytearray(), 0, mybytearray.length);
			File Word = new File(PATH + serverFile.getFileName());
			FileOutputStream fos = new FileOutputStream(PATHSOLUTION + serverFile.getFileName());
			fos.write(wordFile.getMybytearray());
			fis.close();
			bis.close();
			SimpleMessage sendMessage = (SimpleMessage) message.getMessage("ok-put-wordexam", null);
			return sendMessage;
		} catch (Exception e) {
			System.out.println("Error send (Files)msg) to Server");
		}
		return null;
	}

	/**
	 * 
	 * A method that is called when a client wants to get a new manually exam from
	 * the execute exam folder that is in the server
	 * 
	 * @author Omer Haimovich
	 * @param msg
	 *            type of WordMessage
	 * @return simple message with ok if the server handle it or error message
	 *         otherwise
	 */
	private static AbstractMessage handleGetWordExam(AbstractMessage msg) {
		WordMessage recivedMessage = (WordMessage) msg;
		String[] userId = recivedMessage.getUserId().split("-");
		root.dao.message.MyFile wordFile = new root.dao.message.MyFile(userId[0] + "-" + userId[1] + ".docx");
		String LocalfilePath = PATH + userId[1] + ".docx";
		try {

			File newFile = new File(LocalfilePath);
			byte[] mybytearray = new byte[(int) newFile.length()];
			FileInputStream fis = new FileInputStream(newFile);
			BufferedInputStream bis = new BufferedInputStream(fis);
			wordFile.initArray(mybytearray.length);
			wordFile.setSize(mybytearray.length);
			bis.read(wordFile.getMybytearray(), 0, mybytearray.length);
			wordFile.setDescription(LocalfilePath);
			fis.close();
			bis.close();
			WordMessage sendMessage = (WordMessage) message.getMessage("put-wordexam", wordFile);
			return sendMessage;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error send (Files)msg) to Server");
		}
		return null;

	}

	/**
	 * A method that is called when the client wants to get specific CSV from the
	 * folder CSV exam that is in the server
	 * 
	 * @author gal
	 * @param msg
	 *            type of CsvMessage
	 * @return simple message with ok if the server handle it or error message
	 *         otherwise
	 */
	private static AbstractMessage handleGetCSVfromServer(AbstractMessage msg) {
		SimpleDateFormat monthDayYearformatter = new SimpleDateFormat("yyyy-MM-dd");
		CSVReader instace = CSVReader.getInstace();
		CsvMessage newMessage = (CsvMessage)msg;
		CsvDetails csv = newMessage.getCsv();
		SolvedExams solvedExam = csv.getSolvedExam();
		String date = monthDayYearformatter.format((java.util.Date) solvedExam.getExamDateTime());
		String path = PATHCSV + solvedExam.getExamID() + "-" + date;
		String pathInsideSolvedExamFolder = path + "/" + solvedExam.getSovingStudentID()+ ".csv";
		instace.setCsvFile(pathInsideSolvedExamFolder);
		System.out.println("Path to csv: "+pathInsideSolvedExamFolder);
		ArrayList<String[]> csvDATA = instace.readCSV();
		if (csvDATA != null)return (CsvMessage)message.getMessage("ok-get-csv", csvDATA);
		return new ErrorMessage(new Exception("Error,\nnot a valid csv path,\nplease check in:"+path+ ",\nthat csv name: "+solvedExam.getSovingStudentID()+",\nexist."));
	}

	/**
	 * 
	 * A method that creates a new CSV file for a specific exam
	 * 
	 * @author Omer Haimovich
	 * @param msg
	 *            type of CsvMessage
	 * @return simple message with ok if the server handle it or error message
	 *         otherwise
	 */

	private static AbstractMessage handleGetCsv(AbstractMessage msg) {
		int i = 0;
		SimpleMessage sendMessage;
		CSVWriter csvWriter = null;
		CsvMessage newMessage = (CsvMessage) msg;
		CsvDetails csv = newMessage.getCsv();
		Exam exam = csv.getExamId();
		User student = csv.getUserId();
		ArrayList<QuestionInExam> examQuestions = exam.getExamQuestions();
		Map<String, Integer> question = csv.getQuestionInExam();
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		date = sdf.format(new Date());
		QuestionInExam que = null;
		String path = PATHCSV + exam.getExamId() + "-" + date;
		new File(path).mkdirs();
		String s = path + "/";
		try {
			System.out.println(s);
			csvWriter = new CSVWriter(new FileWriter(s + student.getUserID()+".csv"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<String[]> toCsv = new ArrayList<String[]>();
		toCsv.add(new String[] { "QuestionID", "Selected answer", "Question grade" });
		Set<String> keys = question.keySet();
		for (String q : keys) {
			for (QuestionInExam eq : examQuestions) {
				if (eq.getQuestion().getQuestionId().equals(q)) {
					que = eq;
					break;
				}
			}
			toCsv.add(new String[] { q, Integer.toString(question.get(q)), Integer.toString(que.getQuestionGrade()) });
		}

		csvWriter.writeAll(toCsv);
		try {
			csvWriter.close();
			sendMessage = (SimpleMessage) message.getMessage("ok-get-csv", null);
			return sendMessage;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * 
	 * A method that returns all the executed exams
	 * 
	 * @author Naor Saadia
	 * @param msg
	 *            type of ExecutedExamsMessage
	 * @return abstract message includes array list of all executed exams
	 */

	private static AbstractMessage handleGetExecutedExams(AbstractMessage msg) {
		ExecutedExamsMessage executedMsg = (ExecutedExamsMessage) msg;
		GetFromDB get = new GetFromDB();
		ArrayList<ExecuteExam> arr = get.getExecutedExams(executedMsg.getTeacher());
		executedMsg.addExams(arr);
		return msg;

	}

	/**
	 * A method that deletes executed exams from the database from `execute exams`
	 * table
	 * 
	 * @author Naor Saadia
	 * @param msg
	 *            type of SimpleMessage
	 * @return simple message with ok if the server handle it or error message
	 *         otherwise
	 */
	public static AbstractMessage handleSolvedExams(AbstractMessage msg) {
		SimpleMessage simpleMsg = (SimpleMessage) msg;
		SetInDB set = new SetInDB();
		String simp = simpleMsg.getMsg().split("-")[2];
		set.deleteSolvedExam(simp);
		return msg;
	}
	/**
	 * 
	 * @param msg Assuming ExamStatsByIdDateMessage, countaining id and date for a statistics
	 * @return Message countaining the stats for that exam
	 */
	private static AbstractMessage handleExamStatsByIdDate(AbstractMessage msg) {
		GetFromDB getLines = new GetFromDB();
		Statistic data = getLines.getExamStatsByIdDate((ExamStatsByIdDateMessage) msg);
		return message.getMessage("ok-get-examstatsbyiddate", data);
	}

	/**
	 * A method handles request for exam table data lines, which is used to fill
	 * statistics table
	 * 
	 * @author Alon Ben-yosef
	 * @param msg
	 *            type of UserIDMessage include user id
	 * 
	 * @return abstract message datalines in case of success, null in case of
	 *         failures
	 */
	private static AbstractMessage handleExamTableDataLine(AbstractMessage msg) {
		GetFromDB getLines = new GetFromDB();
		ArrayList<ExamTableDataLine> data = getLines.getLinesByTeacherID(((UserIDMessage) msg).getId());
		return message.getMessage("ok-get-examtabledataline", data);
	}

}
