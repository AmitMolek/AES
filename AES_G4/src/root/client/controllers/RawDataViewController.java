package root.client.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ocsf.client.ObservableClient;
import root.client.managers.DataKeepManager;
import root.client.managers.ScreensManager;
import root.dao.app.AlterDuration;
import root.dao.app.Course;
import root.dao.app.CourseInSubject;
import root.dao.app.Exam;
import root.dao.app.ExamTableDataLine;
import root.dao.message.AbstractMessage;
import root.dao.message.AllTablesMessage;
import root.dao.message.MessageFactory;
import root.dao.message.StatsMessage;

public class RawDataViewController implements Observer {

    @FXML
    private TableView<AlterDuration> alter_duration_table;

    @FXML
    private TableColumn<AlterDuration, String> alter_duration_userID;

    @FXML
    private TableColumn<AlterDuration, String> alter_duration_examID;

    @FXML
    private TableColumn<AlterDuration, String> alter_duration_date;

    @FXML
    private TableColumn<AlterDuration, String> alter_duration_teacherExp;

    @FXML
    private TableColumn<AlterDuration, String> alter_duration_prinAns;

    @FXML
    private TableColumn<AlterDuration, Integer> alter_duration_originalDur;

    @FXML
    private TableColumn<AlterDuration, Integer> alter_duration_changed;

    @FXML
    private TableView<Course> courses_table;

    @FXML
    private TableColumn<Course, String> course_id;

    @FXML
    private TableColumn<Course, String> course_name;

    @FXML
    private TableView<CourseInSubject> course_in_subject_table;

    @FXML
    private TableColumn<CourseInSubject, String> course_in_subject_subject_id;

    @FXML
    private TableColumn<CourseInSubject, String> course_in_subject_course_id;

    @FXML
    private TableView<Exam> exams_table;

    @FXML
    private TableColumn<Exam, String> exams_examId;

    @FXML
    private TableColumn<Exam, String> exams_teacherAssembler;

    @FXML
    private TableColumn<Exam, Integer> exams_exam_original_allocated_duration;

    @FXML
    private TableColumn<Exam, Integer> exams_exams_state;

    @FXML
    private TableColumn<Exam, Integer> exams_lock_flag;

    @FXML
    private TableView<?> exam_stats_table;

    @FXML
    private TableColumn<?, ?> exam_stats_examID;

    @FXML
    private TableColumn<?, ?> exam_stats_date;

    @FXML
    private TableColumn<?, ?> exam_stats_realTime;

    @FXML
    private TableColumn<?, ?> exam_stats_submitted;

    @FXML
    private TableColumn<?, ?> exam_stats_interrupted;

    @FXML
    private TableColumn<?, ?> exam_stats_total;

    @FXML
    private TableColumn<?, ?> exam_stats_average;

    @FXML
    private TableColumn<?, ?> exam_stats_median;

    @FXML
    private TableColumn<?, ?> exam_stats_0_10;

    @FXML
    private TableColumn<?, ?> exam_stats_11_20;

    @FXML
    private TableColumn<?, ?> exam_stats_21_30;

    @FXML
    private TableColumn<?, ?> exam_stats_31_40;

    @FXML
    private TableColumn<?, ?> exam_stats_41_50;

    @FXML
    private TableColumn<?, ?> exam_stats_51_60;

    @FXML
    private TableColumn<?, ?> exam_stats_61_70;

    @FXML
    private TableColumn<?, ?> exam_stats_71_80;

    @FXML
    private TableColumn<?, ?> exam_stats_81_90;

    @FXML
    private TableColumn<?, ?> exam_stats_91_100;

    @FXML
    private TableView<?> execute_exams_table;

    @FXML
    private TableColumn<?, ?> execute_exams_examId;

    @FXML
    private TableColumn<?, ?> execute_exams_startDate;

    @FXML
    private TableColumn<?, ?> execute_exams_code;

    @FXML
    private TableColumn<?, ?> execute_exams_exam_type;

    @FXML
    private TableColumn<?, ?> execute_exams_executing_teacher_ID;

    @FXML
    private TableView<?> questions_table;

    @FXML
    private TableColumn<?, ?> questions_questionId;

    @FXML
    private TableColumn<?, ?> questions_question_instruction;

    @FXML
    private TableColumn<?, ?> questions_answer1;

    @FXML
    private TableColumn<?, ?> questions_answer2;

    @FXML
    private TableColumn<?, ?> questions_answer3;

    @FXML
    private TableColumn<?, ?> questions_answer4;

    @FXML
    private TableColumn<?, ?> questions_correct_answer;

    @FXML
    private TableColumn<?, ?> questions_teacherId;

    @FXML
    private TableView<?> questions_in_exam_table;

    @FXML
    private TableColumn<?, ?> questions_in_exam_questionId;

    @FXML
    private TableColumn<?, ?> questions_in_exam_examId;

    @FXML
    private TableColumn<?, ?> questions_in_exam_grade;

    @FXML
    private TableColumn<?, ?> questions_in_exam_studentText;

    @FXML
    private TableColumn<?, ?> questions_in_exam_teacherText;

    @FXML
    private TableView<?> solved_exams_table;

    @FXML
    private TableColumn<?, ?> solved_exams_userId;

    @FXML
    private TableColumn<?, ?> solved_exams_examId;

    @FXML
    private TableColumn<?, ?> solved_exams_grade;

    @FXML
    private TableColumn<?, ?> solved_exams_duration;

    @FXML
    private TableColumn<?, ?> solved_exams_submitInterrupt;

    @FXML
    private TableColumn<?, ?> solved_exams_date;

    @FXML
    private TableColumn<?, ?> solved_exams_teacherNotes;

    @FXML
    private TableColumn<?, ?> solved_exams_alteration;

    @FXML
    private TableColumn<?, ?> solved_exams_teacherId;

    @FXML
    private TableColumn<?, ?> solved_exams_approval;

    @FXML
    private TableColumn<?, ?> solved_exams_cheatingFlag;

    @FXML
    private TableView<?> subject_a_teacher_teach_table;

    @FXML
    private TableColumn<?, ?> subject_a_teacher_teach_teacherId;

    @FXML
    private TableColumn<?, ?> subject_a_teacher_teach_subjectId;

    @FXML
    private TableView<?> subjects_table;

    @FXML
    private TableColumn<?, ?> subjects_subject_Id;

    @FXML
    private TableColumn<?, ?> subject_name;

    @FXML
    private TableView<?> users_table;

    @FXML
    private TableColumn<?, ?> users_userId;

    @FXML
    private TableColumn<?, ?> users_firstName;

    @FXML
    private TableColumn<?, ?> users_lastName;

    @FXML
    private TableColumn<?, ?> users_password;

    @FXML
    private TableColumn<?, ?> users_permissions;
    
    private ObservableClient client;
    
    public void initialize() {
    	client = (ObservableClient)DataKeepManager.getInstance().getObject_NoRemove("client");// get the client from DataKeep, but dont remove it from there, for later use.
    	client.addObserver(this);
    	initTables();
    	AbstractMessage msg=MessageFactory.getInstance().getMessage("get-alltables", null);
    	try {
			client.sendToServer(msg);
		} catch (IOException e) {
			// TODO Deal with failure
			e.printStackTrace();
		}
    }

	private void initTables() {
		initAlterDurationTable();
		initCoursesTable();
		initCourseInSubjectTable();
		initExamTable();
	}

	private void initExamTable() {
		exams_lock_flag.setCellValueFactory(
    		    new PropertyValueFactory<Exam,Integer>("lock"));
		exams_exams_state.setCellValueFactory(
    		    new PropertyValueFactory<Exam,Integer>("ExamState"));
		exams_teacherAssembler.setCellValueFactory(
    		    new PropertyValueFactory<Exam,String>("teacherId"));
		exams_exam_original_allocated_duration.setCellValueFactory(
    		    new PropertyValueFactory<Exam,Integer>("examDuration"));
		exams_examId.setCellValueFactory(
    		    new PropertyValueFactory<Exam,String>("examId"));
		
	}

	private void initCourseInSubjectTable() {
		course_in_subject_course_id.setCellValueFactory(
    		    new PropertyValueFactory<CourseInSubject,String>("course_id"));
		course_in_subject_subject_id.setCellValueFactory(
    		    new PropertyValueFactory<CourseInSubject,String>("subject_id"));
	}

	private void initCoursesTable() {
		course_id.setCellValueFactory(
    		    new PropertyValueFactory<Course,String>("courseId"));
		course_name.setCellValueFactory(
    		    new PropertyValueFactory<Course,String>("courseName"));
	}

	private void initAlterDurationTable() {
		
		alter_duration_userID.setCellValueFactory(
    		    new PropertyValueFactory<AlterDuration,String>("userID"));
		alter_duration_examID.setCellValueFactory(
    		    new PropertyValueFactory<AlterDuration,String>("examID"));
		alter_duration_date.setCellValueFactory(
				new PropertyValueFactory<AlterDuration,String>("date"));
		alter_duration_teacherExp.setCellValueFactory(
				new PropertyValueFactory<AlterDuration,String>("teacherExp"));
		alter_duration_prinAns.setCellValueFactory(
    		    new PropertyValueFactory<AlterDuration,String>("principalAns"));
		alter_duration_originalDur.setCellValueFactory(
				new PropertyValueFactory<AlterDuration,Integer>("original_duration"));
		alter_duration_changed.setCellValueFactory(
				new PropertyValueFactory<AlterDuration,Integer>("after_change_duration"));
    	
	}

	@Override
	public void update(Observable o, Object msg) {
		if(msg instanceof AbstractMessage) {
			if(((AbstractMessage) msg).getMsg().equals("ok-get-alltables")) {
				AllTablesMessage allMessage=(AllTablesMessage)msg;
				updateTables(allMessage);
				
			}
		}
	}

	private void updateTables(AllTablesMessage allMessage) {
		updateAlterDurationTable(allMessage.getAlterDurList());
		updateCourseTable(allMessage.getCourseList());
		updateCourseInSubjectTable(allMessage.getCourseInSubList());
		updateExamTable(allMessage.getExamList());
	}

	private void updateExamTable(ArrayList<Exam> examList) {
		ObservableList<Exam> myList=FXCollections.observableArrayList();
		myList.addAll(examList);
		exams_table.setItems(myList);
	}

	private void updateCourseInSubjectTable(ArrayList<CourseInSubject> courseInSubList) {
		ObservableList<CourseInSubject> myList=FXCollections.observableArrayList();
		myList.addAll(courseInSubList);
		course_in_subject_table.setItems(myList);
		
	}

	private void updateCourseTable(ArrayList<Course> courseList) {
		ObservableList<Course> myList=FXCollections.observableArrayList();
		myList.addAll(courseList);
		courses_table.setItems(myList);
		
	}

	private void updateAlterDurationTable(ArrayList<AlterDuration> alterDurList) {
		ObservableList<AlterDuration> myList=FXCollections.observableArrayList();
		myList.addAll(alterDurList);
		alter_duration_table.setItems(myList);
	}
}
