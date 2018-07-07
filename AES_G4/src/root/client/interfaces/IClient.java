package root.client.interfaces;

import java.io.IOException;
import java.util.Observer;

import ocsf.client.ObservableClient;

/**
 * This interface is used to change the ObservableClient
 * @author Amit Molek
 *
 */

public interface IClient {

	public void openConnection() throws IOException;
	
	public void sendToServer(Object msg) throws IOException;
	
	public void addObserver(Observer o);
	
	public ObservableClient getObservableClient();
	
	public String getType();
}
