package unittests.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.TestCase;
import root.client.controllers.LoginController;
import root.dao.app.LoginInfo;
import unittests.stubs.ClientStub;
import unittests.stubs.Dependancy;
import unittests.stubs.FxmlStub;
import unittests.stubs.LogMock;
import unittests.stubs.PropertiesFileStub;

public class LoginTest extends TestCase{

	
	public void setUp() throws Exception {
	}

	public void test() {
		FxmlStub fxml = new FxmlStub();
		fxml.setId("204403257");
		fxml.setPassword("12345");
		fxml.setIp("localhost");
		ClientStub client = new ClientStub();
		ArrayList<LoginInfo> user = new ArrayList<LoginInfo>();
		user.add(new LoginInfo("204403257","12345"));
		client.setStubUser(user);
		Dependancy dep = new Dependancy(fxml, client, new LogMock(), new PropertiesFileStub());
		LoginController login = new LoginController();
		login.setDependancy(dep);
		login.SignIn(null);
		ClientStub temp = (ClientStub) dep.getClientStub();
		Assert.assertTrue(temp.getMessage().equals("Login success"));
		
	}

}
