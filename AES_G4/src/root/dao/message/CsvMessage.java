package root.dao.message;
/**
 * Class for csv Message
 * @author Omer Haimovich
 * 
 *
 */

import java.util.ArrayList;

import root.dao.app.CsvDetails;

public class CsvMessage extends AbstractMessage {

	private CsvDetails csv;
	private ArrayList<String[]> csvDetailofSolvedExam;
	
	/**
	 * Constructor send message from client
	 * @param csv the csv details
	 */
	public CsvMessage(CsvDetails csv) {
		super("get-csv");
		this.csv = csv;
	}
	/**
	 * @author gal
	 * constructor for getting a csv of a solvedExam
	 * @param string
	 * @param payload
	 */
	public CsvMessage(String message, CsvDetails payload) {
		super(message);
		this.csv = payload;
	}
	/**
	 * Constructor for returning csvData from server to client
	 * @param message
	 * @param payload
	 */
	public CsvMessage(String message, ArrayList<String[]> payload) {
		super(message);
		this.csvDetailofSolvedExam = payload;
	}
	/**
	 * 
	 * @return csv details
	 */
	public CsvDetails getCsv() {
		return csv;
	}

	/**
	 * Returns type csv
	 */
	@Override
	public String getType() {
		return "csv";
	}
	/**
	 * @return the csvDetailofSolvedExam
	 */
	public ArrayList<String[]> getCsvDetailofSolvedExam() {
		return csvDetailofSolvedExam;
	}
	/**
	 * @param csvDetailofSolvedExam the csvDetailofSolvedExam to set
	 */
	public void setCsvDetailofSolvedExam(ArrayList<String[]> csvDetailofSolvedExam) {
		this.csvDetailofSolvedExam = csvDetailofSolvedExam;
	}

}
