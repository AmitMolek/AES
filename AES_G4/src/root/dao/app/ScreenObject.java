package root.dao.app;

import javafx.scene.layout.Pane;

/**
 * Holds data about screens
 * @author Amit Molek
 *
 */

public class ScreenObject {

	/**
	 * The name of the screen
	 */
	private String screenName;
	
	/**
	 * The pane of the screen
	 */
	private Pane screenPane;

	/**
	 * Class constructor
	 * @param screenName the name of the screen
	 * @param screenPane the pane of the screen
	 */
	public ScreenObject(String screenName, Pane screenPane) {
		this.screenName = screenName;
		this.screenPane = screenPane;
	}

	/**
	 * Returns the name of the screen
	 * @return the name of the screen
	 */
	public String getScreenName() {
		return screenName;
	}
	
	/**
	 * Sets the screen's name
	 * @param screenName the new screen name
	 */
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	
	/**
	 * Returns the screen pane of the screen
	 * @return screenPane the screen
	 */
	public Pane getScreenPane() {
		return screenPane;
	}
	
	/**
	 * Sets the screen's pane
	 * @param screenPane the new screen pane
	 */
	public void setScreenPane(Pane screenPane) {
		this.screenPane = screenPane;
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
		if (screenName == null) {
			if (other.screenName != null)
				return false;
		} else if (!screenName.equals(other.screenName))
			return false;
		if (screenPane == null) {
			if (other.screenPane != null)
				return false;
		} else if (!screenPane.equals(other.screenPane))
			return false;
		return true;
	}
	
}
