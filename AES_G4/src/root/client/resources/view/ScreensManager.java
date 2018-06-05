package root.client.resources.view;

import java.io.IOException;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashMap;
import java.util.Stack;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import root.client.Main;

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
	    
	    public void activate(String name) throws IOException
	    {
	    	VBox sRoot = new VBox();
	    	
	    	FXMLLoader menuFxml = new FXMLLoader(Main.class.getResource("resources/view/Menu.fxml"));
	    	Pane menu = menuFxml.load();
	    	sRoot.getChildren().add(menu);
	    	
	    	FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(screenMap.get(name)));
	    	Pane screen = fxmlLoader.load();
	    	sRoot.getChildren().add(screen);
	    	
	    	primaryStage.setResizable(false);
	    	primaryStage.setTitle("AES");
	    	primaryStage.getIcons().add(new Image("/root/client/resources/images/icons/book.png"));
	    	
	    	Scene scene = new Scene(sRoot, 1280, 720);
	    	if (scene.getStylesheets().isEmpty())
	    		scene.getStylesheets().add(Main.class.getResource("resources/css/materialDesign.css").toExternalForm());
	    	
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
				e.printStackTrace();
			}
		}
		
	}
    
    
