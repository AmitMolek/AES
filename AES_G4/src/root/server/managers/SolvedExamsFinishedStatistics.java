package root.server.managers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import root.dao.app.Exam;
import root.dao.app.SolvedExams;
import root.dao.app.Statistic;
import root.server.managers.dbmgr.GetFromDB;
import root.server.managers.dbmgr.SetInDB;

public class SolvedExamsFinishedStatistics {

	String exam_id;
	ArrayList<SolvedExams> solvedExams;
	Statistic examStatistics;
	
	int submit_counter;
	int interrupt_counter;
	int students_stared;
	
	public SolvedExamsFinishedStatistics(String exam_id) {
		this.solvedExams = new ArrayList<>();
		this.exam_id = exam_id;

		getSolvedExamsFromDB();
		if (!checkContinueExam()) return;
		this.examStatistics = new Statistic(collectGrades());
		collectAndSetDataFromExams();
		addToDB();
	}
	
	private boolean checkContinueExam() {
		GetFromDB db = new GetFromDB();
		if (!isAllFinished() || db.isSolvedExamStatisticsExist(exam_id)) return false;
		return true;
	}
	
	private void getSolvedExamsFromDB() {
		GetFromDB db = new GetFromDB();
		solvedExams = db.getSolvedExamsByExamID(exam_id);
	}

	private void addToDB() {
		SetInDB db = new SetInDB();
		db.addSolvedExamStatistics(examStatistics);
	}
	
	private ArrayList<Integer> collectGrades() {
		ArrayList<Integer> grades = new ArrayList<>();
		for (SolvedExams se : solvedExams) {
			grades.add(se.getExamGrade());
		}
		return grades;
	}
	
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
	
	private boolean isAllFinished() {
		for (SolvedExams se : solvedExams) {
			if (se.getApprovingTeacherID() == null || se.getApprovingTeacherID() == "") return false;
		}
		return true;
	}
	
}
