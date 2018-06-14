package root.dao.app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class UserInfo implements Serializable{
	/**
	 * @author gal
	 * this DAO class is for transferring data between client and server,
	 * when we want to get all teacher name's who wrote the question's
	 */
	private HashMap<String, String> teachersMap;	// key = user ID, value = User fullName
	private ArrayList<Question> questions;
	private ArrayList<SolvedExams> solvedExams;
	private User userInfo;
	/**
	 * this constructor is called when we have users ID (e.g in questions) and we want to get their names;
	 * @param teachersMap
	 * @param questions
	 */
	public UserInfo(HashMap<String, String> teachersMap, ArrayList<Question> questions) {
		super();	
		this.teachersMap = teachersMap;
		this.questions = questions;
	}
	/**
	 * @return the teachersMap
	 */
	public HashMap<String, String> getTeachersMap() {
		return teachersMap;
	}
	/**
	 * @param teachersMap the teachersMap to set
	 */
	public void setTeachersMap(HashMap<String, String> teachersMap) {
		this.teachersMap = teachersMap;
	}
	/**
	 * @return the questions
	 */
	public ArrayList<Question> getQuestions() {
		return questions;
	}
	/**
	 * @param questions the questions to set
	 */
	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}
	/**
	 * @return the solvedExams
	 */
	public ArrayList<SolvedExams> getSolvedExams() {
		return solvedExams;
	}
	/**
	 * @param solvedExams the solvedExams to set
	 */
	public void setSolvedExams(ArrayList<SolvedExams> solvedExams) {
		this.solvedExams = solvedExams;
	}
	
	
}
