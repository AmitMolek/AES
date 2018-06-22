package root.dao.message;

import java.util.ArrayList;

import root.dao.app.ExamTableDataLine;
/**
 * 
 * @author Alon Ben-yosef
 * Contains A ExamDataLines ArrayList
 */
public class ExamDataLinesMessage extends AbstractMessage {

	private static final long serialVersionUID = 1L;

	/**
	 * An ArrayList of ExamTableDataLines, used by TeacherGradesController
	 */
	private ArrayList<ExamTableDataLine> lines;
	
	/**
	 * Fills the message with data
	 * @param payload
	 */
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
