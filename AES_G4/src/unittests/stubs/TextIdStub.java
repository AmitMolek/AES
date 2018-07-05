package unittests.stubs;



public class TextIdStub implements IFxml{
	
	private String id;
	
	

	@Override
	public String getText() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void setVisable(boolean visable) {
		
	}
	
	
	
}
