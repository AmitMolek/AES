package unittests.stubs;

import javafx.scene.layout.Pane;

public class RealPane implements IFxml {

	private Pane ipPane;
	
	public RealPane(Pane ipPane) {
		super();
		this.ipPane = ipPane;
	}

	@Override
	public String getText() {
		return null;
	}

	@Override
	public void setVisible(boolean visable) {
		ipPane.setVisible(visable);
	}

	@Override
	public boolean isVisible() {
		return ipPane.isVisible();
	}

}
