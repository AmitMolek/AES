package root.unittests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import root.client.controllers.LoginController;
import root.client.interface_classes.StubClient;
import root.client.interface_classes.StubFieldText;
import root.client.interface_classes.StubMessageFactory;
import root.client.interface_classes.StubServerPane;
import root.client.mocks.SignInMock;

public class TestClientSignIn {

	/**
	 * This class tests are checking valid inputs (server ip, user id, password)
	 * 
	 * Looking at the code: 
	 * - Almost not validation was made on the input so basically you can enter what ever you want
	 * 
	 * NO CLIENT SIDE VALIDATION WAS MADE :(
	 */

	LoginController loginController;
	SignInMock mock;
	
	StubServerPane serverIPPane;
	StubClient client;
	StubMessageFactory factory;
	
	@Before
	public void setUp() {
		loginController = new LoginController();
		mock = new SignInMock();
		client = new StubClient(null);
		factory = new StubMessageFactory();
		
		loginController.setMockService(mock);
	}
	
	/**
	 * Checks if you insert an invalid ip address below the range (0-255) 
	 */
	@Test
	public void test_ServerIP_BelowRange() {
		String ip = "-1.0.0.0";
		serverIPPane = new StubServerPane(true);
		StubFieldText ipTextField = new StubFieldText(ip);
		
		loginController.signIn_GetIP(serverIPPane, ipTextField);
		
		assertTrue("Invalid server IP (Below range)".equals(mock.getMsg()));
	}
	
	/**
	 * Checks if you insert a valid ip address with in range (0-255)
	 */
	@Test
	public void test_ServerIP_InRange() {
		String ip = "0.0.0.0";
		serverIPPane = new StubServerPane(true);
		StubFieldText ipTextField = new StubFieldText(ip);
		
		loginController.signIn_GetIP(serverIPPane, ipTextField);
		
		assertTrue("Valid server IP".equals(mock.getMsg()));
	}
	
	/**
	 * Checks if you insert an invalid ip address above the range (0-255)
	 */
	@Test
	public void test_ServerIP_AboveRange() {
		String ip = "256.0.0.0";
		serverIPPane = new StubServerPane(true);
		StubFieldText ipTextField = new StubFieldText(ip);
		
		loginController.signIn_GetIP(serverIPPane, ipTextField);
		
		assertTrue("Invalid server IP (Above range)".equals(mock.getMsg()));
	}
	
	/**
	 * Tests an invalid ip syntax (only 2 points and 3 numbers)
	 */
	@Test
	public void test_ServerIP_InvalidSyntax_2Points() {
		String ip = "25.25.25";
		serverIPPane = new StubServerPane(true);
		StubFieldText ipTextField = new StubFieldText(ip);
		
		loginController.signIn_GetIP(serverIPPane, ipTextField);
		
		assertTrue("Invalid server IP syntax".equals(mock.getMsg()));
	}

	/**
	 * Tests a valid server ip
	 */
	@Test
	public void test_ServerIP_ValidSyntax_3Points() {
		String ip = "25.25.25.25";
		serverIPPane = new StubServerPane(true);
		StubFieldText ipTextField = new StubFieldText(ip);
		
		loginController.signIn_GetIP(serverIPPane, ipTextField);
		
		assertTrue("Valid server IP".equals(mock.getMsg()));
	}
	
	/**
	 * Tests an invalid ip syntax (4 points and 5 numbers)
	 */
	@Test
	public void test_ServerIP_InvalidSyntax_4Points() {
		String ip = "25.25.25.25.25";
		serverIPPane = new StubServerPane(true);
		StubFieldText ipTextField = new StubFieldText(ip);
		
		loginController.signIn_GetIP(serverIPPane, ipTextField);
		
		assertTrue("Invalid server IP syntax".equals(mock.getMsg()));
	}
	
	/**
	 * Tests if you enter ip with chars in it
	 */
	@Test
	public void test_ServerIP_InvalidSyntax_NoNumbers() {
		String ip = "a.b.c.d";
		serverIPPane = new StubServerPane(true);
		StubFieldText ipTextField = new StubFieldText(ip);
		
		loginController.signIn_GetIP(serverIPPane, ipTextField);
		
		assertTrue("Invalid server IP syntax (Chars detected)".equals(mock.getMsg()));
	}
	
	/**
	 * Tests if you enter something else than an ip (like a sentece)
	 */
	@Test
	public void test_ServerIP_InvalidSyntax_Sentence() {
		String ip = "His blade was sharp";
		serverIPPane = new StubServerPane(true);
		StubFieldText ipTextField = new StubFieldText(ip);
		
		loginController.signIn_GetIP(serverIPPane, ipTextField);
		
		assertTrue("Invalid server IP syntax (No ip syntax detected)".equals(mock.getMsg()));
	}
	
	/**
	 * Tests if you enter user id with 8 numbers (below the valid length (9))
	 */
	@Test
	public void test_UserID_8Numbers() {
		StubFieldText userid = new StubFieldText("12345678");
		StubFieldText password = new StubFieldText("1234");
		
		loginController.signInUser(client, userid, password, factory);
		
		assertTrue("Invalid User ID (Below length)".equals(mock.getMsg()));
	}
	
	/**
	 * Checks if you enter a valid id
	 */
	@Test
	public void test_UserID_Valid() {
		StubFieldText userid = new StubFieldText("123456789");
		StubFieldText password = new StubFieldText("1234");
		
		loginController.signInUser(client, userid, password, factory);
		
		assertTrue("Valid User ID".equals(mock.getMsg()));
	}
	
	/**
	 * Tests if you enter user id with 10 numbers (above the valid length (9))
	 */
	@Test
	public void test_UserID_10Numbers() {
		StubFieldText userid = new StubFieldText("1234567890");
		StubFieldText password = new StubFieldText("1234");
		
		loginController.signInUser(client, userid, password, factory);
		
		assertTrue("Invalid User ID (Above length)".equals(mock.getMsg()));
	}
	
	/**
	 * Tests if you enter chars (letters) in the user id
	 */
	@Test
	public void test_UserID_Chars() {
		StubFieldText userid = new StubFieldText("blablal");
		StubFieldText password = new StubFieldText("1234");
		
		loginController.signInUser(client, userid, password, factory);
		
		assertTrue("Invalid User ID (Chars found)".equals(mock.getMsg()));
	}
	
	/**
	 * Tests if you enter empty user id
	 */
	@Test
	public void test_UserID_Empty() {
		StubFieldText userid = new StubFieldText("");
		StubFieldText password = new StubFieldText("1234");
		
		loginController.signInUser(client, userid, password, factory);
		
		assertTrue("Invalid User ID (Empty)".equals(mock.getMsg()));
	}
	
	/**
	 * Tests if you enter empty password
	 */
	@Test
	public void test_Password_Empty() {
		StubFieldText userid = new StubFieldText("123456789");
		StubFieldText password = new StubFieldText("");
		
		loginController.signInUser(client, userid, password, factory);
		
		assertTrue("Invalid Password (Empty)".equals(mock.getMsg()));
	}
	
	/**
	 * Tests if you enter empty server ip
	 * THIS IS THE ONLY VALIDATION THAT WAS MADE! :)
	 */
	@Test
	public void test_ServerIP_Empty() {
		String ip = "";
		serverIPPane = new StubServerPane(true);
		StubFieldText ipTextField = new StubFieldText(ip);
		
		try {
			loginController.signIn_GetIP(serverIPPane, ipTextField);
		} catch (Exception e) {
			// We use try-catch because we know that the function showErrorDialog
			// will throw an exception because all the fields are null (dont exist because we test without the whole javafx)
		}
		
		assertTrue("Server IP error".equals(mock.getMsg()));
	}
}
