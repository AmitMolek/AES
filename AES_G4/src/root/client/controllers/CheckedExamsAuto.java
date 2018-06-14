package root.client.controllers;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import ocsf.client.ObservableClient;
import root.client.managers.DataKeepManager;
import root.dao.app.Exam;
import root.dao.app.User;



public class CheckedExamsAuto implements Observer {
	private ArrayList<QuestionInExamObject> questionsInExamObject;
	private Exam exam;
	private ObservableClient client;
	private User student;
	public CheckedExamsAuto(ArrayList<QuestionInExamObject> questionsInExamObject,Exam exam) {
		super();
		this.questionsInExamObject = questionsInExamObject;
		this.exam = exam;
		client = (ObservableClient)DataKeepManager.getInstance().getObject_NoRemove("client");// get the client from DataKeep, but dont remove it from there, for later use.
    	client.addObserver(this);		
    	student = DataKeepManager.getInstance().getUser();
		
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
		}
		
		return totalGrade;
	}

	/**
	 * Creates new csv
	 */
	public void createCsv() {
		
	}
	/**
	 * Method that occurs when server sends message
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

	
	
}
