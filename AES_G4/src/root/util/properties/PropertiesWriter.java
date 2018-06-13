package root.util.properties;

import java.io.File;
import java.io.IOException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * The config writer class, used to write properties to the config file
 * @author Amit Molek
 *
 */

public class PropertiesWriter extends PropertiesIO{
	
	private PropertiesConfiguration config;
	
	/**
	 * Creates a PropertiesWriter with default settings
	 * @throws ConfigurationException
	 * @throws IOException
	 */
	public PropertiesWriter() throws ConfigurationException, IOException {
		this(defaultPath, defaultFileName);
	}
	
	/**
	 * Creates a PropertiesWriter, sets the path and the file name
	 * @param path the path of the config file
	 * @param fileName the name of the config file
	 * @throws ConfigurationException
	 * @throws IOException
	 */
	public PropertiesWriter(String path, String fileName) throws ConfigurationException, IOException {
		super(path, fileName);
		config = openPropertiesConfiguration();
	}
	
	/**
	 * Sets the property inf the config file (and saves it)
	 * @param key the key of the property
	 * @param value the value of the property
	 * @throws ConfigurationException
	 */
	public void setProperty(String key, String value) throws ConfigurationException {
		config.setProperty(key, value);
		config.save();
	}
	
	/**
	 * Creates a config file
	 * @throws IOException
	 */
	private void createPropertiesFile() throws IOException {
		File f = new File(path + fileName + fileExt);
		if (!f.exists()) {
			if (path != "")
				f.mkdirs();
			f.createNewFile();
		}
	}
	
	/**
	 * Returns the PropertiesConfiguration, if the config file doesnt exist, creates it
	 * @return
	 * @throws IOException
	 * @throws ConfigurationException
	 */
	private PropertiesConfiguration openPropertiesConfiguration() throws IOException, ConfigurationException {
		createPropertiesFile();
		return (new PropertiesConfiguration(path + fileName + fileExt));
	}
}
