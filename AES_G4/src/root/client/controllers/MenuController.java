package root.client.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import root.client.managers.DataKeepManager;
import root.client.managers.ScreensManager;
import root.dao.app.User;
import root.util.log.Log;
import root.util.log.LogLine.LineType;

/**
 * The controller class of the menu screen
 * @author Amit Molek
 *
 */

public class MenuController {

    @FXML
    private MenuBar menuBar;

    private User user;
    
    private ScreensManager screensMgr;
    private Log log;
    
    private final String homeIconPath = "../resources/images/icons/home.png";
    private final String returnIconPath = "../resources/images/icons/back_arrow.png";
    private final String helpIconPath = "../resources/images/icons/help.png";
    
    private final String aboutUsNames = "Group 4\nGal Brandwine\nAlon Ben-yosef\nNaor Saadia\nAmit Molek\nOmer Haimovich";
    
    @FXML
	public void initialize() throws IOException{
    	user = DataKeepManager.getInstance().getUser();
    	screensMgr = ScreensManager.getInstance();
    	log = Log.getInstance();

    	initMenuBtn();
    }
    
    /**
     * Calls all the creation functions of the menu buttons
     */
    public void initMenuBtn() {
    	initReturnMenu();
    	initGoHome();
    	
    	if (user.getUserPremission().equals("Teacher")) {
    		initGoOther();
    	}
    	
    	initGoHelp();
    }
    
    /**
     * Creates the return menu object
     */
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
    
    /**
     * Creates the home menu object
     */
    public void initGoHome() {
    	Image img = new Image(getClass().getResource(homeIconPath).toExternalForm());
    	EventHandler<MouseEvent> e = getChangeScreenMouseEvent("home");
    	createMenuItem(new Menu(), img, "Home", 70, 25, e);
    }
    
    /**
     * Init the help menu object, with about us
     */
    public void initGoHelp() {
    	Menu help = new Menu();
    	
    	Image img = new Image(getClass().getResource(helpIconPath).toExternalForm());
    	createMenuItem(help, img, "Help", 70, 25);
    	
    	EventHandler<ActionEvent> e = new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			Stage aboutStage = new Stage();
    			VBox comp = new VBox();
    			
    			Image img = new Image(getClass().getResource(helpIconPath).toExternalForm());
    			
    			Label aboutGroup = new Label();
    			aboutGroup.setText(aboutUsNames);
    			aboutGroup.setTextAlignment(TextAlignment.CENTER);
    			comp.setAlignment(Pos.CENTER);
    			comp.getChildren().add(aboutGroup);
    			
    			Scene aboutStageScene = new Scene(comp, 300, 125);
    			aboutStage.setScene(aboutStageScene);
    			aboutStage.setTitle("About Us");
    			aboutStage.getIcons().add(img);
    			aboutStage.setResizable(false);
    			aboutStage.show();
    		}
		};
    	
    	help.getItems().add(createMenuItem("About Us", e));
    }
    
    // Just to show how to create a menu object with multpie menu items
    public void initGoOther() {
    	Menu other = new Menu();
    	
    	Image img = new Image(getClass().getResource(homeIconPath).toExternalForm());
    	createMenuItem(other, img, "Other", 70, 25);
    	
    	other.getItems().add(createMenuItem("1", getChangeScreenActionEvent("aqw")));
    	other.getItems().add(createMenuItem("2", getChangeScreenActionEvent("aqw")));
    	other.getItems().add(createMenuItem("3", getChangeScreenActionEvent("aqw")));

    }
    
    /**
     * Returns a MenuItem with txtLbl text, and a event when you action click it
     * @param txtLbl the text you want the menu item to display
     * @param e the event you want to happen when you click the menu item
     * @return the MenuItem with txtLbl and e event
     */
    private MenuItem createMenuItem(String txtLbl, EventHandler<ActionEvent> e) {
    	MenuItem item = new MenuItem(txtLbl);
    	item.setOnAction(e);
    	return item;
    } 
    
    /**
     * Build a new FlowPane with an img and text (sets the menu object design)
     * @param menu the menu you want to "design"
     * @param btnImg the icon you want to display in the menu object
     * @param txtLbl the text you want the menu to display
     * @param maxWidth the maximum width of the menu object
     * @param imgFitSize the size of the icon
     * @return FlowPane with all the objects attached to it
     */
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
    
    /**
     * Creates a menu object that and adds it to the menu bar (not a clickable menu object)
     * @param menu the menu object you want to add to the menu bar
     * @param btnImg the icon you want to display in the menu object
     * @param txtLbl the text you want the menu to display
     * @param maxWidth the maximum width of the menu object
     * @param imgFitSize the size of the icon
     */
    private void createMenuItem(Menu menu, Image btnImg, String txtLbl, double maxWidth, double imgFitSize) {
    	createMenu_Structure(menu, btnImg, txtLbl, maxWidth, imgFitSize);
    	menuBar.getMenus().add(menu);
    }
    
    /**
     * Creates a clickable menu object and adds it to the menu bar
     * @param menu the menu object you want to add to the menu bar
     * @param btnImg the icon you want to display in the menu object
     * @param txtLbl the text you want the menu to display
     * @param maxWidth the maximum width of the menu object
     * @param imgFitSize the size of the icon
     * @param e the event you want to happen when you click the menu object
     */
    private void createMenuItem(Menu menu, Image btnImg, String txtLbl, double maxWidth, double imgFitSize, EventHandler<MouseEvent> e) {
    	FlowPane menuPane = createMenu_Structure(menu, btnImg, txtLbl, maxWidth, imgFitSize);
    	
    	menuPane.setOnMouseClicked(e);
    	menuBar.getMenus().add(menu);
    }
    
    /**
     * Return an action event that switches between screens
     * @param screenName the screen name you want to switch to
     * @return action event of the screen switching
     */
    private EventHandler<ActionEvent> getChangeScreenActionEvent(String screenName){
    	EventHandler<ActionEvent> e = new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			screensMgr.switchScreen(screenName);
    		}
		};
		return e;
    }
    
    /**
     * Returns an mouse event that switches between screens
     * @param screenName the screen name you want to switch to
     * @return mouse event of the screen switching
     */
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