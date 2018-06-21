package root.client;

import root.client.controllers.SolvedExamsController;
import root.client.managers.DataKeepManager;
import root.client.managers.ScreensManager;
import root.util.log.Log;
import root.util.log.LogLine.LineType;

/**
 * 
 * 
 * A main class responsible for updating the manager responsible for
 * switching between the windows and running the application
 * 
 * @author Amit Molek
 *
 */
public class Main extends ScreensManager {

	public static void main(String[] args) {
		Log log = Log.getInstance();
		DataKeepManager dkm = DataKeepManager.getInstance();
		
		log.writeToLog(LineType.INFO, "Application started", false);
		ScreensManager.addScreen("solvedExams","resources/view/ViewSolvedExams.fxml");
		ScreensManager.addScreen("questions","resources/view/Questions.fxml");		
		ScreensManager.addScreen("main","resources/view/LoginScreen.fxml");
		ScreensManager.addScreen("home","resources/view/Home.fxml");
		ScreensManager.addScreen("addExam", "resources/view/AddExamScreen.fxml");
		ScreensManager.addScreen("updateDeleteExam", "resources/view/DeleteExamScreen.fxml");
		ScreensManager.addScreen("executefull", "resources/view/ExecuteExamScreen.fxml");
		ScreensManager.addScreen("Enter4digitPassword", "resources/view/Enter4digitsPasswordScreen.fxml");
		ScreensManager.addScreen("updateExam", "resources/view/UpdateExamScreen.fxml");
		ScreensManager.addScreen("check", "resources/view/check.fxml");
		ScreensManager.addScreen("histograms", "resources/view/HistorgramsScreen.fxml");
		ScreensManager.addScreen("executeExamScreen", "resources/view/ExecuteExamScreen.fxml");
		ScreensManager.addScreen("PrepareExam", "resources/view/PerpareExamScreen.fxml");		
		ScreensManager.addScreen("manuallyExam", "resources/view/ManuallyExamScreen.fxml");
		ScreensManager.addScreen("endExam", "resources/view/ExamSubmitScreen.fxml");	
		ScreensManager.addScreen("PrepareExam", "resources/view/PerpareExamScreen.fxml");
		ScreensManager.addScreen("changeDurations", "resources/view/ChangeDurationTimeScreen.fxml");	
		ScreensManager.addScreen("testTeacherGradesStats", "resources/view/TestGradesTeacher.fxml");
		ScreensManager.addScreen("testPrincipalGradesStats", "resources/view/TestGradesPrincipal.fxml");
		ScreensManager.addScreen("teacherExamsView", "resources/view/TeacherExamsView.fxml");
		ScreensManager.addScreen("rawDataView", "resources/view/RawDataView.fxml");

		launch(args);
	}
	
}