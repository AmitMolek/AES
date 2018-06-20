package root.client.controllers;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ocsf.client.ObservableClient;
import root.client.managers.DataKeepManager;
import root.client.managers.ScreensManager;
import root.dao.message.AbstractMessage;
import root.dao.message.AllTablesMessage;
import root.dao.message.MessageFactory;
import root.dao.message.StatsMessage;

public class RawDataViewController implements Observer {

    @FXML
    private TableView<?> alter_duration_table;

    @FXML
    private TableColumn<?, ?> alter_duration_userID;

    @FXML
    private TableColumn<?, ?> alter_duration_examID;

    @FXML
    private TableColumn<?, ?> alter_duration_date;

    @FXML
    private TableColumn<?, ?> alter_duration_teacherExp;

    @FXML
    private TableColumn<?, ?> alter_duration_prinAns;

    @FXML
    private TableColumn<?, ?> alter_duration_originalDur;

    @FXML
    private TableColumn<?, ?> alter_duration_changed;

    @FXML
    private TableView<?> courses_table;

    @FXML
    private TableColumn<?, ?> course_id;

    @FXML
    private TableColumn<?, ?> course_name;

    @FXML
    private TableView<?> course_in_subject_table;

    @FXML
    private TableColumn<?, ?> course_in_subject_subject_id;

    @FXML
    private TableColumn<?, ?> course_in_subject_course_id;

    @FXML
    private TableView<?> exams_table;

    @FXML
    private TableColumn<?, ?> exams_userId;

    @FXML
    private TableColumn<?, ?> exams_teacherAssembler;

    @FXML
    private TableColumn<?, ?> exams_exam_original_allocated_duration;

    @FXML
    private TableColumn<?, ?> exams_exams_state;

    @FXML
    private TableColumn<?, ?> exams_lock_flag;

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
    private TableColumn<?, ?> exam_stats_21_30col;

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
    	MessageFactory.getInstance().getMessage("get-alltables", null);
    }

	@Override
	public void update(Observable o, Object msg) {
		if(msg instanceof AbstractMessage) {
			if(((AbstractMessage) msg).getMsg().equals("ok-get-alltables")) {
				AllTablesMessage allMessage=(AllTablesMessage)msg;
				//TODO
				
			}
		}
	}
}
