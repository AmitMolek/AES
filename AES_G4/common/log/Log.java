package log;

import log.data_types.LogLine;

/*
 * To use the log manager:
 * This log manager is a singleton, so you dont need to create one
 * you cant create one..... -_-
 * Log log = Log.getInstance();
 * 
 * To write to the log use the:
 * log.WriteToLog(LINE TYPE, LOG MSG);
 * 
 * LINE TYPE: 
 * 			LogLine.LineType.ERROR - Indicate an error log line
 * 			LogLine.LineType.INFO - Indicate a info log line
 * 
 * LOG MSG: the message you want to write to the log........... -_-
 */

public class Log {
	
	private LogFile logFile;
	
	private static Log log = new Log(); 
	
	private Log() {
		try {
			logFile = new LogFile();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Writes to the log
	public void WriteToLog(LogLine.LineType line_type, String log_msg) {
		logFile.AppendToLog(new LogLine(line_type, log_msg, getCallerClassName()));
	}
	
	// Returns the name of the caaller class
	private String getCallerClassName(){
		final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		return (stackTrace[3].getClassName()); 
	}
	
	// Returns the singleton of this class (Log)
	// Meanning there is only 1 log manager
	public static Log getInstance() {
		return log;
	}
}
