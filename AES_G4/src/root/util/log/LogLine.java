package root.util.log;

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
	private String callClassName;
	
	public LogLine() {
		this(LineType.INFO, "empty line");
	}
	
	public LogLine(LineType type, String log_line) {
		this.log_type = type;
		this.log_line = log_line;
		this.line_date = getTimestamp();
		callClassName = "";
	}
	
	public LogLine(LineType type, String log_line, String callClassName) {
		this(type, log_line);
		this.callClassName = callClassName;
		
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
		format += "[" + getTypeString(this.log_type) + "]";
		if (callClassName != "")
			format += "[" + callClassName + "] ";
		format += log_line;
		format += " | " + line_date.toString();
		format += "\n";
		return format;
	}
}
