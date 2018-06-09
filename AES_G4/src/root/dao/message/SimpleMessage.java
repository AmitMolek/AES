package root.dao.message;

import javafx.scene.Parent;

public class SimpleMessage extends AbstractMessage {

	public SimpleMessage(String string) {
		super(string);
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "SimpleMessage";
	}
	
	public void setMessage(String message) {
		super.setMsg(message);
	}

}
