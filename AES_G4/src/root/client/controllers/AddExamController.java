package root.client.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ocsf.client.ObservableClient;
import root.client.managers.DataKeepManager;
import root.client.managers.ScreensManager;
import root.dao.app.Course;
import root.dao.app.Exam;
import root.dao.app.Question;
import root.dao.app.QuestionInExam;
import root.dao.app.Subject;
import root.dao.app.User;
import root.dao.message.CourseMessage;
import root.dao.message.ExamMessage;
import root.dao.message.MessageFactory;
import root.dao.message.QuestionsMessage;
import root.dao.message.SimpleMessage;
import root.dao.message.SubjectMessage;
import root.server.managers.dbmgr.DbManagerInterface;
import root.util.log.Log;
import root.util.log.LogLine;

/**
 * 
 * 
 * 
 * A class that is responsible for adding an exam window
 * 
 * @author Omer Haimovich
 *
 */
public class AddExamController implements Observer {

	// FXML variables **********************************************

	@FXML
	private Button btnRemove;

	@FXML
	private Button btnAddToExam;

	@FXML
	private FlowPane myFlow;

	@FXML
	private ComboBox<String> cmbCourse;

	@FXML
	private ComboBox<String> cmbSubject;

	@FXML
	private AnchorPane rootPane;

	@FXML
	private Button btnAddExam;

	@FXML
	private TextField txtDuration;

	@FXML
	private TextField txtTeacher;

	@FXML
	private Button btnAddQuestion;

	// Instance variables **********************************************

	/**
	 * Counter that counts how many time teacher select subject from the subject
	 * combo box
	 */
	private int count;
	/**
	 * The login teacher
	 */
	private User teacher;
	/**
	 * Generates new communications between server and client
	 */
	private MessageFactory messageFact;
	/**
	 * 
	 * Keeps our client in order to communicate with the server
	 */
	private ObservableClient client;
	/**
	 * 
	 * A list of all the subjects taught by the teacher
	 */
	private ArrayList<Subject> teacherSubject;
	/**
	 * A list of all the courses taught by the teacher
	 */
	private ArrayList<Course> CourseInSubject;
	/**
	 * 
	 * A log file that is responsible for documenting the actions performed in the
	 * application
	 */
	private Log log;
	/**
	 * A list of all questions belonging to the chosen course and subject
	 */
	private ArrayList<Question> question;
	/**
	 * A list of all exams belonging to the chosen course and subject
	 */
	private ArrayList<Exam> exams;
	/**
	 * list of the custom component
	 */
	private ArrayList<AddQuestionToExam> myComponent = new ArrayList<AddQuestionToExam>();
	/**
	 * The number of exams
	 */
	private static int countId = 1;
	/**
	 * The chosen subject
	 */
	private Subject newSubject;
	/**
	 * The chosen course
	 */
	private Course newCourse;
	/**
	 * The manager that responsible for switching between windows in the system
	 */
	private ScreensManager screenManager;
	/**
	 * The manager that responsible for transmit data between windows in the system
	 */
	private DataKeepManager dkm;
	/**
	 * The main window of the application
	 */
	private Stage mainApp;
	/**
	 * The id of the exam
	 */
	private String examId;
	/**
	 * The custom component
	 */
	private AddQuestionToExam newQuestion;

	// CLASS METHODS *************************************************

	/**
	 * 
	 * A method that allows the teacher to select a subject
	 * 
	 * @param event
	 * 
	 *            An event that happens when a teacher select from subject combo box
	 */
	@FXML
	void SelectSubject(ActionEvent event) {
		int i = 0;
		int size;
		count++;
		String selectedVaule = cmbSubject.getValue();
		String[] selectedSubject = selectedVaule.toLowerCase().split("-");
		if (count > 1) {
			size = cmbCourse.getItems().size();
			while (i < size) {
				cmbCourse.getItems().remove(0);
				i++;
			}
			i = 0;
			size = myComponent.size();
			while (i < size) {
				myFlow.getChildren().remove(0);
				myComponent.remove(0);
				i++;
			}
			i = 0;
			size = question.size();
			while (i < size) {
				question.remove(0);
				i++;
			}
			i = 0;
			if (newQuestion != null) {
				newQuestion.clearQuestionInExam();
				newQuestion.clearComboBox();

			}
			btnRemove.setDisable(true);
		}
		newSubject = new Subject(selectedSubject[0], selectedSubject[1]);

		CourseMessage getCourseSubject = (CourseMessage) messageFact.getMessage("get-courses", newSubject);
		QuestionsMessage getQuestionOfsubject = (QuestionsMessage) messageFact.getMessage("get-questions", newSubject);
		try {
			client.sendToServer(getCourseSubject);
			client.sendToServer(getQuestionOfsubject);
		} catch (IOException e) {
			log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * A method that allows the teacher to select a course
	 * 
	 * @param event
	 * 
	 *            An event that happens when a teacher select from course combo box
	 */
	@FXML
	void SelectCourse(ActionEvent event) {
		if (cmbCourse.getValue() != null) {
			String selectedVaule = cmbCourse.getValue();
			String[] selectedCourse = selectedVaule.toLowerCase().split("-");
			newCourse = new Course(selectedCourse[0], selectedCourse[1]);
			setIdToExam();
			btnAddQuestion.setDisable(false);
		}

	}

	/**
	 * A method that allows the teacher to add a component to the exam form
	 * 
	 * @param event
	 *            An event occurs when the teacher presses a `add question` button
	 * 
	 */

	@FXML
	void AddQuestionToTheExam(ActionEvent event) {
		newQuestion = new AddQuestionToExam();
		btnRemove.setDisable(false);
		newQuestion.setQuestionCombo(question);
		myFlow.getChildren().add(newQuestion);
		btnAddToExam.setDisable(false);
		btnAddQuestion.setDisable(true);
		btnAddExam.setDisable(true);
		myComponent.add(newQuestion);

	}

	/**
	 * A method that allows the teacher to add exam to exams pool
	 * 
	 * @param event
	 *            An event occurs when the teacher presses a `add exam` button
	 * 
	 */
	@FXML
	void AddExam(ActionEvent event) {
		if (isInputValidAddExam()) {

			ArrayList<QuestionInExam> theQuestions = new ArrayList<QuestionInExam>();
			String examDuration = txtDuration.getText();
			int duration = Integer.parseInt(examDuration);
			for (AddQuestionToExam add : myComponent) {
				theQuestions.add(add.getExamQuestion());
			}
			Exam newExam = new Exam(examId, teacher, duration, theQuestions);
			ExamMessage newMessage = (ExamMessage) messageFact.getMessage("put-exams", newExam);
			try {
				client.sendToServer(newMessage);
			} catch (IOException e) {
				log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
				e.printStackTrace();
			}
		}

	}

	/**
	 * 
	 * 
	 * A method that sets a new id to the exam
	 */
	public void setIdToExam() {
		String examId = newSubject.getSubjectID() + newCourse.getCourseId();
		ExamMessage newMessage = (ExamMessage) messageFact.getMessage("get-exams", examId);
		try {
			client.sendToServer(newMessage);
		} catch (IOException e) {
			log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * 
	 * The method initializes the window when it comes up
	 * 
	 * @throws IOException
	 *             if the window cannot be shown
	 */
	@FXML
	public void initialize() throws IOException {
		count = 0;
		log = Log.getInstance();
		dkm = DataKeepManager.getInstance();
		screenManager = ScreensManager.getInstance();
		mainApp = screenManager.getPrimaryStage();
		client = new ObservableClient((String) dkm.getObject_NoRemove("ip"), 8000);
		client.addObserver(this);
		client.openConnection();
		messageFact = MessageFactory.getInstance();
		cmbCourse.setPromptText("Choose course");
		cmbSubject.setPromptText("Choose subject");
		teacher = dkm.getUser();
		txtTeacher.setText(teacher.getUserFirstName() + " " + teacher.getUserLastName());
		txtTeacher.setDisable(true);
		btnAddToExam.setDisable(true);
		btnAddQuestion.setDisable(true);
		btnRemove.setDisable(true);
		cmbCourse.setDisable(true);
		SubjectMessage getTeacherSubject = (SubjectMessage) messageFact.getMessage("get-subjects", teacher.getUserID());
		client.sendToServer(getTeacherSubject);
	}

	/**
	 *
	 * A method that is responsible for handling messages sent from the server
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof SubjectMessage) {
			SubjectMessage intialSubjectMessage = (SubjectMessage) arg1;
			teacherSubject = intialSubjectMessage.getTeacherSubject();
			for (Subject s : teacherSubject) {
				cmbSubject.getItems().add(s.getSubjectID() + "-" + s.getSubjectName());
			}
		}

		if (arg1 instanceof CourseMessage) {
			CourseMessage intialCourseMessage = (CourseMessage) arg1;
			CourseInSubject = intialCourseMessage.getCourses();
			for (Course c : CourseInSubject) {
				cmbCourse.getItems().add(c.getCourseId() + "-" + c.getCourseName());
			}
			cmbCourse.setDisable(false);
		}

		if (arg1 instanceof QuestionsMessage) {
			QuestionsMessage intialQuestionMessage = (QuestionsMessage) arg1;
			question = intialQuestionMessage.getQuestions();
		}

		if (arg1 instanceof ExamMessage) {
			ExamMessage intialExamMessage = (ExamMessage) arg1;
			exams = intialExamMessage.getExams();
			if (exams.size() > 0) {
				Exam exam = exams.get(exams.size() - 1);
				String tempId = exam.getExamId().substring(4, 6);
				int id = Integer.parseInt(tempId) + 1;
				countId = id;
				if (countId <= 9)
					examId = newSubject.getSubjectID() + newCourse.getCourseId() + "0" + Integer.toString(countId);
				else
					examId = newSubject.getSubjectID() + newCourse.getCourseId() + Integer.toString(countId);
			} else {
				countId = 1;
				examId = newSubject.getSubjectID() + newCourse.getCourseId() + "0" + Integer.toString(countId);
			}

		}

		if (arg1 instanceof SimpleMessage) {
			log.writeToLog(LogLine.LineType.INFO, "Exam added");
			newQuestion.clearQuestionInExam();
			newQuestion.clearComboBox();
			Platform.runLater(() -> { // In order to run javaFX thread.(we recieve from server a java thread)
				try {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.initOwner(mainApp);
					alert.setTitle("Exam added");
					alert.setHeaderText("Exam added successeful");
					alert.setContentText("The exam was added successful");
					alert.showAndWait();
					screenManager.activate("home");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
				}
			});

		}

	}

	/**
	 * Checks whether all fields are valid
	 * 
	 * @return true if all inputs valid and false if not
	 */
	private boolean isInputValidAddExam() {
		String errorMessage = "";

		if (cmbSubject.getSelectionModel().getSelectedItem() == null) {// || firstNameField.getText().length() == 0) {
			errorMessage += "Please Select a Subject!\n";
		}
		if (cmbCourse.getSelectionModel().getSelectedItem() == null) {
			errorMessage += "Please Select a Course!\n";
		}

		if (AddQuestionToExam.getCount() == 0) {
			errorMessage += "Please Add Question!\n";
		}

		if (txtDuration.getText() == null || txtDuration.getText().length() == 0) {
			errorMessage += "No valid exam duration\n";
		}

		if (!(txtDuration.getText().matches("[0-9]+"))) {
			errorMessage += "can not enter letters in duration\n";
		}

		if (txtDuration.getText().matches("[0-9]+")) {
			if (Integer.parseInt(txtDuration.getText()) <= 0)
				errorMessage += "Duration must be more than 0\n";
		}

		if (AddQuestionToExam.getTotalPoints() < 100) {
			errorMessage += "can not enter letters in duration\n";
		}
		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(mainApp);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMessage);
			alert.showAndWait();
			return false;
		}
	}

	/**
	 * A method that allows the teacher to add question to the exam
	 * 
	 * @param event
	 *            An event occurs when the teacher presses a `add question to exam`
	 *            button
	 * 
	 */
	@FXML
	void AddToTheExamForm(ActionEvent event) {
		if (newQuestion.AddQuestion()) {
			btnAddQuestion.setDisable(false);
			btnAddExam.setDisable(false);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.initOwner(mainApp);
			alert.setTitle("Question added successful");
			alert.setHeaderText("Question added successful");
			alert.setContentText("The question is added");
			alert.showAndWait();
		}

	}

	/**
	 * A method that allows the teacher to remove question from exam
	 * 
	 * @param event
	 *            An event occurs when the teacher presses a `remove question`
	 *            button
	 * 
	 */
	@FXML
	void removeQuestion(ActionEvent event) {
		int i = 0;
		btnAddToExam.setDisable(true);
		btnAddQuestion.setDisable(false);
		ArrayList<String> deleted = new ArrayList<String>();
		for (AddQuestionToExam add : myComponent) {
			if (add.checkRemove.isSelected()) {
				add.removeTheQuestion(add);
				myFlow.getChildren().remove(add);
				deleted.add(add.getID());
			}
			i++;
		}
		i = 0;
		for (String s : deleted) {
			for (AddQuestionToExam add : myComponent) {
				if (add.getID().equals(s)) {
					myComponent.remove(i);
					break;
				}
				i++;
			}
		}
	}

}