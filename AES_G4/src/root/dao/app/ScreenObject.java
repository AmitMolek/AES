package root.dao.app;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import root.client.Main;

/**
 * Holds data about screens
 * @author Amit Molek
 *
 */

public class ScreenObject {
	private String screenName;
	private Pane screenPane;
	private String fxmlPath;

	public ScreenObject(String screenName, Pane screenPane) throws IOException {
		this(screenName, screenPane, null);
	}

	public ScreenObject(String screenName, String fxmlPath) throws IOException {
		this(screenName, null, fxmlPath);
	}

	public ScreenObject(String screenName, Pane screenPane, String fxmlPath) throws IOException {
		this.screenName = screenName;
		this.screenPane = screenPane;
		this.fxmlPath = fxmlPath;
		//loadFxml();
	}

	private void loadFxml() throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxmlPath));
    	screenPane = fxmlLoader.load();
	}
	
	public String getScreenName() {
		return screenName;
	}
	
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	
	public Pane getScreenPane() {
		return screenPane;
	}
	
	public void setScreenPane(Pane screenPane) {
		this.screenPane = screenPane;
	}

	public String getFxmlPath() {
		return fxmlPath;
	}

	public void setFxmlPath(String fxmlPath) {
		this.fxmlPath = fxmlPath;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ScreenObject))
			return false;
		ScreenObject other = (ScreenObject) obj;
		if (fxmlPath == null) {
			if (other.fxmlPath != null)
				return false;
		} else if (!fxmlPath.equals(other.fxmlPath))
			return false;
		if (screenName == null) {
			if (other.screenName != null)
				return false;
		} else if (!screenName.equals(other.screenName))
			return false;
		return true;
	}
}
