package root.server.managers.dbmgr;

import java.lang.reflect.Array;
import java.security.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import com.mysql.jdbc.Statement;

import root.dao.app.CheatingExamTest;
import root.dao.app.AlterDuration;
import root.dao.app.Course;
import root.dao.app.CourseInSubject;
import root.dao.app.Exam;
import root.dao.app.ExamTableDataLine;
import root.dao.app.ExecuteExam;
import root.dao.app.Question;
import root.dao.app.QuestionInExam;
import root.dao.app.SolvedExams;
import root.dao.app.Statistic;
import root.dao.app.Subject;
import root.dao.app.User;
import root.dao.message.ErrorMessage;
import root.dao.message.ExamStatsByIdDateMessage;
import root.server.AES_Server;
import root.util.log.Log;
import root.util.log.LogLine;

/**
 * The GetFromDB is a class that implements the DbManagerInterface interface
 * This class responsible for receiving information from the database
 * 
 * @author Omer Haimovich
 *
 */
public class GetFromDB implements DbManagerInterface {

	// Instance variables **********************************************

	/**
	 * A sentence designed for queries
	 */
	private java.sql.Statement stmt;
	/**
	 * 
	 * Connection between the database and the server
	 */
	private Connection conn;

	/**
	 * 
	 * A log file that is responsible for documenting the actions performed in the
	 * application
	 */
	private Log log;

	// CONSTRUCTORS *****************************************************

	/**
	 * Constructs the GetFromDB
	 */
	public GetFromDB() {
		super();
		conn = AES_Server.getConnection();
		log.getInstance();
	}

	// OVERRIDDEN METHODS *************************************************

	/**
	 * A method that returns all questions from the database when given question id
	 * 
	 * @author gal
	 * @param str
	 *            can be null, and then all users will return, or str can contain a
	 *            specific user ID
	 * @return A list of all questions
	 */
	@Override
	public ArrayList<Question> questions(String... str) {
		// str contain a subject id
		ArrayList<Question> questions = new ArrayList<Question>();
		ResultSet rs;
		String QuestionQuery = "SELECT * FROM questions WHERE question_id LIKE '" + str[0] + "%'";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(QuestionQuery + ";");
			while (rs.next()) {
				questions.add(new Question(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8), rs.getString(9)));
			}
			rs.close();
			return questions; // Return A list of all users
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * question overLoading. in order to get questions by quesiton's ID -> a result
	 * of a bad code solution in early staged of the project.
	 * 
	 * @param questionsID
	 * @return A list of all the questions
	 */
	public ArrayList<Question> questions(ArrayList<String> questionsID) {
		// str contain a subject id
		ArrayList<Question> questions = new ArrayList<Question>();
		ResultSet rs;
		String QuestionQuery;
		try {
			stmt = conn.createStatement();
			for (String questionID : questionsID) {
				QuestionQuery = "SELECT * FROM questions WHERE question_id = " + questionID;
				rs = stmt.executeQuery(QuestionQuery + ";");
				while (rs.next()) {
					questions.add(new Question(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
							rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8), rs.getString(9)));
				}
				rs.close();
			}

			return questions; // Return A list of all users
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * A method that returns all users from the database or only one specific user
	 * (when user id is given)
	 * 
	 * @author gal
	 * @param str
	 *            can be null, and then all users will return, or str can contain a
	 *            specific user ID
	 * @return A list of all users
	 */
	@Override
	public ArrayList<User> users(String... str) {
		ArrayList<User> users = new ArrayList<User>(); // needed fixing, add switch case: empty-all users, 1- specific
														// user,2 only these users...
		ResultSet rs;
		String usersQuery = "SELECT users.* FROM users";// fetch all users
		try {
			stmt = conn.createStatement();
			switch (str.length) {
			case 0:
				rs = stmt.executeQuery(usersQuery + ";");
				while (rs.next()) {
					users.add(new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
							rs.getString(5)));
				}
				rs.close();
				return users; // Return A list of all users
			case 1:
				String getSpecificUser = " WHERE users.Users_ID = " + str[0] + ";";
				// System.out.println(usersQuery+getSpecificUser); // for debug - print the
				// query
				rs = stmt.executeQuery(usersQuery + getSpecificUser);
				while (rs.next()) {
					users.add(new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
							rs.getString(5)));
				}
				rs.close();
				return users; // Return a list contain specific user
			}
		} catch (SQLException e) {
			log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
		} finally {
			return users;
		}

	}

	/**
	 * A method that returns all the time changes made in the exams from the
	 * database
	 * 
	 * @author Omer Haimovich
	 * @param str
	 *            can be null, and then all users will return, or str can contain a
	 *            specific user ID or every or any other condition
	 * @return A list of all the time changes made in the exams
	 */
	@Override
	public ArrayList<AlterDuration> alterDuration(String... str) {
		ArrayList<AlterDuration> list = new ArrayList<AlterDuration>();
		ResultSet rs;
		if(str==null) {
			String query = "SELECT * FROM aes.`alter duration request`;";
			try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(query);
				while(rs.next()) {
					list.add(new AlterDuration(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7)));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * A method that returns courses from the database
	 * 
	 * @author gal
	 * @param str,
	 *            if null, get all courses, else: get the course relevant to the
	 *            desired condition.
	 * @return A list of all courses
	 */
	@Override
	public ArrayList<Course> courses(String... str) {
		ArrayList<Course> courses = new ArrayList<Course>();
		ResultSet rs;
		String selectAllCourses = "SELECT * FROM " + " `courses`";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(selectAllCourses); // dumb initialize rs
			switch (str.length) {
			case 0:
				// courseInSubjectQuery = courseInSubjectQuery + ";";
				rs = stmt.executeQuery(selectAllCourses + ";");
				// by now, rs filled with result from wanted query.
				while (rs.next()) {
					courses.add(new Course(rs.getString(1), rs.getString(2)));
				}
				rs.close();
				return courses;

			// from here you can build any condition you want, relevant to any course
			// related query,
			// just enter string representing tables you want to want to involve.
			// example:
			case 1:
				// in this case we have str with one string in it, let say we want to bring all
				// courses for a specific subject:
				switch (str[0].length()) {
				case 2:// the length of subject ID
					String getSpecificUser = ", `courses in subject`" + " WHERE `courses in subject`.subject_id = "
							+ "\'" + str[0] + "\'" + " And  courses.course_id = `courses in subject`.course_id";
					// String courseInSubjectQuery = selectAllCourses+ getSpecificUser;
					rs = stmt.executeQuery(selectAllCourses + getSpecificUser + ";");
					// by now, rs filled with result from wanted query.
					while (rs.next()) {
						courses.add(new Course(rs.getString(1), rs.getString(2)));
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
	 * A method that returns all courses that belong to a particular subject
	 * 
	 * @author Omer Haimovich
	 * @param str
	 *            can be null, and then all courses in subject will return, or str
	 *            can contain a specific subject id
	 * 
	 * 
	 * @return A list of all courses that belong to a particular subject
	 */
	@Override
	public ArrayList<Course> coursesInSubject(String... str) {
		ArrayList<Course> courses = new ArrayList<Course>();
		ResultSet rs;
		String courseInSubjectQuery = "SELECT * FROM " + " `courses in subject`";
		try {
			stmt = conn.createStatement();
			switch (str.length) {
			case 0:
				courseInSubjectQuery = courseInSubjectQuery + ";";
				break;
			case 1:
				String getSpecificUser = ", courses" + " WHERE `courses in subject`.subject_id = " + "\'" + str[0]
						+ "\'" + " And  courses.course_id = `courses in subject`.course_id" + ";";
				courseInSubjectQuery = courseInSubjectQuery + getSpecificUser;
				break;
			}
			rs = stmt.executeQuery(courseInSubjectQuery);
			while (rs.next()) {
				courses.add(new Course(rs.getString(2), rs.getString(4)));
			}
			rs.close();
			return courses;
		} catch (SQLException e) {
			e.printStackTrace();
			log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
		}
		return null;
	}

	/**
	 * 
	 * 
	 * A method that returns solved exams belonging to a specific exam id held on a
	 * specific date from the database
	 * 
	 * @param exam_id
	 *            the exam id
	 * @param date
	 *            the date of the exam
	 * @return A list of all the exams of a particular exam on a given date
	 * @author Amit Molek
	 */
	@SuppressWarnings("deprecation")
	public ArrayList<CheatingExamTest> solvedExamCheatingTest(String exam_id, Date date) {
		ArrayList<CheatingExamTest> exams = new ArrayList<>();
		ResultSet rs;

		String examDate = String.format("%d-%02d-%d", date.getYear(), date.getMonth(), date.getDate());
		String solvedExamsSqlQuery = "SELECT * FROM `solved exams` WHERE exam_ID LIKE '" + exam_id
				+ "%' AND exam_executing_Date LIKE '" + examDate + "%'";

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(solvedExamsSqlQuery + ";");

			while (rs.next()) {
				String temp_user_id = rs.getString(1);
				String temp_exam_id = rs.getString(2);
				Date temp_date = rs.getDate(6);
				boolean cheating_flag = false;

				String temp_cheating_flag_str = rs.getString(11);

				if (temp_cheating_flag_str == "yes")
					cheating_flag = true;

				exams.add(new CheatingExamTest(temp_user_id, temp_exam_id, temp_date, cheating_flag));
			}

			rs.close();
			return exams;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 
	 * 
	 * 
	 * A method that returns all exams from the database
	 * 
	 * @param str
	 *            can be null, and then all courses in subject will return, or str
	 *            can contain a specific exam id
	 * @return A list of all the exams
	 * @author Omer Haimovich
	 */
	@Override
	public ArrayList<Exam> exams(String... str) {
		ArrayList<Exam> exams = new ArrayList<Exam>();
		ArrayList<User> newUsers = new ArrayList<User>();
		ArrayList<QuestionInExam> questions = new ArrayList<QuestionInExam>();
		User teacher;
		ResultSet rs;
		String examQuery = "SELECT * FROM exams WHERE exam_id LIKE '" + str[0] + "%'";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(examQuery + ";");
			while (rs.next()) {
				newUsers = users(rs.getString(2));
				teacher = newUsers.get(0);
				questions = questionInExam(rs.getString(1));
				exams.add(new Exam(rs.getString(1), teacher, rs.getInt(3), questions));
			}
			rs.close();
			return exams; // Return A list of all users
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * A method that returns all exams when given the exam code
	 * 
	 * @autor Naor Saadia
	 * @param pass
	 *            the exam code
	 * @return list of all exams that have this specific code
	 */
	public ArrayList<Exam> getExamByPassword(String pass) {
		String byPassword = "SELECT ex.exam_id as exid,\r\n" + "	ex.teacher_assembler_id as teacher_id,\r\n"
				+ "    ex.exam_original_allocated_duration as duration,\r\n" + "    qie.exam_ID as exid,\r\n"
				+ "    qie.Question_ID as qid,\r\n" + "    qie.Question_Grade as grade,\r\n"
				+ "    qie.Question_Free_text_Student as student_notes,\r\n"
				+ "    qie.Question_Free_text_Teacher as teacher_notes,\r\n" + "    q.question_id as qid,\r\n"
				+ "    q.question_text as qtext,\r\n" + "    q.question_instruction as inst,\r\n"
				+ "    q.question_answer_1 as ans1,\r\n" + "    q.question_answer_2 as ans2,\r\n"
				+ "    q.question_answer_3 as ans3,\r\n" + "    q.question_answer_4 as ans4,\r\n"
				+ "    q.correct_question as corAns,\r\n" + "    execute.exam_date_start as date,\r\n"
				+ "    execute.exam_type as type\r\n" + "\r\n"
				+ "FROM exams ex, `questions in exam` qie, questions q, `execute exams` execute\r\n"
				+ "				WHERE execute.four_Digit=" + pass + "\r\n"
				+ "				AND qie.exam_ID=ex.exam_Id\r\n" + "				AND execute.exam_id=ex.exam_Id\r\n"
				+ "				AND q.question_id = qie.Question_ID;";
		ResultSet rs;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(byPassword);
			Exam exam = null;
			ArrayList<Exam> exams = null;
			if (rs.next()) {

				exam = new Exam(rs.getString("exid"), rs.getString("teacher_id"), rs.getInt("duration"));
				exam.setExecuteExam(
						new ExecuteExam(rs.getString("exid"), rs.getString("date"), pass, rs.getString("type")));
				rs.previous();
				exams = new ArrayList<Exam>();
				exams.add(exam);
				ArrayList<QuestionInExam> questionsInExam = new ArrayList<QuestionInExam>();
				QuestionInExam q;
				while (rs.next()) {
					q = new QuestionInExam(
							new Question(rs.getString("qid"), rs.getString("qtext"), rs.getString("inst"),
									rs.getString("ans1"), rs.getString("ans2"), rs.getString("ans3"),
									rs.getString("ans4"), rs.getInt("corAns"), rs.getString("teacher_id")),
							rs.getInt("grade"), rs.getString("teacher_notes"), rs.getString("student_notes"));
					questionsInExam.add(q);
				}
				exam.setExamQuestions(questionsInExam);
			}
			return exams;

		} catch (SQLException e) {
			// log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 
	 * A method that returns all executed exams when given the teacher id
	 * 
	 * @autor Naor Saadia
	 * @param teacherId
	 *            the id of the teacher who executed the exam
	 * @return list of all executed exams that the specific teacher executed
	 */
	public ArrayList<ExecuteExam> getExecutedExams(String teacherId) {
		ArrayList<ExecuteExam> arr = new ArrayList<ExecuteExam>();
		String getExecuted = "select exe.exam_id as id,\r\n" + "exe.exam_date_start as start_date,\r\n"
				+ "exe.four_Digit as pass,\r\n" + "exe.exam_type as examType,\r\n"
				+ "ex.exam_original_allocated_duration as duration\r\n" + "from `execute exams` exe,exams ex\r\n"
				+ "where exe.exam_id=ex.exam_id AND\r\n" + "exe.executining_teacher_ID = " + "'" + teacherId + "'";
		ResultSet rs;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(getExecuted);
			while (rs.next()) {
				ExecuteExam ex = new ExecuteExam(rs.getString("id"), rs.getDate("start_date").toString(),
						rs.getString("pass"), rs.getString("examType"), rs.getInt("duration"));
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
	 * 
	 * A method that returns all subjects from the database
	 * 
	 * @author Omer Haimovich
	 * @param str
	 *            can be null, and then all subjects of teacher wil return, or str
	 *            can contain a specific subject ID
	 * @return list of all executed exams that the specific teacher executed
	 */
	@Override
	public ArrayList<Subject> subjects(String... str) {
		ArrayList<Subject> userSubjects = new ArrayList<Subject>();
		ResultSet rs;
		String SubjectsQuery = "SELECT * FROM subjects";// fetch all subjects
		try {
			stmt = conn.createStatement();
			switch (str.length) {
			case 0: // if str empty retrieve all subject
				rs = stmt.executeQuery(SubjectsQuery + ";");
				while (rs.next()) {
					userSubjects.add(new Subject(rs.getString(1), rs.getString(2)));
				}
				rs.close();
				return userSubjects; // Return A list of all subjects
			case 1: // if str contain user id retrieve only his subjects (jubjectsTabl join
					// subjectofTeacherTable)
				/**
				 * @author gal given a teacher id, this query will return all his teaching
				 *         subjects
				 */
				String userSubjectsQuery = " WHERE subjects.subject_id in (SELECT `subject a teacher teach`.subject_ID FROM `subject a teacher teach` WHERE `subject a teacher teach`.teacher_ID = "
						+ str[0] + ");";
				rs = stmt.executeQuery(SubjectsQuery + userSubjectsQuery);
				while (rs.next()) {
					userSubjects.add(new Subject(rs.getString(1), rs.getString(2)));
				}
				rs.close();
				return userSubjects; // Return A list of all subjects a teacher teach
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
		}
		return null;
	}

	/**
	 * 
	 * A method that returns all exams statistics from the database
	 * 
	 * @author Alon Ben Yossef
	 * @param str
	 *            can be null, and then all subjects of teacher wil return, or str
	 *            can contain a specific exam ID
	 * @return list of all exam statistics
	 */
	@Override
	public ArrayList<Statistic> solvedExamStatistic(String... str) {

		return null;
	}

	/**
	 * 
	 * 
	 * A method that returns all the solved exams in a particular course
	 * 
	 * @author Alon Ben Yossef
	 * @param course_id
	 *            the id of the course
	 * @return list of all solved exams in a particular course
	 */
	public ArrayList<SolvedExams> getSolvedExamsByCourseId(String course_id) {
		ArrayList<SolvedExams> solvedExams = new ArrayList<>();
		ResultSet rs;
		String query = "SELECT * FROM `solved exams` WHERE exam_ID LIKE '_%_%" + course_id + "_%_%';";

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				SolvedExams se = new SolvedExams(rs.getString(2), rs.getString(1), rs.getInt(3), rs.getInt(4),
						rs.getString(5), rs.getTimestamp(6), rs.getString(7), rs.getString(8), rs.getString(9),
						rs.getString(10), rs.getString(11));
				solvedExams.add(se);
			}

			return solvedExams;
		} catch (SQLException s) {
			s.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * 
	 * A method that returns all the solved exams
	 * 
	 * @author gal
	 * @param str
	 *            - size== 0: retreive all solved exams, if size == 9, retrieve all
	 *            solvedExams of this userID, if size==6, retrieve all solved exams
	 *            of this specific
	 * @return list of all solved exams
	 */
	@Override
	public ArrayList<SolvedExams> solvedExams(String... str) {
		ArrayList<SolvedExams> solvedExams = new ArrayList<SolvedExams>();
		ResultSet rs;
		String SubjectsQuery = "SELECT * FROM `solved exams`";// fetch all solvedExams
		try {
			stmt = conn.createStatement();
			switch (str.length) {
			case 0: // if str empty retrieve all solvedExams
				rs = stmt.executeQuery(SubjectsQuery + ";");
				while (rs.next()) {
					// 1private String examID;
					// 2private String sovingStudentID;
					// 3private int examGrade;
					// 4private int solveDurationTime;
					// 5private String submittedOrInterruptedFlag;
					// 6private timeStamp examDateTime;
					// 7private String teacherNotes;
					// 8private String gradeAlturationExplanation;
					// 9private String approvingTeacherID;
					// 10private String calculatedGradeApprovalStateByTeacher;
					// 11private String cheatingFlag;

					solvedExams.add(new SolvedExams(rs.getString(2), rs.getString(1), rs.getInt(3), rs.getInt(4),
							rs.getString(5), rs.getTimestamp(6), rs.getString(7), rs.getString(8), rs.getString(9),
							rs.getString(10), rs.getString(11)));
				}
				rs.close();
				return solvedExams; // Return A list of all solved exams
			case (1): // if str.length == 1, check if its userID or examID
				switch (str[0].length()) {
				case (9):// if str.length==9, str contain user id retrieve only his SolvedExams
					/**
					 * @author gal given a student id, this query will return all his solved exams.
					 */
					String userSolvedExamsQuery = " WHERE `solved exams`.`User_ID` in (SELECT `solved exams`.`User_ID` FROM `solved exams` WHERE  `solved exams`.`User_ID` = "
							+ str[0] + ");";
					rs = stmt.executeQuery(SubjectsQuery + userSolvedExamsQuery);
					while (rs.next()) {
						solvedExams.add(new SolvedExams(rs.getString(2), rs.getString(1), rs.getInt(3), rs.getInt(4),
								rs.getString(5), rs.getTimestamp(6), rs.getString(7), rs.getString(8), rs.getString(9),
								rs.getString(10), rs.getString(11)));
					}
					rs.close();
					return solvedExams; // Return A list of all solved Exams of this USERID
				case (6): // if str.length==6, str contain Exams ID. retrieve ll solved exams of this
							// Exam's ID
					/**
					 * @author gal given a Exam id, this query will return all solved exams relevant
					 *         for this exam's ID.
					 */
					String examsSolvedExamsQuery = " WHERE `solved exams`.`exam_ID` in (SELECT `solved exams`.`exam_ID` FROM `solved exams` WHERE  `solved exams`.`exam_ID` = "
							+ str[0] + ");";
					rs = stmt.executeQuery(SubjectsQuery + examsSolvedExamsQuery);
					while (rs.next()) {
						solvedExams.add(new SolvedExams(rs.getString(2), rs.getString(1), rs.getInt(3), rs.getInt(4),
								rs.getString(5), rs.getTimestamp(6), rs.getString(7), rs.getString(8), rs.getString(9),
								rs.getString(10), rs.getString(11)));
					}
					rs.close();
					return solvedExams; // Return A list of all subjects a teacher teach
				default:
					break;
				}// end inner switch
			}// end switch
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
		}
		return null;
	}

	/**
	 * 
	 * 
	 * A method that returns all the questions that appear in a particular exam from
	 * the database
	 * 
	 * @author Omer Haimovich
	 * @param str
	 *            can be null, and then all subjects of teacher wil return, or str
	 *            can contain a specific exam ID
	 * @return list of all question in specific exam
	 */
	@Override
	public ArrayList<QuestionInExam> questionInExam(String... str) {
		ArrayList<QuestionInExam> question = new ArrayList<QuestionInExam>();
		Question q;
		ResultSet rs;
		int i = 0;
		String examQuery = "SELECT * FROM `questions in exam` qie, `questions` q WHERE qie.exam_ID = '" + str[0] + "'"
				+ "AND qie.Question_ID = q.question_id";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(examQuery + ";");
			while (rs.next()) {
				q = new Question(rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
						rs.getString(11), rs.getString(12), rs.getInt(13), rs.getString(14));
				question.add(new QuestionInExam(q, rs.getInt(3), rs.getString(5), rs.getString(4)));
			}
			rs.close();
			return question;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * 
	 * 
	 * A method that returns all courses a specific teacher teaches from the
	 * database
	 * 
	 * @author Amit Molek
	 * @param str
	 *            can be null, and then all subjects of teacher wil return, or str
	 *            can contain a specific teacher ID
	 * @return list of all courses of specific teacher
	 */
	@Override
	public ArrayList<Course> courseOfTeacher(String... str) {
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
	
	/**
	 * 
	 * A method that returns all exam table data line when given the teacher id from
	 * the data base
	 * 
	 * @autor Naor Saadia
	 * @param id
	 *            the id of the teacher
	 * @return list of all exam table data lined
	 */
	public ArrayList<ExamTableDataLine> getLinesByTeacherID(String id) {
		String query1 = "SELECT e.exam_id, st.exam_date,c.course_name,sub.subject_name"
				+ " FROM aes.exams e, aes.`exams stats` st, aes.courses c,aes.subjects sub, aes.`courses in subject` cis"
				+ " WHERE e.teacher_assembler_id='" + id + "' AND e.exam_id=st.exam_id"
				+ " AND SUBSTR(e.exam_id,1,2)=cis.subject_id AND substr(e.exam_id,3,2)=cis.course_id"
				+ " AND cis.subject_id=sub.subject_id AND cis.course_id=c.course_id;";
		ResultSet rs;
		ArrayList<ExamTableDataLine> dataList = new ArrayList<ExamTableDataLine>();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query1);
			while (rs.next()) {
				dataList.add(new ExamTableDataLine(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataList;
	}

	/**
	 * 
	 * A method that returns all solved exams statistics when given exam id and exam
	 * executed date
	 * 
	 * @author Alon Ben Yossef
	 * @param msg
	 *            A message containing a date and exam id
	 * @return solved exams statistic
	 */
	public Statistic getExamStatsByIdDate(ExamStatsByIdDateMessage msg) {
		String query1 = "SELECT * FROM aes.`exams stats` s" + " WHERE s.exam_id = '" + msg.getId()
				+ "' AND s.exam_date='" + msg.getDate() + "';";
		ResultSet rs;
		Statistic stat;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query1);
			rs.next();
			stat = new Statistic();
			stat.setExam_ID(rs.getString(1));
			stat.setDate(rs.getString(2));
			stat.setReal_time_duration(rs.getString(3));
			stat.setStudents_started_counter(rs.getInt(4));
			stat.setSubmitted_students_counter(rs.getInt(5));
			stat.setInterrupted_students_counter(rs.getInt(6));
			stat.setExams_avg(rs.getDouble(7));
			stat.setExams_median(rs.getInt(8));
			stat.setGrade_derivative_0_10(rs.getInt(9));
			stat.setGrade_derivative_11_20(rs.getInt(10));
			stat.setGrade_derivative_21_30(rs.getInt(11));
			stat.setGrade_derivative_31_40(rs.getInt(12));
			stat.setGrade_derivative_41_50(rs.getInt(13));
			stat.setGrade_derivative_51_60(rs.getInt(14));
			stat.setGrade_derivative_61_70(rs.getInt(15));
			stat.setGrade_derivative_71_80(rs.getInt(16));
			stat.setGrade_derivative_81_90(rs.getInt(17));
			stat.setGrade_derivative_91_100(rs.getInt(18));
			rs.close();
			return stat;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * A method that returns all solved exams when given exam id and teacher id
	 * 
	 * @author Alon Ben Yossef
	 * @param examId
	 *            the exam id
	 * @param userId
	 *            the id of teacher who executed this exam
	 * @return true if there is row in the solved exam table and else returns false
	 */
	public boolean getSolvedExamByID(String examId, String userId) {
		ResultSet rs;

		String query = "SELECT *\r\n" + "FROM `solved exams`\r\n" + "WHERE exam_ID = " + examId + "\r\n"
				+ "AND User_ID = " + userId + ";";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			if (rs.next())
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	public ArrayList<Integer> getGradesQuery(String query) throws SQLException {
		String query1=query;
		ResultSet rs;
		ArrayList<Integer> list=new ArrayList<Integer>();
		stmt = conn.createStatement();
		rs= stmt.executeQuery(query1);
		while(rs.next()){
			list.add(rs.getInt(1));
		}
		return list;
	}

	/**
	 * @author Alon Ben-yosef
	 * Get all courses in subject rows from DB
	 * @return arraylist of CourseInSubject from DB
	 */
	public ArrayList<CourseInSubject> getCoursesInSubject() {
		ArrayList<CourseInSubject> list = new ArrayList<CourseInSubject>();
		ResultSet rs;
		String query = "SELECT * FROM aes.`courses in subject`;";
		try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(query);
				while(rs.next()) {
					list.add(new CourseInSubject(rs.getString(1), rs.getString(2)));
				}
		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return list;
	}
	/**
	 * @author Alon Ben-yosef
	 * Get all exams as written in the DB
	 * @return list of all exams
	 */
	public ArrayList<Exam> getExams(){
		ArrayList<Exam> list = new ArrayList<Exam>();
		ResultSet rs;
		String query = "SELECT * FROM aes.`exams`;";
		try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(query);
				while(rs.next()) {
					int clean=0;
					int locked=0;
					if(rs.getString(4).equals("dirty")) clean=1;
					if(rs.getString(5).equals("unlocked")) locked=1;
					list.add(new Exam(rs.getString(1), rs.getInt(3),clean, locked, rs.getString(2)));
				}
		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return list;
	}

}