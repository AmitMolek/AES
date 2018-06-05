package root.client;
	

import javafx.scene.text.Font;
//<<<<<<< HEAD
import root.client.managers.ScreensManager;
//>>>>>>> refs/remotes/origin/Amit
import root.util.log.Log;
import root.util.log.LogLine.LineType;


public class Main extends ScreensManager {

	
	
	public static void main(String[] args) {
		Log.getInstance().writeToLog(LineType.INFO, "Application started", false);
//<<<<<<< HEAD
//		ScreensManager.addScreen("loginScreen","resources/view/LoginScreen.fxml");
//		ScreensManager.addScreen("mainWindow","resources/view/AddExamScreen.fxml");
//		ScreensManager.addScreen("questions","resources/view/Questions.fxml");	// questions
//=======
		
		Font.loadFont(Main.class.getResource("resources/fonts/Roboto-Regular.ttf").toExternalForm(), 12);
		
		ScreensManager.addScreen("main","resources/view/LoginScreen.fxml");
		//ScreensManager.addScreen("mainWindow","resources/view/MainScreen.fxml");
		ScreensManager.addScreen("home","resources/view/Home.fxml");
//>>>>>>> refs/remotes/origin/Amit
		
		launch(args);
	}
	
}
