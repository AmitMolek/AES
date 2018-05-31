package comunication;

public class Question implements java.io.Serializable {

	/**
	 * Class that responsible for saving question details
	 */
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String teacherName;
	private String questionIns;
	private String id;
	private String ans1;
	private String ans2;
	private String ans3;
	private String ans4;
	private int correctAns;
	private int questionNum;

	/**
	 * Constructor of question class
	 */

	public Question(String id, int questionNum, String teacherName, String questionIns, String ans1, String ans2,
			String ans3, String ans4, int correctAns) {
		super();
		this.ans1 = ans1;
		this.ans2 = ans2;
		this.ans3 = ans3;
		this.ans4 = ans4;
		this.questionNum = questionNum;
		this.correctAns = correctAns;
		this.teacherName = teacherName;
		this.questionIns = questionIns;
		this.id = id;
	}

	/**
	 * 
	 * @return the number of the question
	 */

	public int getQuestionNum() {
		return questionNum;
	}

	/**
	 * set the number of the question
	 * 
	 * @param questionNum
	 *            the question number
	 */

	public void setQuestionNum(int questionNum) {
		this.questionNum = questionNum;
	}

	/**
	 * 
	 * @return the first answer
	 */
	public String getAns1() {
		return ans1;
	}

	/**
	 * set the the first answer
	 * 
	 * @param ans1
	 *            the first answer
	 */
	public void setAns1(String ans1) {
		this.ans1 = ans1;
	}

	/**
	 * set the the second answer
	 * 
	 * @param ans2
	 *            the second answer
	 */
	public void setAns2(String ans2) {
		this.ans2 = ans2;
	}

	/**
	 * 
	 * @return the second answer
	 */
	public String getAns2() {
		return ans2;
	}

	/**
	 * 
	 * @return the third answer
	 */

	public String getAns3() {
		return ans3;
	}

	/**
	 * set the third answer
	 * 
	 * @param ans3
	 *            the third answer
	 */
	public void setAns3(String ans3) {
		this.ans3 = ans3;
	}

	/**
	 * 
	 * @return the fourth answer
	 */
	public String getAns4() {
		return ans4;
	}

	/**
	 * set the fourth answer
	 * 
	 * @param ans4
	 *            the fourth answer
	 */
	public void setAns4(String ans4) {
		this.ans4 = ans4;
	}

	/**
	 * 
	 * @return the number of correct answer
	 */
	public int getCorrectAns() {
		return correctAns;
	}

	/**
	 * set the number of correct answer
	 * 
	 * @param correctAns
	 *            the number of the correct answer
	 */
	public void setCorrectAns(int correctAns) {
		this.correctAns = correctAns;
	}

	/**
	 * 
	 * @return the question instruction
	 */
	public String getQuestionIns() {
		return questionIns;
	}

	/**
	 * set the question instruction
	 * 
	 * @param questionIns
	 *            the question instruction
	 */
	public void setQuestionIns(String questionIns) {
		this.questionIns = questionIns;
	}

	/**
	 * 
	 * @return the question id
	 */
	public String getId() {
		return id;
	}

	/**
	 * set the question id
	 * 
	 * @param id
	 *            the question id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 
	 * @return the teacher name
	 */
	public String getTeacherName() {
		return teacherName;
	}

	/**
	 * set the teacher name
	 * 
	 * @param teacherName
	 *            the teacher name
	 */
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

}
