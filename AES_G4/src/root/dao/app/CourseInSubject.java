package root.dao.app;

import java.io.Serializable;

public class CourseInSubject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String subject_id;
	private String course_id;

	public CourseInSubject(String subject_id, String course_id) {
		super();
		this.subject_id = subject_id;
		this.course_id = course_id;
	}

	public String getSubject_id() {
		return subject_id;
	}

	public void setSubject_id(String subject_id) {
		this.subject_id = subject_id;
	}

	public String getCourse_id() {
		return course_id;
	}

	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}
}
