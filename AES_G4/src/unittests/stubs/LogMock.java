package unittests.stubs;

import root.util.log.Log;
import root.util.log.LogLine;

public class LogMock extends Log {
	
	String message;
	
	public void writeToLog(LogLine.LineType line_type, String log_msg) {
		message = log_msg;
	}

}
