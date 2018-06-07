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
		
		ScreensManager.addScreen("questions","resources/view/Questions.fxml");	// questions
		ScreensManager.addScreen("main","resources/view/LoginScreen.fxml");
		ScreensManager.addScreen("home","resources/view/Home.fxml");
		ScreensManager.addScreen("addExam", "resources/view/AddExamScreen.fxml");
		ScreensManager.addScreen("updateDeleteExam", "resources/view/DeleteExamScreen.fxml");

	
		launch(args);
	}
	
}
