package root.server.managers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.opencsv.CSVWriter;
import root.client.controllers.QuestionInExamObject;
import root.dao.app.CheatingExamTest;
import com.sun.javafx.geom.transform.GeneralTransform3D;
//import root.client.controllers.TestGradesTeacherController;
import root.dao.app.Course;
import root.dao.app.CsvDetails;
import root.dao.app.Exam;
import root.dao.app.ExecuteExam;
import root.dao.app.LoginInfo;
import root.dao.app.Question;
import root.dao.app.QuestionInExam;
import root.dao.app.SolvedExams;
import root.dao.app.Subject;
import root.dao.app.User;
import root.dao.app.UserInfo;
import root.dao.message.AbstractMessage;
import root.dao.message.CheatingExamsTestMessage;
import root.dao.message.ChangeTimeDurationRequest;
import root.dao.message.CourseMessage;
import root.dao.message.CsvMessage;
import root.dao.message.ErrorMessage;
import root.dao.message.ExamMessage;
import root.dao.message.ExecuteExamMessage;
import root.dao.message.ExecutedExamsMessage;
import root.dao.message.LoggedOutMessage;
import root.dao.message.LoginMessage;
import root.dao.message.MessageFactory;
import root.dao.message.MyFile;
import root.dao.message.QuestionInExamMessage;
import root.dao.message.QuestionsMessage;
import root.dao.message.SimpleMessage;
import root.dao.message.SolvedExamMessage;
import root.dao.message.SubjectMessage;
import root.dao.message.UserInfoMessage;
import root.dao.message.UserIDMessage;
import root.dao.message.UserMessage;
import root.dao.message.UserSolvedExamsMessage;
import root.dao.message.UserSubjectMessage;
import root.dao.message.WordMessage;
import root.server.AES_Server;
import root.server.managers.dbmgr.GetFromDB;
import root.server.managers.dbmgr.SetInDB;
import root.server.managers.usersmgr.ExamExecutedManager;
import root.server.managers.usersmgr.ExecuteStudentManager;
import root.server.managers.usersmgr.PrincipleManager;
import root.util.worddocumentmgr.WordDocument;
import root.util.CSVReader;

public class ServerMessageManager {
	private static ExecuteStudentManager examinees = new ExecuteStudentManager();
	private static PrincipleManager principles=new PrincipleManager();
	private static ServerMessageManager instance=null;
	private static MessageFactory message = MessageFactory.getInstance();;
	private static LoggedInUsersManager usersManager = LoggedInUsersManager.getInstance();
	public static String PATH;
	public static String PATHSOLUTION;
	public static String PATHCSV;
	public static ExamExecutedManager executedUsersManager = new ExamExecutedManager();
	
	private ServerMessageManager() {
		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
		String fullPath = s+"//src//root//server//executeExam//";
		PATH = fullPath;
		PATHSOLUTION = s+"//src//root//server//solvedExam//";
		PATHCSV = s+ "//src//root//server//csvExam//";
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
		case "changetimeduration":
			return handleChangeTimeDurationRequest(msg);
		case "confirmchangeduration":
			return handleChangeTimeConfirm(msg);
		default:
			return null;
		}
	}

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
		case "exams":
			return handleSetExamMessage(msg);
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
		String questionMgs = questionMessage.getMsg();
		GetFromDB getQuestions = new GetFromDB();
		ArrayList<Question> questions = new ArrayList<Question>();
		if (questionMgs.split("-").length == 3) {	// here well be getting questions by question ID, optionaly to add other parameters to this switch/case
			switch (questionMgs.split("-")[2]) {
			case "questionid":
				questions = getQuestions.questions(questionMessage.getQuestionID());
				break;
			default:
				break;
			}
		}
		else {	// get questions by a subjectID.
			questions = getQuestions.questions(questionMessage.getThisQuestionsSubject().getSubjectID());
		}
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


	private static AbstractMessage handleGetCheatingExamsTest(AbstractMessage msg) {
		CheatingExamsTestMessage examsMsg = (CheatingExamsTestMessage)msg;
		GetFromDB getExams = new GetFromDB();
		//ArrayList<CheatingExamTest> dbExams = getExams.solvedExamCheatingTest(examsMsg.getExam_id());
		
		//examsMsg.setExams(dbExams);
		return message.getOkGetMessage("ok-get-cheatingexamstest".split("-"), examsMsg);
	}
	
	 /** 
	 * @param msg type of get message
	 * @return new message for client
	 */
	private static AbstractMessage handleGetMessage(AbstractMessage msg) {
		String[] msgContent = msg.getMsg().toLowerCase().split("-");
		switch(msgContent[1]) {
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
				return handleFetUserMessage(msgContent,msg);
			case "cheatingexamstest":
				return handleGetCheatingExamsTest(msg);
			case "solvedexams":
				return handleGetSolvedExams(msgContent,msg);		// this methos handeles all Get requests from solvedExams table
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
		}
		
		return null;
	}
	
	/**
	 * @author gal
	 * this method called when have map<courseID,courseName> and want to fill it with course names.
	 * @param msg
	 * @return
	 */
	private static AbstractMessage handleGetCourseByCourseIDMessage(AbstractMessage msg) {
		CourseMessage recivedMessage = (CourseMessage) msg;
		//String teacherId = recivedMessage.getTeacherId();
		GetFromDB getCourse = new GetFromDB();
		ArrayList<Course> courses = getCourse.courses();	// fetch all subjects from DB/
		HashMap<String, String> courseMap = recivedMessage.getCourseMap();
		for (Course course: courses) {
			if (courseMap.containsKey(course.getCourseId())){
				courseMap.put(course.getCourseId(), course.getCourseName());
			}
		}
		return message.getMessage("ok-get-courses", courseMap);
	}

	/**
	 * @author gal
	 * this method called when have map<subjectID,subectName> and want to fill it with subject names.
	 * @param msg
	 * @return
	 */
	private static AbstractMessage handleGetSubjectBySubjectIDMessage(AbstractMessage msg) {
		SubjectMessage recivedMessage = (SubjectMessage) msg;
		//String teacherId = recivedMessage.getTeacherId();
		GetFromDB getSubject = new GetFromDB();
		ArrayList<Subject> subjects = getSubject.subjects();	// fetch all subjects from DB/
		HashMap<String, String> subjectMap = recivedMessage.getSubjectsMap();
		for (Subject subject: subjects) {
			if (subjectMap.containsKey(subject.getSubjectID())){
				subjectMap.put(subject.getSubjectID(), subject.getSubjectName());
			}
		}
		return message.getMessage("ok-get-subjects", subjectMap);
	}

	/**
	 * @author gal
	 * this methos called when a client need to get his solved exams
	 * @param msg
	 * @return
	 */
	private static AbstractMessage handleGetSolvedExams(String[] msgContent,AbstractMessage msg) {
		switch (msgContent[2]) {
		case "user":
			return getUserSolvedExambyUserID(msg);
//		case "exam":
//			return getUserSolvedExambyExamID(msg);
		}

		return null;
	}

	private static AbstractMessage getUserSolvedExambyUserID(AbstractMessage msg) {
		UserSolvedExamsMessage userSolvedExamsMessage = (UserSolvedExamsMessage)msg;
		GetFromDB getUserSolvedExams = new GetFromDB();
		ArrayList<SolvedExams> userSolvedExams = getUserSolvedExams.solvedExams(userSolvedExamsMessage.getUser().getUserID());			// get all solvedExams of specific userID
		return message.getMessage("ok-get-solvedExams",userSolvedExams);
	}

	/**
	 * this method is called when a client need to get information about users
	 * @author gal
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
						if(user.getUserPremission().equals("Principal"))
						{
							principles.addPrinciple(AES_Server.CLIENT);
						}
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
		case "solvedexams": 
			return handlePutSolvedExamMessage(msg);
		case "executeexam":
			return handlePutExecuteExamMessage(msg);
		case "wordexam":
			return handlePutwordExamMessage(msg);
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
				{
					if(executedUsersManager.isContains(exams.get(0).getExamId(), msgContent[4]))
						return new ErrorMessage(new NullPointerException("Sorry, this exam has already been submitted"));
					executedUsersManager.add(exams.get(0).getExamId(), msgContent[4]);
					Exam e = exams.get(0);
					examinees.addStudent(e.getExamId(),AES_Server.CLIENT);
					ExamMessage examMsg = (ExamMessage) message.getMessage("ok-get-exams", exams);
					return examMsg;
				}
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
		case "solvedexams":
			return handleSolvedExams(msg);

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
	
	private static AbstractMessage handlePutSolvedExamMessage(AbstractMessage msg) {
		SolvedExamMessage recivedMessage = (SolvedExamMessage)msg;
		SolvedExams newSolvedExam = recivedMessage.getSolvedExam();
		SetInDB putSolvedExam = new SetInDB();
		AbstractMessage sendMessage = (AbstractMessage) putSolvedExam.addSolvedExam(newSolvedExam);
		return sendMessage;
		
	}
	private static AbstractMessage handleGetWord(AbstractMessage msg) {
		WordMessage recivedMessage = (WordMessage)msg;
		Exam newExam = recivedMessage.getExam();
		WordDocument create = new WordDocument(newExam);
		create.createDocument();
		SimpleMessage sendMessage = (SimpleMessage)message.getMessage("ok-get-word", null);
		return sendMessage; 
	}
	
	private static  AbstractMessage handlePutExecuteExamMessage(AbstractMessage msg) {
		ExecuteExamMessage recivedMessage = (ExecuteExamMessage)msg;
		ExecuteExam newExecuteExam = recivedMessage.getNewExam();
		SetInDB putExecuteExam = new SetInDB();
		AbstractMessage sendMessage = (AbstractMessage) putExecuteExam.addExecuteExam(newExecuteExam);
		return sendMessage;
	}
	
	private static AbstractMessage handleSetExamMessage(AbstractMessage msg) {
		ExamMessage recivedMessage = (ExamMessage)msg;
		Exam newExam = recivedMessage.getNewExam();
		SetInDB setExam = new SetInDB();
		AbstractMessage sendMessage = (AbstractMessage) setExam.updateExam(newExam);
		return sendMessage;
	}

	private static AbstractMessage handleChangeTimeDurationRequest(AbstractMessage msg) {
		ChangeTimeDurationRequest cht=(ChangeTimeDurationRequest) msg;
		principles.sendAll((ChangeTimeDurationRequest) msg);
		return message.getMessage("SimpleMessage", null) ;
	}
	
	private static AbstractMessage handleChangeTimeConfirm(AbstractMessage msg) {
		ChangeTimeDurationRequest cht=(ChangeTimeDurationRequest) msg;
		examinees.sendAll(cht.getExamId(),cht.getNewTime());
		return message.getMessage("SimpleMessage", null) ;		
	}

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
	
	private static AbstractMessage handleGetWordExam(AbstractMessage msg) {
		WordMessage recivedMessage = (WordMessage) msg;
		String[] userId = recivedMessage.getUserId().split("-");
		root.dao.message.MyFile wordFile = new root.dao.message.MyFile(userId[0] + "-" + userId[1] + ".docx");
		/*
		 * String LocalfilePath = "C:" +
		 * "\\" + "Users" + "\\" + "omer1" + "\\" + "git" + "\\" + "AES" + "\\" + "
		 * AES_G4" +
		 * "\\" + "src" + "\\" + "root" + "\\" + "server" + "\\" + "executeExam" + "
		 * \\"+ userId[1]+ ".docx";
		 */
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
	 * call this message to get a specific solvedExam's CSV from server 
	 * @author gal
	 * @param msg
	 * @return
	 */
	private static AbstractMessage handleGetCSVfromServer(AbstractMessage msg) {
		SimpleDateFormat monthDayYearformatter = new SimpleDateFormat("yyyy-MM-dd");
		CSVReader instace = CSVReader.getInstace();
		CsvMessage newMessage = (CsvMessage)msg;
		CsvDetails csv = newMessage.getCsv();
		SolvedExams solvedExam = csv.getSolvedExam();
		String date = monthDayYearformatter.format((java.util.Date) solvedExam.getExamDateTime());
		String path = PATHCSV +solvedExam.getExamID() + "-" + date;
		String pathInsideSolvedExamFolder = path + "//" + solvedExam.getSovingStudentID()+ ".csv";
		instace.setCsvFile(pathInsideSolvedExamFolder);
		ArrayList<String[]> csvDATA = instace.readCSV();		// "readCSV" return ArrayList<String[]>, than save it inside newMessage.
		return (CsvMessage)message.getMessage("ok-get-csv", csvDATA);
	}
	
	private static AbstractMessage handleGetCsv(AbstractMessage msg) {
		int i = 0;
		SimpleMessage sendMessage;
		CSVWriter csvWriter = null;
		CsvMessage newMessage = (CsvMessage)msg;
		CsvDetails csv = newMessage.getCsv();
		Exam exam = csv.getExamId();
		User student = csv.getUserId();
		ArrayList<QuestionInExam> examQuestions = exam.getExamQuestions();
		Map<String,Integer> question = csv.getQuestionInExam();
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		date = sdf.format(new Date());
		QuestionInExam que = null;
		String path = PATHCSV +exam.getExamId() + "-" + date;
		new File(path).mkdirs();
		String s = path + "//";
		try {
			csvWriter = new CSVWriter(new FileWriter(s + student.getUserID()+".csv"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<String[]>toCsv = new ArrayList<String[]>();
		toCsv.add(new String[] {"QuestionID","Selected answer","Question grade"});
		Set<String> keys = question.keySet();
		for(String q: keys) {
			for(QuestionInExam eq: examQuestions) {
				if(eq.getQuestion().getQuestionId().equals(q)) {
					que = eq;
					break;
				}			
			}
			toCsv.add(new String[] {q,Integer.toString(question.get(q)),Integer.toString(que.getQuestionGrade())});
		}
	
		csvWriter.writeAll(toCsv);
        try {
			csvWriter.close();
			sendMessage = (SimpleMessage)message.getMessage("ok-get-csv", null);
			return sendMessage;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return null;	
	}
	
	private static AbstractMessage handleGetExecutedExams(AbstractMessage msg) {
		ExecutedExamsMessage executedMsg = (ExecutedExamsMessage)msg;
		GetFromDB get = new GetFromDB();
		ArrayList<ExecuteExam> arr = get.getExecutedExams();
		executedMsg.addExams(arr);
		return msg;
		
	}

	public static AbstractMessage handleSolvedExams(AbstractMessage msg) {
		SimpleMessage simpleMsg = (SimpleMessage)msg;
		SetInDB set = new SetInDB();
		String simp = simpleMsg.getMsg().split("-")[2];
		set.deleteSolvedExam(simp);
		return msg;	
	}
}
