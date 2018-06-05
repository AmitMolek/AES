package root.client.managers;

import java.util.ArrayList;
import java.util.Arrays;

import root.dao.app.User;
import root.util.log.Log;

public class LoggedInUserManager {
	/**
	 * @author gal
	 * This singleton class purpose to contain all loggedIn users, in a FIFO queue.
	 */
	private static int userCounter=0;
	private static ArrayList<User> connectedUsers = new ArrayList<User>();
	Log log = Log.getInstance();
	
	private static LoggedInUserManager INSTANCE = new LoggedInUserManager();
    
	/**
    * This class is a singleton
    * @return the instance of the class
    */
    public static LoggedInUserManager getInstance() {
    	return INSTANCE;
    }
	
    /**
     * Static add user to the singelton
     * @param user
     */
    public void addUser(User user) {
    	connectedUsers.add(user);
    	userCounter++;
    }
    public User getUser() {
    	return connectedUsers.get(userCounter--);
    }
    
    public String toString() {
		return Arrays.toString(connectedUsers.toArray());
    	
    }
}
