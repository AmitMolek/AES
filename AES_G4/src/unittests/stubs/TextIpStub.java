package unittests.stubs;

public class TextIpStub implements IFxml {

	private String ip;

	@Override
	public String getText() {
		return ip;
	}
	
	public void setId(String ip) {
		this.ip = ip;
	}

	@Override
	public void setVisable(boolean visable) {
		
	}
}
