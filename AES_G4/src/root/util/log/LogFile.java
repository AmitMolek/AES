package root.util.log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The class that interacts with the log file
 * @author Amit Molek
 *
 */

public class LogFile {
	
	private File logFile;
	private BufferedWriter writer;
	private String dir_path;
	@SuppressWarnings("unused")
	private String log_name;
	private String final_path;
	
	/**
	 * Class constructor
	 * Creats a log file named log in folder logs
	 */
	public LogFile() {
		this("logs", "log");
	}
	
	/**
	 * Class constructor
	 * @param log_name the log file name
	 */
	public LogFile(String log_name) {
		this("logs", log_name);
	}
	
	/**
	 * Class constructor
	 * @param dir_path the path of the log file
	 * @param log_name the name of the log file
	 */
	private LogFile(String dir_path, String log_name) {
		try {
			this.dir_path = dir_path;
			this.log_name = log_name + ".txt";
			final_path = dir_path + "/" + log_name;
			logFile = openLogFile();
			writer = new BufferedWriter(new FileWriter(logFile, true));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Writes to the log file
	 * Appends the line to the end of the log file
	 * @param line the line you want to write to the log
	 */
	// Appends the line (LogLine) to the log file
	public void appendToLog(LogLine line) {
		try {
			writer.write(line.toString());
			writer.flush();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Opens the log file, if it doesn't exist creates a one
	 * Returns The log file
	 * @return the log file
	 * @throws Exception
	 */
	// Tries to open the log file, if the log doesn't exist, creates it
	private File openLogFile() throws Exception{
		File tryFile = new File(final_path);
		if (tryFile.exists() && !tryFile.isDirectory()) {
			return tryFile;
		}else {
			return createLogFile();
		}
	}
	
	/**
	 * Creates the log file
	 * @return the log file
	 * @throws Exception
	 */
	// Creates a log file, and a logs directory
	private File createLogFile() throws Exception{
		File dir = new File(dir_path);
		if (!dir.exists()) {
			dir.mkdir();
		}
		File file = new File(final_path);
		return file;
	}
	
}