package log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import log.data_types.LogLine;

public class LogFile {
	
	private File logFile;
	private BufferedWriter writer;
	private String dir_path;
	private String log_name;
	private String final_path;
	
	public LogFile() {
		this("logs", "log");
	}
	
	public LogFile(String log_name) {
		this("logs", log_name);
	}
	
	private LogFile(String dir_path, String log_name) {
		try {
			this.dir_path = dir_path;
			this.log_name = log_name + ".txt";
			final_path = dir_path + "/" + log_name;
			logFile = OpenLogFile();
			writer = new BufferedWriter(new FileWriter(logFile, true));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Appends the line (LogLine) to the log file
	public void AppendToLog(LogLine line) {
		try {
			writer.write(line.toString());
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				writer.close();
			}catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	// Tries to open the log file, if the log doesnt exist, creates it
	private File OpenLogFile() throws Exception{
		File tryFile = new File(final_path);
		if (tryFile.exists() && !tryFile.isDirectory()) {
			return tryFile;
		}else {
			return CreateLogFile();
		}
	}
	
	// Creates a log file, and a logs directory
	private File CreateLogFile() throws Exception{
		File dir = new File(dir_path);
		if (!dir.exists()) {
			dir.mkdir();
		}
		File file = new File(final_path);
		return file;
	}
	
}