package root.client.interface_classes;

import root.client.interfaces.IVisiblePane;

/**
 * This is a stub class used to replace pane
 * @author Amit Molek
 *
 */

public class StubServerPane implements IVisiblePane{

	boolean visible;
	
	public StubServerPane() {
		this.visible = false;
	}
	
	public StubServerPane(boolean visible) {
		this.visible = visible;
	} 
	
	@Override
	public boolean isVisible() {
		return this.visible;
	}

}
