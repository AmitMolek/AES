package root.client.managers;

import java.util.HashMap;
import java.util.Map;

import ocsf.client.ObservableClient;
import root.dao.app.User;
import root.util.log.Log;
import root.util.log.LogLine.LineType;

/**
 * Saves data (object) for using in the system The use is that you can switch
 * between screen and keep data between them
 * 
 * @author Amit Molek
 *
 */

public class DataKeepManager {

	Map<String, Object> objMap;
	User user;
	Log log;

	private static DataKeepManager instace = new DataKeepManager();

	/**
	 * Private constructor used to init the instance
	 */
	private DataKeepManager() {
		objMap = new HashMap<String, Object>();
		log = Log.getInstance();
	}

	/**
	 * Keep the user object
	 * 
	 * @param userToKeep
	 *            the user object
	 * @return returns true if the user was added (saved)
	 */
	public boolean keepUser(User userToKeep) {
		if (user != null)
			return false;
		user = userToKeep;
		log.writeToLog(LineType.INFO, "Keep user: " + user.getUserID());
		return true;
	}

	/**
	 * Returns the object of the user, returns null if the user is not saved
	 * 
	 * @return
	 */
	public User getUser() {
		if (user != null)
			return user;
		return null;
	}

	/**
	 * Removes the user from the keep, return false if the user is already deleted
	 * 
	 * @return
	 */
	public boolean removeUser() {
		if (user == null)
			return false;
		String userid = user.getUserID();
		user = null;
		log.writeToLog(LineType.INFO, "Removed user from keep: " + userid);
		return true;
	}

	/**
	 * Keep the object (saves it) so you can use it
	 * 
	 * @param objKey
	 *            the key of the object
	 * @param obj
	 *            the object you want to keep
	 * @return returns true if the object is saved
	 */
	public boolean keepObject(String objKey, Object obj) {
		String objLower = objKey.toLowerCase();
		if (objMap.containsKey(objLower))
			return false;
		objMap.put(objLower, obj);
		if (obj != null)
			log.writeToLog(LineType.INFO, "Keep object: Key: " + objLower + " Object: " + obj.getClass());
		else
			log.writeToLog(LineType.INFO, "Keep object: Key: " + objLower);
		return true;
	}

	/**
	 * Updates the object with the objKey with the new object sent, if the object
	 * does not exist, adds it to the map
	 * 
	 * @param objKey
	 *            the key of the object you want to update
	 * @param obj
	 *            the object itself
	 * @return return true is updated
	 */
	public boolean updateObject(String objKey, Object obj) {
		String objLower = objKey.toLowerCase();
		if (!objMap.containsKey(objLower)) {
			return keepObject(objLower, obj);
		}
		objMap.put(objLower, obj);
		return true;
	}

	/**
	 * Gets the object with objKey, and deletes the object from the keep
	 * 
	 * @param objKey
	 *            the key of the object you want to get
	 * @return returns the object that is associated with objKey, null if there is
	 *         no object with this key
	 */
	public Object getObject(String objKey) {
		String objLower = objKey.toLowerCase();
		if (!objMap.containsKey(objLower))
			return null;
		Object tmpObj = objMap.get(objLower);
		objMap.remove(objLower);
		if (tmpObj != null)
			log.writeToLog(LineType.INFO, "Removed object: Key: " + objLower + " Object: " + tmpObj.getClass());
		else
			log.writeToLog(LineType.INFO, "Removed object: Key: " + objLower);
		return tmpObj;
	}

	/**
	 * Returns the wanted object without removing it from the map
	 * 
	 * @param objKey
	 *            the key of the object you want to get
	 * @return
	 */
	public Object getObject_NoRemove(String objKey) {
		String objLower = objKey.toLowerCase();
		if (!objMap.containsKey(objLower))
			return null;
		if (objLower.equals("client")) {
			ObservableClient tempClient = (ObservableClient) this.objMap.get("client");
			tempClient.deleteObservers();
			return tempClient;
		}
		return (objMap.get(objLower));
	}

	/**
	 * Returns the singleton instance of this class
	 * 
	 * @return
	 */
	public static DataKeepManager getInstance() {
		return instace;
	}

}