package root.dao.message;

import java.io.Serializable;
import java.util.ArrayList;

import root.dao.app.Question;
import root.dao.app.Subject;
import root.dao.app.User;

public class UserSubjectMessage  extends AbstractMessage implements Serializable{
	
	/**
	 * @author gal
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Subject> subjects;
	private User user;
	
	public UserSubjectMessage(User payload) {
		// TODO Auto-generated constructor stub
		super("UserSubjects");
		this.user = payload;
	}
	public UserSubjectMessage(UserSubjectMessage payload) {
		// TODO Auto-generated constructor stub
		super("UserSubjects");
		this.user = payload.getUser();
		this.subjects = payload.getSubjects();
	}
	
	public UserSubjectMessage(User user, ArrayList<Subject> subjects) {
		super("UserSubjects");
		this.subjects=subjects;
		this.user = user;
	}

	@Override
	public String getType() {
		return "UserSubject";
	}

	public ArrayList<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(ArrayList<Subject> subjects) {
		this.subjects = subjects;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
}