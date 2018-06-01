package root.dao.message;

import java.util.ArrayList;

import root.dao.app.Question;

public class QuestionsMessage extends AbstractMessage {
	
	private ArrayList<Question> questions;
	
	public QuestionsMessage(ArrayList<Question> questions) {
		super("ok-questions");
		this.questions=questions;
	}

	@Override
	public String getType() {
		return "Questions";
	}

}
