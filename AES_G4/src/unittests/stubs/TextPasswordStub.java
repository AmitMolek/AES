package unittests.stubs;

public class TextPasswordStub implements IFxml {

	private String password;

	@Override
	public String getText() {
		return password;
	}
	
	public void setId(String password) {
		this.password = password;
	}

	@Override
	public void setVisable(boolean visable) {
		
	}
	
}

