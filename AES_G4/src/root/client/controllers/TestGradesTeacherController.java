package root.client.controllers;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import ocsf.client.ObservableClient;
import root.client.managers.DataKeepManager;
import root.client.managers.ScreensManager;
import root.dao.app.ExamTableDataLine;
import root.dao.app.User;
import root.dao.message.AbstractMessage;
import root.dao.message.ExamDataLinesMessage;
import root.dao.message.MessageFactory;
import root.dao.message.StatsMessage;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;

public class TestGradesTeacherController implements Observer {
	@FXML
	private AnchorPane anchorPane;
	@FXML
	private TableView<ExamTableDataLine> table;
	@FXML
	private TableColumn<ExamTableDataLine,String> examNumCol;
	@FXML
	private TableColumn<ExamTableDataLine,String> dateCol;
	@FXML
	private TableColumn<ExamTableDataLine,String> subjectCol;
	@FXML
	private TableColumn<ExamTableDataLine,String> courseCol;
	@FXML
	private Button viewReportButton;
	
	private ObservableClient client;	

	
	@FXML
	public void initialize() throws IOException {
		client = (ObservableClient)DataKeepManager.getInstance().getObject_NoRemove("client");// get the client from DataKeep, but dont remove it from there, for later use.
    	client.addObserver(this);
    	examNumCol.setCellValueFactory(
    		    new PropertyValueFactory<ExamTableDataLine,String>("examID"));
    	dateCol.setCellValueFactory(
    		    new PropertyValueFactory<ExamTableDataLine,String>("date"));
    	subjectCol.setCellValueFactory(
    			new PropertyValueFactory<ExamTableDataLine,String>("subjectName"));
    	courseCol.setCellValueFactory(
    			new PropertyValueFactory<ExamTableDataLine,String>("courseName"));
    	User user =(User)DataKeepManager.getInstance().getUser();
    	AbstractMessage msg=MessageFactory.getInstance().getMessage("get-examtabledataline",user);
    	client.sendToServer(msg);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof AbstractMessage) {
			if(((AbstractMessage) arg).getMsg().equals("ok-get-examtabledataline")) {
				ExamDataLinesMessage examMessage=(ExamDataLinesMessage) arg;
				ObservableList<ExamTableDataLine> examsList=FXCollections.observableArrayList();
				examsList.addAll(examMessage.getLines());
				table.setItems(examsList);
			}
			else if(((AbstractMessage) arg).getMsg().equals("ok-get-examstatsbyiddate")) {
				StatsMessage statsMessage=(StatsMessage)arg;
				DataKeepManager.getInstance().updateObject("statsForHistogram", statsMessage.getStats());
				Platform.runLater(() -> {				// In order to run javaFX thread.(we recieve from server a java thread)
						try {
							ScreensManager.getInstance().activate("histograms");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					});
			}
		}
	}
	
	@FXML
    void viewReport(ActionEvent event) {
		ExamTableDataLine line=table.getSelectionModel().getSelectedItem();
		if(line!=null) {
			String[] arr=new String[2];
			arr[0]=line.getExamID();
			arr[1]=line.getDate();
			DataKeepManager.getInstance().updateObject("titleForHistogram", "Report for "+line.getCourseName()+" exam on "+line.getDate());
	    	AbstractMessage msg=MessageFactory.getInstance().getMessage("get-examstatsbyiddate",arr);
	    	try {
				client.sendToServer(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		}
	}