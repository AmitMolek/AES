package aes_Root.util.log;

import aes_Root.dao.LogLine;
import aes_Root.dao.LogLine.LineType;

public class LogTest {

	public static void main(String[] args) {
		Log log = Log.getInstance();
		log.WriteToLog(LineType.INFO, "Good test very");
	}
	
}
