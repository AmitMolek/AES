package root.dao.message;

/**
 * 
 * @author Alon Ben-yosef
 * Contains an exam's id and date, used for getting statistics from the server
 */
public class ExamStatsByIdDateMessage extends AbstractMessage {

	/**
	 * An exam's ID
	 */
	private String id;
	/**
	 * An exam's date
	 */
	private String date;
	
	/**
	 * Creates an ExamStatsByIdDateMessage
	 * @param msg the message
	 * @param arr first index should contain the id, second should contain the date
	 */
	public ExamStatsByIdDateMessage(String msg,String[] arr) {
		setMsg(msg);
		if(arr.length>1) {
			this.id=arr[0];
			this.date=arr[1];
		}
	}
	
	@Override
	public String getType() {
		return "ExamStatsByIdDate";
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}