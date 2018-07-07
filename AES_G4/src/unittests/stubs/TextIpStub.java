package unittests.stubs;

public class TextIpStub implements IFxml {

	private String ip;

	@Override
	public String getText() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public void setVisible(boolean visable) {
		
	}

	@Override
	public boolean isVisible() {
		return false;
	}
}
