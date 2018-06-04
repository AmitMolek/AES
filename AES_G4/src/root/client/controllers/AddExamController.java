package root.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class AddExamController {

    @FXML
    private TextArea txtFreeTeacher;

    @FXML
    private ComboBox<String> cmbCourse;

    @FXML
    private ComboBox<String> cmbSubject;

    @FXML
    private TextField txtScore;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Button btnAddExam;

    @FXML
    private ComboBox<String> cmbQuestion;

    @FXML
    private TextArea txtFreeStudent;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtTeacher;

    @FXML
    private Button btnAddQuestion;

    @FXML
    void SelectSubject(ActionEvent event) {

    }

    @FXML
    void SelectCOurse(ActionEvent event) {

    }

    @FXML
    void SelectQuestion(ActionEvent event) {

    }

    @FXML
    void AddQuestionToExam(ActionEvent event) {

    }

    @FXML
    void AddExam(ActionEvent event) {

    }

}