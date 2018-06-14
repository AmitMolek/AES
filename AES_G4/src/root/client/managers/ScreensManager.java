package root.client.managers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import ocsf.client.ObservableClient;
import root.client.Main;
import root.dao.app.ScreenObject;
import root.dao.app.User;
import root.dao.message.LoggedOutMessage;
import root.util.log.Log;
import root.util.log.LogLine;
import root.util.log.LogLine.LineType;

/**
 * Manages the screens in the app, mostly the transition between them
 * @author Naor Saadia
 *
 */
public class ScreensManager extends Application {
	
	private static HashMap<String, String> screenMap = new HashMap<>();
    private static Stage primaryStage = null;
    
    private Stack<ScreenObject> screenStack = new Stack<ScreenObject>();
    private ScreenObject currentScreen;

    private final String cssPath = "resources/css/materialDesign.css";
    private final String menuFxmlPath = "resources/view/Menu.fxml";
    private final String utilityBarPath = "resources/view/UtilityBar.fxml";
    
    private final String aesIconPath = "/root/client/resources/images/icons/book.png";
    
    private Log log = Log.getInstance();
    private DataKeepManager dkm = DataKeepManager.getInstance();
    
    private static ScreensManager INSTANCE = new ScreensManager();
    
   /**
    * This class is a singleton
    * @return the instance of the class
    */
    public static ScreensManager getInstance() {
    	return INSTANCE;
    }
    	
    /**
     * Static method for add new screen to the singleton class
     * @param name get the name of the class
     * @param path get the path of the fxml file
     * @throws IOException 
     */
    public static void addScreen(String name, String path) {
    	screenMap.put(name, path);
    }
    
    /**
     *Static method for removing screen from screenList
     * @param name
     */
    protected void removeScreen(String name){
        screenMap.remove(name);
    }
    
    /**
     * Init the primary stage
     */
    private void init_PrimaryStage() {
    	primaryStage.setResizable(false);
    	primaryStage.setTitle("AES");
    	primaryStage.getIcons().add(new Image(aesIconPath));
    	primaryStage.initStyle(StageStyle.UNDECORATED);
    }
    
    /**
     * Returns the size of the screen stack
     * @return size of the screens stack
     */
    public int getScreenStackSize() {
    	return screenStack.size();
    }
    
    /**
     * Returns to the previous screen if possible
     * @throws IOException
     */
    public void returnToPrevScreen() throws IOException {
    	if(screenStack.size() > 1) {
	    	screenStack.pop();
	    	ScreenObject screenObj = screenStack.pop();
	    	if (screenObj != null)
	    		activate(screenObj);
    	}
    }
    
    /**
     * Clears the screen stack
     */
    public void clearStack() {
    	screenStack.clear();
    }
    
    /**
     * Adds a screen to the saving stack
     * @param screenObj the screen you want to add to the stack
     */
    public void addToStack(ScreenObject screenObj) {
    	String screenName = screenObj.getScreenName();
    	if (screenName != "main")
    		screenStack.push(screenObj);
    }
    
    /**
     * Activate the screen with the key name
     * @param name the key of the screen you want to activate
     * @throws IOException
     */
    public void activate(String name) throws IOException
    {
    	FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(screenMap.get(name)));
    	Pane screen = fxmlLoader.load();
    	activate(new ScreenObject(name, screen));
    	//activate(screenMap.get(name));
    }
    
    /**
     * Activate the screenObj
     * @param screenObj the screen object you want to activate
     * @throws IOException
     */
    public void activate(ScreenObject screenObj) throws IOException{
    	VBox sRoot = new VBox();
    	boolean isFullscreenScreen = screenObj.getScreenName().contains("full");
    	
    	FXMLLoader utilityBarFxml = new FXMLLoader(Main.class.getResource(utilityBarPath));
    	Pane utilityBarMenu = utilityBarFxml.load();
    	sRoot.getChildren().add(utilityBarMenu);
    	
    	if (screenObj.getScreenName() != "main" && !isFullscreenScreen) {
	    	FXMLLoader menuFxml = new FXMLLoader(Main.class.getResource(menuFxmlPath));
	    	Pane menu = menuFxml.load();
	    	sRoot.getChildren().add(menu);
    	}
    	
    	addToStack(screenObj);
    	currentScreen = screenObj;
		log.writeToLog(LineType.INFO, "Added screen to stack: " + screenObj.getScreenName());
    	
    	sRoot.getChildren().add(screenObj.getScreenPane());
    	
    	Scene scene = new Scene(sRoot, 1280, 720);
    	if (scene.getStylesheets().isEmpty())
    		scene.getStylesheets().add(Main.class.getResource(cssPath).toExternalForm());
    	primaryStage.setScene(scene);
    	if (isFullscreenScreen) {
    		AnchorPane fullRoot = new AnchorPane();
    		fullRoot.getChildren().add(screenObj.getScreenPane());
    		screenObj.getScreenPane().prefHeightProperty().bind(fullRoot.heightProperty());
    		screenObj.getScreenPane().prefWidthProperty().bind(fullRoot.widthProperty());
    		Scene fullScene = new Scene(fullRoot);
    		fullScene.getStylesheets().add(Main.class.getResource(cssPath).toExternalForm());
    		primaryStage.setScene(fullScene);
    		primaryStage.setFullScreenExitHint("");
    		primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
    		primaryStage.setFullScreen(true);
    		primaryStage.setFullScreen(true);
    		primaryStage.setResizable(false);

    	}
    	
    	primaryStage.show();
    }
    
    /**
     * Creates a thread that will switch to the screen with the screenName key
     * @param screenName the key of the screen you want to switch to
     */
    public void switchScreen(String screenName) {
    	if (!screenMap.containsKey(screenName)) return;
    	Platform.runLater(() -> {
			try {
				if (currentScreen.getScreenName() != screenName)
					activate(screenName);
			} catch (Exception e) {
				e.printStackTrace();
				log.writeToLog(LineType.ERROR, "Failed switching screens, Screen name: " + screenName);
			}
		});
    }
    
    /**
     * Method that called where the main call launch 
     */
    @Override
	public void start(Stage primaryStage) throws Exception {
		try {
			ScreensManager.primaryStage = primaryStage;
			init_PrimaryStage();
			activate("main");
			
			// when pressed "Close" button, kill thread and exit app.
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			       @Override
			       public void handle(WindowEvent e) {
			    	   
			    	   User user = dkm.getUser();
			    	   if (user != null) {
			    		   ObservableClient client = (ObservableClient)dkm.getObject_NoRemove("client");
			    		   LoggedOutMessage msg = new LoggedOutMessage(user.getUserID());
			    	   	   try {
							client.sendToServer(msg);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
			    	   }
			          Platform.exit();
			          System.exit(0);
			       }
			    });
			
		} catch(Exception e) {
			log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
		}
	}
    
    /**
     * Returns the primary stage
     * @return primary stage
     */
    public Stage getPrimaryStage() {
    	return primaryStage;
    }
    
}


