package root.client.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import root.client.Main;
import root.client.managers.ScreensManager;
import root.dao.app.Question;
import root.dao.app.QuestionInExam;

/**
 * 
 * A class that is responsible for adding a component of a question to a exam
 * form
 * 
 * @author Omer Haimovich
 *
 */
public class AddQuestionToExam extends AnchorPane {

	// FXML variables **********************************************

	@FXML
	private TextArea txtFreeTeacher;

	@FXML
	private GridPane questionGridPane;

	@FXML
	private TextField txtScore;

	@FXML
	private AnchorPane rootPane;

	@FXML
	private ComboBox<String> cmbQuestion;

	@FXML
	private TextArea txtFreeStudent;

	@FXML
	private Label lblQuestionNumber;

	@FXML
	public CheckBox checkRemove;

	// Instance variables **********************************************

	/**
	 * Counter that counts how many component we added to the form exam
	 */
	private static int count = 0;
	/**
	 * The number of points you have until now in the exam
	 */
	private static int totalPoints = 0;
	/**
	 * The id of the component
	 */
	private String id;
	/**
	 * A specific question that belongs to the exam
	 */
	private QuestionInExam examQuestion;
	/**
	 * List of questions of the chosen course and subjects
	 */
	private static ArrayList<Question> questions = new ArrayList<Question>();
	/**
	 * 
	 * List of questions that are currently belong to the exam
	 */
	private static ArrayList<QuestionInExam> examQuestions = new ArrayList<QuestionInExam>();
	/**
	 * 
	 * List of data found in ComboBox
	 */
	private static ArrayList<String> combobox = new ArrayList<String>();
	/**
	 * A specific question
	 */
	private Question newQuestion;
	/**
	 * The main window of the application
	 */
	private Stage mainApp;
	/**
	 * The manager that responsible for switching between windows in the system
	 */
	private ScreensManager screenManager;

	// CONSTRUCTORS *****************************************************

	/**
	 * Constructs the AddQuestionToExam
	 */
	public AddQuestionToExam() {
		FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("resources/view/AddQuestionToAnExam.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		id = Integer.toString(count);
		count++;
		examQuestion = new QuestionInExam();
		try {
			fxmlLoader.load();
			lblQuestionNumber.setText("Question number: " + count);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// CLASS METHODS *************************************************

	/**
	 * A method that returns the component id
	 * 
	 * @return the component id
	 */
	public String getID() {
		return id;
	}

	/**
	 * A method that returns the number of components that added to the add exam
	 * form
	 * 
	 * @return number of components
	 */
	public static int getCount() {
		return count;
	}

	/**
	 * 
	 * A method that allows the teacher to select a question from the pool of
	 * questions
	 * 
	 * @param event
	 * 
	 *            An event that happens when a teacher select from question combo
	 *            box
	 */
	@FXML
	void SelectQuestion(ActionEvent event) {
		int i = 0;
		String value = cmbQuestion.getValue();
		String[] questionId = value.split("-");
		newQuestion = null;
		for (Question q : questions) {

			if (q.getQuestionId().equals(questionId[0])) {
				newQuestion = questions.get(i);
				break;
			}
			i++;
		}

		for (QuestionInExam exs : examQuestions) {
			if (exs.getQuestion().getQuestionId().equals(newQuestion.getQuestionId())) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.initOwner(mainApp);
				alert.setTitle("Invalid Fields");
				alert.setHeaderText("Please correct invalid fields");
				alert.setContentText("You select this question already!");
				alert.showAndWait();
			}
		}
	}

	/**
	 * 
	 * A method that adds a question to the list of exam questions
	 * 
	 * @return true if it is added successful or false otherwise
	 */
	public boolean AddQuestion() {

		if (isInputValidQuestion()) {
			String scoringPoints = txtScore.getText();
			int points = Integer.parseInt(scoringPoints);
			totalPoints = totalPoints + points;
			if (totalPoints > 100) {
				Alert alert = new Alert(AlertType.ERROR);
				totalPoints = totalPoints - points;
				alert.initOwner(mainApp);
				alert.setTitle("Invalid Fields");
				alert.setHeaderText("Please correct invalid fields");
				alert.setContentText("More than 100 points change the points of the last question!");
				alert.showAndWait();
				return false;
			}
			String freeTextTeacher = txtFreeTeacher.getText();
			String freeTextStudent = txtFreeStudent.getText();
			examQuestion = new QuestionInExam(newQuestion, points, freeTextTeacher, freeTextStudent);
			examQuestions.add(examQuestion);
			return true;
		} else
			return false;

	}

	/**
	 * Clears the list of questions belonging to the exam
	 */
	public static void clearQuestionInExam() {
		int i = 0;
		int size;
		size = questions.size();
		while (i < size) {
			questions.remove(0);
			i++;
		}
		i = 0;
		size = examQuestions.size();
		while (i < size) {
			examQuestions.remove(0);
			i++;
		}

		count = 0;
		totalPoints = 0;
	}

	/**
	 * A method that returns the question that belong to the exam
	 * 
	 * @return the list of the question in exam
	 */
	public static ArrayList<QuestionInExam> getExamQuestions() {
		return examQuestions;
	}

	/**
	 * 
	 * Fills the Combo Box with questions belong to the course and the subject
	 * chosen
	 * 
	 * @param question
	 *            the list of the questions
	 */
	public void setQuestionCombo(ArrayList<Question> question) {
		for (Question q : question) {
			cmbQuestion.getItems().add(q.getQuestionId() + "-" + q.getQuestionText());
		}
		this.questions = question;
	}

	/**
	 * A method that returns the question of the course and the subject chosen
	 * 
	 * @return the list of the questions
	 */
	public static ArrayList<Question> getQuestions() {
		return questions;
	}

	/**
	 * Clears all the information in ComboBox
	 */
	public void clearComboBox() {
		int i = 0;
		int size;
		if (cmbQuestion.getItems().size() != 0)
			cmbQuestion.getItems().removeAll(combobox);
		size = combobox.size();
		while (i < size) {
			combobox.remove(0);
			i++;
		}
	}

	/**
	 * A method that set new number of components
	 * 
	 * @param count
	 *            the number of components
	 */
	public static void setCount(int count) {
		AddQuestionToExam.count = count;
	}

	/**
	 * Checks whether all fields are valid
	 * 
	 * @return true if all inputs valid and false if not
	 */
	private boolean isInputValidQuestion() {
		String errorMessage = "";

		if (cmbQuestion.getSelectionModel().getSelectedItem() == null) {// || firstNameField.getText().length() == 0) {
			errorMessage += "Please Select a Question!\n";
		}

		if (txtScore.getText() == null || txtScore.getText().length() == 0) {
			errorMessage += "No valid Question points\n";
		}
		if (txtScore.getText().length() > 0) {
			if (txtScore.getText().matches("[0-9]+")) {
				if (Integer.parseInt(txtScore.getText()) == 0) {
					errorMessage += "No valid Question points\n";
				}
			}
			if ((!(txtScore.getText().matches("[0-9]+")))) {
				errorMessage += "No valid Question points\n";
			}
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
	 * 
	 * 
	 * The method initializes the window when it comes up
	 * 
	 * @throws IOException
	 *             if the window cannot be shown
	 */
	@FXML
	public void initialize() throws IOException {
		screenManager = ScreensManager.getInstance();
		mainApp = screenManager.getPrimaryStage();
	}

	/**
	 * A method that returns the number of points you have until now in the exam
	 * 
	 * @return the number of points you have until now in the exam
	 */
	public static int getTotalPoints() {
		return totalPoints;
	}

	/**
	 * A method that is responsible for changing the text field of a number of
	 * points to a question
	 * 
	 * @param event
	 *            An event that happens when a teacher change points in the text
	 *            field of a number of points to a question
	 */
	@FXML
	void changePoints(ActionEvent event) {

		if (txtScore.getText() == null || txtScore.getText().length() == 0
				|| (!(txtScore.getText().matches("[0-9]+")))) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(mainApp);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText("No valid Question points\n");
			alert.showAndWait();
			return;
		}
		String scoringPoints = txtScore.getText();
		int points = Integer.parseInt(scoringPoints);
		totalPoints = totalPoints - examQuestion.getQuestionGrade();
		totalPoints = totalPoints + points;
		if (totalPoints > 100) {
			Alert alert = new Alert(AlertType.ERROR);
			totalPoints = totalPoints - points;
			alert.initOwner(mainApp);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText("More than 100 points change the points of the last question!");
			alert.showAndWait();
			return;
		}
		examQuestion.setQuestionGrade(points);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(mainApp);
		alert.setTitle("The points is changed");
		alert.setHeaderText("The point changed");
		alert.setContentText("The point changed! do not press Add  the question to the exam button\n");
		alert.showAndWait();
	}

	/**
	 * A method that returns the list of questions that are currently belong to the
	 * exam
	 * 
	 * @return the list of questions that are currently belong to the exam
	 */
	public QuestionInExam getExamQuestion() {
		return examQuestion;
	}

	/**
	 * Remove question from the exam
	 * 
	 * @param add
	 *            the add question to exam component
	 */
	public void removeTheQuestion(AddQuestionToExam add) {
		int i = 0;
		if (cmbQuestion.getSelectionModel().getSelectedItem() != null) {

			if (add.getExamQuestion().getQuestionGrade() >= 0 || add.getExamQuestion().getQuestionGrade() <= 100)
				totalPoints = totalPoints - add.getExamQuestion().getQuestionGrade();
			for (QuestionInExam q : examQuestions) {
				if (add.getExamQuestion().getQuestion().getQuestionId().equals(q.getQuestion().getQuestionId())) {
					examQuestions.remove(q);
					break;
				}
			}
		}
	}

}