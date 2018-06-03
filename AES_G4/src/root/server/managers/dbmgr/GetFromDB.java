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
import root.dao.app.SolvedExams;
import root.dao.app.Statistic;
import root.dao.app.Subject;
import root.dao.app.User;
import root.dao.message.Message;
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
/**
 * @author gal
 * @param str can be null, and then all users wil return, or str can contain a specific user ID
 */
	@Override
	public ArrayList<User> users(String... str) {
		ArrayList<User> users= new ArrayList<User>();	// needed fixing, add switch case: empty-all users, 1- specific user,2 only these users...
		try {
			stmt = conn.createStatement();
			ResultSet rs;
			String usersQuery =  "SELECT users.* FROM users";// fetch all users
			
			switch(str.length) {
				case 0:
					rs = stmt.executeQuery(usersQuery);
					while(rs.next()) {
						users.add(new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
					}
					rs.close();
					return users;			// Return A list of all users
				case 1:
					String getSpecificUser = " WHERE users.Users_ID = "+"\'"+str[0]+"\';";
					System.out.println(usersQuery+getSpecificUser);
					rs = stmt.executeQuery(usersQuery+getSpecificUser);
					while(rs.next()) {
						users.add(new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
					}
					rs.close();
					return users;			// Return a list contain specific user
			}
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
