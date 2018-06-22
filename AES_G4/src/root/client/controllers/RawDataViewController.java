package root.client.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import ocsf.client.ObservableClient;
import root.client.managers.DataKeepManager;
import root.client.managers.ScreensManager;
import root.dao.app.AlterDuration;
import root.dao.app.Course;
import root.dao.app.CourseInSubject;
import root.dao.app.Exam;
import root.dao.app.ExecuteExam;
import root.dao.app.Question;
import root.dao.app.QuestionInExamData;
import root.dao.app.SolvedExams;
import root.dao.app.Statistic;
import root.dao.app.Subject;
import root.dao.app.SubjectATeacherTeach;
import root.dao.app.User;
import root.dao.message.AbstractMessage;
import root.dao.message.AllTablesMessage;
import root.dao.message.MessageFactory;

/**
 * 
 * @author Alon Ben-yosef
 * RawDataViewController is a controller class for RawDataView and is managing a full database view for the principal
 */
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
    private TableView<Statistic> exam_stats_table;

    @FXML
    private TableColumn<Statistic, String> exam_stats_examID;

    @FXML
    private TableColumn<Statistic, String> exam_stats_date;

    @FXML
    private TableColumn<Statistic, String> exam_stats_realTime;

    @FXML
    private TableColumn<Statistic, Integer> exam_stats_submitted;

    @FXML
    private TableColumn<Statistic, Integer> exam_stats_interrupted;

    @FXML
    private TableColumn<Statistic, Integer> exam_stats_total;

    @FXML
    private TableColumn<Statistic, Double> exam_stats_average;

    @FXML
    private TableColumn<Statistic, Integer> exam_stats_median;

    @FXML
    private TableColumn<Statistic, Integer> exam_stats_0_10;

    @FXML
    private TableColumn<Statistic, Integer> exam_stats_11_20;

    @FXML
    private TableColumn<Statistic, Integer> exam_stats_21_30;

    @FXML
    private TableColumn<Statistic, Integer> exam_stats_31_40;

    @FXML
    private TableColumn<Statistic, Integer> exam_stats_41_50;

    @FXML
    private TableColumn<Statistic, Integer> exam_stats_51_60;

    @FXML
    private TableColumn<Statistic, Integer> exam_stats_61_70;

    @FXML
    private TableColumn<Statistic, Integer> exam_stats_71_80;

    @FXML
    private TableColumn<Statistic, Integer> exam_stats_81_90;

    @FXML
    private TableColumn<Statistic, Integer> exam_stats_91_100;

    @FXML
    private TableView<ExecuteExam> execute_exams_table;

    @FXML
    private TableColumn<ExecuteExam, String> execute_exams_examId;

    @FXML
    private TableColumn<ExecuteExam, String> execute_exams_startDate;

    @FXML
    private TableColumn<ExecuteExam, String> execute_exams_code;

    @FXML
    private TableColumn<ExecuteExam, String> execute_exams_exam_type;

    @FXML
    private TableColumn<ExecuteExam, String> execute_exams_executing_teacher_ID;

    @FXML
    private TableView<Question> questions_table;

    @FXML
    private TableColumn<Question, String> questions_questionId;

    @FXML
    private TableColumn<Question, String> questions_question_text;
    
    @FXML
    private TableColumn<Question, String> questions_question_instruction;

    @FXML
    private TableColumn<Question, String> questions_answer1;

    @FXML
    private TableColumn<Question, String> questions_answer2;

    @FXML
    private TableColumn<Question, String> questions_answer3;

    @FXML
    private TableColumn<Question, String> questions_answer4;

    @FXML
    private TableColumn<Question, Integer> questions_correct_answer;

    @FXML
    private TableColumn<Question, String> questions_teacherId;

    @FXML
    private TableView<QuestionInExamData> questions_in_exam_table;

    @FXML
    private TableColumn<QuestionInExamData, String> questions_in_exam_questionId;

    @FXML
    private TableColumn<QuestionInExamData, String> questions_in_exam_examId;

    @FXML
    private TableColumn<QuestionInExamData, Integer> questions_in_exam_grade;

    @FXML
    private TableColumn<QuestionInExamData, String> questions_in_exam_studentText;

    @FXML
    private TableColumn<QuestionInExamData, String> questions_in_exam_teacherText;

    @FXML
    private TableView<SolvedExams> solved_exams_table;

    @FXML
    private TableColumn<SolvedExams, String> solved_exams_userId;

    @FXML
    private TableColumn<SolvedExams, String> solved_exams_examId;

    @FXML
    private TableColumn<SolvedExams, Integer> solved_exams_grade;

    @FXML
    private TableColumn<SolvedExams, Integer> solved_exams_duration;

    @FXML
    private TableColumn<SolvedExams, String> solved_exams_submitInterrupt;

    @FXML
    private TableColumn<SolvedExams, String> solved_exams_date;

    @FXML
    private TableColumn<SolvedExams, String> solved_exams_teacherNotes;

    @FXML
    private TableColumn<SolvedExams, String> solved_exams_alteration;

    @FXML
    private TableColumn<SolvedExams, String> solved_exams_teacherId;

    @FXML
    private TableColumn<SolvedExams, String> solved_exams_approval;

    @FXML
    private TableColumn<SolvedExams, String> solved_exams_cheatingFlag;

    @FXML
    private TableView<SubjectATeacherTeach> subject_a_teacher_teach_table;

    @FXML
    private TableColumn<SubjectATeacherTeach, String> subject_a_teacher_teach_teacherId;

    @FXML
    private TableColumn<SubjectATeacherTeach, String> subject_a_teacher_teach_subjectId;

    @FXML
    private TableView<Subject> subjects_table;

    @FXML
    private TableColumn<Subject, String> subjects_subject_Id;

    @FXML
    private TableColumn<Subject, String> subject_name;

    @FXML
    private TableView<User> users_table;

    @FXML
    private TableColumn<User,String> users_userId;

    @FXML
    private TableColumn<User, String> users_firstName;

    @FXML
    private TableColumn<User, String> users_lastName;

    @FXML
    private TableColumn<User, String> users_password;

    @FXML
    private TableColumn<User, String> users_permissions;
    
    /**
	 * An instance of client, used for observing
     */
    private ObservableClient client;
    
    /**
     * Is called as JavaFX loads the scene, this will define the factories for the table columns and send a
     * request to the sever to get the data tables. This method will also register RawDataViewController as a listener
     * for the client (in the order to get server's reply).
     */
    public void initialize() {
    	client = (ObservableClient)DataKeepManager.getInstance().getObject_NoRemove("client");// get the client from DataKeep, but dont remove it from there, for later use.
    	client.addObserver(this);
    	initTables();
    	AbstractMessage msg=MessageFactory.getInstance().getMessage("get-alltables", null);
    	try {
			client.sendToServer(msg);
		} catch (IOException e) {
			showAlert("Error Communicating With AES Sever","Please contact system administrator");
		}
    }
	/**
	 * Is called from initialize() to define property factory for each column.
	 */
	private void initTables() {
		initAlterDurationTable();
		initCoursesTable();
		initCourseInSubjectTable();
		initExamTable();
		initStatisticsTable();
		initExecuteExams();
		initQuestions();
		initQuestionInExam();
		initSolvedExam();
		initSubjectATeacherTeach();
		initSubject();
		initUsers();
	}

	/**
	 * Defines a PropertyValueFactory for each column in the Users table
	 */
	private void initUsers() {
		users_userId.setCellValueFactory(
    		    new PropertyValueFactory<User,String>("userID"));
		users_firstName.setCellValueFactory(
    		    new PropertyValueFactory<User,String>("userFirstName"));
		users_lastName.setCellValueFactory(
    		    new PropertyValueFactory<User,String>("userLastName"));
		users_password.setCellValueFactory(
    		    new PropertyValueFactory<User,String>("userPassword"));
		users_permissions.setCellValueFactory(
    		    new PropertyValueFactory<User,String>("userPremission"));
		
	}

	/**
	 * Defines a PropertyValueFactory for each column in the Subjects table
	 */
	private void initSubject() {
		subjects_subject_Id.setCellValueFactory(
    		    new PropertyValueFactory<Subject,String>("subjectID"));
		subject_name.setCellValueFactory(
    		    new PropertyValueFactory<Subject,String>("subjectName"));
	}

	/**
	 * Defines a PropertyValueFactory for each column in the Subjects for teachers table
	 */
	private void initSubjectATeacherTeach() {
		subject_a_teacher_teach_subjectId.setCellValueFactory(
    		    new PropertyValueFactory<SubjectATeacherTeach,String>("subjectID"));
		subject_a_teacher_teach_teacherId.setCellValueFactory(
    		    new PropertyValueFactory<SubjectATeacherTeach,String>("teacherID"));
		
	}

	/**
	 * Defines a PropertyValueFactory for each column in the Solved exams table
	 */
	private void initSolvedExam() {
			 solved_exams_userId.setCellValueFactory(
	    		    new PropertyValueFactory<SolvedExams,String>("sovingStudentID"));
			 solved_exams_examId.setCellValueFactory(
	    		    new PropertyValueFactory<SolvedExams,String>("examID"));
			 solved_exams_grade.setCellValueFactory(
		    		    new PropertyValueFactory<SolvedExams,Integer>("examGrade"));
			 solved_exams_duration.setCellValueFactory(
		    		    new PropertyValueFactory<SolvedExams,Integer>("solveDurationTime"));
			 solved_exams_submitInterrupt.setCellValueFactory(
		    		    new PropertyValueFactory<SolvedExams,String>("submittedOrInterruptedFlag"));
			 solved_exams_date.setCellValueFactory(
		    		    new PropertyValueFactory<SolvedExams,String>("examDateTime"));
			 solved_exams_teacherNotes.setCellValueFactory(
		    		    new PropertyValueFactory<SolvedExams,String>("teacherNotes"));
			 solved_exams_alteration.setCellValueFactory(
		    		    new PropertyValueFactory<SolvedExams,String>("gradeAlturationExplanation"));
			 solved_exams_teacherId.setCellValueFactory(
		    		    new PropertyValueFactory<SolvedExams,String>("approvingTeacherID"));
			 solved_exams_approval.setCellValueFactory(
		    		    new PropertyValueFactory<SolvedExams,String>("calculatedGradeApprovalStateByTeacher"));
			 solved_exams_cheatingFlag.setCellValueFactory(
		    		    new PropertyValueFactory<SolvedExams,String>("cheatingFlag"));
	}

	/**
	 * Defines a PropertyValueFactory for each column in the Question in exam table
	 */
	private void initQuestionInExam() {
		questions_in_exam_examId.setCellValueFactory(
    		    new PropertyValueFactory<QuestionInExamData,String>("examId"));
		questions_in_exam_questionId.setCellValueFactory(
    		    new PropertyValueFactory<QuestionInExamData,String>("questionId"));
		questions_in_exam_grade.setCellValueFactory(
				new PropertyValueFactory<QuestionInExamData,Integer>("grade"));
		questions_in_exam_teacherText.setCellValueFactory(
    		    new PropertyValueFactory<QuestionInExamData,String>("teacherText"));
		questions_in_exam_studentText.setCellValueFactory(
    		    new PropertyValueFactory<QuestionInExamData,String>("studentText"));
	}

	/**
	 * Defines a PropertyValueFactory for each column in the Questions table
	 */
	private void initQuestions() {
		questions_questionId.setCellValueFactory(
    		    new PropertyValueFactory<Question,String>("questionId"));
		questions_teacherId.setCellValueFactory(
    		    new PropertyValueFactory<Question,String>("teacherAssembeld"));
		questions_question_text.setCellValueFactory(
    		    new PropertyValueFactory<Question,String>("questionText"));
		questions_question_instruction.setCellValueFactory(
    		    new PropertyValueFactory<Question,String>("idquestionIntruction"));
		questions_answer1.setCellValueFactory(
    		    new PropertyValueFactory<Question,String>("ans1"));
		questions_answer2.setCellValueFactory(
    		    new PropertyValueFactory<Question,String>("ans2"));
		questions_answer3.setCellValueFactory(
    		    new PropertyValueFactory<Question,String>("ans3"));
		questions_answer4.setCellValueFactory(
    		    new PropertyValueFactory<Question,String>("ans4"));
		questions_correct_answer.setCellValueFactory(
    		    new PropertyValueFactory<Question,Integer>("correctAns"));
	}

	/**
	 * Defines a PropertyValueFactory for each column in the Execute exam table
	 */
	private void initExecuteExams() {
		execute_exams_code.setCellValueFactory(
    		    new PropertyValueFactory<ExecuteExam,String>("examPassword"));
		execute_exams_exam_type.setCellValueFactory(
    		    new PropertyValueFactory<ExecuteExam,String>("examType"));
		execute_exams_executing_teacher_ID.setCellValueFactory(
    		    new PropertyValueFactory<ExecuteExam,String>("teacherId"));
		execute_exams_startDate.setCellValueFactory(
    		    new PropertyValueFactory<ExecuteExam,String>("startTime"));
		execute_exams_examId.setCellValueFactory(
    		    new PropertyValueFactory<ExecuteExam,String>("examId"));
	}
	
	/**
	 * Defines a PropertyValueFactory for each column in the Statistics exam table
	 */
	private void initStatisticsTable() {
		exam_stats_examID.setCellValueFactory(
    		    new PropertyValueFactory<Statistic,String>("exam_ID"));
		exam_stats_date.setCellValueFactory(
    		    new PropertyValueFactory<Statistic,String>("date"));
		exam_stats_realTime.setCellValueFactory(
    		    new PropertyValueFactory<Statistic,String>("real_time_duration"));
		exam_stats_submitted.setCellValueFactory(
    		    new PropertyValueFactory<Statistic,Integer>("submitted_students_counter"));
		exam_stats_interrupted.setCellValueFactory(
    		    new PropertyValueFactory<Statistic,Integer>("interrupted_students_counter"));
		exam_stats_total.setCellValueFactory(
    		    new PropertyValueFactory<Statistic,Integer>("students_started_counter"));
		exam_stats_average.setCellValueFactory(
    		    new PropertyValueFactory<Statistic,Double>("exams_avg"));
		exam_stats_median.setCellValueFactory(
    		    new PropertyValueFactory<Statistic,Integer>("exams_median"));
		exam_stats_0_10.setCellValueFactory(
    		    new PropertyValueFactory<Statistic,Integer>("grade_derivative_0_10"));
		exam_stats_11_20.setCellValueFactory(
    		    new PropertyValueFactory<Statistic,Integer>("grade_derivative_11_20"));
		exam_stats_21_30.setCellValueFactory(
    		    new PropertyValueFactory<Statistic,Integer>("grade_derivative_21_30"));
		exam_stats_31_40.setCellValueFactory(
    		    new PropertyValueFactory<Statistic,Integer>("grade_derivative_31_40"));
		exam_stats_41_50.setCellValueFactory(
    		    new PropertyValueFactory<Statistic,Integer>("grade_derivative_41_50"));
		exam_stats_51_60.setCellValueFactory(
    		    new PropertyValueFactory<Statistic,Integer>("grade_derivative_51_60"));
		exam_stats_61_70.setCellValueFactory(
    		    new PropertyValueFactory<Statistic,Integer>("grade_derivative_61_70"));
		exam_stats_71_80.setCellValueFactory(
    		    new PropertyValueFactory<Statistic,Integer>("grade_derivative_71_80"));
		exam_stats_81_90.setCellValueFactory(
    		    new PropertyValueFactory<Statistic,Integer>("grade_derivative_81_90"));
		exam_stats_91_100.setCellValueFactory(
    		    new PropertyValueFactory<Statistic,Integer>("grade_derivative_91_100"));
	}

	/**
	 * Defines a PropertyValueFactory for each column in the Exams table
	 */
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

	/**
	 * Defines a PropertyValueFactory for each column in the Course in subject table
	 */
	private void initCourseInSubjectTable() {
		course_in_subject_course_id.setCellValueFactory(
    		    new PropertyValueFactory<CourseInSubject,String>("course_id"));
		course_in_subject_subject_id.setCellValueFactory(
    		    new PropertyValueFactory<CourseInSubject,String>("subject_id"));
	}

	/**
	 * Defines a PropertyValueFactory for each column in the Courses table
	 */
	private void initCoursesTable() {
		course_id.setCellValueFactory(
    		    new PropertyValueFactory<Course,String>("courseId"));
		course_name.setCellValueFactory(
    		    new PropertyValueFactory<Course,String>("courseName"));
	}

	/**
	 * Defines a PropertyValueFactory for each column in the Alter duration table
	 */
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

	/**
	 * An override of update in Observer, will be called once ObservableClient calls it
	 */
	@Override
	public void update(Observable o, Object msg) {
		if(msg instanceof AbstractMessage) {
			if(((AbstractMessage) msg).getMsg().equals("ok-get-alltables")) {
				AllTablesMessage allMessage=(AllTablesMessage)msg;
				updateTables(allMessage);
				
			}
		}
	}

	/**
	 * Updates all tables with data from the server
	 * @param allMessage a message containing all the db tables from the server
	 */
	private void updateTables(AllTablesMessage allMessage) {
		updateAlterDurationTable(allMessage.getAlterDurList());
		updateCourseTable(allMessage.getCourseList());
		updateCourseInSubjectTable(allMessage.getCourseInSubList());
		updateExamTable(allMessage.getExamList());
		updateStatsTable(allMessage.getStatList());
		updateExecuteExamTable(allMessage.getExecuteList());
		updateQuestionsTable(allMessage.getQuestionList());
		updateQuestionsInExamTable(allMessage.getQuestionInExamList());
		updateSolvedExam(allMessage.getSolvedExamList());
		updateSubjectATeacherTeach(allMessage.getSubjectTeacherList());
		updateSubject(allMessage.getSubjectList());
		updateUsers(allMessage.getUserList());
	}

	/**
	 * Updates the Users table
	 * @param userList a list of Users from the server
	 */
	private void updateUsers(ArrayList<User> userList) {
		ObservableList<User> myList=FXCollections.observableArrayList();
		myList.addAll(userList);
		users_table.setItems(myList);	
	}

	/**
	 * Updates the Subjects table
	 * @param subjectList a list of Subjects from the server
	 */
	private void updateSubject(ArrayList<Subject> subjectList) {
		ObservableList<Subject> myList=FXCollections.observableArrayList();
		myList.addAll(subjectList);
		subjects_table.setItems(myList);			
	}

	/**
	 * Updates the Subject a teacher teach table
	 * @param SubjectTeacherList a list of SubjectATeacherTeach from the server
	 */
	private void updateSubjectATeacherTeach(ArrayList<SubjectATeacherTeach> SubjectTeacherList) {
		ObservableList<SubjectATeacherTeach> myList=FXCollections.observableArrayList();
		myList.addAll(SubjectTeacherList);
		subject_a_teacher_teach_table.setItems(myList);			
	}

	/**
	 * Updates the Solved exams table
	 * @param solvedExamList a list of SolvedExams from the server
	 */
	private void updateSolvedExam(ArrayList<SolvedExams> solvedExamList) {
		ObservableList<SolvedExams> myList=FXCollections.observableArrayList();
		myList.addAll(solvedExamList);
		solved_exams_table.setItems(myList);			
	}

	/**
	 * Updates the Questions in exam table
 	 * @param questionInExamList a list of QuestionsInExamData from the server
	 */
	private void updateQuestionsInExamTable(ArrayList<QuestionInExamData> questionInExamList) {
		ObservableList<QuestionInExamData> myList=FXCollections.observableArrayList();
		myList.addAll(questionInExamList);
		questions_in_exam_table.setItems(myList);		
	}

	/**
	 * Updates the Questions table
	 * @param questionList a list of Questions from the server
	 */
	private void updateQuestionsTable(ArrayList<Question> questionList) {
		ObservableList<Question> myList=FXCollections.observableArrayList();
		myList.addAll(questionList);
		questions_table.setItems(myList);
	}

	/**
	 * Updates the Exam executions table
	 * @param executeList a list of ExecuteExam from the server
	 */
	private void updateExecuteExamTable(ArrayList<ExecuteExam> executeList) {
		ObservableList<ExecuteExam> myList=FXCollections.observableArrayList();
		myList.addAll(executeList);
		execute_exams_table.setItems(myList);
	}

	/**
	 * Updates the Statistics table
	 * @param statList a list of Statistics from the server
	 */
	private void updateStatsTable(ArrayList<Statistic> statList) {
		ObservableList<Statistic> myList=FXCollections.observableArrayList();
		myList.addAll(statList);
		exam_stats_table.setItems(myList);		
	}
	
	/**
	 * Updates the Exam table
	 * @param examList a list of exams from the server
	 */
	private void updateExamTable(ArrayList<Exam> examList) {
		ObservableList<Exam> myList=FXCollections.observableArrayList();
		myList.addAll(examList);
		exams_table.setItems(myList);
	}
	
	/**
	 * Updates the Course in subject table
	 * @param courseInSubList a list of CourseInSubject from the server
	 */
	private void updateCourseInSubjectTable(ArrayList<CourseInSubject> courseInSubList) {
		ObservableList<CourseInSubject> myList=FXCollections.observableArrayList();
		myList.addAll(courseInSubList);
		course_in_subject_table.setItems(myList);
		
	}

	/**
	 * Updates the Course table
	 * @param courseList a list of Courses from the server
	 */
	private void updateCourseTable(ArrayList<Course> courseList) {
		ObservableList<Course> myList=FXCollections.observableArrayList();
		myList.addAll(courseList);
		courses_table.setItems(myList);
		
	}

	/**
	 * Updates the Alter duration table
	 * @param alterDurList a list of AlterDurations from the server
	 */
	private void updateAlterDurationTable(ArrayList<AlterDuration> alterDurList) {
		ObservableList<AlterDuration> myList=FXCollections.observableArrayList();
		myList.addAll(alterDurList);
		alter_duration_table.setItems(myList);
	}
	
	  /**
     * This will show a temporary warning text for invalid queries or server errors
     * @param header the head of the warning
     * @param errorMessage the content of thrown exception
     */
    private void showAlert(String header,String errorMessage) {
        	Platform.runLater(() -> {								
                Alert alert = new Alert(AlertType.ERROR);
                alert.initOwner(ScreensManager.getInstance().getPrimaryStage());
                alert.setTitle("Error");
                alert.setHeaderText(header);
                alert.setContentText(errorMessage);
                alert.showAndWait();       
    		});
    }
}
