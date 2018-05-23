package log;

import log.data_types.LogLine;
import log.data_types.LogLine.LineType;

public class LogTest {

	public static void main(String[] args) {
		Log log = Log.getInstance();
		log.WriteToLog(LineType.INFO, "Good test very");
	}
	
}
