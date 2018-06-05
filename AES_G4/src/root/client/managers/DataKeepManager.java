package root.client.managers;

import java.util.HashMap;
import java.util.Map;

/**
 * Saves data (object) for using in the system
 * The use is that you can switch between screen and keep data between them
 * @author Amit Molek
 *
 */

public class DataKeepManager {

	Map<String, Object> objMap;
	
	private static DataKeepManager instace = new DataKeepManager();
	
	private DataKeepManager() {
		objMap = new HashMap<String, Object>();
	}
	
	/**
	 * Keep the object (saves it) so you can use it
	 * @param objKey the key of the object
	 * @param obj the object you want to keep
	 * @return returns true if the object is saved
	 */
	public boolean keepObject(String objKey, Object obj) {
		if (objMap.containsKey(objKey)) return false;
		objMap.put(objKey, obj);
		return true;
	}
	
	/**
	 * Gets the object with objKey
	 * @param objKey the key of the object you want to get
	 * @return returns the object that is associated with objKey, null if there is no object with this key
	 */
	public Object getObject(String objKey) {
		if (!objMap.containsKey(objKey)) return null;
		return objMap.get(objKey);
	}
	
	/**
	 * Returns the singleton instance of this class
	 * @return
	 */
	public static DataKeepManager getInstance() {
		return instace;
	}
	
}
