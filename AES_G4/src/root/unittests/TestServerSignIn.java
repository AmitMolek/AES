package root.unittests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import root.dao.app.LoginInfo;
import root.dao.app.User;
import root.dao.message.AbstractMessage;
import root.dao.message.ErrorMessage;
import root.dao.message.LoginMessage;
import root.dao.message.MessageFactory;
import root.dao.message.UserMessage;
import root.server.managers.ServerMessageManager;
import root.server.stubs.GetFromDbStub;

public class TestServerSignIn {

	@Before
	public void setUp() {

		
	}

	/**
	 * Test for valid user 
	 */
	@Test
	public void test_valid_user() {
		MessageFactory message = MessageFactory.getInstance();
		LoginInfo loginInformation = new LoginInfo("204403257", "12345");
		ArrayList<User> user = new ArrayList<User>();
		User u = new User("204403257","Omer","Haimovich","12345","student");
		user.add(u);
		GetFromDbStub getFromDb = new GetFromDbStub(user);
		ServerMessageManager smm = new ServerMessageManager(getFromDb);
		LoginMessage login = (LoginMessage) message.getMessage("login", loginInformation);
		AbstractMessage msg = smm.handleMessage(login);
		UserMessage messageUser = (UserMessage) msg;
		assertTrue((msg.getMsg().equals("User")) && (u.getUserID().equals(messageUser.getUser().getUserID())));
				
	}
	
	/**
	 * Test for user already logged in the system
	 */
	@Test
	public void test_user_already_logeedIn() {
		MessageFactory message = MessageFactory.getInstance();
		LoginInfo loginInformation = new LoginInfo("308023381", "1234");
		ArrayList<User> user = new ArrayList<User>();
		User u = new User("308023381","Naor","Saadia","1234","student");
		user.add(u);
		GetFromDbStub getFromDb = new GetFromDbStub(user);
		ServerMessageManager smm = new ServerMessageManager(getFromDb);
		LoginMessage login = (LoginMessage) message.getMessage("login", loginInformation);
		AbstractMessage msg = smm.handleMessage(login);
		UserMessage messageUser = (UserMessage) msg;
		if((msg.getMsg().equals("User"))) {
			if((u.getUserID().equals(messageUser.getUser().getUserID()))){
				msg = smm.handleMessage(login);
				ErrorMessage errorMessage = (ErrorMessage) msg;
				assertTrue((msg.getMsg().equals("error")) && (errorMessage.getErrorException().getMessage().equals("User is logged in")));
			}
				
		}		
		else {
			fail("User not login in first try");
		}
	}
	
	/**
	 * Test for wrong password and valid user id
	 */
	@Test
	public void test_valid_id_wrong_password() {
		MessageFactory message = MessageFactory.getInstance();
		LoginInfo loginInformation = new LoginInfo("311157523", "12");
		ArrayList<User> user = new ArrayList<User>();
		User u = new User("311157523","Alon","Ben-Yosef","1234","teacher");
		user.add(u);
		GetFromDbStub getFromDb = new GetFromDbStub(user);
		ServerMessageManager smm = new ServerMessageManager(getFromDb);
		LoginMessage login = (LoginMessage) message.getMessage("login", loginInformation);
		AbstractMessage msg = smm.handleMessage(login);
		ErrorMessage errorMessage = (ErrorMessage) msg;
		assertTrue((msg.getMsg().equals("error")) && (errorMessage.getErrorException().getMessage().equals("Wrong Password")));
	}
	
	/**
	 * Test for user does not exist
	 */
	@Test
	public void test_user_not_exist() {
		MessageFactory message = MessageFactory.getInstance();
		LoginInfo loginInformation = new LoginInfo("111111111", "1");
		ArrayList<User> user = new ArrayList<User>();
		User u = new User("222222222","Gal","Brand","12","teacher");
		user.add(u);
		GetFromDbStub getFromDb = new GetFromDbStub(user);
		ServerMessageManager smm = new ServerMessageManager(getFromDb);
		LoginMessage login = (LoginMessage) message.getMessage("login", loginInformation);
		AbstractMessage msg = smm.handleMessage(login);
		ErrorMessage errorMessage = (ErrorMessage) msg;
		assertTrue((msg.getMsg().equals("error")) && (errorMessage.getErrorException().getMessage().equals("User not exist")));
	}
	
	/**
	 * Test for wrong id and correct password
	 */
	@Test
	public void test_wrong_id_valid_password() {
		MessageFactory message = MessageFactory.getInstance();
		LoginInfo loginInformation = new LoginInfo("333333333", "1");
		ArrayList<User> user = new ArrayList<User>();
		User u = new User("444444444","Gal","Brand","1","teacher");
		user.add(u);
		GetFromDbStub getFromDb = new GetFromDbStub(user);
		ServerMessageManager smm = new ServerMessageManager(getFromDb);
		LoginMessage login = (LoginMessage) message.getMessage("login", loginInformation);
		AbstractMessage msg = smm.handleMessage(login);
		ErrorMessage errorMessage = (ErrorMessage) msg;
		assertTrue((msg.getMsg().equals("error")) && (errorMessage.getErrorException().getMessage().equals("User not exist")));
	}
	
	
	
	

}
