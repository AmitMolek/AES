package root.client.managers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @author Naor Saadia
 *
 */
public class ScreensManager extends Application {

	    private static HashMap<String, String> screenMap = new HashMap<>();
	    private static Stage primaryStage;
	    private Stack<Scene> sceneStack = new Stack<Scene>();
	    private double height=400;
	    private double width=400;
	    
	    
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
	    
	    protected void activate(String name) throws IOException
	    {
	    	if(primaryStage.getScene()!=null)	
	    		sceneStack.add(primaryStage.getScene());
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(screenMap.get(name)));
			AnchorPane root = (AnchorPane)fxmlLoader.load();
			height = primaryStage.getHeight();
			width = primaryStage.getWidth();
			primaryStage.setHeight(height);
			primaryStage.setWidth(width);			
			Scene scene = new Scene(root,height,width);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
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
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}
    
    
