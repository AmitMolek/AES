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

/**
 * 
 * A class that is responsible for automatically checking a auto exam and
 * preparing a CSV file for this exam and adding it to the appropriate
 * place(database and the CSV in the appropriate folder)
 * 
 * @author Omer Haimovich
 *
 */
public class CheckedExamsAuto implements Observer {

	// Instance variables **********************************************

	/**
	 * 
	 * A list of all the test questions and all the answers that the student has
	 * marked them
	 */
	private ArrayList<QuestionInExamObject> questionsInExamObject;
	/**
	 * The auto exam that will be checked
	 */
	private Exam exam;
	/**
	 * 
	 * Keeps our client in order to communicate with the server
	 */
	private ObservableClient client;
	/**
	 * The student who solved this exam
	 */
	private User student;
	/**
	 * Map<String, Integer> that holds the question id and the selected answer
	 */
	private Map<String, Integer> questionInExam;

	/**
	 * Generates new communications between server and client
	 */
	private MessageFactory messageFact;

	// CONSTRUCTORS *****************************************************

	/**
	 * Constructs the CheckedExamsAuto
	 * 
	 * @param questionsInExamObject
	 *            the list of all the test questions and all the answers that the
	 *            student has marked them
	 * @param exam
	 *            the auto exam that will be checked
	 */
	public CheckedExamsAuto(ArrayList<QuestionInExamObject> questionsInExamObject, Exam exam) {
		super();
		this.questionsInExamObject = questionsInExamObject;
		this.exam = exam;
		client = (ObservableClient) DataKeepManager.getInstance().getObject_NoRemove("client");
		client.addObserver(this);
		student = DataKeepManager.getInstance().getUser();
		messageFact = MessageFactory.getInstance();
		questionInExam = new HashMap<String, Integer>();

	}

	// CLASS METHODS *************************************************

	/**
	 * 
	 * A method that checks the exam and calculates the grade and returns it
	 * 
	 * @return the student grade
	 */
	public int calculateGrade() {
		int totalGrade = 0;
		for (QuestionInExamObject q : questionsInExamObject) {
			if (q.getCorrectAns() == q.getSelectedAns())
				totalGrade = totalGrade + q.getQuestionGrade();
			questionInExam.put(q.getQuestionId(), q.getSelectedAns());
		}

		return totalGrade;
	}

	/**
	 * 
	 * 
	 * A method that generates a new CSV file containing the exam data
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
	 *
	 * A method that is responsible for handling messages sent from the server
	 */
	@Override
	public void update(Observable arg0, Object arg1) {

	}

	/**
	 * A method that returns the map<String, Integer> that holds the question id and
	 * the selected answer
	 * 
	 * @return the map<String, Integer> that holds the question id and the selected
	 *         answer
	 */
	public Map<String, Integer> getQuestionInExam() {
		return questionInExam;
	}

}