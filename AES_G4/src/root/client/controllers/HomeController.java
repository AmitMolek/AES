package root.client.controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import root.client.Main;
import root.client.managers.DataKeepManager;
import root.client.managers.ImageLoader;
import root.client.managers.ScreensManager;
import root.dao.app.User;
import root.util.log.Log;
import root.util.log.LogLine.LineType;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;

public class HomeController {

    @FXML
    private AnchorPane anchorPane;

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
    
    @FXML
    public void initialize() {
    	init_welcomeMsg();
    	init_Clock();
    	init_Date();
    	init_DayOfTheWeek();
    	init_UserNameText();
    	init_GeneralInfo();
    	init_BgImage();
    	init_setStyleClass();
    }

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
    
    private void init_BgImage() {
    	String mainPath = "src/root/client/resources/images/bg/home/";
    	try {
    		ArrayList<Image> images = ImageLoader.loadImagesFromFolder(mainPath + getStageOfDay().toLowerCase());
    		Random rnd = new Random();
    		int rndImg = rnd.nextInt(images.size());
    		bgImg.setImage(images.get(rndImg));
    	}catch (Exception e) {
    		Log.getInstance().writeToLog(LineType.ERROR, "Tried loading background images.");
    		e.printStackTrace();
    	}
    }
    
    private void init_GeneralInfo() {
    	User user = DataKeepManager.getInstance().getUser();
    	try {
    		setGeneralInfoText(user.getUserFirstName() + " " + user.getUserLastName(), user.getUserID(), user.getUserPremission());
    	}catch (Exception e) {
    		Log.getInstance().writeToLog(LineType.ERROR, "User keep object is null");
    		setGeneralInfoText("", "", "");
    	}
    } 
    
    private void setGeneralInfoText(String name, String id, String permission) {
    	info_name.setText(name);
    	info_id.setText(id);
    	info_permission.setText(permission);
    }
    
    private String getStageOfDay() {
    	int hour = LocalTime.now().getHour();
    	if (hour >= 0 && hour < 12) {
    		return "Morning";
    	}else if (hour == 12) {
    		return "Noon";
    	}else if (hour > 12 && hour < 18) {
    		return "Afternoon";
    	}else {
    		return "Evening";
    	}
    	/*
    	int sec = LocalTime.now().getSecond();
    	if (sec >= 0 & sec < 10) {
    		return "Morning";
    	}else if (sec == 10) {
    		return "Noon";
    	}else if (sec > 10 && sec < 20) {
    		return "Afternoon";
    	}else {
    		return "Evening";
    	}
    	*/
    }
    
    private void init_welcomeMsg() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
        	String stage = getStageOfDay();
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
    
    private void init_UserNameText() {
    	User user = DataKeepManager.getInstance().getUser();
    	if (user != null)
    		userName_txt.setText(user.getUserFirstName());
    }
    
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
