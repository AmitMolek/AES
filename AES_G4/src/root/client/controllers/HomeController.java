package root.client.controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import ocsf.client.ObservableClient;
import root.client.managers.DataKeepManager;
import root.client.managers.HomeScreenBGLoader;
import root.client.managers.HomeScreenBGLoader.HomeScreenStages;
import root.dao.app.User;
import root.util.log.Log;
import root.util.log.LogLine.LineType;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * The controller of the home screen
 * @author Amit Molek
 *
 */

public class HomeController {

    @FXML
    private Text good_lbl;

    @FXML
    private Text stageOfDay_txt;
    
    @FXML
    private Text comma_lbl;

    @FXML
    private Text userName_txt;

    @FXML
    private BorderPane clockBorderPane;

    @FXML
    private Text clock_txt;

    @FXML
    private Text date_txt;

    @FXML
    private Text dayOfTheWeek_txt;
    
    @FXML
    private Text info_name;

    @FXML
    private Text info_id;

    @FXML
    private Text info_permission;
    
    @FXML
    private ImageView bgImg;
    
    DataKeepManager dkm = DataKeepManager.getInstance();
    
    private String pervStageOfDay;
    private String currentStageOfDay;

	private ObservableClient client;
    
	/**
	 * FXML init func
	 * Used to init the whole screen
	 */
    @FXML
    public void initialize() {
    	String serverIP = (String) dkm.getObject_NoRemove("ip");
    	 
    	if (dkm.getObject_NoRemove("client")== null) {
    	   	client = new ObservableClient(serverIP, 8000);				// opens a connection only if user exist.										// --||--
        	try {
    			client.openConnection();
    			dkm.keepObject("client", client);
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
    	
    	init_welcomeMsg();
    	init_Clock();
    	init_Date();
    	init_DayOfTheWeek();
    	init_UserNameText();
    	init_GeneralInfo();
    	init_BgImage();
    	init_setStyleClass();
    }

    /**
     * Adds a CSS class to the text objects of the screen
     * for some reason it wont work when you add it through the .fxml file ...... really ?!
     */
    private void init_setStyleClass() {
    	// WTF FXML ?!
    	// whyyyyyy fxml css not working ?!
    	good_lbl.getStyleClass().add("brightText");
    	stageOfDay_txt.getStyleClass().add("brightText");
    	comma_lbl.getStyleClass().add("brightText");
    	userName_txt.getStyleClass().add("brightText");
    	clockBorderPane.getStyleClass().add("brightText");
    	clock_txt.getStyleClass().add("brightText");
    	date_txt.getStyleClass().add("brightText");
    	dayOfTheWeek_txt.getStyleClass().add("brightText");
    	info_name.getStyleClass().add("brightText");
    	info_id.getStyleClass().add("brightText");
    	info_permission.getStyleClass().add("brightText");
    }
    
    /**
     * Init the background image of the home screen
     */
    private void init_BgImage() {
    	bgImg.setImage(HomeScreenBGLoader.getInstance().getRandomImage(getStageOfDay()));
    }
    
    /**
     * Init the user's short info
     */
    private void init_GeneralInfo() {
    	User user = DataKeepManager.getInstance().getUser();
    	try {
    		setGeneralInfoText(user.getUserFirstName() + " " + user.getUserLastName(), user.getUserID(), user.getUserPremission());
    	}catch (Exception e) {
    		Log.getInstance().writeToLog(LineType.ERROR, "User keep object is null");
    		setGeneralInfoText("", "", "");
    	}
    } 
    
    /**
     * Sets the user's short info
     * @param name the name you want to display
     * @param id the id you want to display
     * @param permission the permission you want to display
     */
    private void setGeneralInfoText(String name, String id, String permission) {
    	info_name.setText(name);
    	info_id.setText(id);
    	info_permission.setText(permission);
    }
    
    /**
     * Returns the current stage of the day
     * @return the current stage of the day (String)
     */
    private HomeScreenStages getStageOfDay() {
    	int hour = LocalTime.now().getHour();
    	if (hour >= 0 && hour < 12) {
    		return HomeScreenStages.MORNING;
    	}else if (hour == 12) {
    		return HomeScreenStages.NOON;
    	}else if (hour > 12 && hour < 18) {
    		return HomeScreenStages.AFTERNOON;
    	}else {
    		return HomeScreenStages.EVENING;
    	}
    }
    
    /**
     * Init the welcome message
     */
    private void init_welcomeMsg() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
        	String stage = getStageOfDay().toString();
        	stageOfDay_txt.setText(stage);
        	currentStageOfDay = stage;
        	
        	dkm.updateObject("currentStageOfDay", currentStageOfDay);
        	
        	if (pervStageOfDay != currentStageOfDay) {
        		pervStageOfDay = currentStageOfDay;
        		dkm.updateObject("pervStageOfDay", pervStageOfDay);
        		init_BgImage();
        	}
        }),
             new KeyFrame(Duration.minutes(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
    
    /**
     * Init the username firstname
     */
    private void init_UserNameText() {
    	User user = DataKeepManager.getInstance().getUser();
    	if (user != null)
    		userName_txt.setText(user.getUserFirstName());
    }
    
    /**
     * Init the dispaly clock of the home screen
     */
    private void init_Clock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            clock_txt.setText(LocalTime.now().format(dtf));
        }),
             new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
    
    /**
     * Init the dispaly of the date
     */
    private void init_Date() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter localizedMediumDate = DateTimeFormatter.ofPattern("dd MMM YYYY");
            date_txt.setText(LocalDate.now().format(localizedMediumDate.withLocale(Locale.US)));
        }),
             new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
    
    /**
     * Init the dispaly of the current day of the week
     */
    private void init_DayOfTheWeek() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter localizedMediumDate = DateTimeFormatter.ofPattern("EEEE");
            dayOfTheWeek_txt.setText(LocalDate.now().format(localizedMediumDate.withLocale(Locale.US)));
        }),
             new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
}