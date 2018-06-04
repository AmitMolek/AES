package root.client;
	

import root.client.managers.ScreensManager;
import root.util.log.Log;
import root.util.log.LogLine.LineType;


public class Main extends ScreensManager {

	
	
	public static void main(String[] args) {
		Log.getInstance().writeToLog(LineType.INFO, "Application started", false);
		ScreensManager.addScreen("loginScreen","resources/view/LoginScreen.fxml");
		ScreensManager.addScreen("mainWindow","resources/view/AddExamScreen.fxml");
		
		launch(args);
	}
	
}
