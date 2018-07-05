package root.util.log;

/**
 * A Singleton class the manages the log
 * Manages the log
 * @author Amit Molek
 *
 */

public class Log {
	
	private LogFile logFile;
	
	private static Log log = new Log();
	
	/**
	 * Private constructor (because singleton)
	 * Gets the log file
	 */
	public Log() {
		try {
			logFile = new LogFile();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Writes to the log file
	 * @param line_type the type of the log line
	 * @param log_msg the message you want to write to the log
	 */
	// Writes to the log
	public void writeToLog(LogLine.LineType line_type, String log_msg) {
		logFile.appendToLog(new LogLine(line_type, log_msg, getCallerClassName()));
	}
	
	/**
	 * Writes to the log file
	 * @param line_type the type of the log line
	 * @param log_msg the message you want to write to the log
	 * @param writeCallerClassName the name of the calling class
	 */
	// Writes to the log
	// writeCallerClassName if true -> writes to the log the calling class name
	public void writeToLog(LogLine.LineType line_type, String log_msg, boolean writeCallerClassName) {
		if (writeCallerClassName)
			logFile.appendToLog(new LogLine(line_type, log_msg, getCallerClassName()));
		else logFile.appendToLog(new LogLine(line_type, log_msg, ""));
	}
	
	/**
	 * Returns the calling class name
	 * @return
	 */
	// Returns the name of the caller class
	private String getCallerClassName(){
		final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		return (stackTrace[3].getClassName()); 
	}
	
	/**
	 * Returns the instance of the singleton
	 * @return
	 */
	// Returns the singleton of this class (Log)
	// Meanning there is only 1 log manager
	public static Log getInstance() {
		return log;
	}
}
