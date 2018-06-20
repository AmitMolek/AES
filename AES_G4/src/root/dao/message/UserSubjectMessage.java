package root.dao.message;

import java.io.Serializable;
import java.util.ArrayList;

import root.dao.app.Question;
import root.dao.app.Subject;
import root.dao.app.User;


/**
 * @author gal
 * this dao message class used when need to get all teaching subjects of a specific user (Teacher)
 */
public class UserSubjectMessage  extends AbstractMessage implements Serializable{

	private static final long serialVersionUID = 1L;
	private ArrayList<Subject> subjects;
	private User user;
	
	public UserSubjectMessage(User payload) {
		super("UserSubjects");
		this.user = payload;
	}
	public UserSubjectMessage(UserSubjectMessage payload) {
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