package root.client;
	

import javafx.scene.text.Font;
import root.client.managers.ScreensManager;
import root.util.log.Log;
import root.util.log.LogLine.LineType;


public class Main extends ScreensManager {

	public static void main(String[] args) {
		Log log = Log.getInstance();
		log.writeToLog(LineType.INFO, "Application started", false);
		
		ScreensManager.addScreen("questions","resources/view/Questions.fxml");		
		ScreensManager.addScreen("main","resources/view/LoginScreen.fxml");
		ScreensManager.addScreen("home","resources/view/Home.fxml");
		ScreensManager.addScreen("addExam", "resources/view/AddExamScreen.fxml");
		ScreensManager.addScreen("updateDeleteExam", "resources/view/DeleteExamScreen.fxml");
		ScreensManager.addScreen("executefull", "resources/view/ExecuteExamScreen.fxml");
		ScreensManager.addScreen("Enter4digitPassword", "resources/view/Enter4digitsPasswordScreen.fxml");
		ScreensManager.addScreen("updateExam", "resources/view/UpdateExamScreen.fxml");
		ScreensManager.addScreen("histograms", "resources/view/HistorgramsScreen.fxml");
		ScreensManager.addScreen("PrepareExam", "resources/view/PerpareExamScreen.fxml");

		launch(args);
	}
	
}