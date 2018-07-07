package unittests.stubs;

public class TextPasswordStub implements IFxml {

	private String password;

	@Override
	public String getText() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public void setVisible(boolean visable) {
		
	}

	@Override
	public boolean isVisible() {
		return false;
	}
	
}

