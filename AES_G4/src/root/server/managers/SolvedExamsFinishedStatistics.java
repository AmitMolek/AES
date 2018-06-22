package root.server.managers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import root.dao.app.Exam;
import root.dao.app.SolvedExams;
import root.dao.app.Statistic;
import root.server.managers.dbmgr.GetFromDB;
import root.server.managers.dbmgr.SetInDB;

/**
 * The managing class that inserts new data to the solved exams statistics table
 * @author Amit Molek
 *
 */

public class SolvedExamsFinishedStatistics {
	
	/**
	 * The id of the exam
	 */
	String exam_id;
	
	/**
	 * The SolvedExams that there are in the db
	 */
	ArrayList<SolvedExams> solvedExams;
	
	/**
	 * The Statistics object we saves all the final data to
	 */
	Statistic examStatistics;
	
	/**
	 * Counter for how many users submitted the exam
	 */
	int submit_counter;
	
	/**
	 * Counter for how many users were interrupted
	 */
	int interrupt_counter;
	
	/**
	 * Counter for how many users started the exam
	 */
	int students_stared;
	
	/**
	 * Contructor for the class
	 * You dont need to call anything else
	 * To Create statistics for a solved exam (if the exam is locked and finished) all you need to do is
	 * new SolvedExamsFinishedStatistics(the id of the exam)
	 * @param exam_id
	 */
	public SolvedExamsFinishedStatistics(String exam_id) {
		this.solvedExams = new ArrayList<>();
		this.exam_id = exam_id;

		getSolvedExamsFromDB();
		if (!checkContinueExam()) return;
		this.examStatistics = new Statistic(collectGrades());
		collectAndSetDataFromExams();
		addToDB();
	}
	
	/**
	 * Checks if we can continue with the process
	 * (if the exam is locked and all the exams were approved)
	 * @return true if we can continue
	 */
	private boolean checkContinueExam() {
		GetFromDB db = new GetFromDB();
		if (!isAllFinished() || db.isSolvedExamStatisticsExist(exam_id)) return false;
		return true;
	}
	
	/**
	 * Gets all the solved exams with the exam_id from the database 
	 */
	private void getSolvedExamsFromDB() {
		GetFromDB db = new GetFromDB();
		solvedExams = db.getSolvedExamsByExamID(exam_id);
	}

	/**
	 * Adds a new row to the statistics table in the database
	 */
	private void addToDB() {
		SetInDB db = new SetInDB();
		db.addSolvedExamStatistics(examStatistics);
	}
	
	/**
	 * Collects all the grades from all the solved exams
	 * @return a lits full with all the grades
	 */
	private ArrayList<Integer> collectGrades() {
		ArrayList<Integer> grades = new ArrayList<>();
		for (SolvedExams se : solvedExams) {
			grades.add(se.getExamGrade());
		}
		return grades;
	}
	
	/**
	 * Collects all the data we need about the exams
	 * And Save tha data for later use
	 */
	private void collectAndSetDataFromExams() {
		GetFromDB db = new GetFromDB();
		Exam exam = db.getExam(exam_id);
		
		students_stared = solvedExams.size();
		for (SolvedExams se : solvedExams) {
			String seSorI = se.getSubmittedOrInterruptedFlag();
			if (seSorI.equals("submitted")) submit_counter++;
			else interrupt_counter++;
		}

		examStatistics.setSubmitted_students_counter(submit_counter);
		examStatistics.setInterrupted_students_counter(interrupt_counter);
		examStatistics.setStudents_started_counter(students_stared);
		examStatistics.setReal_time_duration(String.valueOf(exam.getExamDuration()));
		examStatistics.setExam_ID(exam_id);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		examStatistics.setDate(dateFormat.format(solvedExams.get(0).getExamDateTime()));
		examStatistics.setDateTime(solvedExams.get(0).getExamDateTime());
	}
	
	/**
	 * Checks if all the solved exams were approved
	 * @return true if all the exams were approved
	 */
	private boolean isAllFinished() {
		for (SolvedExams se : solvedExams) {
			if (se.getApprovingTeacherID() == null || se.getApprovingTeacherID() == "") return false;
		}
		return true;
	}
	
}
