package root.dao.message;

public class SimpleMessage extends AbstractMessage {

	public SimpleMessage(String string) {
		super(string);
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "SimpleMessage";
	}

}
