package root.client;
	

import javafx.scene.text.Font;
import root.client.managers.ScreensManager;
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
		ScreensManager.addScreen("addExam", "resources/view/AddExamScreen.fxml");
		ScreensManager.addScreen("updateDeleteExam", "resources/view/DeleteExamScreen.fxml");
		ScreensManager.addScreen("executeExam", "resources/view/ExecuteExamScreen.fxml");
		ScreensManager.addScreen("Enter4digitPassword", "resources/view/Enter4digitsPasswordScreen.fxml");
		

		ScreensManager.addScreen("updateExam", "resources/view/UpdateExamScreen.fxml");
		

		launch(args);
	}
	
}