package root.client.interface_classes;

import javafx.scene.control.TextField;
import root.client.interfaces.IFieldText;

/**
 * This class is used to hold a TextField when running
 * @author Amit Molek
 *
 */

public class RealTextField implements IFieldText{

	TextField field;
	
	public RealTextField(TextField field) {
		this.field = field;
	}
	
	@Override
	public String getText() {
		return this.field.getText();
	}

}
