package unittests.stubs;

public class Dependancy {

	private TextIdStub txtId;
	private TextPasswordStub txtPassword;
	private TextIpStub txtIp;
	private PaneStub pane;
	private IClient clientStub;
	private LogMock logMock;
	private PropertiesFileStub propertiesFileStub;
	
	public Dependancy(TextIdStub txtId, TextPasswordStub txtPassword, TextIpStub txtIp, PaneStub pane,
			IClient clientStub, LogMock logMock, PropertiesFileStub propertiesFileStub) {
		super();
		this.txtId = txtId;
		this.txtPassword = txtPassword;
		this.txtIp = txtIp;
		this.pane = pane;
		this.clientStub = clientStub;
		this.logMock = logMock;
		this.propertiesFileStub = propertiesFileStub;
	}
	
	public TextIdStub getTxtId() {
		return txtId;
	}

	public TextPasswordStub getTxtPassword() {
		return txtPassword;
	}

	public TextIpStub getTxtIp() {
		return txtIp;
	}

	public PaneStub getPane() {
		return pane;
	}

	public IClient getClientStub() {
		return clientStub;
	}

	public LogMock getLogMock() {
		return logMock;
	}

	public PropertiesFileStub getPropertiesFileStub() {
		return propertiesFileStub;
	}
	
	
	
	
	
}
