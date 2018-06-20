package root.dao.message;

import java.util.ArrayList;

import root.dao.app.Exam;
import root.dao.app.SolvedExams;
import root.dao.app.User;

/**
 * 
 * @author gal
 *	this dao message class is used to send and recieve from server all user solved exams
 */
public class UserSolvedExamsMessage extends AbstractMessage{
	private ArrayList<SolvedExams> userSolvedExams;
	private SolvedExams solvedExam;
	private User user;
	private Exam exam;
	/**
	 * use this constructor when sending back to client all his solved exams
	 * @param userSolvedExams
	 */
	public UserSolvedExamsMessage(ArrayList<SolvedExams> userSolvedExams) {
		super("get-solvedExams");
		this.userSolvedExams = userSolvedExams;
	}
	/**
	 * use this constructor when client want to put new solved exam into DB
	 * @param solvedExam
	 */
	public UserSolvedExamsMessage(SolvedExams solvedExam) {
		super("put-solvedExams");
		this.solvedExam = solvedExam;
	}
	/**
	 * use this constructor when client want to get all solved exams for a specific user
	 * @param user
	 */
	public UserSolvedExamsMessage(User user) {
		super("get-solvedExams-user");
		this.user = user;
	}
	/**
	 * use this constructor when client want to get all solved exams for a specific exam
	 */
	public UserSolvedExamsMessage(Exam exam) {
		super("get-solvedExams-exam");
		this.exam = exam;
	}
	
	@Override
	public String getType() {
		return "solved Exams";
	}
	/**
	 * @return the userSolvedExams
	 */
	public ArrayList<SolvedExams> getUserSolvedExams() {
		return userSolvedExams;
	}
	/**
	 * @param userSolvedExams the userSolvedExams to set
	 */
	public void setUserSolvedExams(ArrayList<SolvedExams> userSolvedExams) {
		this.userSolvedExams = userSolvedExams;
	}
	/**
	 * @return the solvedExam
	 */
	public SolvedExams getSolvedExam() {
		return solvedExam;
	}
	/**
	 * @param solvedExam the solvedExam to set
	 */
	public void setSolvedExam(SolvedExams solvedExam) {
		this.solvedExam = solvedExam;
	}
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * @return the exam
	 */
	public Exam getExam() {
		return exam;
	}
	/**
	 * @param exam the exam to set
	 */
	public void setExam(Exam exam) {
		this.exam = exam;
	}
	
	

}
