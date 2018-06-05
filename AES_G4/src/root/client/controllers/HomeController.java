package root.client.controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

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
    public void initialize() {
    	init_Clock();
    	init_Date();
    	init_DayOfTheWeek();
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
