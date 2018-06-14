package root.client.controllers;

public class ExamDuration {
	
	private static int seconds;
	
	public static int getTime() {
		return seconds;
	}
	
	public static void setTime(int newTime) {
		seconds = newTime*60;
	}
	
	public static void minusOne() {
		seconds--;
	}

}
