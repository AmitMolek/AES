package root.client.interface_classes;

import root.client.interfaces.IFieldText;

/**
 * This is a stub class used to replace the FieldText
 * @author Amit Molek
 *
 */

public class StubFieldText implements IFieldText{

	String text;
	
	public StubFieldText() {
		text = "default";
	}
	
	public StubFieldText(String text) {
		this.text = text;
	}
	
	@Override
	public String getText() {
		return text;
	}

}
