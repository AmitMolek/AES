package root.server.managers.dbmgr;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
import root.dao.message.AbstractMessage;
import root.dao.message.MessageFactory;
import root.dao.message.QuestionsMessage;
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

	public SetInDB() {
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
	public ArrayList<QuestionInExam> questionInExam(String... str) {
		// TODO Auto-generated method stub
		return null;
	}

	public AbstractMessage AddExam(Exam exam) {
		String insertExam = "insert into exams (exam_id, teacher_assembler_id, exam_original_allocated_duration, exams_state, lock_flag)"
				+ " values (?, ?, ?, ?, ?);";
		try {
			newStmt = conn.prepareStatement(insertExam);
			newStmt.setString(1, exam.getExamId());
			newStmt.setString(2, exam.getAuthor().getUserID());
			newStmt.setInt(3, exam.getExamDuration());
			newStmt.setString(4, "clean");
			newStmt.setString(5, "locked");
			newStmt.execute();
			String insertQuestionInExam = "insert into `questions in exam` (exam_ID, Question_ID, Question_Grade, Question_Free_text_Student, Question_Free_text_Teacher)"
					+ " values (?, ?, ?, ?, ?);";
			newStmt = conn.prepareStatement(insertQuestionInExam);
			ArrayList<QuestionInExam> examQuestions = exam.getExamQuestions();
			for (QuestionInExam q : examQuestions) {
				newStmt.setString(1, exam.getExamId());
				newStmt.setString(2, q.getQuestion().getQuestionId());
				newStmt.setInt(3, q.getQuestionGrade());
				newStmt.setString(4, q.getFreeTextForStudent());
				newStmt.setString(5, q.getFreeTextForTeacher());
				newStmt.execute();
			}
			return message.getMessage("ok-put-exams", null); // because we didnt needed to get from DB theres nothing to
																// send back to client but the confirmation

		} catch (SQLException e) {
			// log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
			e.printStackTrace();
		}
		return null;

	}

	public AbstractMessage AddNewQuestion(Question newQuestionTooAdd) {
		String insertQuestion = "INSERT INTO `aes`.`questions`(`question_id`,`question_text`,`question_instruction`,`question_answer_1`,`question_answer_2`,`question_answer_3`,`question_answer_4`,`correct_question`,`teacher_assembeld_id`)"
				+ "VALUES(?,?,?,?,?,?,?,?,?);";

		try {
			newStmt = conn.prepareStatement(insertQuestion);
			newStmt.setString(1, newQuestionTooAdd.getQuestionId());
			newStmt.setString(2, newQuestionTooAdd.getQuestionText());
			newStmt.setString(3, newQuestionTooAdd.getIdquestionIntruction());
			newStmt.setString(4, newQuestionTooAdd.getAns1());
			newStmt.setString(5, newQuestionTooAdd.getAns2());
			newStmt.setString(6, newQuestionTooAdd.getAns3());
			newStmt.setString(7, newQuestionTooAdd.getAns4());
			newStmt.setInt(8, newQuestionTooAdd.getCorrectAns());
			newStmt.setString(9, newQuestionTooAdd.getTeacherAssembeld());
			newStmt.execute();
			return message.getMessage("ok-put-questions", null); // because we didnt needed to get from DB theres
																	// nothing to send back to client but the
																	// confirmation

		} catch (SQLException e) {
			// log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
			e.printStackTrace();
		}
		return null;

	}

	public AbstractMessage deleteTheExam(Exam exam) {
		String deleteExam = "delete from exams where exam_id = " + exam.getExamId();
		try {
			newStmt = conn.prepareStatement(deleteExam + ";");
			newStmt.execute();
			return message.getMessage("ok-delete-exams", null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public AbstractMessage deleteTheQuestion(Question Question) {
		String deleteQuestion = "delete from questions where question_id = " + Question.getQuestionId();
		try {
			newStmt = conn.prepareStatement(deleteQuestion + ";");
			newStmt.execute();
			return message.getMessage("ok-delete-questions", null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public AbstractMessage updateExistingQuestion(Question questionMessage) {
		String updateQuestion = "UPDATE questions SET " + " `question_id` = ?," + " `question_text` = ?,"
				+ " `question_instruction` = ?," + " `question_answer_1` = ?," + " `question_answer_2` = ?,"
				+ " `question_answer_3` = ?," + " `question_answer_4` = ?," + " `correct_question` = ?,"
				+ " `teacher_assembeld_id` = ?" + " WHERE `question_id` = ?;";
		System.out.println(questionMessage);
		System.out.println(updateQuestion);

		// +"SELECT * FROM aes.questions;";
		try {
			newStmt = conn.prepareStatement(updateQuestion);
			newStmt.setString(1, questionMessage.getQuestionId());
			newStmt.setString(2, questionMessage.getQuestionText());
			newStmt.setString(3, questionMessage.getIdquestionIntruction());
			newStmt.setString(4, questionMessage.getAns1());
			newStmt.setString(5, questionMessage.getAns2());
			newStmt.setString(6, questionMessage.getAns3());
			newStmt.setString(7, questionMessage.getAns4());
			newStmt.setInt(8, questionMessage.getCorrectAns());
			newStmt.setString(9, questionMessage.getTeacherAssembeld());
			newStmt.setString(10, questionMessage.getQuestionId());
			newStmt.execute();
			return message.getMessage("ok-set-questions", null); // because we didnt needed to get from DB theres
																	// nothing to send back to client but the
																	// confirmation

		} catch (SQLException e) {
			// log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
			e.printStackTrace();
		}

		return null;
	}

	public AbstractMessage addQuestionToExam(String id, ArrayList<QuestionInExam> quest) {
		deleteQuestionInExam(id);
		String insertQuestionInExam = "insert into `questions in exam` (exam_ID, Question_ID, Question_Grade, Question_Free_text_Student, Question_Free_text_Teacher)"
				+ " values (?, ?, ?, ?, ?);";
		try {
			newStmt = conn.prepareStatement(insertQuestionInExam);
			for (QuestionInExam q : quest) {
				newStmt.setString(1, id);
				newStmt.setString(2, q.getQuestion().getQuestionId());
				newStmt.setInt(3, q.getQuestionGrade());
				newStmt.setString(4, q.getFreeTextForStudent());
				newStmt.setString(5, q.getFreeTextForTeacher());
				newStmt.execute();
			}
			return message.getMessage("ok-put-questioninexam", null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public AbstractMessage deleteQuestionInExam(String id) {
		String deleteExam = "delete from `questions in exam` where exam_ID = " + id;
		try {
			newStmt = conn.prepareStatement(deleteExam + ";");
			newStmt.execute();
			return message.getMessage("ok-delete-questioninexam", null);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public AbstractMessage addSolvedExam(SolvedExams newExam) {
		String insertSolvedExam = "insert into `solved exams` (User_ID, exam_ID, solved_exam_grade, solve_duration_timer, submitted_or_interrupted_Flag, exam_executing_Date)"
				+ " values (?, ?, ?, ?, ?, ?);";
		try {
			newStmt = conn.prepareStatement(insertSolvedExam);
			java.sql.Date dateDB = new java.sql.Date(newExam.getDate().getTime());
			newStmt.setString(1, newExam.getSovingStudentID());
			newStmt.setString(2, newExam.getExamID());
			newStmt.setInt(3, newExam.getExamGrade());
			newStmt.setInt(4, newExam.getSolveDurationTime());
			newStmt.setString(5, newExam.getSubmittedOrInterruptedFlag());
			newStmt.setDate(6, dateDB);
			newStmt.execute();
			return message.getMessage("ok-put-solvedexams", null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public AbstractMessage addExecuteExam(ExecuteExam newExam) {
		String insertExecuteExam = "insert into `execute exams` (exam_id, exam_date_start, four_Digit, exam_type)"
				+ " values (?, ?, ?, ?);";
		try {
			newStmt = conn.prepareStatement(insertExecuteExam);
			newStmt.setString(1, newExam.getExamId());
			newStmt.setString(2, newExam.getStartTime());
			newStmt.setString(3, newExam.getExamPassword());
			newStmt.setString(4, newExam.getExamType());
			newStmt.execute();
			return message.getMessage("ok-put-executeexam", null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public AbstractMessage updateExam (Exam newExam) {
		String updateExam = "UPDATE exams SET exams_state = ?, lock_flag = ? WHERE exam_id = ?;";
		try {
			newStmt = conn.prepareStatement(updateExam);
			newStmt.setString(1, "dirty");
			newStmt.setString(2, "unlocked");
			newStmt.setString(3, newExam.getExamId());
			newStmt.execute();
			return message.getMessage("ok-set-exams", null); // because we didnt needed to get from DB theres
																	// nothing to send back to client but the
																	// confirmation

		} catch (SQLException e) {
			// log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
			e.printStackTrace();
		}

		return null;
		
	}
}