package root.client.controllers;

import java.util.ArrayList;



public class CheckedExamsAuto {
	private ArrayList<QuestionInExamObject> questionsInExamObject;
	public CheckedExamsAuto(ArrayList<QuestionInExamObject> questionsInExamObject) {
		super();
		this.questionsInExamObject = questionsInExamObject;
		
	}

	public int calculateGrade() {
		int totalGrade = 0;
		for(QuestionInExamObject q: questionsInExamObject ) {
			if(q.getCorrectAns() == q.getSelectedAns())
				totalGrade = totalGrade + q.getQuestionGrade();
		}
		
		return totalGrade;
	}

	
	
}
