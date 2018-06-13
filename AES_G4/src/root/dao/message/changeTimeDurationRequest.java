package root.dao.message;

import java.io.Serializable;

public class changeTimeDurationRequest implements Serializable{
	
	private boolean approved = false;
	
	private String messageFromTeacher;

	public boolean hasApproved() {
		return approved;
	}

	public void setApproved(boolean hasApproved) {
		this.approved = hasApproved;
	}

	public String getMessageFromTeacher() {
		return messageFromTeacher;
	}

	public void setMessageFromTeacher(String messageFromTeacher) {
		this.messageFromTeacher = messageFromTeacher;
	}
	
	
	

}
