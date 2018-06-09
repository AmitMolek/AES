package root.client.controllers;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import ocsf.client.ObservableClient;
import root.client.managers.DataKeepManager;
import root.client.managers.ScreensManager;
import root.dao.app.Exam;
import root.dao.message.ErrorMessage;
import root.dao.message.ExamMessage;
import root.dao.message.MessageFactory;
import root.dao.message.SimpleMessage;
import root.util.log.Log;
import root.util.log.LogLine;

public class Enter4digitPasswordController implements Observer {
    @FXML
    private TextField txt4Digit;
    
    private Log log = Log.getInstance();

    private ObservableClient client;
    
    private ScreensManager scrMgr;
    
    private DataKeepManager dataKeeper;
    
    
	public void initialize() throws IOException{
		scrMgr =ScreensManager.getInstance();
		dataKeeper = DataKeepManager.getInstance();
    	client = new ObservableClient("localhost",8000);
    	client.addObserver(this);
    	client.openConnection();

	}

    public void StartExam(ActionEvent e) {
    		MessageFactory msgFactory = MessageFactory.getInstance();
    		String txt = txt4Digit.getText();
    		txt="'"+txt+"'";
    		SimpleMessage getExamMessage=(SimpleMessage) msgFactory.getMessage("get-simple",new Object());
    		getExamMessage.setMessage("get-exams-pass-"+txt);
    		try {
				client.sendToServer(getExamMessage);
			} catch (IOException e1) {
				log.writeToLog(LogLine.LineType.ERROR, e1.getMessage());
				e1.printStackTrace();
			}
    		
    		
    }

	@Override
	public void update(Observable arg0, Object arg1) {
		ExamMessage examMessage=null;
		if(arg1 instanceof ExamMessage)
		{
			examMessage= (ExamMessage)arg1;
			ArrayList<Exam> examsList = examMessage.getExams();
			
			//scrMgr.activate();
		
			dataKeeper.keepObject("RunningExam", examsList.get(0));
				Platform.runLater(() -> {				
						try {
							scrMgr.activate("executefull");
						} catch (IOException e) {
							log.writeToLog(LogLine.LineType.ERROR, "Trying to activate wrong window");
							e.printStackTrace();
						}
				});
		}
		else if(arg1 instanceof ErrorMessage) {
			log.writeToLog(LogLine.LineType.ERROR, ((ErrorMessage) arg1).getErrorException().getMessage());

			//Add Here handle with wrong password
			//Add Here handle with locked exam
			Platform.runLater(() -> {
	            Alert alert = new Alert(AlertType.ERROR);
	            alert.initOwner(scrMgr.getPrimaryStage());
	            alert.setTitle("Wrong 4 digits password");
	            alert.setHeaderText("Please correct invalid fields");
	            alert.setContentText(arg1.toString());
	            alert.showAndWait();       
	        	log.writeToLog(LogLine.LineType.ERROR, "Cant Find running exam");
		});
		}
	}
    
    
}
