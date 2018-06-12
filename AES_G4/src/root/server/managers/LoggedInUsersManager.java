package root.server.managers;

import java.util.HashSet;

/**
 * Singleton class
 * Track all the logged in users
 * @author Amit Molek
 *
 */

public class LoggedInUsersManager {

	private HashSet<String> loggedInUsers;
	
	private static LoggedInUsersManager lium = new LoggedInUsersManager();
	
	/**
	 * Private class constructor, init the users list
	 */
	private LoggedInUsersManager() {
		loggedInUsers = new HashSet<>();
	}
	
	/**
	 * Adds a user to the logged in tracking
	 * @param user_id the user's id
	 * @return true if the user was added
	 */
	public boolean addLoggedInUser(String user_id) {
		return (loggedInUsers.add(user_id));
	}
	
	/**
	 * Removes a logged in user from the system
	 * @param user_id the user's id you want to remove
	 * @return true if user was removed from the system
	 */
	public boolean removeLoggedInUser(String user_id) {
		return (loggedInUsers.remove(user_id));
	}
	
	/**
	 * Returns if the user is logged in to the system
	 * @param user_id the user's id
	 * @return true if the user is logged in to the system
	 */
	public boolean isUserLoggedIn(String user_id) {
		return (loggedInUsers.contains(user_id));
	}
	
	/**
	 * Returns the singleton of this class
	 * @return
	 */
	public static LoggedInUsersManager getInstance() {
		return lium;
	}
}
