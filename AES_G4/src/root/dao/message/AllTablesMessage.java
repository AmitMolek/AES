package root.dao.message;

import java.util.ArrayList;
import root.dao.app.AlterDuration;
import root.dao.app.Course;
import root.dao.app.CourseInSubject;
import root.dao.app.Exam;
import root.dao.app.ExecuteExam;
import root.dao.app.Question;
import root.dao.app.QuestionInExam;
import root.dao.app.QuestionInExamData;
import root.dao.app.SolvedExams;
import root.dao.app.Statistic;
import root.dao.app.Subject;
import root.dao.app.SubjectATeacherTeach;
import root.dao.app.User;

public class AllTablesMessage extends AbstractMessage{

	private static final long serialVersionUID = 1L;

	private ArrayList<AlterDuration> alterDurList;
	private ArrayList<Course> courseList;
	private ArrayList<CourseInSubject> courseInSubList;
	private ArrayList<Exam> examList;
	private ArrayList<Statistic> statList;
	private ArrayList<ExecuteExam> executeList;
	private ArrayList<Question> questionList;
	private ArrayList<QuestionInExamData> questionInExamList;
	private ArrayList<SolvedExams> solvedExamList;
	private ArrayList<SubjectATeacherTeach> subjectTeacherList;
	private ArrayList<Subject> subjectList;
	private ArrayList<User> userList;
	
	/**
	 * a constructor for an empty AllTablesMessage
	 * @param msg
	 */
	public AllTablesMessage(String msg) {
		this.setMsg(msg);
	}
	
	public ArrayList<AlterDuration> getAlterDurList() {
		return alterDurList;
	}


	public void setAlterDurList(ArrayList<AlterDuration> alterDurList) {
		this.alterDurList = alterDurList;
	}


	public ArrayList<Course> getCourseList() {
		return courseList;
	}


	public void setCourseList(ArrayList<Course> courseList) {
		this.courseList = courseList;
	}


	public ArrayList<CourseInSubject> getCourseInSubList() {
		return courseInSubList;
	}


	public void setCourseInSubList(ArrayList<CourseInSubject> courseInSubList) {
		this.courseInSubList = courseInSubList;
	}


	public ArrayList<Exam> getExamList() {
		return examList;
	}


	public void setExamList(ArrayList<Exam> examList) {
		this.examList = examList;
	}


	public ArrayList<Statistic> getStatList() {
		return statList;
	}


	public void setStatList(ArrayList<Statistic> statList) {
		this.statList = statList;
	}


	public ArrayList<ExecuteExam> getExecuteList() {
		return executeList;
	}


	public void setExecuteList(ArrayList<ExecuteExam> executeList) {
		this.executeList = executeList;
	}


	public ArrayList<Question> getQuestionList() {
		return questionList;
	}


	public void setQuestionList(ArrayList<Question> questionList) {
		this.questionList = questionList;
	}


	public ArrayList<QuestionInExamData> getQuestionInExamList() {
		return questionInExamList;
	}


	public void setQuestionInExamList(ArrayList<QuestionInExamData> questionInExamList) {
		this.questionInExamList = questionInExamList;
	}


	public ArrayList<SolvedExams> getSolvedExamList() {
		return solvedExamList;
	}


	public void setSolvedExamList(ArrayList<SolvedExams> solvedExamList) {
		this.solvedExamList = solvedExamList;
	}


	public ArrayList<SubjectATeacherTeach> getSubjectTeacherList() {
		return subjectTeacherList;
	}


	public void setSubjectTeacherList(ArrayList<SubjectATeacherTeach> subjectTeacherList) {
		this.subjectTeacherList = subjectTeacherList;
	}


	public ArrayList<Subject> getSubjectList() {
		return subjectList;
	}


	public void setSubjectList(ArrayList<Subject> subjectList) {
		this.subjectList = subjectList;
	}


	public ArrayList<User> getUserList() {
		return userList;
	}


	public void setUserList(ArrayList<User> userList) {
		this.userList = userList;
	}

	@Override
	public String getType() {
		return "AllTables";
	}
}
