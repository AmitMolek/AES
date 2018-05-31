package aes_Root;
	


import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Questions.fxml"));
			AnchorPane root = (AnchorPane)fxmlLoader.load();
			Scene scene = new Scene(root,1024,700);
			primaryStage.setTitle("AES");
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(new javafx.scene.image.Image("/application/icons/Categories-applications-education-university-icon.png"));
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
