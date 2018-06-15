package root.dao.message;
/**
 * Class for csv Message
 * @author Omer Haimovich
 * 
 *
 */

import root.dao.app.CsvDetails;

public class CsvMessage extends AbstractMessage {

	private CsvDetails csv;
	
	/**
	 * Constructor send message from client
	 * @param csv the csv details
	 */
	public CsvMessage(CsvDetails csv) {
		super("get-csv");
		this.csv = csv;
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

}
