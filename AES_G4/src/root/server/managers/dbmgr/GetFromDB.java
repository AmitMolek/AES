package root.server.managers.dbmgr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import com.mysql.jdbc.Statement;

import root.dao.app.AlterDuration;
import root.dao.app.Course;
import root.dao.app.Exam;
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
	@Override
	public ArrayList<Course> courses(String... str) {
		// TODO Auto-generated method stub
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
				"    q.correct_question as corAns\r\n" + 
				"\r\n" + 
				"FROM exams ex, `questions in exam` qie, questions q\r\n" + 
				"				WHERE ex.four_Digit="+pass+"\r\n" + 
				"				AND qie.exam_ID=ex.exam_Id\r\n" + 
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
			log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
			e.printStackTrace();
			return null;
		}
		
	
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

	@Override
	public ArrayList<SolvedExams> solvedExams(String... str) {
		// TODO Auto-generated method stub
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