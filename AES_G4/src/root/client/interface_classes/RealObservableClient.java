package root.client.interface_classes;

import java.io.IOException;
import java.util.Observer;

import ocsf.client.ObservableClient;
import root.client.interfaces.IClient;

/**
 * This class is used to hold the real Observable client when running
 * @author Amit Molek
 *
 */

public class RealObservableClient implements IClient{

	ObservableClient client;
	
	public RealObservableClient(ObservableClient client) {
		this.client = client;
	}
	
	@Override
	public void openConnection() throws IOException{
		client.openConnection();
	}

	@Override
	public void sendToServer(Object msg) throws IOException {
		client.sendToServer(msg);
	}
	
	public void setClient(ObservableClient client) {
		this.client = client;
	}
	
	public ObservableClient getClient(ObservableClient client) {
		return this.client = client;
	}

	@Override
	public String getType() {
		return "Real";
	}

	@Override
	public void addObserver(Observer o) {
		this.client.addObserver(o);
	}

	@Override
	public ObservableClient getObservableClient() {
		return client;
	}
	
}
