package root.client.managers;

import java.util.HashMap;
import java.util.Map;

import root.dao.app.User;
import root.util.log.Log;
import root.util.log.LogLine.LineType;

/**
 * Saves data (object) for using in the system
 * The use is that you can switch between screen and keep data between them
 * @author Amit Molek
 *
 */

public class DataKeepManager {

	Map<String, Object> objMap;
	User user;
	Log log;
	
	private static DataKeepManager instace = new DataKeepManager();
	
	private DataKeepManager() {
		objMap = new HashMap<String, Object>();
		log = Log.getInstance();
	}
	
	/**
	 * Keep the user object
	 * @param user the user object
	 * @return returns true if the user was added (saved)
	 */
	public boolean keepUser(User user) {
		if (user != null) return false;
		this.user = user;
		log.writeToLog(LineType.INFO, "Keep user: " + user.getUserID());
		return true;
	}
	
	/**
	 * Returns the object of the user, returns null if the user is not saved
	 * @return
	 */
	public User getUser() {
		if (user != null) return user;
		return null;
	}
	
	/**
	 * Removes the user from the keep, return false if the user is already deleted
	 * @return
	 */
	public boolean removeUser() {
		if (user == null) return false;
		user = null;
		log.writeToLog(LineType.INFO, "Removed user from keep: " + user.getUserID());
		return true;
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
		log.writeToLog(LineType.INFO, "Keep object: " + obj.getClass());
		return true;
	}
	
	/**
	 * Gets the object with objKey, and deletes the object from the keep
	 * @param objKey the key of the object you want to get
	 * @return returns the object that is associated with objKey, null if there is no object with this key
	 */
	public Object getObject(String objKey) {
		if (!objMap.containsKey(objKey)) return null;
		Object tmpObj = objMap.get(objKey);
		objMap.remove(tmpObj);
		log.writeToLog(LineType.INFO, "Removed object from keep: " + tmpObj.getClass());
		return tmpObj;
	}
	
	/**
	 * Returns the singleton instance of this class
	 * @return
	 */
	public static DataKeepManager getInstance() {
		return instace;
	}
	
}
