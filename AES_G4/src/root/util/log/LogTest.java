package root.util.log;

public class LogTest {

	public static void main(String[] args) {
		Log log = Log.getInstance();
		log.WriteToLog(LineType.INFO, "Good test very");
	}
	
}
