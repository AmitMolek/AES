package unittests.stubs;

import java.io.IOException;
import java.util.Observer;

public interface IClient {

	 public void openConnection() throws IOException;
	 public void sendToServer(Object msg) throws IOException;
	 public void addObserver(Observer o);
	 public void deleteObservers();
	 
}
