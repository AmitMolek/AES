package root.client.interface_classes;

import javafx.scene.layout.Pane;
import root.client.interfaces.IVisiblePane;

/**
 * This class is used to hold a pane when running
 * @author Amit Molek
 *
 */

public class RealPane implements IVisiblePane{

	Pane pane;
	
	public RealPane(Pane pane) {
		this.pane = pane;
	}

	@Override
	public boolean isVisible() {
		return pane.isVisible();
	}

	public Pane getPane() {
		return pane;
	}

	public void setPane(Pane pane) {
		this.pane = pane;
	}
	
}
