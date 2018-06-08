package root.dao.message;

import java.util.HashMap;

import root.dao.app.UserInfo;

/**
 * 
 * @author gal
 *
 */
public class UserInfoMessage extends AbstractMessage{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private UserInfo userInfo;
	
	/**
	 * this constructor is called when we want to sent to server the teachers ID and to get from the server theirs full names
	 * @param teacherInfo
	 */ 
	public UserInfoMessage(UserInfo teacherInfo) {
		super("get-user-name");
		this.userInfo = teacherInfo;
	}
	
	public UserInfoMessage(HashMap<String, String> usersMap) {
		super("ok-get-users");
		this.userInfo = new UserInfo(usersMap, null);
	}
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}


	/**
	 * @return the userInfo
	 */
	public UserInfo getUserInfo() {
		return userInfo;
	}


	/**
	 * @param userInfo the userInfo to set
	 */
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

}
