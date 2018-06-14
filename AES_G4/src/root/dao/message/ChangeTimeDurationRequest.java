package root.dao.message;

import java.io.Serializable;

import org.w3c.dom.views.AbstractView;

public class ChangeTimeDurationRequest extends AbstractMessage{
	
	private boolean approved = false;
	
	private String messageFromTeacher;
	
	private String examId;
	
	private int newTime;
	
	public int getNewTime() {
		return newTime;
	}

	public void setNewTime(int newTime) {
		this.newTime = newTime;
	}

	public ChangeTimeDurationRequest() {
		super.setMsg("changetimeduration");
	}

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

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "changeTimeDuration";
	}
	
	public void setExamId(String s) {
		examId=s;
	}
	
	public String getExamId() {
		return examId;
	}
	
	public void confirm() {
		super.setMsg("confirmchangeduration");
	}
	
	
	

}
