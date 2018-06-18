package root.dao.message;

public class ExamStatsByIdDateMessage extends AbstractMessage {

	private String id;
	private String date;
	
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