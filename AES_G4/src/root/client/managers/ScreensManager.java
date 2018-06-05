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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import root.client.Main;
import root.util.log.Log;
import root.util.log.LogLine;

/**
 * @author Naor Saadia
 *
 */
public class ScreensManager extends Application {

	    private static HashMap<String, String> screenMap = new HashMap<>();
	    private static Stage primaryStage=null;
	    private Stack<Scene> sceneStack = new Stack<Scene>();
	    private double height=400;
	    private double width=400;
	    Log log = Log.getInstance();
	    
	    
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
	     */
	    public static void addScreen(String name, String path){
	         screenMap.put(name, path);
	    }
	    
	    /**
	     *Static method for removing screen from screenList
	     * @param name
	     */
	    protected void removeScreen(String name){
	        screenMap.remove(name);
	    }
	    
	    public void activate(String name) throws IOException
	    {
	    	if(primaryStage.getScene()!=null)	
	    		sceneStack.add(primaryStage.getScene());
	    	FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(screenMap.get(name)));
	    	AnchorPane root = (AnchorPane)fxmlLoader.load();
	    	primaryStage.setResizable(false);
	    	primaryStage.setTitle("AES_G4");
	    	if(primaryStage.getIcons().size() ==0)
	    		primaryStage.getIcons().add(new Image("/root/client/resources/images/Categories-applications-education-university-icon.png"));
			height = primaryStage.getHeight();
			width = primaryStage.getWidth();
			Scene scene = new Scene(root,1024,720);
			scene.getStylesheets().add(Main.class.getResource("resources/css/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

	    }
	    
		
	    /**
	     * Method that called where the main call launch 
	     */
	    @Override
		public void start(Stage primaryStage) throws Exception {
			try {
				ScreensManager.primaryStage = primaryStage;
				activate("main");
				
				// when pressed "Close" button, kill thread and exit app.
				primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				       @Override
				       public void handle(WindowEvent e) {
				          Platform.exit();
				          System.exit(0);
				       }
				    });
				
			} catch(Exception e) {
				log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
			}
		}
	    
	    public Stage getPrimaryStage() {
	    	return primaryStage;
	    }
		
	}
    
    
