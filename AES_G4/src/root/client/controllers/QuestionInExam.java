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

public class QuestionInExam extends AnchorPane{
	

 
	
    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label lblQuestion;
    
    @FXML
    private Button btnAns4;

    @FXML
    private Button btnAns1;

    @FXML
    private Button btnAns3;

    @FXML
    private Button btnAns2;
    
    @FXML
    private GridPane answersGrid;
    
	private static int count=0;
	
	private String id=null;
	
	private int ans =0;
	
	private String pressedButton = "-fx-background-color: green;";
	private String nonPressedButton; 
	
	public void initialize() {
		nonPressedButton = btnAns1.getStyle();
	}
	
	public QuestionInExam() {
		FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("resources/view/QuestionInExamComponent.fxml"));
		fxmlLoader.setRoot(this);
		id = Integer.toString(count);
		count++;
	   fxmlLoader.setController(this);
	   try {
	      fxmlLoader.load();
	   } catch (IOException exception) {
	      throw new RuntimeException(exception);
	      }

	}
	
	public String getID() {
		return id;
	}
	
	public void setQuestion(String question) {
		lblQuestion.setText(question);
	}
	
	   
	    public AnchorPane getRootPane() {
	    	return rootPane;
	    }
	    
	    public void answerPressed(ActionEvent e)
	    {
	    	if(e.getSource().equals(btnAns1))
	    	{
	    		btnAns1.setStyle(pressedButton);
	    		btnAns2.setStyle(nonPressedButton);
	    		btnAns3.setStyle(nonPressedButton);
	    		btnAns4.setStyle(nonPressedButton);
	    		ans=1;

	    	}
	    	if(e.getSource().equals(btnAns2))
	    	{
	    		btnAns1.setStyle(nonPressedButton);
	    		btnAns2.setStyle(pressedButton);
	    		btnAns3.setStyle(nonPressedButton);
	    		btnAns4.setStyle(nonPressedButton);
	    		ans=2;
	    	}
	    		
	    	if(e.getSource().equals(btnAns3))
	    	{
	    		btnAns1.setStyle(nonPressedButton);
	    		btnAns2.setStyle(nonPressedButton);
	    		btnAns3.setStyle(pressedButton);
	    		btnAns4.setStyle(nonPressedButton);
	    		ans=3;
	    	}
	    		
	    	if(e.getSource().equals(btnAns4))
	    	{
	    		btnAns1.setStyle(nonPressedButton);
	    		btnAns2.setStyle(nonPressedButton);
	    		btnAns3.setStyle(nonPressedButton);
	    		btnAns4.setStyle(pressedButton);
	    		ans=4;
	    	}
	    		
	    }
	    
	    public int getSelectedAns() {
	    	return ans;
	    }
}

