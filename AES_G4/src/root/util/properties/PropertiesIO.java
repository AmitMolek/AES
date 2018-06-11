package root.util.properties;

/**
 * Base class for the writer and reader config classes
 * @author Amit Molek
 *
 */

public class PropertiesIO {
	
	protected String path;
	protected String fileName;
	
	protected final static String fileExt = ".properties";
	protected final static String defaultFileName = "config";
	protected final static String defaultPath = "";
	
	/**
	 * Default base info for the config file
	 */
	public PropertiesIO() {
		this(defaultPath, defaultFileName);
	}
	
	/**
	 * Creates a base info for the config file
	 * @param path the path of the config file
	 * @param fileName the name of the config file
	 */
	public PropertiesIO(String path, String fileName) {
		this.path = path;
		this.fileName = fileName;
	}

	/**
	 * Returns the path of the config file
	 * @return
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Sets the path of the config file
	 * @param path the new path of the config file
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Returns the name of the config file
	 * @return
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Sets the name of the config file
	 * @param fileName the new name of the config file
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
