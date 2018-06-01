package root.server.managers.dbmgr;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;

import root.dao.app.AlterDuration;
import root.dao.app.Course;
import root.dao.app.Exam;
import root.dao.app.Question;
import root.dao.app.SolvedExams;
import root.dao.app.Statistic;
import root.dao.app.Subject;
import root.dao.app.User;
import root.server.AES_Server;

public class GetFromDB implements DbManagerInterface {
	private java.sql.Statement stmt;
	private Connection conn;
	
	
	public GetFromDB() {
		super();
		conn = AES_Server.getConnection();
	}

	@Override
	public ArrayList<Question> questions(String... str) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<User> users(String... str) {
		ArrayList<User> users= new ArrayList<User>();
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM USERS;");
			while(rs.next()) {
				users.add(new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
			}
			rs.close();
			return users;			// A list of all users
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}

	@Override
	public ArrayList<AlterDuration> alterDuration(String... str) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Course> courseOfTeacher(String... str) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Course> courses(String... str) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Course> coursesInSubject(String... str) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Exam> exams(String... str) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Subject> subjects(String... str) {
		// TODO Auto-generated method stub
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
	public ArrayList<Question> questionInExam(String... str) {
		// TODO Auto-generated method stub
		return null;
	}

}
