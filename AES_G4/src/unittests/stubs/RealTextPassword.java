package unittests.stubs;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RealTextPassword implements IFxml{
	private PasswordField txtPassword;


	public RealTextPassword(PasswordField txtPassword) {
		this.txtPassword = txtPassword;
	}

	@Override
	public String getText() {
		return txtPassword.getText();
	}

	@Override
	public void setVisable(boolean visable) {

	}

}
