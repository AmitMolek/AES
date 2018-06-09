package root.client.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import javafx.application.Platform;
import javafx.event.ActionEvent;
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
import root.client.Main;
import root.client.managers.DataKeepManager;
import root.client.managers.ScreensManager;
import root.dao.app.ScreenObject;
import root.dao.app.User;
import root.util.log.Log;
import root.util.log.LogLine.LineType;

public class MenuController {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Menu menu_item_0;
    
    @FXML
    private Menu menu_item_1;

    @FXML
    private MenuItem menu_item_1_1;
    
    @FXML
    private ImageView homeImg;
    
    private User user;
    
    private ScreensManager screensMgr;
    private Log log;
    
    private final String homeIconPath = "../resources/images/icons/home.png";
    private final String returnIconPath = "../resources/images/icons/back_arrow.png";
    
    @FXML
	public void initialize() throws IOException{
    	user = DataKeepManager.getInstance().getUser();
    	screensMgr = ScreensManager.getInstance();
    	log = Log.getInstance();

    	initReturnMenu();
    	initGoHome();
    	
    	if (user.getUserPremission().equals("Teacher")) {
    		initGoOther();
    	}
    }
    
    public void initGoOther() {
    	Menu other = new Menu();
    	
    	Image img = new Image(getClass().getResource(homeIconPath).toExternalForm());
    	createMenuItem(other, img, "Other", 70, 25);
    	
    	other.getItems().add(createMenuItem("1", getChangeScreenActionEvent("aqw")));
    	other.getItems().add(createMenuItem("2", getChangeScreenActionEvent("aqw")));
    	other.getItems().add(createMenuItem("3", getChangeScreenActionEvent("aqw")));

    }
    
    private MenuItem createMenuItem(String txtLbl, EventHandler<ActionEvent> e) {
    	MenuItem item = new MenuItem(txtLbl);
    	item.setOnAction(e);
    	return item;
    } 
    
    private FlowPane createMenu_Structure(Menu menu, Image btnImg, String txtLbl, double maxWidth, double imgFitSize) {
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
    	return menuPane;
    }
    
    private void createMenuItem(Menu menu, Image btnImg, String txtLbl, double maxWidth, double imgFitSize) {
    	createMenu_Structure(menu, btnImg, txtLbl, maxWidth, imgFitSize);
    	menuBar.getMenus().add(menu);
    }
    
    private void createMenuItem(Menu menu, Image btnImg, String txtLbl, double maxWidth, double imgFitSize, EventHandler<MouseEvent> e) {
    	FlowPane menuPane = createMenu_Structure(menu, btnImg, txtLbl, maxWidth, imgFitSize);
    	
    	menuPane.setOnMouseClicked(e);
    	menuBar.getMenus().add(menu);
    }
    
    public void initReturnMenu() {
    	Image img = new Image(getClass().getResource(returnIconPath).toExternalForm());
    	
    	EventHandler<MouseEvent> e = new EventHandler<MouseEvent>() {
    		@Override
    		public void handle(MouseEvent event) {
    			try {
    				screensMgr.returnToPrevScreen();
				} catch (IOException e) {
					log.writeToLog(LineType.ERROR, "Failed returning to previous screen");
					e.printStackTrace();
				}
    		}
		};
    	
		Menu returnMenu = new Menu();
    	createMenuItem(returnMenu, img, "", 30, 25, e);
    	if (screensMgr.getScreenStackSize() < 1) {
    		returnMenu.setDisable(true);
    	}
    }
    
    public void initGoHome() {
    	Image img = new Image(getClass().getResource(homeIconPath).toExternalForm());
    	EventHandler<MouseEvent> e = getChangeScreenMouseEvent("home");
    	createMenuItem(new Menu(), img, "Home", 70, 25, e);
    }
    
    private EventHandler<ActionEvent> getChangeScreenActionEvent(String screenName){
    	EventHandler<ActionEvent> e = new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			screensMgr.switchScreen(screenName);
    		}
		};
		return e;
    }
    
    private EventHandler<MouseEvent> getChangeScreenMouseEvent(String screenName){
    	EventHandler<MouseEvent> e = new EventHandler<MouseEvent>() {
    		@Override
    		public void handle(MouseEvent event) {
    			screensMgr.switchScreen(screenName);
    		}
		};
		return e;
    }
    
}
