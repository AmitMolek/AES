package root.util.properties;

import java.io.IOException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import root.util.log.Log;
import root.util.log.LogLine.LineType;

/**
 * Singleton class
 * Manages the config file
 * @author Amit Molek
 *
 */

public class PropertiesFile {
	
	private PropertiesWriter writer;
	private PropertiesReader reader;
	
	private Log log = Log.getInstance();
	
	private static PropertiesFile propFile = new PropertiesFile();
	
	/**
	 * Private singleton constructor
	 */
	public PropertiesFile() {
		Logger.getRootLogger().setLevel(Level.OFF);
		try {
			writer = new PropertiesWriter();
			reader = new PropertiesReader();
		} catch (ConfigurationException | IOException e) {
			e.printStackTrace();
			log.writeToLog(LineType.ERROR, "Error creating PropertiesWriter / PropertiesReader");
		}
	}
	
	/**
	 * Writes a property with key and value to the config file
	 * @param key the key of the property
	 * @param value the value of the property
	 */
	public void writeToConfig(String key, String value) {
		String logMsg = key + "[ " + value + "]";
		try {
			writer.setProperty(key, value);
			log.writeToLog(LineType.INFO, "Wrote property to config: " + logMsg);
		} catch (ConfigurationException e) {
			e.printStackTrace();
			log.writeToLog(LineType.ERROR, "Failed writing to config: " + logMsg);
		}
	}
	
	/**
	 * Returns a property with the key from the config
	 * @param key the key of the property
	 * @return
	 */
	public String getFromConfig(String key) {
		try {
			reader = new PropertiesReader();
			String pro = reader.getProperty(key);
			log.writeToLog(LineType.INFO, "Got property from config:" + key + "[ " + pro + "]");
			return pro;
		} catch (ConfigurationException e) {
			e.printStackTrace();
			log.writeToLog(LineType.ERROR, "Failed getting property from config: " + key);
			return null;
		}
	}
	
	/**
	 * Returns the singleton of this class
	 * @return
	 */
	public static PropertiesFile getInstance() {
		return propFile;
	}
	
}
