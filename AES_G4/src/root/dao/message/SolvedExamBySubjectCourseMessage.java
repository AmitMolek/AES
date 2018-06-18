package root.dao.message;

import java.util.ArrayList;

import root.dao.app.Course;
import root.dao.app.SolvedExams;
import root.dao.app.Subject;

public class SolvedExamBySubjectCourseMessage extends AbstractMessage{

	private Subject subject;
	private Course course;
	private ArrayList<SolvedExams> solvedExams;
	
	public SolvedExamBySubjectCourseMessage(Subject subject, Course course) {
		super("get-solvedbysubjectcourse");
		this.subject = subject;
		this.course = course;
		this.solvedExams = new ArrayList<>();
	}
	
	public SolvedExamBySubjectCourseMessage(SolvedExamBySubjectCourseMessage msg) {
		super("ok-get-solvedbysubjectcourse");
		this.subject = msg.getSubject();
		this.course = msg.getCourse();
		this.solvedExams = msg.solvedExams;
	}
	
	public ArrayList<SolvedExams> getSolvedExams() {
		return solvedExams;
	}

	public void setSolvedExams(ArrayList<SolvedExams> solvedExams) {
		this.solvedExams = solvedExams;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	@Override
	public String getType() {
		return "SolvedBySubjectCourse";
	}
	
}
