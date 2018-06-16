package root.dao.message;

import java.util.ArrayList;

import root.dao.app.ExamTableDataLine;

public class ExamDataLinesMessage extends AbstractMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ArrayList<ExamTableDataLine> lines;
	
	public ExamDataLinesMessage(ArrayList<ExamTableDataLine> payload) {
		this.lines=payload;
	}


	public ArrayList<ExamTableDataLine> getLines() {
		return lines;
	}



	public void setLines(ArrayList<ExamTableDataLine> lines) {
		this.lines = lines;
	}



	@Override
	public String getType() {
		return "ExamDataLines";
	}

}
