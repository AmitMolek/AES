package root.server.managers.dbmgr;

import java.util.ArrayList;
import java.util.Map;

import root.dao.app.*;

public interface DbManagerInterface {
	public ArrayList<Question> questions(String... str);
	public ArrayList<User> users(String... str);
	public ArrayList<AlterDuration> alterDuration(String... str);
	public Map<String, String> courseOfTeacher(String... str);
	public ArrayList<Course> courses(String... str);
	public Map<String, String> coursesInSubject(String... str);
	public ArrayList<Exam> exams(String... str);
	public ArrayList<Subject> subjects(String... str);
	public ArrayList<Statistic> solvedExamStatistic(String... str);
	





	
	
}
