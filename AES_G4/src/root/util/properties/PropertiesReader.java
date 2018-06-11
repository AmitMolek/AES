package root.util.properties;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * The config reader class, used to read properties from the config file
 * @author Amit Molek
 *
 */

public class PropertiesReader extends PropertiesIO{
	
	Configuration config;
	
	/**
	 * Creates a PropertiesReader with default settings
	 * @throws ConfigurationException
	 */
	public PropertiesReader() throws ConfigurationException {
		this(defaultPath, defaultFileName);
	}
	
	/**
	 * Creates a PropertiesReader, sets the path and the file name
	 * @param path the path of the config file
	 * @param fileName the name of the config file
	 * @throws ConfigurationException
	 */
	public PropertiesReader(String path, String fileName) throws ConfigurationException {
		super(path, fileName);
		config = getPropConfig();
	}
	
	/**
	 * Reads the property from the config file with the key and returns it
	 * @param key the key of the property
	 * @return
	 */
	public String getProperty(String key) {
		return config.getString(key);
	}
	
	/**
	 * Returns PropertiesConfiguration of the config file
	 * @return
	 * @throws ConfigurationException
	 */
	private PropertiesConfiguration getPropConfig() throws ConfigurationException {
		return (new PropertiesConfiguration(path + fileName + fileExt));
	}
	
}
