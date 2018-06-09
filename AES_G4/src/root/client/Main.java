package root.client;
	

import javafx.scene.text.Font;
//<<<<<<< HEAD
import root.client.managers.ScreensManager;
//>>>>>>> refs/remotes/origin/Amit
import root.util.log.Log;
import root.util.log.LogLine.LineType;


public class Main extends ScreensManager {

	public static void main(String[] args) {
		Log log = Log.getInstance();
		log.writeToLog(LineType.INFO, "Application started", false);
		
		//ScreensManager.addScreen("menu","resources/view/Menu.fxml");
		ScreensManager.addScreen("aqw","resources/view/AddQuestionWizzard.fxml");
		ScreensManager.addScreen("main","resources/view/LoginScreen.fxml");
		ScreensManager.addScreen("home","resources/view/Home.fxml");

		
		launch(args);
	}
	
}
