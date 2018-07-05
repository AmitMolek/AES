package unittests.stubs;

import java.io.IOException;
import java.util.Observer;

import ocsf.client.ObservableClient;

public class RealClient implements IClient {

	ObservableClient client;

	public RealClient(String host, int port) {
		client = new ObservableClient(host, port);
	}
	
	@Override
	public void openConnection() throws IOException {
		client.openConnection();
	}

	@Override
	public void sendToServer(Object msg) throws IOException {
		client.sendToServer(msg);
	}

	@Override
	public void addObserver(Observer o) {
		client.addObserver(o);
		
	}

	@Override
	public void deleteObservers() {
		client.deleteObservers();
		
	}

}
