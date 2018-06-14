package root.client.controllers;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import root.client.managers.DataKeepManager;
import root.client.managers.ScreensManager;
import root.dao.app.Exam;
import root.dao.app.QuestionInExam;

/**
 * @author Naor Saadia
 * This controller implements execute exam screen
 *  the user get list of questions for specific exam and answer the quesitons
 */
public class ExecuteExamController {
	
		@FXML 
		private Button btnBack;
	
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

	    @FXML
	    private Label lblInstruction;
	    
	    @FXML
	    private TextArea txtNotes;
	    
	    @FXML
	    private Label lblDate;
	    
	    @FXML
	    private AnchorPane buttomPane;

	    private ArrayList<QuestionInExamObject> questionsInExamObject = new ArrayList<QuestionInExamObject>();
	    
	    private int displayQuestion;

	    private int stopWatch;
	    	    
	    private DataKeepManager dataKeeper =DataKeepManager.getInstance();;
	    
	    private ArrayList<Button> tabsButton = new ArrayList<Button>();
	    
	    private ArrayList<String> intructText = new ArrayList<String>();
	   
	    Timeline examStopWatch;
	    
	    ScreensManager scrMgr = ScreensManager.getInstance();
	    
	    
	    /**
	     *  this method happens when the window shown
	     *  
	     */
	    
	   public void initialize() {
		
		   txtNotes.setEditable(false);
		   btnBack.setDisable(true);
		   String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		   lblDate.setText(date);
		   displayQuestion =0;
		   Exam exam = (Exam) dataKeeper.getObject("RunningExam");
		   ArrayList<QuestionInExam> questionsInExam = exam.getExamQuestions();
		   int i=0;
		   for(QuestionInExam q:questionsInExam) {
			   i++;
			   intructText.add(q.getQuestion().getIdquestionIntruction());
			   Button tab = new Button("question "+i);
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
		   
		   txtNotes.setText(intructText.get(displayQuestion));
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
		   Platform.runLater(()->{
			   TextInputDialog dialog = new TextInputDialog("walter");
			   dialog.setTitle("Text Input Dialog");
			   dialog.setHeaderText("Look, a Text Input Dialog");
			   dialog.setContentText("Please enter your name:");
			   dialog.showAndWait();
			   }
					   );
		   }
	   
	    /**
	     * This method happens when the user click on next button
	     * the method check if the next should disabled
	     * the method allways make back button to enable
	     */
	    @FXML
	    public void nextQuestion(ActionEvent event) {
	    	displayQuestion++;
			txtNotes.setText(intructText.get(displayQuestion));
	    	if(displayQuestion==tabsButton.size()-1)
	    		btnNext.setDisable(true);
	    	btnBack.setDisable(false);
	    	myBorder.setCenter(questionsInExamObject.get(displayQuestion));
	    }
	    
	    /**
	     * This method happens when the user click on back button
	     * the method check if the back should disabled
	     * the method always make next button to enable
	     */
	    public void prevQuestion(ActionEvent event) {
	    	displayQuestion--;
	    	if(displayQuestion==0)
	    		btnBack.setDisable(true);
			txtNotes.setText(intructText.get(displayQuestion));
	    	if(!(displayQuestion==tabsButton.size()-1))
	    		btnNext.setDisable(false);
	    	myBorder.setCenter(questionsInExamObject.get(displayQuestion));
	    }
	    
	    /**
	     * This method stop the exams
	     */
	    public void stopExam()
	    {
	    	examStopWatch.stop();
	    	try {
				ScreensManager.getInstance().activate("home");
			} catch (IOException e) {
				e.printStackTrace();
			}
	    	
	    }
	    
	    /**
	     * This method implements the changing question
	     */	    
	    public void changeTab(ActionEvent e) {
	    	Button tabButton = (Button)e.getSource();
	    	displayQuestion = tabsButton.indexOf(tabButton);
			txtNotes.setText(intructText.get(displayQuestion));
	    	myBorder.setCenter(questionsInExamObject.get(displayQuestion));
	    	if(displayQuestion==tabsButton.size()-1)
	    		btnNext.setDisable(true);
	    	else
	    		btnNext.setDisable(false);
	    	if(displayQuestion==0)
	    		btnBack.setDisable(true);
	    	else
	    		btnBack.setDisable(false);
	    }
	    
	    public void submitPress(ActionEvent e) {
				Platform.runLater(() -> {
		            Alert alert = new Alert(AlertType.CONFIRMATION);
		            alert.initOwner(scrMgr.getPrimaryStage());
		            alert.setTitle("Submit message");
		            alert.setHeaderText("");
		            alert.setContentText("are you soure you want to submit?");
		            Optional<ButtonType> result = alert.showAndWait();
		            if(result.get()==ButtonType.OK) {
		            	submitTest();
		            }
		            	
				});
				
				
	    		
	    		
	  
	    }
	    
	    public void submitTest() {
	    	stopExam();
	    }
}
