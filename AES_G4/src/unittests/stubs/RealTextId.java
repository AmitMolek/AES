package unittests.stubs;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class RealTextId implements IFxml {

	private TextField txtId;


	public RealTextId(TextField txtId) {
		this.txtId = txtId;
	}

	@Override
	public String getText() {
		return txtId.getText();
	}

	@Override
	public void setVisible(boolean visable) {

	}

	@Override
	public boolean isVisible() {
		return txtId.isVisible();
	}

}
