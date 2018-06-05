package root.client;
	

import java.io.IOException;

import javafx.scene.text.Font;
import root.client.resources.view.ScreensManager;
import root.util.log.Log;
import root.util.log.LogLine.LineType;


public class Main extends ScreensManager {

	
	
	public static void main(String[] args) {
		Log.getInstance().writeToLog(LineType.INFO, "Application started", false);
		
		Font.loadFont(Main.class.getResource("resources/fonts/Roboto-Regular.ttf").toExternalForm(), 12);
		
		ScreensManager.addScreen("main","resources/view/LoginScreen.fxml");
		//ScreensManager.addScreen("mainWindow","resources/view/MainScreen.fxml");
		ScreensManager.addScreen("home","resources/view/Home.fxml");
		
		launch(args);
	}
	
}
