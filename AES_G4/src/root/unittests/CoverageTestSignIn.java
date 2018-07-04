package root.unittests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ocsf.client.ObservableClient;
import root.client.controllers.LoginController;
import root.client.interface_classes.RealObservableClient;
import root.client.interface_classes.StubClient;
import root.client.interface_classes.StubFieldText;
import root.client.interface_classes.StubMessageFactory;
import root.client.interface_classes.StubServerPane;
import root.client.managers.DataKeepManager;
import root.client.mocks.SignInMock;
import root.dao.app.User;
import root.dao.message.ErrorMessage;
import root.dao.message.UserMessage;

public class CoverageTestSignIn {
	
	/*
	 * This class is used to test mostly coverage of the LoginController sign in process and update function
	 * We refactored the signIn function (for better testability) to 3 sub-functions:
	 * 1. Getting the IP of the server (signIn_GetIP)
	 * 2. Getting/creating the client (signIn_Client)
	 * 3. Sending the login info (signInUser)
	 * 
	 */
	
	LoginController loginController;
	SignInMock mock;
	
	StubServerPane severIPPane;
	
	DataKeepManager dkm = DataKeepManager.getInstance();
	
	@Before
	public void setUp() {
		loginController = new LoginController();
		mock = new SignInMock();
		
		loginController.setMockService(mock);
	}
	
	/**
	 * Used to clear the objects from the DataKeep
	 * So all tests can run smoothly
	 */
	private void resetDataKeep() {
		dkm.getObject("login_client");
		dkm.getObject("ip");
	}
	
	/**
	 * This is the route the test takes:
	 * 1. We want the server ip pane to be visible
	 * 2. We want the server ip text field to be empty
	 * 
	 * The expected result is "Server IP error"
	 */
	@Test
	public void test_IPPaneVisible_IPTextFieldEmpty() {
		severIPPane = new StubServerPane(true);
		StubFieldText ipTextField = new StubFieldText("");
		
		try {
			loginController.signIn_GetIP(severIPPane, ipTextField);
		} catch (Exception e) {
			// We use try-catch because we know that the function showErrorDialog
			// will throw an exception because all the fields are null (dont exist because we test without the whole javafx)
		}
		
		assertTrue("Server IP error".equals(mock.getMsg()));
	}
	
	/**
	 * This is the route the test takes:
	 * 1. The server ip pane is visible
	 * 2. The server ip text field is not empty
	 * 3. The IP is not null
	 * 4. We dont need to create the client
	 * 
	 * Expected result: "Got Client from keep"
	 */
	@Test
	public void test_IPPaneVisible_IPTextFieldNotEmpty_IPNotNull_GetsClient() {
		StubClient client = new StubClient(new ObservableClient("bla", 69));
		severIPPane = new StubServerPane(true);
		StubFieldText ipTextField = new StubFieldText("Not Empty");
		
		dkm.keepObject("ip", "70");
		dkm.keepObject("login_client", client);
		
		loginController.signIn_GetIP(severIPPane, ipTextField);
		loginController.signIn_Client();
		
		assertTrue("Got Client from keep".equals(mock.getMsg()));
		
		resetDataKeep();
	}
	
	/**
	 * This is the route the test takes:
	 * 1. The server ip pane is visible
	 * 2. The server ip text field is not empty
	 * 3. The IP is not null
	 * 4. We need to create the client
	 * 
	 * Expected result: "Created Client and stored it"
	 */
	@Test
	public void test_IPPaneVisible_IPTextFieldNotEmpty_IPNotNull_CreatesClient() {
		StubClient client = new StubClient(null);
		severIPPane = new StubServerPane(true);
		StubFieldText ipTextField = new StubFieldText("Not Empty");
		
		dkm.keepObject("ip", "70");
		dkm.keepObject("login_client", client);
		
		loginController.signIn_GetIP(severIPPane, ipTextField);
		loginController.signIn_Client();
		
		assertTrue("Created Client and stored it".equals(mock.getMsg()));
		
		resetDataKeep();
	}
	
	/**
	 * This is the route the test takes:
	 * 1. The server ip pane is not visible
	 * 2. The server ip is null
	 * we dont care about the rest
	 * 
	 * Expected result: "Server IP is null"
	 */
	@Test
	public void test_IPPaneNotVisible_IPNull() {
		severIPPane = new StubServerPane(false);

		dkm.keepObject("ip", null);
		
		loginController.signIn_GetIP(severIPPane, null);
		try {
			loginController.signIn_Client();
		} catch (Exception e) {
			// We use try-catch because we know that the function showErrorDialog
			// will throw an exception because all the fields are null (dont exist because we test without the whole javafx)
		}
		
		assertTrue("Server IP is null".equals(mock.getMsg()));
		
		resetDataKeep();
	}
	
	/**
	 * This is the route the test takes:
	 * 1. THe server ip pane is not visible
	 * 2. The IP is not null
	 * 3. We need to create the client
	 * 
	 * Expected result: "Created Client and stored it"
	 */
	@Test
	public void test_IPPaneNotVisible_IPNotNull_CreatesClient() {
		StubClient client = new StubClient(null);
		severIPPane = new StubServerPane(false);
		
		dkm.keepObject("ip", "70");
		dkm.keepObject("login_client", client);
		
		loginController.signIn_GetIP(severIPPane, null);
		loginController.signIn_Client();
		
		assertTrue("Created Client and stored it".equals(mock.getMsg()));
		
		resetDataKeep();
	}
	
	/**
	 * This is the route the test takes:
	 * 1. The server ip pane is not visible
	 * 2. The server ip is not null
	 * 3. We need to get the client from the DataKeep
	 * 
	 * Expected result: "Got Client from keep"
	 */
	@Test
	public void test_IPPaneNotVisible_IPNotNull_GetsClient() {
		StubClient client = new StubClient(new ObservableClient("blabla", 69));
		severIPPane = new StubServerPane(false);
		
		dkm.keepObject("ip", "70");
		dkm.keepObject("login_client", client);
		
		loginController.signIn_GetIP(severIPPane, null);
		loginController.signIn_Client();
		
		assertTrue("Got Client from keep".equals(mock.getMsg()));
		
		resetDataKeep();
	}
	
	/**
	 * This is the route the test takes:
	 * 1. The server ip pane is visible
	 * 2. The server ip text field is not empty
	 * 
	 * Expected result: "Stored IP in keep"
	 */
	@Test
	public void test_CheckIPKeep() {
		severIPPane = new StubServerPane(true);
		StubFieldText ipTextField = new StubFieldText("Not Empty");
		
		loginController.signIn_GetIP(severIPPane, ipTextField);
		
		assertTrue("Stored IP in keep".equals(mock.getMsg()));
	}
	
	/**
	 * This test checks if the sending message to the server
	 * 
	 * Expected result: "Sent message to server"
	 */
	@Test
	public void test_SendLoginMessage() {
		StubFieldText username = new StubFieldText("bla");
		StubFieldText password = new StubFieldText("6969");
		StubClient client = new StubClient(null);
		StubMessageFactory factory = new StubMessageFactory();
		
		loginController.signInUser(client, username, password, factory);
		
		assertTrue("Sent message to server".equals(mock.getMsg()));
	}
	
	/**
	 * This test checks what happens if the client isn't working
	 * We expect it to print out an error message to the console and throw an exception (because of the javafx framework not initiazllized)
	 * 
	 * Expected result: Error message + "Failed sending the message"
	 */
	@Test
	public void test_FailedSendingLoginMessage() {
		StubFieldText username = new StubFieldText("bla");
		StubFieldText password = new StubFieldText("6969");
		RealObservableClient client = new RealObservableClient(new ObservableClient("sure its gonna fail", 6969));
		StubMessageFactory factory = new StubMessageFactory();
		
		try {
			loginController.signInUser(client, username, password, factory);
		} catch (Exception e) {
			// We use try-catch because we know that the function showErrorDialog
			// will throw an exception because all the fields are null (dont exist because we test without the whole javafx)
		}

		assertTrue("Failed sending the message".equals(mock.getMsg()));
	}
	
	/**
	 * This test checks the update method if you send it a UserMessage
	 * We expect that the user info will be printed to the console (and we expect toolkit not init error because javafx)
	 * 
	 * Expected result: Console print + Exception + "Principal detected"
	 */
	@Test
	public void test_UpdateLoginPrincipal_UserMessage() {
		User user = new User("301726717", "gal", "amit", "1234", "Principal");
		UserMessage userMsg = new UserMessage(user);
		
		try {
			loginController.update(null, userMsg);
		}catch (Exception e) {
			// We use try-catch because we know that the function showErrorDialog
			// will throw an exception because all the fields are null (dont exist because we test without the whole javafx)
		}
		
		assertTrue("Principal detected".equals(mock.getMsg()));
	}
	
	/**
	 * This test checks what happens when you send to the Update function an error message
	 * We expect it to throw an exception because we show error dialog and javafx is not initiazllized
	 * 
	 * Expected result: "Got an ErrorMessage"
	 */
	@Test
	public void test_UpdateErrorMessage() {
		ErrorMessage errMsg = new ErrorMessage(new Exception("This will fail"));
		
		try {
			loginController.update(null, errMsg);
		}catch (Exception e) {
			// We use try-catch because we know that the function showErrorDialog
			// will throw an exception because all the fields are null (dont exist because we test without the whole javafx)
		}
		
		assertTrue("Got an ErrorMessage".equals(mock.getMsg()));
	}
}
