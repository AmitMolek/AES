package unittests.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import junit.framework.TestCase;
import root.client.controllers.LoginController;
import root.dao.app.LoginInfo;
import unittests.stubs.ClientStub;
import unittests.stubs.Dependancy;
import unittests.stubs.LogMock;
import unittests.stubs.PaneStub;
import unittests.stubs.PropertiesFileStub;
import unittests.stubs.TextIdStub;
import unittests.stubs.TextIpStub;
import unittests.stubs.TextPasswordStub;

public class LoginTest extends TestCase {

	public void setUp() throws Exception {
	}
	public void test() {
		TextIdStub txtId = new TextIdStub();
		TextPasswordStub txtPassword = new TextPasswordStub();
		TextIpStub txtIp = new TextIpStub();
		PaneStub pane = new PaneStub();
		txtId.setId("204403257");
		txtPassword.setPassword("12345");
		txtIp.setIp("localhost");
		ClientStub client = new ClientStub();
		ArrayList<LoginInfo> user = new ArrayList<LoginInfo>();
		user.add(new LoginInfo("204403257", "12345"));
		client.setStubUser(user);
		Dependancy dep = new Dependancy(txtId, txtPassword, txtIp, pane, client, new LogMock(),
				new PropertiesFileStub());
		LoginController login = new LoginController();
		login.setDependancy(dep);
		login.SignIn(null);
		ClientStub temp = (ClientStub) dep.getClientStub();
		Assert.assertTrue(temp.getMessage().equals("Login success"));

	}

}
