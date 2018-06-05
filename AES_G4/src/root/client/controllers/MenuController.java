package root.client.controllers;

import java.io.IOException;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import root.client.managers.DataKeepManager;
import root.client.managers.ScreensManager;

public class MenuController {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Menu menu_item_1;

    @FXML
    private MenuItem menu_item_1_1;
    
    @FXML
    private ImageView homeImg;
    
    @FXML
	public void initialize() throws IOException{
    	initGoHome();
    }
    
    /**
     * Creates a new clickable menu button
     * @param menu the menu object you want to make clickable
     * @param btnImg the icon you want to display in the menu
     * @param txtLbl the text you want the menu will display
     * @param maxWidth the maximum width of the menu object
     * @param imgFitSize the size of the icon
     * @param e the action you want to fire when clicking the menu object
     */
    private void createMenuItem(Menu menu, Image btnImg, String txtLbl, double maxWidth, double imgFitSize, EventHandler<MouseEvent> e) {
    	FlowPane menuPane = new FlowPane();
    	menuPane.setMaxWidth(maxWidth);
    	
    	ImageView img = new ImageView(btnImg);
    	img.setFitHeight(imgFitSize);
    	img.setFitWidth(imgFitSize);
    	
    	Label lbl = new Label(" " + txtLbl);
    	lbl.getStyleClass().add("label");
    	
    	menuPane.getChildren().add(img);
    	menuPane.getChildren().add(lbl);
    	menu.setGraphic(menuPane);
    	
    	menuPane.setOnMouseClicked(e);
    }
    
    @FXML
    public void initGoHome() {
    	Image img = new Image(getClass().getResource("../resources/images/icons/home.png").toExternalForm());
    	EventHandler<MouseEvent> e = new EventHandler<MouseEvent>() {
    		@Override
    		public void handle(MouseEvent event) {
    			try {
    				ScreensManager.getInstance().activate("home");
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
		};
    	createMenuItem(menu_item_1, img, "Home", 70, 25, e);
    }
    
}
