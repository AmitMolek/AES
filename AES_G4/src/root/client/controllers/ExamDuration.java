package root.client.controllers;

/**
 * ExamDurationTime is class that hold the time for execute students
 * 
 * @author Naor Saadia
 *
 */
public class ExamDuration {
	// Instance variables **********************************************

	/**
	 * The duration time in seconds
	 */
	private static int seconds;

	/**
	 * getTime is method that return the time in seconds
	 */
	public static int getTime() {
		return seconds;
	}

	/**
	 * method that set new time
	 * 
	 * @param newTime
	 *            the wanted time in seconds
	 */
	public static void setTime(int newTime) {
		seconds = newTime * 60;
	}

	/**
	 * when the exam is running the time should count down
	 */
	public static void minusOne() {
		seconds--;
	}

}
