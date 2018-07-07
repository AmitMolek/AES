package unittests.stubs;

import javafx.scene.control.TextField;

public class RealTextIp implements IFxml{

	private TextField txtIp;


	public RealTextIp(TextField txtIp) {
		this.txtIp = txtIp;
	}

	@Override
	public String getText() {
		return txtIp.getText();
	}

	@Override
	public void setVisible(boolean visable) {

	}

	@Override
	public boolean isVisible() {
		return txtIp.isVisible();
	}
	
}
