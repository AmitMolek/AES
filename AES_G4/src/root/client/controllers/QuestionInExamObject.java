package root.client.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import root.client.Main;
import root.dao.app.Question;
import root.dao.app.QuestionInExam;

/**
 * @author Naor Saadia
 * this is controller and root class
 * for private use control implementation
 * the object have 4 buttons for answeres
 * and lable for questions
 *
 */
public class QuestionInExamObject extends AnchorPane{
	// Instance variables **********************************************

    /**
     * this is the root panes
     */
    @FXML
    private AnchorPane rootPane;
    
    /**
     * this is the label for the question 
     */
    @FXML
    private Label lblQuestion;
    
    /**
     * this is the for number 4 answer
     */
    @FXML
    private Button btnAns4;
    
    /**
     * this is the for number 1 answer
     */
    @FXML
    private Button btnAns1;

    /**
     * this is the for number 3 answer
     */
    @FXML
    private Button btnAns3;

    /**
     * this is the for number 2 answer
     */
    @FXML
    private Button btnAns2;
    
    /**
     * this is grid pane for all 4 answers 
     */
    @FXML
    private GridPane answersGrid;
    
    /**
     * this is the for the points 
     */
    @FXML
    private Label lblPoints;
   	
    /**
     * this for the component id
     */
	private String id=null;
	
    /**
     * this is the for the selected answer
     */
	private int selectedAns =0;
	
	 /**
     * this for the question id
     */
	private String questionId;
	
	 /**
     * the css for change the 
     * button color after he pressed
     */
	private String pressedButton = "-fx-background-color: green;";
	
	 /**
     * this for the change 
     * back the button if the answer is changed
     */
	private String nonPressedButton; 
	
	 /**
	  * this for the correct answer
     */
	private int correctAns;
	
	 /**
     * this for the point of the question
     */
	private int questionGrade;
	
	 /**
     * the initalize method called when the component displayed
     * in the menthod we save the default button style
     */
	public void initialize() {
		nonPressedButton = btnAns1.getStyle();
	}
	
	 /**
     * the constructor
     * set the question in the label and the answers in the buttons
     */
	public QuestionInExamObject(Question q, int questionGrade) {
		
		questionId = q.getQuestionId();
		correctAns = q.getCorrectAns();
		this.questionGrade = questionGrade;
		FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("resources/view/QuestionInExamComponent.fxml"));
		fxmlLoader.setRoot(this);
		String questionText = q.getQuestionText();
		
	   fxmlLoader.setController(this);
	   try {
	      fxmlLoader.load();
	   } catch (IOException exception) {
	      throw new RuntimeException(exception);
	      }
		lblQuestion.setText(questionText);
		btnAns1.setText(q.getAns1());
		btnAns2.setText(q.getAns2());
		btnAns3.setText(q.getAns3());
		btnAns4.setText(q.getAns4());
	}
	
	 /**
     * get the question id
     */
	public String getQuestionId() {
		return questionId;
	}

	 /**
     * the method return the correct answer
     */
	public int getCorrectAns() {
		return correctAns;
	}

	 /**
     * this method return the question grade
     */
	public int getQuestionGrade() {
		return questionGrade;
	}

	 /**
     * this method return the object id 
     */
	public String getID() {
		return id;
	}
	
	
	 /**
     * this method return the root pane
     */
	public AnchorPane getRootPane() {
	    return rootPane;
	}
	
	 /**
     * this method when action happens
     * when the user press on answer
     * the method change the object answer 
     * and change the button color
     */
	public void answerPressed(ActionEvent e)
	{
		if(e.getSource().equals(btnAns1))
	    {
	    	btnAns1.setStyle(pressedButton);
	    	btnAns2.setStyle(nonPressedButton);
	    	btnAns3.setStyle(nonPressedButton);
	    	btnAns4.setStyle(nonPressedButton);
	    	selectedAns=1;
	    }	
	    if(e.getSource().equals(btnAns2))
	    {
	    	btnAns1.setStyle(nonPressedButton);
	    	btnAns2.setStyle(pressedButton);
	    	btnAns3.setStyle(nonPressedButton);
	    	btnAns4.setStyle(nonPressedButton);
	    	selectedAns=2;
	    }
	    		
	    if(e.getSource().equals(btnAns3))
	    {
	    	btnAns1.setStyle(nonPressedButton);
	    	btnAns2.setStyle(nonPressedButton);
	    	btnAns3.setStyle(pressedButton);
	    	btnAns4.setStyle(nonPressedButton);
	    	selectedAns=3;
	    }
	    		
	    if(e.getSource().equals(btnAns4))
	    {
	    	btnAns1.setStyle(nonPressedButton);
	    	btnAns2.setStyle(nonPressedButton);
	    	btnAns3.setStyle(nonPressedButton);
	    	btnAns4.setStyle(pressedButton);
	    	selectedAns=4;
	    }    		
	}
	
	/**
    * this method return the selected answer
    */
	public int getSelectedAns() {
	    return selectedAns;
	}
}

