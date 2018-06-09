package root.client.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import root.client.managers.DataKeepManager;
import root.client.managers.ScreensManager;
import root.dao.app.Exam;
import root.dao.app.QuestionInExam;

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

	    private ArrayList<QuestionInExamObject> questionsInExamObject = new ArrayList<QuestionInExamObject>();
	    
	    private int displayQuestion;

	    private int stopWatch;
	    	    
	    private DataKeepManager dataKeeper =DataKeepManager.getInstance();;
	    
	    private ArrayList<Button> tabsButton = new ArrayList<Button>();
	        
	    Timeline examStopWatch;
	    
	   public void initialize() {		   
		   displayQuestion =0;
		   Exam exam = (Exam) dataKeeper.getObject("RunningExam");
		   ArrayList<QuestionInExam> QuestionsInExam = exam.getExamQuestions();
		   for(QuestionInExam q:QuestionsInExam) {
			   Button tab = new Button(q.getQuestion().getIdquestionIntruction());
			   tab.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					changeTab(arg0);					
				}
				   
			});
			   tabsButton.add(tab);
			   vbxQuetionsTab.getChildren().add(tab);
			   QuestionInExamObject qie = new QuestionInExamObject(q.getQuestion());
			   questionsInExamObject.add(qie);
		   }		   
		   stopWatch=exam.getExamDuration()*60;
		   examStopWatch = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
			   
			    @Override
			    public void handle(ActionEvent event) {
			    	int hours = (stopWatch/60)/60;
			    	int minuts = (stopWatch/60)%60;
			    	int seconds = stopWatch%60; 
			     lblTimer.setText(""+hours+":"+minuts+":"+seconds);
			     if(stopWatch==0)
			    	 stopExam();
			     stopWatch--;
			    }
			}));
		   examStopWatch.setCycleCount(Timeline.INDEFINITE);
		   examStopWatch.play();
		   myBorder.setCenter(questionsInExamObject.get(0));
	   }

	    @FXML
	    public void nextQuestion(ActionEvent event) {
	    	displayQuestion++;
	    	if(displayQuestion==tabsButton.size()-1)
	    		btnNext.setDisable(true);
	    	myBorder.setCenter(questionsInExamObject.get(displayQuestion));
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
	    	displayQuestion = tabsButton.indexOf(tabButton);
	    	myBorder.setCenter(questionsInExamObject.get(displayQuestion));
	    		
	    }


}
