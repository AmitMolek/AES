package root.server.managers.dbmgr;

import java.util.ArrayList;
import root.dao.app.*;
/**
 * The DbManagerInterface is an interface that 
 * Which is responsible for managing communications between the server and the database
 * @author Omer Haimovich
 *
 */
public interface DbManagerInterface {
	public ArrayList<Question> questions(String... str);
	public ArrayList<User> users(String... str);
	public ArrayList<AlterDuration> alterDuration(String... str);
	public ArrayList<Course> courseOfTeacher(String... str);
	public ArrayList<Course> courses(String... str);
	public ArrayList<Course> coursesInSubject(String... str);
	public ArrayList<Exam> exams(String... str);
	public ArrayList<Subject> subjects(String... str);
	public ArrayList<Statistic> solvedExamStatistic(String... str);
	public ArrayList<SolvedExams> solvedExams(String... str);
	public ArrayList<QuestionInExam> questionInExam(String... str);
	
}
