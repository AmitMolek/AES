package log.data_types;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class LogLine {
	public enum LineType{
		ERROR,
		INFO
	}
	
	private LineType log_type;
	private String log_line;
	private Timestamp line_date;
	
	public LogLine() {
		log_type = LineType.INFO;
		log_line = "empty line";
		line_date = getTimestamp();
	}
	
	public LogLine(LineType type, String log_line) {
		this.log_type = type;
		this.log_line = log_line;
		this.line_date = getTimestamp();
	}
	
	// Returns the current time stamp
	private Timestamp getTimestamp() {
		try {
			Calendar cal = Calendar.getInstance();
			Date currentDate = cal.getTime();
			return (new Timestamp(currentDate.getTime()));
		}catch (Exception e){
			return (new Timestamp(0));
		}
	}
	
	// Returns the correct string representation of the linetype
	public static String getTypeString(LineType type) {
		switch (type) {
			case ERROR:
				return "ERROR";
			case INFO:
				return "INFO";
			default:
				return "INFO";
		}
	}
	
	@Override
	public String toString() {
		String format = "";
		format += "[" + getTypeString(this.log_type) + "] ";
		format += log_line;
		format += " | " + line_date.toString();
		format += "\n";
		return format;
	}
}
