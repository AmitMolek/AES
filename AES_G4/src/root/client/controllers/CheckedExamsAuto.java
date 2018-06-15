package root.client.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import ocsf.client.ObservableClient;
import root.client.managers.DataKeepManager;
import root.dao.app.CsvDetails;
import root.dao.app.Exam;
import root.dao.app.User;
import root.dao.message.CsvMessage;
import root.dao.message.MessageFactory;


public class CheckedExamsAuto implements Observer {
	private ArrayList<QuestionInExamObject> questionsInExamObject;
	private Exam exam;
	private ObservableClient client;
	private User student;
	private Map<String, Integer> questionInExam;
	

	private MessageFactory messageFact;
	public CheckedExamsAuto(ArrayList<QuestionInExamObject> questionsInExamObject,Exam exam) {
		super();
		this.questionsInExamObject = questionsInExamObject;
		this.exam = exam;
		client = (ObservableClient)DataKeepManager.getInstance().getObject_NoRemove("client");// get the client from DataKeep, but dont remove it from there, for later use.
    	client.addObserver(this);		
    	student = DataKeepManager.getInstance().getUser();
    	messageFact = MessageFactory.getInstance();
    	questionInExam = new HashMap<String, Integer>();
		
	}
/**
 * 
 * @return the student grade
 */
	public int calculateGrade() {
		int totalGrade = 0;
		for(QuestionInExamObject q: questionsInExamObject ) {
			if(q.getCorrectAns() == q.getSelectedAns())
				totalGrade = totalGrade + q.getQuestionGrade();
			questionInExam.put(q.getQuestionId(), q.getSelectedAns());
		}
		
		return totalGrade;
	}

	/**
	 * Creates new csv
	 */
	public void createCsv() {
		
		CsvDetails csv = new CsvDetails(exam, student, questionInExam);
		CsvMessage newMessage = (CsvMessage) messageFact.getMessage("get-csv", csv);
		try {
			client.sendToServer(newMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Method that occurs when server sends message
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public Map<String, Integer> getQuestionInExam() {
		return questionInExam;
	}

	
	
}
