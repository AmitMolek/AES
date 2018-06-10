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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import root.client.Main;
import root.client.managers.ScreensManager;
import root.dao.app.Question;
import root.dao.app.QuestionInExam;

/**
 * Class for add question custom component
 * 
 * @author Omer Haimovich
 *
 */
public class AddQuestionToExam extends AnchorPane {
	private static int count = 0;

	private static int totalPoints = 0;

	private String id;

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

	private static ArrayList<Question> questions = new ArrayList<Question>();

	private static ArrayList<QuestionInExam> examQuestions = new ArrayList<QuestionInExam>();

	private Question newQuestion;
	private Stage mainApp;
	private ScreensManager screenManager;

	/**
	 * Constructor for add question custom component
	 */
	public AddQuestionToExam() {
		FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("resources/view/AddQuestionToAnExam.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		id = Integer.toString(count);
		count++;

		try {
			fxmlLoader.load();
			lblQuestionNumber.setText("Question number: " + count);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return component id
	 */
	public String getID() {
		return id;
	}

	/**
	 * 
	 * @return number of components
	 */
	public static int getCount() {
		return count;
	}

	/**
	 * Method that occurs when teacher select question
	 * 
	 * @param event
	 *            when teacher select from question combo box
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
	 * Add question to an exam
	 * 
	 * @return list of questions in exam
	 */
	public ArrayList<QuestionInExam> AddQuestion() {

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
			}
			String freeTextTeacher = txtFreeTeacher.getText();
			String freeTextStudent = txtFreeStudent.getText();
			QuestionInExam newQuestionExam = new QuestionInExam(newQuestion, points, freeTextTeacher, freeTextStudent);
			examQuestions.add(newQuestionExam);
			return examQuestions;
		}
		return null;
	}

	/**
	 * Clears the question exam list
	 */
	public static void clearQuestionInExam() {
		examQuestions.clear();
	}

	/**
	 * 
	 * @return the question in exam list
	 */
	public static ArrayList<QuestionInExam> getExamQuestions() {
		return examQuestions;
	}

	/**
	 * Sets the question in the combo box
	 * 
	 * @param question
	 *            the question list
	 */
	public void setQuestionCombo(ArrayList<Question> question) {
		for (Question q : question) {
			cmbQuestion.getItems().add(q.getQuestionId() + "-" + q.getQuestionText());
		}
		this.questions = question;
	}

	/**
	 * 
	 * @return the question list
	 */
	public static ArrayList<Question> getQuestions() {
		return questions;
	}

	/**
	 * Sets new value to the count
	 * 
	 * @param count
	 *            the new count value
	 */
	public static void setCount(int count) {
		AddQuestionToExam.count = count;
	}

	/**
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
	 * This method occurs when the window is shown up.
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
	 * 
	 * @return the total points of the exam
	 */
	public static int getTotalPoints() {
		return totalPoints;
	}

}