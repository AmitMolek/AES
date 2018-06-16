package root.server.managers.dbmgr;

import java.security.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import com.mysql.jdbc.Statement;

import root.dao.app.CheatingExamTest;
import root.dao.app.AlterDuration;
import root.dao.app.Course;
import root.dao.app.Exam;
import root.dao.app.ExecuteExam;
import root.dao.app.Question;
import root.dao.app.QuestionInExam;
import root.dao.app.SolvedExams;
import root.dao.app.Statistic;
import root.dao.app.Subject;
import root.dao.app.User;
import root.dao.message.ErrorMessage;
import root.server.AES_Server;
import root.util.log.Log;
import root.util.log.LogLine;

public class GetFromDB implements DbManagerInterface {
	private java.sql.Statement stmt;
	private Connection conn;
	private Log log;
	
	public GetFromDB() {
		super();
		conn = AES_Server.getConnection();
		log.getInstance();
	}

	@Override
	public ArrayList<Question> questions(String... str) {
		// TODO Auto-generated method stub
		// str contain a subject id
		ArrayList<Question> questions= new ArrayList<Question>();
		ResultSet rs;
		String QuestionQuery = "SELECT * FROM questions WHERE question_id LIKE '"+str[0]+"%'";
		try {
			stmt = conn.createStatement();
					rs = stmt.executeQuery(QuestionQuery+";");
					while(rs.next()) {
						questions.add(new Question(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6),rs.getString(7), rs.getInt(8),rs.getString(9)));
					}
					rs.close();
					return questions;			// Return A list of all users
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * question overLoading. in order to get questions by quesiton's ID
	 *  -> a result of a bad code solution in early staged of the project.
	 * @param questionsID
	 * @return
	 */
	public ArrayList<Question> questions(ArrayList<String> questionsID) {
		// TODO Auto-generated method stub
		// str contain a subject id
		ArrayList<Question> questions= new ArrayList<Question>();
		ResultSet rs;
		String QuestionQuery;
		try {
			stmt = conn.createStatement();
			for (String questionID: questionsID) {
				QuestionQuery = "SELECT * FROM questions WHERE question_id = "+questionID;
				rs = stmt.executeQuery(QuestionQuery+";");
				while(rs.next()) {
					questions.add(new Question(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6),rs.getString(7), rs.getInt(8),rs.getString(9)));
				}
				rs.close();
			}
			
			return questions;			// Return A list of all users
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
/**
 * @author gal
 * @param str can be null, and then all users will return, or str can contain a specific user ID
 */
	@Override
	public ArrayList<User> users(String... str) {
		ArrayList<User> users= new ArrayList<User>();	// needed fixing, add switch case: empty-all users, 1- specific user,2 only these users...
		ResultSet rs;
		String usersQuery =  "SELECT users.* FROM users";// fetch all users
		try {
			stmt = conn.createStatement();
			switch(str.length) {
				case 0:
					rs = stmt.executeQuery(usersQuery+";");
					while(rs.next()) {
						users.add(new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
					}
					rs.close();
					return users;			// Return A list of all users
				case 1:
					String getSpecificUser = " WHERE users.Users_ID = "+str[0]+";";
					//System.out.println(usersQuery+getSpecificUser);	// for debug - print the query
					rs = stmt.executeQuery(usersQuery+getSpecificUser);
					while(rs.next()) {
						users.add(new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
					}
					rs.close();
					return users;			// Return a list contain specific user
			}
		} catch (SQLException e) {
			log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
		}
		return null;
	}

	
	@Override
	public ArrayList<AlterDuration> alterDuration(String... str) {
		// TODO Auto-generated method stub
		return null;
	}

/*	@Override
	public ArrayList<Course> subjectOfTeacher(String... str) {
		// TODO Auto-generated method stub
		return null;
	}
*/
	/**
	 * @author gal
	 * @param str, if null, get all courses, else: get the course relevat to the desired condition.
	 */
	@Override
	public ArrayList<Course> courses(String... str) {
		ArrayList<Course> courses = new ArrayList<Course>();
		ResultSet rs;
		String selectAllCourses = "SELECT * FROM " + " `courses`";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(selectAllCourses);	// dumb initialize rs
			switch(str.length){
			case 0:
				//courseInSubjectQuery = courseInSubjectQuery + ";";
				rs = stmt.executeQuery(selectAllCourses+";");
				// by now, rs filled with result from wanted query.
				while(rs.next()) {
					courses.add(new Course(rs.getString(1),rs.getString(2)));
				}
				rs.close();
				return courses;

			// from here you can build any condition you want, relevant to any course related query, 
			// just enter string representing tables you want to want to involve. 
			// example:
			case 1:
				// in this case we have str with one string in it, let say we want to bring all courses for a specific subject:
				switch(str[0].length()) {
				case 2:// the length of subject ID
					String getSpecificUser =  ", `courses in subject`" + " WHERE `courses in subject`.subject_id = "+"\'"+str[0]+"\'" + " And  courses.course_id = `courses in subject`.course_id";
					//String courseInSubjectQuery = selectAllCourses+ getSpecificUser;
					rs = stmt.executeQuery(selectAllCourses+ getSpecificUser + ";");
					// by now, rs filled with result from wanted query.
					while(rs.next()) {
						courses.add(new Course(rs.getString(1),rs.getString(2)));
					}
					rs.close();
					return courses;
				}
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
		}
		return null;
	}

	/**
	 * @author Omer Haimovich
	 * @param str can be null, and then all courses in subject will return, or str can contain a specific user ID
	 */
	@Override
	public ArrayList<Course> coursesInSubject(String... str) {
		ArrayList<Course> courses = new ArrayList<Course>();
		ResultSet rs;
		String courseInSubjectQuery = "SELECT * FROM " + " `courses in subject`";
		try {
			stmt = conn.createStatement();
			switch(str.length){
			case 0:
				courseInSubjectQuery = courseInSubjectQuery + ";";
				break;
			case 1:
				String getSpecificUser =  ", courses" + " WHERE `courses in subject`.subject_id = "+"\'"+str[0]+"\'" + " And  courses.course_id = `courses in subject`.course_id" + ";";
				courseInSubjectQuery = courseInSubjectQuery+ getSpecificUser;
				break;	
			}
			rs = stmt.executeQuery(courseInSubjectQuery);
			while(rs.next()) {
				courses.add(new Course(rs.getString(2),rs.getString(4)));
			}
			rs.close();
			return courses;
		} catch (SQLException e) {
			e.printStackTrace();
			log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
		}
		return null;
	}

	@Override
	public ArrayList<Exam> exams(String... str) {
		ArrayList<Exam> exams = new ArrayList<Exam>();
		ArrayList<User> newUsers = new ArrayList<User>();
		ArrayList<QuestionInExam> questions = new ArrayList<QuestionInExam>();
		User teacher;
		ResultSet rs;
		String examQuery = "SELECT * FROM exams WHERE exam_id LIKE '"+str[0]+"%'";
		try {
			stmt = conn.createStatement();
					rs = stmt.executeQuery(examQuery+";");
					while(rs.next()) {
						newUsers = users(rs.getString(2));
						teacher = newUsers.get(0);
						questions = questionInExam(str[0]);
						exams.add(new Exam(rs.getString(1), teacher,rs.getInt(3),questions));
					}
					rs.close();
					return exams;			// Return A list of all users
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @autor Naor Saadia
	 * @param pass
	 * @return
	 */
	public ArrayList<Exam> getExamByPassword(String pass)
	{
		String byPassword = "SELECT ex.exam_id as exid,\r\n" + 
				"	ex.teacher_assembler_id as teacher_id,\r\n" + 
				"    ex.exam_original_allocated_duration as duration,\r\n" + 
				"    qie.exam_ID as exid,\r\n" + 
				"    qie.Question_ID as qid,\r\n" + 
				"    qie.Question_Grade as grade,\r\n" + 
				"    qie.Question_Free_text_Student as student_notes,\r\n" + 
				"    qie.Question_Free_text_Teacher as teacher_notes,\r\n" + 
				"    q.question_id as qid,\r\n" + 
				"    q.question_text as qtext,\r\n" + 
				"    q.question_instruction as inst,\r\n" + 
				"    q.question_answer_1 as ans1,\r\n" + 
				"    q.question_answer_2 as ans2,\r\n" + 
				"    q.question_answer_3 as ans3,\r\n" + 
				"    q.question_answer_4 as ans4,\r\n" + 
				"    q.correct_question as corAns,\r\n" +
				"    execute.exam_date_start as date,\r\n" + 
				"    execute.exam_type as type\r\n" + 
				"\r\n" + 
				"FROM exams ex, `questions in exam` qie, questions q, `execute exams` execute\r\n" + 
				"				WHERE execute.four_Digit="+pass+"\r\n" + 
				"				AND qie.exam_ID=ex.exam_Id\r\n" + 
				"				AND execute.exam_id=ex.exam_Id\r\n" +
				"				AND q.question_id = qie.Question_ID;";
		ResultSet rs;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(byPassword);
			Exam exam=null;
			ArrayList<Exam> exams = null;
			if(rs.next())
			{
			
				exam= new Exam(rs.getString("exid"),rs.getString("teacher_id")
						,rs.getInt("duration"));
			exam.setExecuteExam(new ExecuteExam(rs.getString("exid"),rs.getString("date"),pass,rs.getString("type")));	
			rs.previous();
			exams = new ArrayList<Exam>();
			exams.add(exam);
			ArrayList<QuestionInExam> questionsInExam = new ArrayList<QuestionInExam>();
			QuestionInExam q;
			while(rs.next()) {
				 q = new QuestionInExam(new Question(rs.getString("qid"),rs.getString("qtext"),
						 rs.getString("inst"), rs.getString("ans1"),
						 rs.getString("ans2"),rs.getString("ans3"),
						 rs.getString("ans4"),
						 rs.getInt("corAns"),rs.getString("teacher_id")),rs.getInt("grade"),rs.getString("teacher_notes"), rs.getString("student_notes"));
			     questionsInExam.add(q);
			}
			exam.setExamQuestions(questionsInExam);
			}
			return exams;

		} catch (SQLException e) {
			//log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
			e.printStackTrace();
			return null;
		}
		
	
	}
	
	public ArrayList<ExecuteExam> getExecutedExams() {
		ArrayList<ExecuteExam> arr = new ArrayList<ExecuteExam>();
		String getExecuted = "select exe.exam_id as id,\r\n" + 
				"exe.exam_date_start as start_date,\r\n" + 
				"exe.four_Digit as pass,\r\n" + 
				"exe.exam_type as examType,\r\n" + 
				"ex.exam_original_allocated_duration as duration\r\n" + 
				"from `execute exams` exe,exams ex\r\n" + 
				"where exe.exam_id=ex.exam_id;";
		ResultSet rs;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(getExecuted);
			while(rs.next()) {
				ExecuteExam ex = new ExecuteExam(rs.getString("id"),rs.getDate("start_date").toString(), rs.getString("pass"), rs.getString("examType"), rs.getInt("duration"));
				arr.add(ex);
			}
			return arr;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arr;		
	}

	/**
	 * @author Omer Haimovich
	 * @param str can be null, and then all subjects of teacher wil return, or str can contain a specific user ID
	 */
	@Override
	public ArrayList<Subject> subjects(String... str) {
		ArrayList<Subject> userSubjects = new ArrayList<Subject>();
		ResultSet rs;
		String SubjectsQuery =  "SELECT * FROM subjects";// fetch all subjects
		try {
			stmt = conn.createStatement();
			switch(str.length) {
				case 0: // if str empty retrieve all subject
					rs = stmt.executeQuery(SubjectsQuery+";");
					while(rs.next()) {
						userSubjects.add(new Subject(rs.getString(1), rs.getString(2)));
					}
					rs.close();
					return userSubjects;			// Return A list of all subjects
				case 1:		// if str contain user id retrieve only his subjects (jubjectsTabl join subjectofTeacherTable)
					/**
					 * @author gal
					 * given a teacher id, this query will return all his teaching subjects
					 */
					String userSubjectsQuery = " WHERE subjects.subject_id in (SELECT `subject a teacher teach`.subject_ID FROM `subject a teacher teach` WHERE `subject a teacher teach`.teacher_ID = "+str[0]+");";
					rs = stmt.executeQuery(SubjectsQuery+userSubjectsQuery);
					while(rs.next()) {
						userSubjects.add(new Subject(rs.getString(1), rs.getString(2)));
					}
					rs.close();
					return userSubjects;			// Return A list of all subjects a teacher teach
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
		}
		return null;
	}
		

	@Override
	public ArrayList<Statistic> solvedExamStatistic(String... str) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @author gal
	 * @param str - size== 0: retreive all solved exams, if size == 9, retrieve all solvedExams of this userID, if size==6, retrieve all solved exams of this specific
	 */
	@Override
	public ArrayList<SolvedExams> solvedExams(String... str) {
		ArrayList<SolvedExams> solvedExams = new ArrayList<SolvedExams>();
		ResultSet rs;
		String SubjectsQuery =  "SELECT * FROM `solved exams`";// fetch all subjects
		try {
			stmt = conn.createStatement();
			switch(str.length) {
				case 0: // if str empty retrieve all solvedExams
					rs = stmt.executeQuery(SubjectsQuery+";");
					while(rs.next()) {
//						1private String examID;
//						2private String sovingStudentID;
//						3private int examGrade;
//						4private int solveDurationTime;
//						5private String submittedOrInterruptedFlag;
//						6private timeStamp examDateTime;
//						7private String teacherNotes;
//						8private String gradeAlturationExplanation;
//						9private String approvingTeacherID;
//						10private String calculatedGradeApprovalStateByTeacher;
//						11private String cheatingFlag;
						
						
						solvedExams.add(new SolvedExams(rs.getString(2), rs.getString(1), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getTimestamp(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11)) );
					}
					rs.close();
					return solvedExams;				// Return A list of all solved exams
				case (1):		// if str.length == 1, check if its userID or examID
					switch (str[0].length()) {
						case(9):// if str.length==9, str contain user id retrieve only his SolvedExams
							/**
							 * @author gal
							 * given a student id, this query will return all his solved exams.
							 */
							String userSolvedExamsQuery = " WHERE `solved exams`.`User_ID` in (SELECT `solved exams`.`User_ID` FROM `solved exams` WHERE  `solved exams`.`User_ID` = "+str[0]+");";
							rs = stmt.executeQuery(SubjectsQuery+userSolvedExamsQuery);
							while(rs.next()) {
								solvedExams.add(new SolvedExams(rs.getString(2), rs.getString(1), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getTimestamp(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11)) );
							}
							rs.close();
							return solvedExams;			// Return A list of all solved Exams of this USERID
						case (6):	// if str.length==6, str contain Exams ID. retrieve ll solved exams of this Exam's ID
							/**
							 * @author gal
							 * given a Exam id, this query will return all solved exams relevant for this exam's ID.
							 */
							String examsSolvedExamsQuery = " WHERE `solved exams`.`exam_ID` in (SELECT `solved exams`.`exam_ID` FROM `solved exams` WHERE  `solved exams`.`exam_ID` = "+str[0]+");";
							rs = stmt.executeQuery(SubjectsQuery+examsSolvedExamsQuery);
							while(rs.next()) {
								solvedExams.add(new SolvedExams(rs.getString(2), rs.getString(1), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getTimestamp(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11)) );
							}
							rs.close();
							return solvedExams;			// Return A list of all subjects a teacher teach	
						default:
							break;
					}// end inner switch
				}// end switch			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
		}
		return null;
	}

	@Override
	public ArrayList<QuestionInExam> questionInExam(String... str) {
		ArrayList<QuestionInExam> question = new ArrayList<QuestionInExam>();
		ArrayList<String>ids = new ArrayList<String>();
		ResultSet rs;
		int i=0;
		Question q = null;
		String examQuery = "SELECT * FROM `questions in exam` WHERE exam_ID LIKE '"+str[0]+"%'";
		try {
			stmt = conn.createStatement();
					rs = stmt.executeQuery(examQuery+";");
					while(rs.next()) {
						 ids.add(rs.getString(2));
						 question.add(new QuestionInExam(null,rs.getInt(3),rs.getString(5),rs.getString(4)));
					}
					for(String s: ids)
					{
						q = returnQuestion(s);
						QuestionInExam eq = question.get(i);
						eq.setQuestion(q);
						question.set(i, eq);
						i++;
						
					}
					rs.close();
					return question;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<Course> courseOfTeacher(String... str) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Question returnQuestion(String id) {
		String QuestionQuery = "SELECT * FROM questions WHERE question_id = "+id;
		ResultSet qs;
		Question q = null;
		try {
			qs = stmt.executeQuery(QuestionQuery+";");
			while(qs.next()) {
				 q = new Question(qs.getString(1),qs.getString(2),qs.getString(3),qs.getString(4),qs.getString(5),qs.getString(6),qs.getString(7),qs.getInt(8),qs.getString(9));
			}
			qs.close();
			return q;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
}