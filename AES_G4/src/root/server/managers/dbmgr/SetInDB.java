package root.server.managers.dbmgr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import root.dao.app.AlterDuration;
import root.dao.app.Course;
import root.dao.app.Exam;
import root.dao.app.Question;
import root.dao.app.QuestionInExam;
import root.dao.app.SolvedExams;
import root.dao.app.Statistic;
import root.dao.app.Subject;
import root.dao.app.User;
import root.dao.message.AbstractMessage;
import root.dao.message.MessageFactory;
import root.server.AES_Server;
import root.util.log.Log;
import root.util.log.LogLine;
import root.util.log.LogLine.LineType;

public class SetInDB implements DbManagerInterface {

	private java.sql.Statement stmt;
	private PreparedStatement newStmt;
	private Connection conn;
	private Log log;
	private MessageFactory message;
	public SetInDB()
	{
		super();
		conn = AES_Server.getConnection();
		log.getInstance();
		message = MessageFactory.getInstance();
	}
	@Override
	public ArrayList<Question> questions(String... str) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<User> users(String... str) {
		// TODO Auto-generated method stub
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
	
	public AbstractMessage AddExam(Exam exam) {
		String insertExam = "insert into exams (exam_id, teacher_assembler_id, exam_original_allocated_duration, exams_state, lock_flag)" + " values (?, ?, ?, ?, ?);";
		try {
			newStmt = conn.prepareStatement(insertExam);
			newStmt.setString(1,exam.getExamId());
			newStmt.setString(2, exam.getAuthor().getUserID());
			newStmt.setInt(3, exam.getExamDuration());
			newStmt.setString(4, "clean");
			newStmt.setString(5, "locked");
			newStmt.execute();
			String insertQuestionInExam = "insert into `questions in exam` (exam_ID, Question_ID, Question_Grade, Question_Free_text_Student, Question_Free_text_Teacher)" + " values (?, ?, ?, ?, ?);";
			newStmt = conn.prepareStatement(insertQuestionInExam);
			ArrayList<QuestionInExam> examQuestions = exam.getExamQuestions();
			for(QuestionInExam q: examQuestions) {
				newStmt.setString(1, exam.getExamId());
				newStmt.setString(2, q.getQuestion().getQuestionId());
				newStmt.setInt(3 , q.getQuestionGrade());
				newStmt.setString(4, q.getFreeTextForStudent());
				newStmt.setString(5, q.getFreeTextForTeacher());
				newStmt.execute();
			}
			return message.getMessage("ok-put-exams", null);
			
		} catch (SQLException e) {
			//log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
			e.printStackTrace();
		}
		return null;
		
	}

	public AbstractMessage deleteTheExam(Exam exam) {
		String deleteExam = "delete from exams where exam_id = " + exam.getExamId();
		try {
			newStmt = conn.prepareStatement(deleteExam+";");
			newStmt.execute();
			return message.getMessage("ok-delete-exams",null);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}