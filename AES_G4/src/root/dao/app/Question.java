package root.dao.app;

public class Question implements java.io.Serializable {

	/**
	 * Class that responsible for saving question details
	 */
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String questionId;
	private String questionText;
	private String idquestionIntruction;
	private String ans1;
	private String ans2;
	private String ans3;
	private String ans4;
	private int correctAns;
	private String teacherAssembeld;

	/**
	 * Constructor of question class
	 */
	public Question(String questionId, String questionText, String idquestionIntruction, String ans1, String ans2,
			String ans3, String ans4, int correctAns, String teacherAssembeld) {
		super();
		this.questionId = questionId;
		this.questionText = questionText;
		this.idquestionIntruction = idquestionIntruction;
		this.ans1 = ans1;
		this.ans2 = ans2;
		this.ans3 = ans3;
		this.ans4 = ans4;
		this.correctAns = correctAns;
		this.teacherAssembeld = teacherAssembeld;
	}
/**
 * copy constractor
 * @param question
 */

	public Question(Question question) {
		// TODO Auto-generated constructor stub
		super();
		this.questionId = question.getQuestionId();
		this.questionText = question.getQuestionText();
		this.idquestionIntruction = question.getIdquestionIntruction();
		this.ans1 = question.getAns1();
		this.ans2 = question.getAns2();
		this.ans3 = question.getAns3();
		this.ans4 = question.getAns4();
		this.correctAns = question.getCorrectAns();
		this.teacherAssembeld = question.getTeacherAssembeld();
	}

	/**
	 * @return the questionId
	 */
	public String getQuestionId() {
		return questionId;
	}

	/**
	 * @param questionId the questionId to set
	 */
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	/**
	 * @return the questionText
	 */
	public String getQuestionText() {
		return questionText;
	}

	/**
	 * @param questionText the questionText to set
	 */
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	/**
	 * @return the idquestionIntruction
	 */
	public String getIdquestionIntruction() {
		return idquestionIntruction;
	}

	/**
	 * @param idquestionIntruction the idquestionIntruction to set
	 */
	public void setIdquestionIntruction(String idquestionIntruction) {
		this.idquestionIntruction = idquestionIntruction;
	}

	/**
	 * @return the ans1
	 */
	public String getAns1() {
		return ans1;
	}

	/**
	 * @param ans1 the ans1 to set
	 */
	public void setAns1(String ans1) {
		this.ans1 = ans1;
	}

	/**
	 * @return the ans2
	 */
	public String getAns2() {
		return ans2;
	}

	/**
	 * @param ans2 the ans2 to set
	 */
	public void setAns2(String ans2) {
		this.ans2 = ans2;
	}

	/**
	 * @return the ans3
	 */
	public String getAns3() {
		return ans3;
	}

	/**
	 * @param ans3 the ans3 to set
	 */
	public void setAns3(String ans3) {
		this.ans3 = ans3;
	}

	/**
	 * @return the ans4
	 */
	public String getAns4() {
		return ans4;
	}

	/**
	 * @param ans4 the ans4 to set
	 */
	public void setAns4(String ans4) {
		this.ans4 = ans4;
	}

	/**
	 * @return the correctAns
	 */
	public int getCorrectAns() {
		return correctAns;
	}

	/**
	 * @param correctAns the correctAns to set
	 */
	public void setCorrectAns(int correctAns) {
		this.correctAns = correctAns;
	}

	/**
	 * @return the teacherAssembeld
	 */
	public String getTeacherAssembeld() {
		return teacherAssembeld;
	}

	/**
	 * @param teacherAssembeld the teacherAssembeld to set
	 */
	public void setTeacherAssembeld(String teacherAssembeld) {
		this.teacherAssembeld = teacherAssembeld;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "QuestionId = " + questionId + ", \n"
				+ "questionText = " + questionText +", \n" 
				+ "idquestionIntruction = "+ idquestionIntruction +", \n" 
				+ "ans1 = " + ans1+", \n"
				+ "ans2 = " + ans2+", \n"
				+ "ans3 = " + ans3+", \n"
				+ "ans4 = " + ans4+", \n"
				+ "correctAns = " + correctAns+", \n" 
				+ "teacherAssembeld = " + teacherAssembeld +", \n\n";
	}

}
