package root.client.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import root.client.managers.ScreensManager;

public class ExecuteExamController {
	
	
	   @FXML
	    private VBox vbxQuetionsTab;
	
	   @FXML
	    private BorderPane myBorder;   

	    @FXML
	    private AnchorPane rootExecute;
	    
	    @FXML
	    private AnchorPane answersPane;
	    
	    @FXML
	    private Button btnNext;

	    @FXML
	    private Label lblTimer;

	    private ArrayList<QuestionInExam> questionsInExam = new ArrayList<QuestionInExam>();
	    
	    private int displayQuestion;

	    private int stopWatch;
	    
	    Timeline examStopWatch;
	   public void initialize() {
		   for(int i=0;i<6;i++) {
			   Button tab = new Button(""+i);
			   tab.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					changeTab(arg0);					
				}
				   
			});
			   vbxQuetionsTab.getChildren().add(tab);
			   QuestionInExam qie = new QuestionInExam();
			   questionsInExam.add(qie);
			   questionsInExam.get(i).setQuestion(""+i);
		   }
		   stopWatch=100;
		   examStopWatch = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
			   
			    @Override
			    public void handle(ActionEvent event) {
			     lblTimer.setText(""+stopWatch);
			     if(stopWatch==0)
			    	 stopExam();
			     stopWatch--;
			    }
			}));
		   examStopWatch.setCycleCount(Timeline.INDEFINITE);
		   examStopWatch.play();
		   myBorder.setCenter(questionsInExam.get(0));
	   }

	    @FXML
	    public void nextQuestion(ActionEvent event) {
	    	displayQuestion++;
	    	if(displayQuestion==6)
	    	{
	    		for(int i=0;i<=5;i++) {
	    			QuestionInExam t=questionsInExam.get(i);
	    			System.out.println(""+t.getSelectedAns());
	    		}
	    	}
	    	myBorder.setCenter(questionsInExam.get(displayQuestion));
	    }
	    
	    public void stopExam()
	    {
	    	examStopWatch.stop();
	    	try {
				ScreensManager.getInstance().activate("home");
			} catch (IOException e) {
				e.printStackTrace();
			}
	    	
	    }
	    
	    public void changeTab(ActionEvent e) {
	    	Button tabButton = (Button)e.getSource();
	    	int index;
	    	index=Integer.parseInt(tabButton.getText());
	    	displayQuestion = index;
	    	myBorder.setCenter(questionsInExam.get(index));
	    		
	    }


}
