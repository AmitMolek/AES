package root.client.interface_classes;

import java.util.Observer;

import ocsf.client.ObservableClient;
import root.client.interfaces.IClient;

/**
 * This is a stub class used to replace the ObservableClient
 * @author Amit Molek
 *
 */

public class StubClient implements IClient{

	ObservableClient client;
	
	public StubClient(ObservableClient client) {
		this.client = client;
	}
	
	@Override
	public void openConnection() {
	}

	@Override
	public void sendToServer(Object msg) {
	}

	@Override
	public String getType() {
		return "Stub";
	}

	@Override
	public void addObserver(Observer o) {
	}

	@Override
	public ObservableClient getObservableClient() {
		return client;
	}

}
